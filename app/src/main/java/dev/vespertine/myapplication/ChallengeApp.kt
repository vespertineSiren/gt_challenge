package dev.vespertine.myapplication

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.vespertine.myapplication.di.component.DaggerMainComponent
import dev.vespertine.myapplication.di.component.MainComponent

class ChallengeApp : Application() {

    private lateinit var mainComponent: MainComponent

    override fun onCreate() {
        super.onCreate()



        mainComponent = DaggerMainComponent.builder()
            .build()
    }

    fun getMainComponent() = mainComponent
}
