package com.example.appcuahang.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {
    private static final String BASE_URL = "http://10.0.2.2:8686/";

    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = createApiService();
        }
        return apiService;
    }

    private static ApiService createApiService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }
}
