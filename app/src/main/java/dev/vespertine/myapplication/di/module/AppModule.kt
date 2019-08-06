package dev.vespertine.myapplication.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dev.vespertine.myapplication.db.PinDao
import dev.vespertine.myapplication.db.PinRoomDatabase
import dev.vespertine.myapplication.view_model.PinViewModelFactory
import javax.inject.Singleton

@Module
class AppModule {

/*
    @Provides
    @Singleton
    fun provideApplication(): Application = app
*/

    @Provides
    @Singleton
    fun providePinDatabase(application: Application) : PinRoomDatabase
            = PinRoomDatabase.getDatabase(application.applicationContext)


    @Provides
    @Singleton
    fun providePinDao(pinRoomDatabase: PinRoomDatabase) : PinDao
            = pinRoomDatabase.pinDao()

    @Provides
    @Singleton
    fun providePinViewModelFactory(
        factory : PinViewModelFactory):ViewModelProvider.Factory = factory
}