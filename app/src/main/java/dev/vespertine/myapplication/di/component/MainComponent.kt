package dev.vespertine.myapplication.di.component

import dagger.Component
import dev.vespertine.myapplication.di.module.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface MainComponent {
}