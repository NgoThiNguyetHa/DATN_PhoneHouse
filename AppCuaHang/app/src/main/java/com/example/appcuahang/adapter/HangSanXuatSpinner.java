package com.example.appcuahang.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.appcuahang.R;
import com.example.appcuahang.model.Brand;
import com.squareup.picasso.Picasso;


import java.util.List;


public class HangSanXuatSpinner implements SpinnerAdapter {
    Context mContext;
    List<Brand> list;

    public HangSanXuatSpinner(Context mContext, List<Brand> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_custom, null);
        TextView tv_tenHang = convertView.findViewById(R.id.spinner_tenHang);
        ImageView imgHangSX = convertView.findViewById(R.id.spinner_imageHangSX);
        String tenHang = list.get(position).getTenHang();
        tv_tenHang.setText(tenHang);
        if (list.get(position).getHinhAnh() == null){
            imgHangSX.setImageResource(R.drawable.img_10);
        }else {
            Picasso.get().load(list.get(position).getHinhAnh()).into(imgHangSX);
        }
        return convertView;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_custom, null);
        TextView tv_tenHang = convertView.findViewById(R.id.spinner_tenHang);
        ImageView imgHangSX = convertView.findViewById(R.id.spinner_imageHangSX);
        String tenHang = list.get(position).getTenHang();
        tv_tenHang.setText(tenHang);
        if (list.get(position).getHinhAnh() == null){
            imgHangSX.setImageResource(R.drawable.img_10);
        }else {
            Picasso.get().load(list.get(position).getHinhAnh()).into(imgHangSX);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
