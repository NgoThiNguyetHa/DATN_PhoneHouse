package com.example.appcuahang.api;

import com.example.appcuahang.model.DungLuong;
import com.example.appcuahang.model.UuDai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUuDaiService {
    @GET ("uudais/getUuDai")
    Call<List<UuDai>> getUuDai();

    @POST ("uudais/addUuDai")
    Call<UuDai> postUuDai(@Body UuDai uuDai);

    @PUT("uudais/updateUuDai/{id}")
    Call<UuDai> putUuDai(@Path("id") String id,
                                 @Body UuDai uuDai);

}
