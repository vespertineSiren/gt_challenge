package dev.vespertine.myapplication.di.component

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import dev.vespertine.myapplication.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class])
interface MainComponent {
}