package com.dev.numberslight.injection.module

import com.dev.numberslight.interceptor.CurlLoggingInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: Converter.Factory,
    ) = Retrofit.Builder()
        .baseUrl("http://dev.tapptic.com/")
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            // if (BuildConfig.DEBUG) {
            addInterceptor(CurlLoggingInterceptor())
            //  }
        }.build()
    }

    @Provides
    fun provideMoshi() = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): Converter.Factory {
        return retrofit2.converter.moshi.MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
