package dev.vespertine.myapplication.db

import androidx.room.Dao
import androidx.room.Query
import dev.vespertine.myapplication.model.PinData
import io.reactivex.Single

@Dao
abstract class PinDao : BaseDao<PinData> {

    @Query("SELECT * FROM  pin_data ORDER BY pin_id ASC")
    abstract fun getAllPinsInIDOrder() : Single<List<PinData>>


}