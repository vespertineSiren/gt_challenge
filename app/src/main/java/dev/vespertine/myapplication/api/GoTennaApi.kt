package dev.vespertine.myapplication.api

import dev.vespertine.myapplication.model.PinData
import io.reactivex.Single
import retrofit2.http.GET

interface GoTennaApi {

    @GET("get_map_pins.php")
    fun getPins() : Single<List<PinData>>

}