package com.example.appkhachhang.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.DanhGiaAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.LoginScreen;
import com.example.appkhachhang.MainActivity;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.CartActivity;
import com.example.appkhachhang.activity.ShopActivity;
import com.example.appkhachhang.ThanhToanActivity;
import com.example.appkhachhang.untils.CartSharedPreferences;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScreenFragment extends Fragment {

    ImageView imgAnhChiTiet, imgMinus, imgAdd;
    TextView tv_tenDienThoai, tv_giaChiTiet, tv_soLuong, tv_moTa, tv_danhGia, tvGiaTienGoc, tvDanhGiaKhachHang, tvSoLuong, tvGiamGia, tvRam, tvMau, tvDungLuong, tvTenCuaHang, tvDiaChi;
    LinearLayout lnError;
    ChiTietDienThoai chiTietDienThoai;
    List<DanhGia> danhGiaList;
    LinearLayoutManager manager;
    RecyclerView rcDanhGia;
    DanhGiaAdapter adapter;
    int quantity = 1;
    Button btnMuaLuon, btnThemVaoGio;
    MySharedPreferences mySharedPreferences;
    AppCompatButton btnXemShop;
    List<ChiTietGioHang> listChon;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_screen, container, false);
        ((Activity) getContext()).setTitle("Chi Tiết Điện Thoại");
        initView(view);
        initVariable();
        getDataBundle();
        return view;
    }

    private void initView(View view) {
        imgAnhChiTiet = view.findViewById(R.id.imgAnhChiTiet);
        tv_tenDienThoai = view.findViewById(R.id.tv_tenSPChiTiet);
        tv_giaChiTiet = view.findViewById(R.id.tv_giaChiTiet);
        tv_soLuong = view.findViewById(R.id.tv_soLuongChiTiet);
        tv_moTa = view.findViewById(R.id.tv_moTaChiTiet);
        tv_danhGia = view.findViewById(R.id.tv_danhGia);
        tvGiaTienGoc = view.findViewById(R.id.tv_giaTienGoc);
        tvDanhGiaKhachHang = view.findViewById(R.id.detailScreen_tvDanhGia);
        rcDanhGia = view.findViewById(R.id.detailScreen_rcDanhGia);
        imgAdd = view.findViewById(R.id.detailScreen_imgAdd);
        imgMinus = view.findViewById(R.id.detailScreen_imgMinus);
        btnMuaLuon = view.findViewById(R.id.btnMuaLuon);
        btnThemVaoGio = view.findViewById(R.id.btnAddGioHang);
        tvGiamGia = view.findViewById(R.id.detailScreen_tvGiamGia);
        tvMau = view.findViewById(R.id.detailScreen_tvMau);
        tvRam = view.findViewById(R.id.detailScreen_tvRam);
        tvDungLuong = view.findViewById(R.id.detailScreen_tvDungLuong);
        lnError = view.findViewById(R.id.detailScreen_lnError);
        tvSoLuong = view.findViewById(R.id.detailScreen_tvSoLuong);
        tvTenCuaHang = view.findViewById(R.id.tvTenCuaHang);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        btnXemShop = view.findViewById(R.id.btnXemShop);
    }

    private void initVariable() {
        danhGiaList = new ArrayList<>();
        listChon = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rcDanhGia.setLayoutManager(manager);
        adapter = new DanhGiaAdapter(getContext());
        adapter.setData(danhGiaList);
        rcDanhGia.setAdapter(adapter);
    }

    private void getDataBundle() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            chiTietDienThoai = (ChiTietDienThoai) bundle.getSerializable("idChiTietDienThoai");// Lấy dữ liệu từ Bundle bằng key
            Log.e("abcabcabc", "getDataBundle: " + chiTietDienThoai.getMaDienThoai() );
            getData(chiTietDienThoai.get_id());
            setTextData(chiTietDienThoai);
            actionAddToCartAndBuyNow(chiTietDienThoai);
        }
        btnXemShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cuaHang", chiTietDienThoai.getMaDienThoai().getMaCuaHang());
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getData(String id) {
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<DanhGia>> call = apiService.getListDanhGiaTheoChiTiet(id);
        call.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()) {
                    List<DanhGia> data = response.body();
                    danhGiaList.clear();
                    danhGiaList.addAll(data);
                    adapter.notifyDataSetChanged();
                    //điểm đánh giá
                    int tongDiemDanhGia = 0;
                    for (int i = 0; i < danhGiaList.size(); i++) {
                        tongDiemDanhGia += danhGiaList.get(i).getDiemDanhGia();
                    }
                    int soLuongDanhGia = danhGiaList.size();
                    float diemTrungBinh = 0;
                    if (soLuongDanhGia != 0) {
                        diemTrungBinh = (float) tongDiemDanhGia / soLuongDanhGia;
                    }
                    tv_danhGia.setText("" + diemTrungBinh);
                    DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
                    df.setRoundingMode(RoundingMode.CEILING);
                    String formatted = df.format(diemTrungBinh);
                    tv_danhGia.setText(formatted);
                    tvDanhGiaKhachHang.setText("Đánh giá của khách hàng " + "(" + danhGiaList.size() + ")");
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {
                Log.e("danh gia", t.getMessage());
            }
        });
    }

    private void setTextData(ChiTietDienThoai chiTietDienThoai) {
        if (chiTietDienThoai.getMaDienThoai().getTenDienThoai() == null) {
            tv_tenDienThoai.setText("");
        } else {
            tv_tenDienThoai.setText("Điện thoại " + chiTietDienThoai.getMaDienThoai().getTenDienThoai());
        }
        tv_giaChiTiet.setText("" + chiTietDienThoai.getGiaTien());
        tv_soLuong.setText("| Số lượng còn: " + chiTietDienThoai.getSoLuong());

        if (chiTietDienThoai.getHinhAnh().equals("")) {
            imgAnhChiTiet.setImageResource(R.drawable.shape_custom_dialog);
        } else {
            Picasso.get().load(chiTietDienThoai.getHinhAnh()).into(imgAnhChiTiet);
        }

        if (chiTietDienThoai.getMaDienThoai().getMaUuDai() == null) {
            tvGiaTienGoc.setVisibility(View.GONE);
            tvGiamGia.setVisibility(View.GONE);
        } else {
            tvGiaTienGoc.setText("" + chiTietDienThoai.getGiaTien());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            String tongTien = String.valueOf(chiTietDienThoai.getGiaTien());
            try {
                double tongTienNumber = Double.parseDouble(tongTien);
                String formattedNumber = decimalFormat.format(tongTienNumber);
                tvGiaTienGoc.setPaintFlags(tvGiaTienGoc.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                tvGiaTienGoc.setText(formattedNumber + "₫");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            tvGiamGia.setText("Giảm " + chiTietDienThoai.getMaDienThoai().getMaUuDai().getGiamGia() + "%");
        }
        String tongTienGiam;
        if (chiTietDienThoai.getMaDienThoai().getMaUuDai() == null) {
            tongTienGiam = String.valueOf(chiTietDienThoai.getGiaTien());

        } else {
            tongTienGiam = String.valueOf(chiTietDienThoai.getGiaTien() - (chiTietDienThoai.getGiaTien() * (Double.parseDouble(chiTietDienThoai.getMaDienThoai().getMaUuDai().getGiamGia()) / 100)));
        }

        DecimalFormat decimalFormat1 = new DecimalFormat("#,##0");
        try {
            double tongTienGiamNumber = Double.parseDouble(tongTienGiam);
            String formattedNumber = decimalFormat1.format(tongTienGiamNumber);
            tv_giaChiTiet.setText(formattedNumber + "₫");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        tv_moTa.setText(
                "Kích thước: " + chiTietDienThoai.getMaDienThoai().getKichThuoc() +
                        "\nCông nghệ màn hình: " + chiTietDienThoai.getMaDienThoai().getCongNgheManHinh()
                        + "\nCamera: " + chiTietDienThoai.getMaDienThoai().getCamera()
                        + "\nCPU: " + chiTietDienThoai.getMaDienThoai().getCpu()
                        + "\nPin: " + chiTietDienThoai.getMaDienThoai().getPin()
                        + "\nHệ điều hành: " + chiTietDienThoai.getMaDienThoai().getHeDieuHanh()
                        + "\nĐộ phân giải: " + chiTietDienThoai.getMaDienThoai().getDoPhanGiai()
                        + "\nNăm sản xuất: " + chiTietDienThoai.getMaDienThoai().getNamSanXuat()
                        + "\nThời gian bảo hành: " + chiTietDienThoai.getMaDienThoai().getThoiGianBaoHanh()
                        + "\n" + chiTietDienThoai.getMaDienThoai().getMoTaThem());
        tvMau.setText("" + chiTietDienThoai.getMaMau().getTenMau());
        tvRam.setText("" + chiTietDienThoai.getMaRam().getRAM() + " GB Ram");
        tvDungLuong.setText("" + chiTietDienThoai.getMaDungLuong().getBoNho() + " GB Dung Lượng");
        tvTenCuaHang.setText(chiTietDienThoai.getMaDienThoai().getMaCuaHang().getUsername());
        tvDiaChi.setText(chiTietDienThoai.getMaDienThoai().getMaCuaHang().getDiaChi());
    }

    private void actionAddToCartAndBuyNow(ChiTietDienThoai chiTietDienThoai) {
        mySharedPreferences = new MySharedPreferences(getContext());
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(quantity > 0) {
//                    quantity--;
//                    tvSoLuong.setText(""+quantity);
//                    lnError.setVisibility(View.GONE);
//                } else {
//                    quantity += 1;
//                }
                if (quantity <= 0) {
                    quantity += 1;
                } else {
                    quantity--;
                    tvSoLuong.setText("" + quantity);
                    lnError.setVisibility(View.GONE);
                }
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < chiTietDienThoai.getSoLuong()) {
                    quantity++;
                    tvSoLuong.setText("" + quantity);
                    lnError.setVisibility(View.GONE);
                } else {
                    lnError.setVisibility(View.VISIBLE);
                }
            }
        });

        btnThemVaoGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
                    if (quantity <= chiTietDienThoai.getSoLuong()) {
                       // progressDialog = new ProgressDialog(getContext());
                       // progressDialog.setMessage("Vui Lòng Chờ...");
                       // progressDialog.setCancelable(false);
                       // progressDialog.show();
                        ChiTietGioHang chiTietGioHang = new ChiTietGioHang();
                        chiTietGioHang.setMaChiTietDienThoai(chiTietDienThoai);
                        chiTietGioHang.setSoLuong(quantity);
                        if (chiTietDienThoai.getMaDienThoai().getMaUuDai() == null) {
                            chiTietGioHang.setGiaTien(chiTietDienThoai.getGiaTien());
                        } else {
                            chiTietGioHang.setGiaTien((int) (chiTietDienThoai.getGiaTien() - Math.round(chiTietDienThoai.getGiaTien() * (Double.parseDouble(chiTietDienThoai.getMaDienThoai().getMaUuDai().getGiamGia()) / 100))));
                        }

                        ApiRetrofit.getApiService().addGioHang(chiTietGioHang, mySharedPreferences.getUserId()).enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response.body().equals("Thêm thành công")) {
                                    Toast.makeText(getActivity(), response.body(), Toast.LENGTH_SHORT).show();
                                    CartSharedPreferences sharedPreferences = new CartSharedPreferences(getContext());
                                    boolean isSuccess = sharedPreferences.saveChiTietGioHangForId(getContext(), mySharedPreferences.getUserId(), chiTietGioHang);
                                    if (isSuccess) {
                                        Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                        quantity = 1;
//                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
//                                    Toast.makeText(getContext(), response.body(), Toast.LENGTH_SHORT).show();
//                                    Log.d("zzzz", "onResponse: "+ response);
                                    handleNotification(response.body());
                                    //progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.d("error", "onFailure: " + t.getMessage());
                            }
                        });
                    } else {
                        lnError.setVisibility(View.VISIBLE);
                        return;
                    }
                } else {
                    Intent intent = new Intent(getContext(), LoginScreen.class);
                    startActivity(intent);
                }
            }
        });

