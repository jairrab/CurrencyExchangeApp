package com.jairrab.remote.service

import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private var networkUtils: NetworkUtils? = null
    private var isDebug = false

    private const val BASE_URL = "http://apilayer.net/api/"

    var apiKey = ""
        private set

    fun makeRetrofitTestClient(
        apiKey: String,
        isDebug: Boolean,
        networkUtils: NetworkUtils,
        cacheDir: File
    ): RetrofitService {
        this.apiKey = apiKey
        this.networkUtils = networkUtils
        this.isDebug = isDebug
        val cache = Cache(cacheDir, (5 * 1024 * 1024).toLong())

        return getRetrofitClient(
            OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(makeLoggingInterceptor())
                .addInterceptor(cacheInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
        )
    }

    private fun getRetrofitClient(okHttpClient: OkHttpClient): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(RetrofitService::class.java)
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                isDebug -> HttpLoggingInterceptor.Level.BODY
                else    -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    //https://medium.com/mindorks/caching-with-retrofit-store-responses-offline-71439ed32fda
    private fun cacheInterceptor(): (Interceptor.Chain) -> Response {
        return { chain ->
            var request = chain.request()
            request = if (networkUtils?.hasConnection() == true)
            /*
            *  If there is Internet, get the cache that was stored 60 seconds ago.
            *  If the cache is older than 60 seconds, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-age' attribute is responsible for this behavior.
            */
                request.newBuilder().header(
                    "Cache-Control",
                    "public, max-age=" + 60
                ).build()
            else
            /*
            *  If there is no Internet, get the cache that was stored 1 day ago.
            *  If the cache is older than 7 days, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-stale' attribute is responsible for this behavior.
            *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
            */
                request.newBuilder().header(
                    "Cache-Control",
                    "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 1
                ).build()

            // Add the modified request to the chain.
            chain.proceed(request)
        }
    }
}