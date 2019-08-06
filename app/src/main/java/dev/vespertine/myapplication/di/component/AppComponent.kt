package dev.vespertine.myapplication.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dev.vespertine.myapplication.ChallengeApp
import dev.vespertine.myapplication.di.module.AppModule
import dev.vespertine.myapplication.di.module.BuildersModule
import dev.vespertine.myapplication.di.module.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(AndroidSupportInjectionModule::class),
    (AppModule::class),
    (BuildersModule::class),
    (NetworkModule::class)])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder
        @BindsInstance
        fun baseUrl(url : String) : AppComponent.Builder //For testing mockserver*/
        fun build(): AppComponent
    }

    fun inject(instance: ChallengeApp)
}