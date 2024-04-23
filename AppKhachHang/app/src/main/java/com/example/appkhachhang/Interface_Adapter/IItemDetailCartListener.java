package com.example.appkhachhang.Interface_Adapter;

import android.content.Context;

import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.Root;

public interface IItemDetailCartListener {

    void onClickIncreaseQuantity(ChiTietGioHang item);
    void onClickReduceQuantity(ChiTietGioHang item);
    void onClickRemoveItem(ChiTietGioHang item);

}
