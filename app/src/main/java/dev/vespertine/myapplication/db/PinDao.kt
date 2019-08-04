package dev.vespertine.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.vespertine.myapplication.model.PinData

@Dao
interface PinDao : BaseDao<PinData> {

    @Query("SELECT * FROM  pin_data ORDER BY pin_id ASC")
    fun getAllPinsInIDOrder() : LiveData<List<PinData>>


}