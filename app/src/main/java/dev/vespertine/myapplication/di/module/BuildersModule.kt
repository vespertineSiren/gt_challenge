package dev.vespertine.myapplication.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.vespertine.myapplication.activity.PinActivity

@Module
abstract class BuildersModule{

    @ContributesAndroidInjector
    abstract  fun contributesPinActivity(): PinActivity

}