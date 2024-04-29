package com.example.appkhachhang.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.GioHangAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Interface.OnClickListenerGioHang;
import com.example.appkhachhang.Interface_Adapter.IItemDetailCartListener;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.R;
import com.example.appkhachhang.ThanhToanActivity;
import com.example.appkhachhang.untils.CartSharedPreferences;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements OnClickListenerGioHang {

    RecyclerView rc_gioHang;
    TextView tvEmpty, tvTongTien;
    Button btnThanhToan;

    List<ChiTietGioHang> list, listChon;
    GioHangAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    User user;
    MySharedPreferences mySharedPreferences;
    List<ChiTietGioHang> cartList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc_gioHang = view.findViewById(R.id.rc_gioHang);
        tvEmpty = view.findViewById(R.id.gioHang_tvEmpty);
        tvTongTien = view.findViewById(R.id.gioHang_tvTongTien);
        btnThanhToan = view.findViewById(R.id.gioHang_btnThanhToan);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rc_gioHang.setLayoutManager(linearLayoutManager);
        ((Activity) getContext()).setTitle("Giỏ hàng");
        list = new ArrayList<>();
        mySharedPreferences = new MySharedPreferences(getContext());
        getDataGioHang();

        listChon = new ArrayList<>();
        adapter = new GioHangAdapter(getContext(), list);
        adapter.isOnClickListenerGioHang(this);
        adapter.isOnClickListener(new IItemDetailCartListener() {
            @Override
            public void onClickIncreaseQuantity(ChiTietGioHang item) {
                CartSharedPreferences sharedPreferences = new CartSharedPreferences(getContext());
                boolean isSuccess = sharedPreferences.updateQuantityForItem(getContext(), mySharedPreferences.getUserId(), item.getMaChiTietDienThoai().get_id(), +1);
                if (isSuccess) {
                    cartList = sharedPreferences.getChiTietGioHangForId(getContext(),mySharedPreferences.getUserId());
                    Toast.makeText(getContext(), "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy sản phẩm để cập nhật", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickReduceQuantity(ChiTietGioHang item) {
                CartSharedPreferences sharedPreferences = new CartSharedPreferences(getContext());
                boolean isSuccess = sharedPreferences.updateQuantityForItem(getContext(), mySharedPreferences.getUserId(), item.getMaChiTietDienThoai().get_id(), -1);
                if (isSuccess) {
                    Toast.makeText(getContext(), "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
//                    cartList.add((ChiTietGioHang) CartSharedPreferences.getChiTietGioHangForId(getContext(),mySharedPreferences.getUserId()));
                    cartList = sharedPreferences.getChiTietGioHangForId(getContext(),mySharedPreferences.getUserId());
                    Toast.makeText(getContext(), "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Không tìm thấy sản phẩm để cập nhật", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onClickRemoveItem(ChiTietGioHang item) {
                deleteItemGioHang(item);
            }
        });
        rc_gioHang.setAdapter(adapter);
        if (list.isEmpty()){
            tvEmpty.setText("Bạn chưa có sản phẩm nào " + "\n" + "trong Giỏ Hàng");
            tvEmpty.setVisibility(View.VISIBLE);
        }else{
            tvEmpty.setVisibility(View.GONE);
            tvEmpty.setText("");
        }
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), ThanhToanActivity.class);
//                Gson gson = new Gson();
//                String json = gson.toJson(listChon);
//                intent.putExtra("chiTietGioHangList", json);
//                startActivity(intent);
                ThanhToanFragment fragmentB = new ThanhToanFragment();
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                String json = gson.toJson(listChon);
                bundle.putString("chiTietGioHangList", json);
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getContext(), ThanhToanActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);


            }
        });
    }

    private void getDataGioHang() {
        SharedPreferences prefs = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
//        String idKhachHang = prefs.getString("idKhachHang", "abc");//giá trị abc
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ChiTietGioHang>> call = apiService.getListGioHang(mySharedPreferences.getUserId());
        call.enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if (response.body() != null) {
                    List<ChiTietGioHang> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                    tvEmpty.setVisibility(View.GONE);
                    tvEmpty.setText("");
                } else {
                    tvEmpty.setText("Bạn chưa có sản phẩm nào " + "\n" + "trong Giỏ Hàng");
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                // Handle failure
                Log.e("handle", t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(ChiTietGioHang chiTietGioHang, boolean isChecked) {
//        if (isChecked) {
//            listChon.add(chiTietGioHang);
//        } else {
//            listChon.remove(chiTietGioHang);
//        }
//        int tongtien = 0;
//        for (int i = 0; i < listChon.size(); i++) {
//            tongtien += listChon.get(i).getGiaTien() * listChon.get(i).getSoLuong();
//        }
//        DecimalFormat decimalFormat1 = new DecimalFormat("#,##0");
//        try {
//            double tongTienGiamNumber = Double.parseDouble(String.valueOf(tongtien));
//            String formattedNumber = decimalFormat1.format(tongTienGiamNumber);
//            tvTongTien.setText(formattedNumber + "đ");
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }

        if (isChecked) {
            listChon.add(chiTietGioHang);
            updateTongTien(); // Gọi phương thức để cập nhật tổng tiền
        } else {
            Iterator<ChiTietGioHang> iterator = listChon.iterator();
            while (iterator.hasNext()) {
                ChiTietGioHang item = iterator.next();
                if (item.equals(chiTietGioHang)) {
                    iterator.remove();
                    break;
                }
            }
            updateTongTien(); // Gọi phương thức để cập nhật tổng tiền
        }
    }

    @Override
    public void onPause() {
        reloadListGioHang();
        super.onPause();
    }

    @Override
    public void onResume() {
        reloadListGioHang();
        super.onResume();
    }

    private void reloadListGioHang() {
        ApiService apiService = ApiRetrofit.getApiService();
        cartList = new ArrayList<>();
        CartSharedPreferences cartSharedPreferences = new CartSharedPreferences(getContext());
        cartList = cartSharedPreferences.getChiTietGioHangForId(getContext(), mySharedPreferences.getUserId());
        if (cartList != null) {
//            Log.e("list", String.valueOf(cartList));
            if (!cartList.isEmpty()) {
//                Log.e("list", String.valueOf(cartList.size()));
                Call<List<ChiTietGioHang>> call = apiService.updateListChiTietGioHang(mySharedPreferences.getUserId(), cartList);
                call.enqueue(new Callback<List<ChiTietGioHang>>() {
                    @Override
                    public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
//                        Log.e("log update",call.toString());
                        if (response.isSuccessful()) {
//                            Toast.makeText(getContext(), "update thành công", Toast.LENGTH_SHORT).show();

                        } else {
//                            Toast.makeText(getContext(), "update không thành công", Toast.LENGTH_SHORT).show();

                        }
                    }
                    @Override
                    public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {

                    }
                });
            }
        }
    }

    private void deleteItemGioHang(ChiTietGioHang item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Bạn có muốn xóa sản phẩm này?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiRetrofit.getApiService().deleteGioHang(item.get_id()).enqueue(new Callback<ChiTietGioHang>() {
                    @Override
                    public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                        list.remove(item);
                        adapter.notifyDataSetChanged();
                        CartSharedPreferences sharedPreferences = new CartSharedPreferences(getContext());
                        sharedPreferences.removeChiTietGioHang(getContext(), mySharedPreferences.getUserId(), item.getMaChiTietDienThoai().get_id());
                    }

                    @Override
                    public void onFailure(Call<ChiTietGioHang> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void updateTongTien() {
        int tongtien = 0;
        for (ChiTietGioHang item : listChon) {
            tongtien += item.getGiaTien() * item.getSoLuong();
        }

        DecimalFormat decimalFormat1 = new DecimalFormat("#,##0");
        try {
            double tongTienGiamNumber = Double.parseDouble(String.valueOf(tongtien));
            String formattedNumber = decimalFormat1.format(tongTienGiamNumber);
            tvTongTien.setText(formattedNumber + "đ");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}