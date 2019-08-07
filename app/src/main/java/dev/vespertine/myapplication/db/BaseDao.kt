package dev.vespertine.myapplication.db

import androidx.room.*
import dev.vespertine.myapplication.model.PinData
import io.reactivex.Completable
import io.reactivex.Maybe

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type: T)

    @Delete
    fun delete(type: T): Completable

    @Update
    fun update(type: T): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pins : List<PinData>)


}