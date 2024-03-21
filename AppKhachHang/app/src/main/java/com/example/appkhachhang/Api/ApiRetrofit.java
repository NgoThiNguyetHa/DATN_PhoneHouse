package com.example.appkhachhang.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    private static final String BASE_URL = "http://10.0.2.2:8686/";

    //private static final String BASE_URL = "http://192.168.0.189:8686/"; //Yen


//    private static final String BASE_URL = "http://192.168.1.103:8686/";//Long

//     private static final String BASE_URL = "http://192.168.1.143:8686/"; //hantnph28876
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
