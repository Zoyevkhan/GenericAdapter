package com.example.genericadapter

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
        fun getInstance(context: Context): WebInterface {
            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            httpClient.addInterceptor(logging)
            httpClient.addInterceptor(NetworkConnectionInterceptor(context))
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response = chain.run {
                    proceed(
                        request()
                            .newBuilder()
                            .addHeader("appid", "hello")
                            .addHeader("deviceplatform", "android")
                            .removeHeader("User-Agent")
                            .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
                            .build()
                    )
                }

            })

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .baseUrl(ApiConstants.BASE_URL)
                .build()
            return retrofit.create(WebInterface::class.java)

        }


}