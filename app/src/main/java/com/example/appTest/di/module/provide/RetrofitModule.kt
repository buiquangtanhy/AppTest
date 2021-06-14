package com.example.appTest.di.module.provide

import android.content.Context
import com.example.appTest.BuildConfig
import com.example.appTest.di.BaseURL
import com.example.appTest.repository.remote.RemoteService
import com.example.appTest.util.retrofit.adapter.FlowCallAdapterFactory
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRemoteService(retrofit: Retrofit): RemoteService =
        retrofit.create(RemoteService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(
        mapper: ObjectMapper,
        @BaseURL baseURL: String,
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseURL)
            client(client)
            addConverterFactory(JacksonConverterFactory.create(mapper))
            addCallAdapterFactory(FlowCallAdapterFactory.create())
        }.build()

    @Provides
    fun provideHttpClient(
        inters: List<@JvmSuppressWildcards Interceptor>?,
        auth: Authenticator?,
        eventFactory: EventListener.Factory?
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT))
            if (inters != null) {
                interceptors().addAll(inters)
            }
            if (auth != null) {
                authenticator(auth)
            }
            if (eventFactory != null) {
                eventListenerFactory(eventFactory)
            }
        }.build()

    @Provides
    fun provideInterceptors(
        @ApplicationContext context: Context,
        mapper: ObjectMapper
    ): List<Interceptor>? =
        if (BuildConfig.DEBUG) {
            listOf(HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY })
        } else {
            null
        }

    @Provides
    fun provideAuthenticator(): Authenticator? = null

    @Provides
    fun provideEventFactory(): EventListener.Factory? =
        if (BuildConfig.DEBUG) LoggingEventListener.Factory() else null
}