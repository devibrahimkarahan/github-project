package com.example.github_project.network

import com.example.github_project.utility.Const
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ClientRetrofit {
    companion object {
        fun getClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Const.BASE_API_URL)
                .client(
                    OkHttpClient
                        .Builder()
                        .connectTimeout(Const.TIMEOUT_CONNECT, TimeUnit.SECONDS)
                        .readTimeout(Const.TIMEOUT_READ, TimeUnit.SECONDS)
                        .writeTimeout(Const.TIMEOUT_WRITE, TimeUnit.SECONDS)
                        .addInterceptor { chain ->
                            val newRequest =
                                chain.request().newBuilder().addHeader("User-Agent", "github-project").build()
                            chain.proceed(newRequest)
                        }
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }
}