package dev.vespertine.myapplication.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.vespertine.myapplication.model.PinData
import dev.vespertine.myapplication.repository.PinRepository
import javax.inject.Inject

class PinViewModel @Inject constructor(pinRepository: PinRepository) : BaseViewModel() {

    private val pinLiveData : MutableLiveData<List<PinData>>  by lazy { MutableLiveData<List<PinData>>() }

    init {
        addDisposable(pinRepository.getPins().subscribe(
            { pinLiveData.postValue(it)}
        ))
    }
}