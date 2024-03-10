package com.example.appcuahang.interface_adapter;

import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Phone;

public interface IItemPhoneListenner {
    public void detailPhone(Phone idPhone);
    public void editPhone(Phone idPhone);
    public void addDetail(String idPhone, String tenDienThoai);
}
