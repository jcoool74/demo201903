package com.example.app.restful

import android.util.Log

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpUtil {
    private val TAG = OkHttpUtil::class.java.simpleName

    val client: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor { message -> Log.d(TAG, message) }

            logging.level = HttpLoggingInterceptor.Level.BASIC

            return OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
        }
}
