package dev.vespertine.myapplication.repository

import dev.vespertine.myapplication.api.GoTennaApi
import dev.vespertine.myapplication.db.PinDao
import dev.vespertine.myapplication.model.PinData
import io.reactivex.Observable
import javax.inject.Inject

class PinRepository @Inject constructor(val api : GoTennaApi, val pinDao: PinDao) {


    fun getPins() : Observable<List<PinData>> {
        var observableAPI = api.getPins().toObservable()
        val observableDB = pinDao.getAllPinsInIDOrder().toObservable()
        return Observable.concat(observableAPI, observableDB)
    }


}