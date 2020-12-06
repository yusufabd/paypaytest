package me.stayplus.paypaytest.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.stayplus.paypaytest.PayPayApp.Companion.BASE_URL
import me.stayplus.paypaytest.data.network.AppNetworkService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkingModule = module {
    single<Gson> { GsonBuilder().create() }

    single<OkHttpClient> {
        OkHttpClient.Builder().apply {
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            addNetworkInterceptor {
                val request = it.request()
                val url = request
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", "b99eb43af4ff1623d407e0dace610f53")
                    .build()
                it.proceed(request.newBuilder().url(url).build())
            }
        }.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single { provideApi<AppNetworkService>(get()) }
}

inline fun <reified T> provideApi(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}