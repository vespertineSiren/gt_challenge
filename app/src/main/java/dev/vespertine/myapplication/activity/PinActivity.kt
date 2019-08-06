package dev.vespertine.myapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import dagger.android.AndroidInjection
import dev.vespertine.myapplication.R
import dev.vespertine.myapplication.adapter.PinAdapter
import dev.vespertine.myapplication.model.PinData
import dev.vespertine.myapplication.view_model.PinViewModel
import dev.vespertine.myapplication.view_model.PinViewModelFactory
import kotlinx.android.synthetic.main.content_base.*
import javax.inject.Inject


class PinActivity : AppCompatActivity(), PermissionsListener, OnMapReadyCallback{


    @Inject
    lateinit var pinviewmodelFactory: PinViewModelFactory
    lateinit var pinViewModel: PinViewModel
    lateinit var pinAdapter : PinAdapter
    lateinit var permissionManager: PermissionsManager
    lateinit var map : MapboxMap


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
        map.setStyle(Style.MAPBOX_STREETS)
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

            val location = map?.locationComponent
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}
