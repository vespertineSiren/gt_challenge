package dev.vespertine.myapplication.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.vespertine.myapplication.model.PinData
import dev.vespertine.myapplication.repository.PinRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PinViewModel @Inject constructor(private val pinRepository: PinRepository) : BaseViewModel() {

    var pinLiveData : MutableLiveData<List<PinData>>  = MutableLiveData()


    fun loadPins() {
        addDisposable(pinRepository.getPins()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {it->pinLiveData.postValue(it)},
                {err-> Log.e("Error Messgage", err.toString())},
                {}
            ))
    }

    fun getPins() : LiveData<List<PinData>> {
        return pinLiveData
    }


}