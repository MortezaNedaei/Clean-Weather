package com.mooncascade.di

import android.content.Context
import coil.ImageLoader
import com.mooncascade.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ImageModule {

    /**
     * provides [ImageLoader] to use in application
     * @param context: [ApplicationContext]
     * @param okHttpClient: [OkHttpClient] in [NetworkModule]
     */
    @Provides
    @Singleton
    fun provideImageLoader(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context,
    ): ImageLoader =
        ImageLoader.Builder(context)
            .okHttpClient { okHttpClient }
            .crossfade(true)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_placeholder)
            //.diskCache(DiskCache.Builder().build())
            .build()
}
