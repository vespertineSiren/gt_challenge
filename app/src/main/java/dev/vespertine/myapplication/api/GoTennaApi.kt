package dev.vespertine.myapplication.api

import dev.vespertine.myapplication.model.PinData
import io.reactivex.Observable
import retrofit2.http.GET

interface GoTennaApi {

    @GET("get_map_pins.php")
    fun getPins() : Observable<List<PinData>>

}