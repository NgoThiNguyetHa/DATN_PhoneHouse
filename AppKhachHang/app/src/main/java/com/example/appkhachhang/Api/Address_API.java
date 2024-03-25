package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.HangSanXuat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Address_API {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    Address_API addRess = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8686/diachinhanhangs/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(Address_API.class);

    @GET("getDiaChiNhanHang/{id}")
    Call<List<AddressDelivery>> getDiaChi(@Path("id") String id);

    @POST("addDiaChiNhanHang")
    Call<List<AddressDelivery>> postDiaChi(@Body AddressDelivery addressDelivery);

    @PUT("updateDiaChiNhanHang")
    Call<List<AddressDelivery>> putDiaChi(@Body AddressDelivery addressDelivery);



}
