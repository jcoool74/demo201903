package com.example.app.restful;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpUtil {
    private static final  String TAG = OkHttpUtil.class.getSimpleName();

    public static OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(message -> Log.d(TAG, message));

        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        return  client;
    }
}
