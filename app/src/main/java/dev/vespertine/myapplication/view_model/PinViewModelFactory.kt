package dev.vespertine.myapplication.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PinViewModelFactory @Inject constructor(
    private val pinViewModel: PinViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(PinViewModel::class.java)) {
            return  pinViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}