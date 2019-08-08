package dev.vespertine.myapplication.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mapbox.geojson.Feature
import com.mapbox.geojson.Point
import dev.vespertine.myapplication.model.PinData
import dev.vespertine.myapplication.repository.PinRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PinViewModel @Inject constructor(private val pinRepository: PinRepository) : BaseViewModel() {

    private val pickedPinLiveData : MutableLiveData<PinData> by lazy {MutableLiveData<PinData>()}
    private val pinLiveData : MutableLiveData<List<PinData>>  by lazy { MutableLiveData<List<PinData>>()}
    private val pinFeatureLiveData : MutableLiveData<List<Feature>> by lazy { MutableLiveData<List<Feature>>() }



    fun loadPins() {
        addDisposable(pinRepository.getPins()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it->pinLiveData.postValue(it)
                    val features = mutableListOf<Feature>()
                    it.forEach {
                        features.add(Feature.fromGeometry(Point.fromLngLat(it.longitude, it.latitude)))
                        pinFeatureLiveData.postValue(features)
                    }
                },
                {err-> Log.e("Error Messgage", err.toString())}
            )
        )
    }

    fun getPins() : LiveData<List<PinData>> = pinLiveData

    fun getpickedPin() = pickedPinLiveData

    fun getPinPoints() : LiveData<List<Feature>> = pinFeatureLiveData

    fun setPickedPin(selected : PinData) {
        pickedPinLiveData.value = selected
    }





}