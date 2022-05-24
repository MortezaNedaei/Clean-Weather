package com.mooncascade.di.repository

import com.mooncascade.data.respository.ForecastDataRepository
import com.mooncascade.data.respository.ObservationDataRepository
import com.mooncascade.data.respository.LocationDetailsDataRepository
import com.mooncascade.domain.respository.ForecastRepository
import com.mooncascade.domain.respository.ObservationRepository
import com.mooncascade.domain.respository.LocationDetailsRepository
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
    fun provideObservationRepository(dataRepository: ObservationDataRepository): ObservationRepository {
        return dataRepository

    }

    @Provides
    @Singleton
    fun provideForecastRepository(dataRepository: ForecastDataRepository): ForecastRepository {
        return dataRepository

    }

    @Provides
    @Singleton
    fun provideLocationRepository(dataRepository: LocationDetailsDataRepository): LocationDetailsRepository {
        return dataRepository

    }
}