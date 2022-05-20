package com.mooncascade.data.di

import com.google.gson.Gson
import com.mooncascade.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides base url for [provideRetrofit] method
     */
    @Provides
    @Singleton
    fun provideBaseUrl() = BuildConfig.API_BASE_URL

    /**
     * Provides log level for [provideHttpLoggingInterceptor] method
     */
    @Provides
    @Singleton
    fun provideLogLevel() =
        if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE

    /**
     * Provides HttpLoggingInterceptor to use in [provideOkHttpClient] method
     */
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(
        logLevel: HttpLoggingInterceptor.Level
    ) = HttpLoggingInterceptor().apply { level = logLevel }

    /**
     * Provides HeadersInterceptor to use in [provideOkHttpClient] method
     */
    @Provides
    @Singleton
    fun provideHeadersInterceptor() = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("accept", "application/json")
                .build()
        )
    }

    /**
     * Provides OkHttpClient to use in [provideRetrofit] method
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES))
        .dispatcher(
            Dispatcher().apply {
                // Allow for high number of concurrent image fetches on same host.
                maxRequestsPerHost = 15
            }
        )
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .build()

    /**
     * Provides Moshi to use in [provideMoshiConverterFactory] method
     */
    @Singleton
    @Provides
    fun provideMoshiBuilder(): Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    /**
     * Provides MoshiConverterFactory to use in [provideRetrofit] method
     */
    @Singleton
    @Provides
    fun provideMoshiConverterFactory(
        moshi: Moshi
    ): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    /**
     * provides Retrofit to use in [ApiServiceModule]
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient,
        API_BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addConverterFactory(moshiConverterFactory)
            .baseUrl(API_BASE_URL)
            .client(okHttpClient)
            .build()
}