//        btn mua ngay
        btnMuaLuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
                    if (quantity < chiTietDienThoai.getSoLuong()) {
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Vui Lòng Chờ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        ChiTietGioHang chiTietGioHang = new ChiTietGioHang();
                        chiTietGioHang.setMaChiTietDienThoai(chiTietDienThoai);
                        chiTietGioHang.setSoLuong(quantity);
                        if (chiTietDienThoai.getMaDienThoai().getMaUuDai() == null) {
                            chiTietGioHang.setGiaTien(chiTietDienThoai.getGiaTien());
                        } else {
                            chiTietGioHang.setGiaTien((int) (chiTietDienThoai.getGiaTien() - Math.round(chiTietDienThoai.getGiaTien() * (Double.parseDouble(chiTietDienThoai.getMaDienThoai().getMaUuDai().getGiamGia()) / 100))));
                        }

                        listChon.add(chiTietGioHang);
                        Gson gson = new Gson();
                        Bundle bundle = new Bundle();
                        String json = gson.toJson(listChon);
                        ThanhToanFragment fragmentB = new ThanhToanFragment();
                        bundle.putString("chiTietGioHangList", json);
                        fragmentB.setArguments(bundle);
                        Intent intent = new Intent(getContext(), ThanhToanActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        progressDialog.dismiss();
                    } else {
                        lnError.setVisibility(View.VISIBLE);
                        return;
                    }
                } else {
                    Toast.makeText(getContext(), "Bạn cần đăng nhập để mua hàng", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginScreen.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem = menu.findItem(R.id.iconSearch);
        if (menuItem != null) {
            menuItem.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.gioHang) {
            if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
//                FragmentManager manager = getActivity().getSupportFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                transaction.replace(R.id.detailScreen_frameLayout, new CartFragment());
//                transaction.commit();
                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), LoginScreen.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);

    }

    private void handleNotification(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_yes_no, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
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

        Button btnYes = view.findViewById(R.id.yesButton);
        Button btnNo = view.findViewById(R.id.noButton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvTitle= view.findViewById(R.id.tvTitle);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvMessage = view.findViewById(R.id.tvMessage);

        tvMessage.setText(message);
        btnNo.setText("Đồng ý");
        tvTitle.setVisibility(View.GONE);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setVisibility(View.GONE);
    }

}