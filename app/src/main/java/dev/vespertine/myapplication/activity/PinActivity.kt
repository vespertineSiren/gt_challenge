package dev.vespertine.myapplication.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.gms.location.LocationRequest
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.location.LocationEngineRequest
import com.mapbox.android.core.location.LocationEngineResult
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.CompassEngine
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions
import dagger.android.AndroidInjection
import dev.vespertine.myapplication.R
import dev.vespertine.myapplication.adapter.PinAdapter
import dev.vespertine.myapplication.model.PinData
import dev.vespertine.myapplication.view_model.PinViewModel
import dev.vespertine.myapplication.view_model.PinViewModelFactory
import kotlinx.android.synthetic.main.content_base.*
import com.mapbox.mapboxsdk.plugins.annotation.Symbol
import javax.inject.Inject


class PinActivity : AppCompatActivity(), PermissionsListener, OnMapReadyCallback {


    @Inject
    lateinit var pinviewmodelFactory: PinViewModelFactory
    lateinit var pinViewModel: PinViewModel
    lateinit var pinAdapter : PinAdapter
    lateinit var permissionManager: PermissionsManager
    lateinit var map : MapboxMap
    lateinit var symbolManager: SymbolManager
    private var symbol: Symbol? = null
    lateinit var locationComponent : LocationComponent
    lateinit var locationEngine: LocationEngine


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, getString(R.string.access_token))
        setContentView(R.layout.activity_base)
        AndroidInjection.inject(this)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)










        initRecyclerView()

        pinViewModel = ViewModelProviders.of(this, pinviewmodelFactory)
            .get(PinViewModel::class.java)

        pinViewModel.loadPins()

        pinViewModel.getPins().observe(this, Observer<List<PinData>> {
                if (it != null) {
                    pinAdapter.setPins(it)
                    rv_pin.adapter = pinAdapter
                }
            })

    }



    override fun onMapReady(mapboxMap: MapboxMap) {
        map = mapboxMap

        map.setStyle(Style.MAPBOX_STREETS) {
            style ->
//            val geoJsonOptions = GeoJsonOptions().withTolerance(0.4f)
            symbolManager = SymbolManager(mapView, map, style, null)
            style.addImage("pin", ContextCompat.getDrawable(this, R.drawable.pin)!!)

            enableLocationComponent(style)

            val symbolOptions = SymbolOptions()
                .withLatLng(LatLng(35.652832,
                    139.839478))
                .withIconImage("pin")
                .withIconSize(1.0f)
            //    .withTextField("A place of Fun")
                .withSymbolSortKey(10.0f)
                .withDraggable(false)
            symbol = symbolManager.create(symbolOptions)

        }





    }

    @SuppressLint("MissingPermission")
    fun enableLocationComponent(mapStyle: Style){

        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            val lcOptions = LocationComponentOptions.builder(this)
                .foregroundDrawable(R.drawable.user)
                .elevation(5f)
                .build()

            val lcAOption = LocationComponentActivationOptions
                .builder(this, mapStyle)
                .useDefaultLocationEngine(true)
                .build()

            locationComponent  = map.locationComponent

            locationComponent.apply {
                activateLocationComponent(lcAOption)
                isLocationComponentEnabled = true
            }

            locationEngine = LocationEngineProvider.getBestLocationEngine(this)
            Log.v("Permission", ": Granted")



//            user = PinData("Test", 99,
//                map.locationComponent.lastKnownLocation!!.latitude,
//                map.locationComponent.lastKnownLocation!!.longitude, "Test User")





        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
    }

    fun initLocationEngine(){
        locationEngine = LocationEngineProvider.getBestLocationEngine(this)
        val request = LocationEngineRequest.Builder(1000L)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setMaxWaitTime(5000L)
            .build()

    }

    fun initRecyclerView() {
        pinAdapter = PinAdapter(mutableListOf())
        rv_pin.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv_pin.adapter = pinAdapter
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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




//        map.setStyle(Style.MAPBOX_STREETS)
//        map.style?.addImage("icon", BitmapFactory
//            .decodeResource(this@PinActivity.resources,R.drawable.pin_drop))
//        map.style?.addImage("pin", BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(this, R.drawable.pin_drop))!!)
//        map.setStyle(Style.Builder().fromUri(Style.MAPBOX_STREETS).withImage("pin", (ContextCompat.getDrawable(this, R.drawable.pin_drop))!!))