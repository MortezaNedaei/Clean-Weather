package com.mooncascade.di.network


import com.mooncascade.data.network.service.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideForecastService(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)
}
