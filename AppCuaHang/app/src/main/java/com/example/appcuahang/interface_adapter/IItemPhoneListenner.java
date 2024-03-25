package com.example.appcuahang.interface_adapter;

import android.content.Context;

import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Phone;

public interface IItemPhoneListenner {
    public void detailPhone(Phone idPhone);
    public void editPhone(Phone idPhone);
    public void addDetail(Context mContext, String idPhone, String tenDienThoai);
}
