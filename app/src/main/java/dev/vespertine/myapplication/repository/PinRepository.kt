package dev.vespertine.myapplication.repository

import dev.vespertine.myapplication.api.GoTennaApi
import dev.vespertine.myapplication.db.PinDao
import dev.vespertine.myapplication.model.PinData
import io.reactivex.Single
import javax.inject.Inject

class PinRepository @Inject constructor(val api : GoTennaApi, val pinDao: PinDao) {


    fun getPins() : Single<List<PinData>> {
        return api.getPins()
            .onErrorResumeNext(
                pinDao.getAllPinsInIDOrder())
            .doOnSuccess{
                savePins(it)
            }
    }

    fun savePins(list: List<PinData>) {
        pinDao.insertAll(list)
    }
}