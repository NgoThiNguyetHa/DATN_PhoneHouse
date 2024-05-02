package com.example.appkhachhang.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.AdapterSpinnerDiaChi;
import com.example.appkhachhang.Adapter.DiaChiNhanHangAdapter;
import com.example.appkhachhang.Adapter.DienThoaiThanhToanAdapter;
import com.example.appkhachhang.Api.Address_API;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Helper.AppInfo;
import com.example.appkhachhang.Helper.CreateOrder;
import com.example.appkhachhang.MainActivity;
import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.R;
import com.example.appkhachhang.ThanhToanActivity;
import com.example.appkhachhang.activity.ZalopayActivity;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;


public class ThanhToanFragment extends Fragment {
  User user;
  TextView tvThemDiaChi, tvTongTienHang, tvPhiVanChuyen, tvTongThanhToan, tvTongHoaDon;
  RecyclerView rc_listChon;
  LinearLayoutManager linearLayoutManager;
  DienThoaiThanhToanAdapter adapter;
  Spinner spnPhuongThucThanhToan, spnDiaChi;
  List<AddressDelivery> list;
  String idDiaChi, selectedItem;
  List<ChiTietHoaDon> chiTietHoaDons;
  MySharedPreferences mySharedPreferences;
  ChiTietDienThoai chiTietDienThoai;
  List<ChiTietGioHang> chiTietGioHangList;
  AdapterSpinnerDiaChi adapterSpinnerDiaChi;
  int phiVanChuyen = 0;
  EditText dl_edDiaChiChiTiet, dl_edPhuongXa, dl_edQuanHuyen, dl_edTinhThanhPho, edTenNguoiNhan, edSDT;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    StrictMode.ThreadPolicy policy = new
            StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_thanh_toan, container, false);
    ((getActivity())).setTitle("Thanh Toán");
    initView(view);
//        getDataBundle();
    initVariable(view);
    return view;
  }

  private void initView(View view) {
//    tvTen = view.findViewById(R.id.tv_tenKhachHang);
//    tvSdt = view.findViewById(R.id.tv_sdtKhachHang);
//    tvDiaChi = view.findViewById(R.id.tv_DiaChiKhachHang);
//    imgDiaChi = view.findViewById(R.id.imgDiaChi);

    tvThemDiaChi = view.findViewById(R.id.thanhToan_tvThemDiaChi);
    rc_listChon = view.findViewById(R.id.rc_listChon);
    tvTongTienHang = view.findViewById(R.id.tv_tongTienHang);
    tvPhiVanChuyen = view.findViewById(R.id.tv_PhiVanChuyen);
    tvTongThanhToan = view.findViewById(R.id.tv_tongThanhToan);
    tvTongHoaDon = view.findViewById(R.id.tvTongHoaDon);
    spnPhuongThucThanhToan = view.findViewById(R.id.spn_PhuongThucThanhToan);
    spnDiaChi = view.findViewById(R.id.spn_diaChiNhanHang);

  }

//    private void getDataBundle(){
//
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String json = bundle.getString("chiTietGioHangList");
//            Gson gson = new Gson();
//            user = gson.fromJson(json, User.class);
//        }
//
//        SharedPreferences pref = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String user_json = pref.getString("user", "abc");
//         user = gson.fromJson(user_json, User.class);
//    }

  private void initVariable(View view) {
    mySharedPreferences = new MySharedPreferences(getContext());
    list = new ArrayList<>();
    getData(mySharedPreferences.getUserId());
    adapterSpinnerDiaChi = new AdapterSpinnerDiaChi(getContext(),R.layout.item_selected_diachi, list);
    spnDiaChi.setAdapter(adapterSpinnerDiaChi);
    spnDiaChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          idDiaChi = adapterSpinnerDiaChi.getItem(i).get_id();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
    tvThemDiaChi.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_address, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
          return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        edTenNguoiNhan = view.findViewById(R.id.dl_edTenNguoiNhan);
        edSDT = view.findViewById(R.id.dl_edSDT);
        dl_edDiaChiChiTiet = view.findViewById(R.id.dl_edDiaChiChiTiet);
        dl_edPhuongXa = view.findViewById(R.id.dl_edPhuongXa);
        dl_edQuanHuyen = view.findViewById(R.id.dl_edQuanHuyen);
        dl_edTinhThanhPho = view.findViewById(R.id.dl_edTinhThanhPho);

        Button btnSave = view.findViewById(R.id.yesButton);
        TextView tvTitle = view.findViewById(R.id.dl_mau_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_mau_imageView);

        tvTitle.setText("Dialog Thêm Địa chỉ");
        btnSave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            if(Validate()){
              String tenNguoiNhan = edTenNguoiNhan.getText().toString().trim();
              String soDienThoai = edSDT.getText().toString().trim();
              String diaChi = dl_edDiaChiChiTiet.getText().toString().trim() + ", "
                      + dl_edPhuongXa.getText().toString().trim() + ", "
                      + dl_edQuanHuyen.getText().toString().trim()+ ", "
                      + dl_edTinhThanhPho.getText().toString().trim();
              Address_API address_api = ApiRetrofit.getApiAddress();
              mySharedPreferences = new MySharedPreferences(getContext());
              Call<AddressDelivery> call = address_api.postDiaChi(new AddressDelivery(soDienThoai , tenNguoiNhan , diaChi , new User(mySharedPreferences.getUserId())));
              call.enqueue(new Callback<AddressDelivery>() {
                @Override
                public void onResponse(Call<AddressDelivery> call, Response<AddressDelivery> response) {
                  if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getData(mySharedPreferences.getUserId());
                  }
                }

                @Override
                public void onFailure(Call<AddressDelivery> call, Throwable t) {
                  Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
              });
            }
          }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            dialog.dismiss();
          }
        });
      }
    });
