package com.example.appkhachhang.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRetrofit {

//    private static final String BASE_URL = "https://datn-phonehouse.onrender.com/";

    //private static final String BASE_URL = "http://192.168.0.189:8686/"; //Yen


//    private static final String BASE_URL = "http://192.168.1.18:8686";//Long

     private static final String BASE_URL = "http://192.168.1.170:8686/"; //hantnph28876
    private static ApiService apiService;
    private static Address_API address_api;
    private static FeedbackAPI feedbackAPI;

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

    public static Address_API getApiAddress() {
        if (address_api == null) {
            address_api =  createApiAddress();
        }
        return address_api;
    }

    private static Address_API createApiAddress() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Address_API.class);
    }

    public static FeedbackAPI getFeedbackAPI() {
        if (feedbackAPI == null) {
            feedbackAPI =  createFeedback();
        }
        return feedbackAPI;
    }

    private static FeedbackAPI createFeedback() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FeedbackAPI.class);
    }

}
