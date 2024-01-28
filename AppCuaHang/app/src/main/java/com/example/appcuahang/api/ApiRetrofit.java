package com.example.appcuahang.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

//     private static final String BASE_URL = "http://10.0.2.2:8686/";
    private static final String BASE_URL = "http://192.168.1.106:8686/";


    private static ApiService apiService;
    private static ApiMauService apiMauService;
    private static ApiRamService apiRamService;

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

//MÀU
    public static ApiMauService getApiMauService() {
        if (apiMauService == null) {
            apiMauService = creaApiMauService();
        }
        return apiMauService;
    }


    private static ApiMauService creaApiMauService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMauService.class);
    }

    //RAM
    public static ApiRamService getApiRamService() {
        if (apiRamService == null) {
            apiRamService = creaApiRamService();
        }
        return apiRamService;
    }


    private static ApiRamService creaApiRamService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRamService.class);
    }

}
