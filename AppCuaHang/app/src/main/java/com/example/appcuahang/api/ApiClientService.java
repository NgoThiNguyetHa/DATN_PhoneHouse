package com.example.appcuahang.api;

import com.example.appcuahang.model.Client;
import com.example.appcuahang.model.DungLuong;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClientService {

    @GET("khachhangs/getKhachHangTheoCuaHang/{id}")
    Call<List<Client>> getClient(@Path("id") String id);



}
