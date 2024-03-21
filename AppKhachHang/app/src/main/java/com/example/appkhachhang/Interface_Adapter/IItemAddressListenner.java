package com.example.appkhachhang.Interface_Adapter;

import com.example.appkhachhang.Model.AddressDelivery;

public interface IItemAddressListenner {
    public void deleteAddress(String idAddress);
    public void editAddress(AddressDelivery isAddress);
    public void showAddress(String idAddress);
}
