package com.mooncascade.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mooncascade.di.network.NetworkModule
import com.mooncascade.data.network.service.WeatherApi
import com.mooncascade.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class NetworkModuleTest {

    @get: Rule
    val mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.enqueue(MockResponse().setBody("").setResponseCode(500))
    }

    @Test
    fun HeadersInterceptor_should_put_headers_on_all_requests() {
        runBlocking {
            val interceptorUnderTest = NetworkModule.provideHeadersInterceptor()
            val service = constructWithInterceptor(interceptorUnderTest).create(
                WeatherApi::class.java
            )

            service.getCurrentWeather()

            mockWebServer.takeRequest(1, TimeUnit.SECONDS)?.headers?.contains(
                Pair(
                    "Accept",
                    "application/json"
                )
            )
                ?.let { assert(it) }
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    private fun constructWithInterceptor(interceptorUnderTest: Interceptor) =
        constructRetrofit(
            Json.asConverterFactory("application/json".toMediaType()),
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
            ),
            OkHttpClient().newBuilder().addInterceptor(interceptorUnderTest).build(),
        )


    private fun constructRetrofit(
        serializationConverterFactory: Converter.Factory,
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return NetworkModule.provideRetrofit(
            serializationConverterFactory,
            moshiConverterFactory,
            okHttpClient,
            mockWebServer.url(BuildConfig.API_BASE_URL).toString()
        )
    }
}