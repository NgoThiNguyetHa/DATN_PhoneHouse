package com.example.appcuahang.api;

import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Mau;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiMauService {

    @GET("maus/getMau")
    Call<List<Mau>> getMau();


    @POST("maus/addMau")
    Call<Mau> postMau(@Body Mau mau);

    @PUT("maus/updateMau/{id}")
    Call<Mau> putMau(@Path("id") String id,
                               @Body Mau mau);
}
