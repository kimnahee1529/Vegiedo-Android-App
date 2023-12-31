package com.devinsight.vegiedo.utill;

import com.devinsight.vegiedo.service.ApiService;
import com.devinsight.vegiedo.service.api.AdminApiService;
import com.devinsight.vegiedo.service.api.CommentApiService;
import com.devinsight.vegiedo.service.api.MapApiService;
import com.devinsight.vegiedo.service.api.PostApiService;
import com.devinsight.vegiedo.service.api.ReviewApiService;
import com.devinsight.vegiedo.service.api.StoreApiService;
import com.devinsight.vegiedo.service.api.UserApiService;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "http://43.202.88.184:8080/";
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

    public static UserApiService getUserApiService(){
        return getRetrofit(BASE_URL).create(UserApiService.class);
    }

    public static StoreApiService getStoreApiService(){
        return getRetrofit(BASE_URL).create(StoreApiService.class);
    }

    public static PostApiService getPostApiService(){
        return getRetrofit(BASE_URL).create(PostApiService.class);
    }

    public static ReviewApiService getReviewApiService(){
        return getRetrofit(BASE_URL).create(ReviewApiService.class);
    }

    public static CommentApiService getCommentApiService(){
        return getRetrofit(BASE_URL).create(CommentApiService.class);
    }

    public static MapApiService getMapApiService(){
        return getRetrofit(BASE_URL).create(MapApiService.class);
    }

    public static AdminApiService getAdminApiService(){
        return getRetrofit(BASE_URL).create(AdminApiService.class);
    }


}

