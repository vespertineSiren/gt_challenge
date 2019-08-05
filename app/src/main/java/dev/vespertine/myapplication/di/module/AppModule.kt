package dev.vespertine.myapplication.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dev.vespertine.myapplication.db.PinDao
import dev.vespertine.myapplication.db.PinRoomDatabase
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun providePinDatabase(application: Application) : PinRoomDatabase
            = PinRoomDatabase.getDatabase(application.applicationContext)


    @Provides
    @Singleton
    fun providePinDao(pinRoomDatabase: PinRoomDatabase) : PinDao
            = pinRoomDatabase.pinDao()


}