package com.example.appcuahang.interface_adapter.interface_adapter;

import com.example.appcuahang.model.Brand;

public interface IItemBrandListenner {
    public void deleteBrand(String idBrand);
    public void editBrand(Brand idBrand);
    public void showDetail(String idBrand);
}
