package dev.vespertine.myapplication.di.module

import dagger.Module
import dagger.Provides
import dev.vespertine.myapplication.api.GoTennaApi
import dev.vespertine.myapplication.model.PinData
import dev.vespertine.myapplication.utils.Utils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
//            .readTimeout(Utils.READ_TIMEOUT, TimeUnit.MILLISECONDS)
//            .connectTimeout(Utils.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Utils.CHALLENGE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesGoTennaApiService(retrofit : Retrofit) : GoTennaApi =
        retrofit.create(GoTennaApi::class.java)


}