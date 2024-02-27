package com.example.appcuahang.api;

import com.example.appcuahang.model.DungLuong;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDungLuongService {
    @GET("dungluongs/getDungLuong")
    Call<List<DungLuong>> getDungLuong();

    @POST("dungluongs/addDungLuong")
    Call<DungLuong> postDungLuong(@Body DungLuong dungLuong);

    @PUT("dungluongs/updateDungLuong/{id}")
    Call<DungLuong> putDungLuong(@Path("id") String id,
                     @Body DungLuong dungLuong);
}