//    imgDiaChi.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        LayoutInflater inflater = getLayoutInflater();
//        View dialog_view = inflater.inflate(R.layout.dialog_diachi, null);
//        rcDiaChiNhanHang = dialog_view.findViewById(R.id.rc_diaChiNhanHang);
//        linearLayoutManager = new LinearLayoutManager(getContext());
//        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
//        rcDiaChiNhanHang.setLayoutManager(linearLayoutManager);
//        builder.setView(dialog_view);
//
//        rcDiaChiNhanHang.setAdapter(adapterDiaChi);
//
//        builder.setPositiveButton("Chọn", new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialogInterface, int i) {
//            adapterDiaChi.setOnItemCheckedChangeListener(new DiaChiNhanHangAdapter.OnItemCheckedChangeListener() {
//              @Override
//              public void onItemCheckedChanged(AddressDelivery addressDelivery) {
//                tvTen.setText(addressDelivery.getTenNguoiNhan() + " | ");
//                tvSdt.setText(addressDelivery.getSdt());
//                tvDiaChi.setText("Địa chỉ: " + addressDelivery.getDiaChi());
//
//                idDiaChi = addressDelivery.get_id();
//                addressCheck = addressDelivery.getDiaChi();
//                if (addressDelivery.getDiaChi().toLowerCase().equals("hà nội")){
//                  phiVanChuyen += 15000;
//                } else {
//                  phiVanChuyen += 30000;
//                }
//              }
//            });
//          }
//        });
//        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
//          @Override
//          public void onClick(DialogInterface dialogInterface, int i) {
//            dialogInterface.dismiss();
//          }
//        });
//
//        builder.create().show();
//      }
//    });
    linearLayoutManager = new LinearLayoutManager(getContext());
    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    rc_listChon.setLayoutManager(linearLayoutManager);
    Intent intent = getActivity().getIntent();
    String json = intent.getStringExtra("chiTietGioHangList");
    Gson gson = new Gson();
    Type type = new TypeToken<List<ChiTietGioHang>>() {
    }.getType();
    chiTietGioHangList = gson.fromJson(json, type);
    String jsonChiTietDienThoai = intent.getStringExtra("chiTietDienThoai");
    if (jsonChiTietDienThoai != null && json == null) {
      chiTietDienThoai = gson.fromJson(jsonChiTietDienThoai, ChiTietDienThoai.class);
      ChiTietGioHang chiTietGioHang = new ChiTietGioHang();
      chiTietGioHang.setMaChiTietDienThoai(chiTietDienThoai);
      chiTietGioHang.setGiaTien(chiTietDienThoai.getGiaTien());
      chiTietGioHang.setSoLuong(1);
      chiTietGioHangList = new ArrayList<>();
      chiTietGioHangList.add(chiTietGioHang);
    }

    adapter = new DienThoaiThanhToanAdapter(chiTietGioHangList, getContext());
    rc_listChon.setAdapter(adapter);

    int tongTien = 0;
    for (int i = 0; i < chiTietGioHangList.size(); i++) {
      tongTien += chiTietGioHangList.get(i).getGiaTien() * chiTietGioHangList.get(i).getSoLuong();
    }


    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
    try {
      double tongTienGiamNumber = Double.parseDouble(String.valueOf(tongTien));
      String formattedNumber = decimalFormat.format(tongTienGiamNumber);
      tvTongTienHang.setText(formattedNumber + "");
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }

    try {
      double tongTienGiamNumber = Double.parseDouble(String.valueOf(phiVanChuyen));
      String formattedNumber = decimalFormat.format(tongTienGiamNumber);
      tvPhiVanChuyen.setText(formattedNumber + "đ");
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    try {
      double tongTienGiamNumber = Double.parseDouble(String.valueOf(tongTien + phiVanChuyen));
      String formattedNumber = decimalFormat.format(tongTienGiamNumber);
      tvTongThanhToan.setText(formattedNumber + "đ");
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    try {
      double tongTienGiamNumber = Double.parseDouble(String.valueOf(+tongTien + phiVanChuyen));
      String formattedNumber = decimalFormat.format(tongTienGiamNumber);
      tvTongHoaDon.setText(formattedNumber + "đ");
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    int tongThanhToan = tongTien + phiVanChuyen;

    ArrayAdapter<CharSequence> adapterSpn = ArrayAdapter.createFromResource(getContext(), R.array.spn_phuongthuc, android.R.layout.simple_list_item_1);
    spnPhuongThucThanhToan.setDropDownWidth(android.R.layout.simple_spinner_dropdown_item);
    spnPhuongThucThanhToan.setAdapter(adapterSpn);
    spnPhuongThucThanhToan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedItem = adapterView.getItemAtPosition(i).toString();
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
    view.findViewById(R.id.btnThanhToan).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (selectedItem.toString().equals("Thanh toán qua ví Zalopay")) {
          CreateOrder orderApi = new CreateOrder();
          try {
            JSONObject data = orderApi.createOrder(String.valueOf(10000));
            String code = data.getString("returncode");

            if (code.equals("1")) {

              String token = data.getString("zptranstoken");

              ZaloPaySDK.getInstance().payOrder((Activity) getContext(), token, "demozpdk://app", new PayOrderListener() {
                @Override
                public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                  Intent intent = new Intent(getContext(), MainActivity.class);
                  startActivity(intent);
                  Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPaymentCanceled(String zpTransToken, String appTransID) {
                  Toast.makeText(getContext(), "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                  Toast.makeText(getContext(), "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                }
              });
            }

          } catch (Exception e) {
            e.printStackTrace();
          }

//                    Intent intent = new Intent(getActivity(), MainActivity.class);
//                    intent.putExtra("key", "Thanh toan thanh cong");
//                    startActivity(intent);
        } else {
          List<String> addStores = new ArrayList<>();
          for (ChiTietGioHang item : chiTietGioHangList) {
            String maCuaHang = item.getMaChiTietDienThoai().getMaDienThoai().getMaCuaHang().get_id();

            if (!addStores.contains(maCuaHang)) {
              Calendar calendar = Calendar.getInstance();
              SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
              String formattedDate = dateFormat.format(calendar.getTime());
              HoaDon hoaDon = new HoaDon();
              hoaDon.setTongTien(String.valueOf(0 + phiVanChuyen));
              hoaDon.setNgayTao(formattedDate);
              hoaDon.setPhuongThucThanhToan(selectedItem);
              hoaDon.setMaKhachHang(new User(mySharedPreferences.getUserId()));
              hoaDon.setMaCuaHang(new Store(maCuaHang));
              hoaDon.setMaDiaChiNhanHang(new AddressDelivery(idDiaChi));
              hoaDon.setTrangThaiNhanHang("Đang xử lý");
              ApiRetrofit.getApiService().addHoaDon(hoaDon).enqueue(new Callback<HoaDon>() {
                @Override
                public void onResponse(Call<HoaDon> call, Response<HoaDon> response) {
                  if (response.body() != null) {
                    String hoaDonId = response.body().getMaCuaHang().get_id();
                    chiTietHoaDons = new ArrayList<>();
                    for (ChiTietGioHang item : chiTietGioHangList) {
                      if (item.getMaChiTietDienThoai().getMaDienThoai().getMaCuaHang().get_id().equals(hoaDonId)) {
                        ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                        chiTietHoaDon.setMaHoaDon(new HoaDon(response.body().get_id()));
                        chiTietHoaDon.setMaChiTietDienThoai(item.getMaChiTietDienThoai());
                        chiTietHoaDon.setSoLuong(String.valueOf(item.getSoLuong()));
                        if (item.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai() != null) {
                          chiTietHoaDon.setGiaTien(String.valueOf((item.getSoLuong() * Math.round(item.getMaChiTietDienThoai().getGiaTien() * (100 - Double.parseDouble(item.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia())) / 100))));
                        } else {

                          chiTietHoaDon.setGiaTien(String.valueOf(item.getSoLuong() * item.getMaChiTietDienThoai().getGiaTien()));
                        }
                        chiTietHoaDons.add(chiTietHoaDon);
                      } else {
                        Log.e("Error", "Response not successful");
                      }
                    }
                    ApiRetrofit.getApiService().addChiTietHoaDon(chiTietHoaDons, mySharedPreferences.getUserId()).enqueue(new Callback<String>() {
                      @Override
                      public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body() != null) {
                          Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                          if (response.body().equals("Đặt hàng thành công")) {
                            //chuyển màn hình
                            Log.d("zzz", "onResponse: ");
                          }
                        }
                      }

                      @Override
                      public void onFailure(Call<String> call, Throwable t) {
                        Log.d("themHoaDon", "onResponse: " + t.getMessage());
                      }
                    });
                  }
                }

                @Override
                public void onFailure(Call<HoaDon> call, Throwable t) {
                  Log.e("errorrr", "onFailure: " + t.getMessage());
                }
              });
              addStores.add(maCuaHang);
            }
          }
        }
      }
    });
  }

  private void getData(String id) {
    Address_API address_api = ApiRetrofit.getApiAddress();
    Call<List<AddressDelivery>> call = address_api.getDiaChi(id);
    call.enqueue(new Callback<List<AddressDelivery>>() {
      @Override
      public void onResponse(Call<List<AddressDelivery>> call, Response<List<AddressDelivery>> response) {
        if (response.isSuccessful() && response.body() != null) {
          list.clear();
          list.addAll(response.body());
          adapterSpinnerDiaChi.notifyDataSetChanged();
//          if (!list.isEmpty()) {
//            AddressDelivery firstAddress = list.get(0);
//            idDiaChi = firstAddress.get_id();
//            tvTen.setText(firstAddress.getTenNguoiNhan() + " | ");
//            tvSdt.setText(firstAddress.getSdt());
//            tvDiaChi.setText("Địa chỉ: " + firstAddress.getDiaChi());
//          } else {
//            Toast.makeText(getContext(), "Không có địa chỉ nào.", Toast.LENGTH_SHORT).show();
//          }
        } else {
          Toast.makeText(getContext(), "Không thể lấy danh sách địa chỉ.", Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onFailure(Call<List<AddressDelivery>> call, Throwable t) {
        Log.e("errorrr", t.getMessage());
      }
    });
  }

//  void addChiTietHoaDon() {
//    mySharedPreferences = new MySharedPreferences(getContext());
//    ApiRetrofit.getApiService().addChiTietHoaDon(chiTietHoaDons, mySharedPreferences.getUserId()).enqueue(new Callback<String>() {
//      @Override
//      public void onResponse(Call<String> call, Response<String> response) {
//        if (response.body() != null) {
//          Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
//          if (response.body().equals("Đặt hàng thành công")) {
//            //chuyển màn hình
//            Log.d("zzz", "onResponse: ");
//          }
//        }
//      }
//
//      @Override
//      public void onFailure(Call<String> call, Throwable t) {
//        Log.d("themHoaDon", "onResponse: " + t.getMessage());
//      }
//    });
//  }

  private boolean Validate(){
    String usernamePattern = "^[\\p{L}\\s]+$";
    String phonePattern = "^0\\d{9}$";
    String pattern = "^[\\p{L}0-9\\s\\-_]+$";

    if(edTenNguoiNhan.getText().toString().isEmpty()){
      edTenNguoiNhan.setError("Không được để trống!!");
      return false;
    }else if(!edTenNguoiNhan.getText().toString().trim().matches(usernamePattern)){
      edTenNguoiNhan.setError("Tên người nhận phải là chữ cái!!");
      return false;
    }else if(edSDT.getText().toString().isEmpty()){
      edSDT.setError("Không được để trống!!");
      return false;
    }else if(!edSDT.getText().toString().trim().matches(phonePattern)){
      edSDT.setError("Số điện thoại không hợp lệ!!");
      return false;
    } else if(dl_edDiaChiChiTiet.getText().toString().isEmpty()){
      dl_edDiaChiChiTiet.setError("Địa chỉ không được để trống!!");
      return false;
    } else if(dl_edPhuongXa.getText().toString().isEmpty()){
      dl_edPhuongXa.setError("Phường xã không được để trống!!");
      return false;
    } else if(!dl_edPhuongXa.getText().toString().trim().matches(pattern)){
      dl_edPhuongXa.setError("Phường xã không được nhập ký tự đặc biệt!!");
      return false;
    } else if(dl_edQuanHuyen.getText().toString().isEmpty()){
      dl_edQuanHuyen.setError("Quận huyện không được để trống!!");
      return false;
    } else if(!dl_edQuanHuyen.getText().toString().trim().matches(pattern)){
      dl_edQuanHuyen.setError("Quận huyện không được chứa ký tự đặc biệt!!");
      return false;
    } else if(dl_edTinhThanhPho.getText().toString().isEmpty()){
      dl_edTinhThanhPho.setError("Tỉnh, thành phố không được để trống!!");
      return false;
    }else if(!dl_edTinhThanhPho.getText().toString().trim().matches(pattern)){
      dl_edTinhThanhPho.setError("Tỉnh, thành phố không được chứa ký tự đặc biệt!!");
      return false;
    }
    return true;
  }
}
