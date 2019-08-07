package dev.vespertine.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.vespertine.myapplication.model.PinData

const val DATABASE_SCHEMA_VERSION = 1
const val DB_NAME = "local-db"

@Database(
    entities = [PinData::class],
    version = DATABASE_SCHEMA_VERSION,
    exportSchema = false)
abstract class PinRoomDatabase: RoomDatabase(){

    abstract fun pinDao() : PinDao

    companion object {
        @Volatile
        private var INSTANCE: PinRoomDatabase? = null

        fun getDatabase(context: Context): PinRoomDatabase {
            if (INSTANCE == null) {
                INSTANCE = createDatabase(context)
            }
            return INSTANCE!!
        }

        private fun createDatabase(context: Context): PinRoomDatabase {
            return Room.databaseBuilder(context, PinRoomDatabase::class.java, DB_NAME)
                //TODO: To be removed once models are finalized and ready for final delivery.
                //NOTE: Not good practice since it destroys all the data previously.
             .build()
        }
    }
}