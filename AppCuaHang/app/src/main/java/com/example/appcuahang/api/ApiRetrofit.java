package com.example.appcuahang.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

 //   private static final String BASE_URL = "http://192.168.1.9:8686/";

//    private static final String BASE_URL = "http://192.168.1.8:8686/";

     private static final String BASE_URL = "http://192.168.0.189:8686/";
     //Yen

    //    private static final String BASE_URL = "http://192.168.1.106:8686/";
//     private static final String BASE_URL = "http://192.168.1.143:8686/"; //hantnph28876


    private static ApiService apiService;
    private static ApiMauService apiMauService;
    private static ApiRamService apiRamService;
    private static ApiDungLuongService apiDungDuongService;

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
    public static ApiMauService getApiMauService() {
        if (apiMauService == null) {
            apiMauService = createApiMauService();
        }
        return apiMauService;
    }

    private static ApiMauService createApiMauService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMauService.class);
    }
    public static ApiRamService getApiRamService() {
        if (apiRamService == null) {
            apiRamService = createApiRamService();
        }
        return apiRamService;
    }

    private static ApiRamService createApiRamService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiRamService.class);
    }

    public static ApiDungLuongService getApiDungLuongService() {
        if (apiDungDuongService == null) {
            apiDungDuongService = createApiDungLuongService();
        }
        return apiDungDuongService;
    }

    private static ApiDungLuongService createApiDungLuongService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiDungLuongService.class);
    }
}
