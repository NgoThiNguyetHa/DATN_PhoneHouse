package com.example.appcuahang.interface_adapter;

import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Mau;

public interface IItemMauListenner {
    public void deleteBrand(String idBrand);
    public void editMau(Mau isMau);
    public void showDetail(String idBrand);
}
