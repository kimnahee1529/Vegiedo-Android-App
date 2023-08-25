package com.devinsight.vegiedo.utill;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "";
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(String baseurl) {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(baseurl);
            builder.client(new OkHttpClient());
            builder.addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()));
            retrofit = builder.build();
        }
        return retrofit;
    }
}

