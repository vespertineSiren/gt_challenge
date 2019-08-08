package dev.vespertine.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.geojson.Feature
import com.mapbox.geojson.FeatureCollection
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.PropertyFactory
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import dagger.android.AndroidInjection
import dev.vespertine.myapplication.R
import dev.vespertine.myapplication.adapter.PinAdapter
import dev.vespertine.myapplication.model.PinData
import dev.vespertine.myapplication.view_model.PinViewModel
import dev.vespertine.myapplication.view_model.PinViewModelFactory
import kotlinx.android.synthetic.main.content_base.*
import javax.inject.Inject

const val MARKER_SOURCE : String = "markers-source"
const val MARKER_STYLE_LAYER = "markers-style-layer"
const val MARKER_IMAGE  = "custom-marker"

class PinActivity : AppCompatActivity(), PermissionsListener, OnMapReadyCallback{

    @Inject
    lateinit var pinviewmodelFactory: PinViewModelFactory
    lateinit var pinViewModel: PinViewModel
    lateinit var pinAdapter : PinAdapter
    lateinit var permissionManager: PermissionsManager
    private lateinit var map : MapboxMap
    lateinit var locationComponent : LocationComponent
    lateinit var userLatLng: LatLng


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.access_token))
        setContentView(R.layout.activity_base)
        AndroidInjection.inject(this)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        initRecyclerView()

        observePins()

        b_my_location.setOnClickListener {
            if(this::userLatLng.isInitialized){
                val position = CameraPosition.Builder()
                    .target(userLatLng)
                    .zoom(17.0)
                    .build()

                map.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000)
                changeUIandMap()
            }
        }
    }

    fun observePins() {
        pinViewModel = ViewModelProviders.of(this, pinviewmodelFactory)
            .get(PinViewModel::class.java)

        pinViewModel.loadPins()

        pinViewModel.getPins().observe(this, Observer<List<PinData>> {
            if (it != null) {
                pinAdapter.setPins(it)
                rv_pin.adapter = pinAdapter
            }
        })

        pinViewModel.getpickedPin().observe(this, Observer<PinData> {
            if(it != null) {
                changeUIandMap(it)
                val position = CameraPosition.Builder()
                    .target(LatLng(it.latitude, it.longitude))
                    .zoom(17.0)
                    .build()

                map.animateCamera(CameraUpdateFactory.newCameraPosition(position), 3000)
            }
        })
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        map = mapboxMap

        map.setStyle(Style.MAPBOX_STREETS) {
            style ->
            style.addImage(MARKER_IMAGE, BitmapFactory.decodeResource(resources,R.drawable.pin))

            addPinsToMap(style)

            enableLocationComponent(style)
        }
    }

    private fun addPinsToMap(mapStyle: Style) {

        pinViewModel.getPinPoints().observe(this, Observer<List<Feature>> {
            it?.apply {
                mapStyle.addSource(GeoJsonSource(MARKER_SOURCE, FeatureCollection.fromFeatures(it)))
            }
        })

        mapStyle.addLayer(SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
            .withProperties(
                PropertyFactory.iconAllowOverlap(true),
                PropertyFactory.iconIgnorePlacement(true),
                PropertyFactory.iconImage(MARKER_IMAGE),
                PropertyFactory.iconOffset(arrayOf(0f, 0f))
            )
        )
    }

    @SuppressLint("MissingPermission")
    fun enableLocationComponent(mapStyle: Style){

        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            val lcOptions = LocationComponentOptions.builder(this)
                .elevation(5f)
                .foregroundDrawable(R.drawable.franxx)
                .build()

            val lcAOption = LocationComponentActivationOptions
                .builder(this, mapStyle)
                .locationComponentOptions(lcOptions)
                .build()

            locationComponent  = map.locationComponent

            locationComponent.apply {
                activateLocationComponent(lcAOption)
                isLocationComponentEnabled = true
                renderMode = RenderMode.COMPASS
            }

            val locManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val locUser = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            userLatLng = LatLng(locUser.latitude, locUser.longitude)

        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
    }

    fun initRecyclerView() {
        pinAdapter = PinAdapter(mutableListOf()) {
            pinViewModel.setPickedPin(it)
        }
        rv_pin.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_pin.adapter = pinAdapter
    }

    private fun changeUIandMap(focusPin: PinData? = null) {

        if(focusPin != null) {
            tv_pin_name_clicked.text = focusPin.name
            tv_pin_lat_clicked.text = focusPin.latitude.toString()
            tv_pin_long_clicked.text = focusPin.longitude.toString()
            tv_pin_desc_clicked.text = focusPin.description
        } else {
            tv_pin_name_clicked.text = getString(R.string.click_name)
            tv_pin_lat_clicked.text = userLatLng.latitude.toString()
            tv_pin_long_clicked.text = userLatLng.longitude.toString()
            tv_pin_desc_clicked.text = getString(R.string.click_loc_desc)
        }
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
    }

    override fun onPermissionResult(granted: Boolean) {
        if(granted){
           map.getStyle {
               enableLocationComponent(it)
            }
        } else {
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}


