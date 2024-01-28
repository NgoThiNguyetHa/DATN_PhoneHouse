package com.example.appcuahang.api;

import com.example.appcuahang.model.Ram;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiRamService {
    @GET("rams/getRam")
    Call<List<Ram>> getRam() ;

    @POST("rams/addRam")
    Call<Ram> postRam(@Body Ram ram);

    @PUT("rams/updateRam/{id}")
    Call<Ram> putRam(@Path("id") String id, @Body Ram ram);
}
