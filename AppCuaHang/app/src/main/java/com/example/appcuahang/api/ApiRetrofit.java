package com.example.appcuahang.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

    private static final String BASE_URL = "https://datn-phonehouse.onrender.com/";


    private static ApiService apiService;
    private static ApiMauService apiMauService;
    private static ApiRamService apiRamService;
    private static ApiDungLuongService apiDungDuongService;

    private static  ApiClientService apiClientService;
    private static ApiUuDaiService apiUuDaiService;


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
            apiMauService = (ApiMauService) createApiMauService();
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
    //RAM
    public static ApiRamService getApiRamService() {
        if (apiRamService == null) {
            apiRamService = (ApiRamService) createApiRamService();
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

    //Dungluong
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

    //KhachHang
    public static ApiClientService getApiClientService(){
        if(apiClientService == null){
            apiClientService = createApiClientService();
        }
        return apiClientService;
    }

    private static ApiClientService createApiClientService(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiClientService.class);
    }
    //Uu dai
    public static ApiUuDaiService getApiUuDaiService(){
        if(apiUuDaiService == null){
            apiUuDaiService = createApiUuDaiService();
        }
        return apiUuDaiService;
    }

    public static ApiUuDaiService createApiUuDaiService(){
        return (ApiUuDaiService) new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiUuDaiService.class);

    }
}
