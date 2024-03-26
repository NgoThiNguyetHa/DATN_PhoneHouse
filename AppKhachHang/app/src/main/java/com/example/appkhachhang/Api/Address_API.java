package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.AddressDelivery;
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
            .baseUrl("http://192.168.3.183:8686/diachinhanhangs/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(Address_API.class);

    @GET("diachinhanhangs/getDiaChiNhanHang/{id}")
    Call<List<AddressDelivery>> getDiaChi(@Path("id") String id);

    @POST("diachinhanhangs/addDiaChiNhanHang")
    Call<AddressDelivery> postDiaChi(@Body AddressDelivery addressDelivery);

    @PUT("diachinhanhangs/updateDiaChiNhanHang/{id}")
    Call<AddressDelivery> putDiaChi(@Body AddressDelivery addressDelivery , @Path("id") String id);



}
