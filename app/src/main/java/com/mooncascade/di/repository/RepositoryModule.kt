package com.mooncascade.di.repository

import com.mooncascade.data.respository.WeatherDataRepository
import com.mooncascade.domain.respository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(dataRepository: WeatherDataRepository): WeatherRepository {
        return dataRepository

    }
}