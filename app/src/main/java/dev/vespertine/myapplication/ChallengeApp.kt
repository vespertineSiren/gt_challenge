package dev.vespertine.myapplication

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dev.vespertine.myapplication.di.component.DaggerAppComponent
import dev.vespertine.myapplication.utils.Utils
import javax.inject.Inject


class ChallengeApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()


        DaggerAppComponent.builder()
            .application(this)
            .baseUrl(Utils.CHALLENGE_URL)
            .build().inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}
