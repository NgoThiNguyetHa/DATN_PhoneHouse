package com.example.appkhachhang.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.InfoOrderAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.Model.ThongTinDonHang;
import com.example.appkhachhang.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoOrderFragment extends Fragment {

    Button btnHuyDon , btnNhanHang;
    TextView tvTrangThai, tvTitleTrangThai , tvTenKhachHang, tvSoDT , tvDiaChi , tvThanhToan, tvTongTienHang, tvPhiVanChuyen, tvThanhTien,tvTitleTongThanhTien, tvTongThanhToan , tvMaDonHang, tvNgayTao;
    ImageView imgTrangThai , imgDienThoai;
    HoaDon hoaDon;
    List<ThongTinDonHang> list;
    InfoOrderAdapter adapter;
    LinearLayoutManager manager;
    RecyclerView rc_detailOrder;
    int tienPhiVanChuyen;
    Date currentDate = new Date();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_hoa_don, container, false);
        ((Activity)getContext()).setTitle("Thông Tin Đơn Hàng");
        initView(view);
        initVariable();
        getVariable();
        return view;
    }
    private void initView(View view){
        btnHuyDon = view.findViewById(R.id.detailOder_btnHuyDon);
        btnNhanHang = view.
                findViewById(R.id.detailOder_btnNhanHang);
        //
        tvTrangThai = view.findViewById(R.id.detailOder_tvTrangThai);
        tvTitleTrangThai = view.findViewById(R.id.detailOder_tvTitleTrangThai);
        tvTenKhachHang = view.findViewById(R.id.detailOrder_tvTenKhachHang);
        tvSoDT = view.findViewById(R.id.detailOrder_tvSoDT);
        tvDiaChi = view.findViewById(R.id.detailOrder_tvDiaChi);
        tvThanhToan = view.findViewById(R.id.detailOrder_tvThanhToan);
        tvTongTienHang = view.findViewById(R.id.detailOrder_tvTongTienHang);
        tvPhiVanChuyen = view.findViewById(R.id.detailOrder_tvPhiVanChuyen);
        tvThanhTien = view.findViewById(R.id.detailOrder_tvThanhTien);
        tvTongThanhToan = view.findViewById(R.id.detailOrder_tvTongThanhToan);
        rc_detailOrder = view.findViewById(R.id.rc_detailOrder);
        tvMaDonHang = view.findViewById(R.id.detailOrder_tvMaDonHang);
        tvNgayTao = view.findViewById(R.id.detailOrder_tvNgayTao);
        tvTitleTongThanhTien = view.findViewById(R.id.detailOrder_tvTitleTongTien);
        imgTrangThai = view.findViewById(R.id.detailOder_imgTrangThai);
    }
    private void initVariable(){
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rc_detailOrder.setLayoutManager(manager);;
        adapter = new InfoOrderAdapter(getContext(),list);
        rc_detailOrder.setAdapter(adapter);
    }
    //đổ dữ liệu lên textView, image
    private void getVariable(){
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            hoaDon = (HoaDon) bundle.getSerializable("infoOrder");
            tvTrangThai.setText(""+hoaDon.getTrangThaiNhanHang());
            tvTenKhachHang.setText("Tên khách hàng: "+hoaDon.getMaDiaChiNhanHang().getTenNguoiNhan());
            tvSoDT.setText("Số điện thoại: "+hoaDon.getMaDiaChiNhanHang().getSdt());
            tvDiaChi.setText("Địa chỉ: "+hoaDon.getMaDiaChiNhanHang().getDiaChi());
            tvThanhToan.setText(""+hoaDon.getPhuongThucThanhToan());
            String orderCode = generateOrderCode(hoaDon.get_id());
            tvMaDonHang.setText("DH"+orderCode);
            SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            try {
                Date ngayTaoDate = sdfInput.parse(hoaDon.getNgayTao());
                String ngayTaoFormatted = sdfOutput.format(ngayTaoDate);
                tvNgayTao.setText(""+ngayTaoFormatted);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //kiểm tra vị trí
            if (hoaDon.getMaDiaChiNhanHang().getDiaChi().toLowerCase().equals("hà nội")) {
                tienPhiVanChuyen += 15000;
            } else {
                tienPhiVanChuyen += 30000;
            }
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            String tongTien = String.valueOf(tienPhiVanChuyen);
            try {
                double tongTienNumber = Double.parseDouble(tongTien);
                String formattedNumber = decimalFormat.format(tongTienNumber);
                tvPhiVanChuyen.setText(""+formattedNumber+"₫");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            //check trangThai
            if (hoaDon.getTrangThaiNhanHang().equals("Đang xử lý")){
                tvTitleTrangThai.setText("Đơn hàng đang được cửa hàng xác nhận");
                imgTrangThai.setImageResource(R.drawable.dang_xu_ly);
            }else if(hoaDon.getTrangThaiNhanHang().equals("Đã giao")){
                tvTitleTrangThai.setText("Đơn hàng đang được đưa tới khách hàng");
                imgTrangThai.setImageResource(R.drawable.transport_1);
                btnHuyDon.setVisibility(View.GONE);
                btnNhanHang.setVisibility(View.GONE);
                tvTongThanhToan.setPadding(0,20,20,20);
            }else if (hoaDon.getTrangThaiNhanHang().equals("Đã hủy")){
                tvTitleTrangThai.setText("Đơn hàng đã hủy thành công");
                imgTrangThai.setImageResource(R.drawable.cancel_1);
                btnHuyDon.setVisibility(View.GONE);
                btnNhanHang.setVisibility(View.GONE);
                tvTongThanhToan.setPadding(0,20,20,20);
            }
            //get dữ liệu
            getData(hoaDon.get_id());
        }
    }
    //getData lên recyclerview
    private void getData(String id){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ThongTinDonHang>> call = apiService.getThongTinDonHang(id);
        call.enqueue(new Callback<List<ThongTinDonHang>>() {
            @Override
            public void onResponse(Call<List<ThongTinDonHang>> call, Response<List<ThongTinDonHang>> response) {
                if (response.isSuccessful()) {
                    List<ThongTinDonHang> hoaDonList = response.body();
                    list.clear();
                    list.addAll(hoaDonList);
                    long total = 0;
                    for (ThongTinDonHang item : hoaDonList) {
                        if (item.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai() == null){
                            total += (item.getMaChiTietDienThoai().getGiaTien()  * (item.getSoLuong()));
                        }else{
                            total += (item.getMaChiTietDienThoai().getGiaTien() * Integer.parseInt(item.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia()) / 100) * (item.getSoLuong());
                        }
                        Log.e("soLuong", String.valueOf(item.getSoLuong()));
                    }
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
                    String tongTienHang = String.valueOf(total);
                    try {
                        double tongTienNumber = Double.parseDouble(tongTienHang);
                        String formattedNumber = decimalFormat.format(tongTienNumber);
                        tvTongTienHang.setText(""+formattedNumber+"₫");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    long thanhTien = total + tienPhiVanChuyen;
                    String tongThanhTien = String.valueOf(thanhTien);
                    try {
                        double tongTienNumber = Double.parseDouble(tongThanhTien);
                        String formattedNumber = decimalFormat.format(tongTienNumber);
                        tvThanhTien.setText(""+formattedNumber+"₫");
                        tvTitleTongThanhTien.setText("Vui lòng thanh toán "+ formattedNumber+"₫" + " khi nhận hàng");
                        tvTongThanhToan.setText(""+formattedNumber+"₫");
                        actionButton(hoaDon.get_id(),formattedNumber, hoaDon.getNgayTao());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ThongTinDonHang>> call, Throwable t) {

            }
        });
    }
    private void actionButton(String id, String thanhTien, String ngayTao){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String ngayHienTai = dateFormat.format(currentDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            // Chuyển đổi ngày từ chuỗi thành Date
            Date dateHienTai = sdf.parse(ngayHienTai);
            Date dateTao = sdf.parse(ngayTao);

            // Tính số ngày chênh lệch
            long diffInMillies = Math.abs(dateHienTai.getTime() - dateTao.getTime());
            long diff = diffInMillies / (24 * 60 * 60 * 1000);

            if (diff >= 1) {
                btnHuyDon.setEnabled(false);
                btnHuyDon.setText("Đang giao");
            }else {
                btnHuyDon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogHuyDon(id);
                    }
                });
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogXacNhan(id, thanhTien);
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    private void dialogXacNhan(String id, String TongTien){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_nhan_duoc_hang, null);
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
        Button btnXacNhan, btnThoat;
        TextView tvTitle;
        btnXacNhan = view.findViewById(R.id.btn_xac_nhan);
        btnThoat = view.findViewById(R.id.btn_thoat);
        tvTitle = view.findViewById(R.id.dl_xacNhan_tvTitle);
        ApiService apiService = ApiRetrofit.getApiService();
        tvTitle.setText("Thanh toán "+ TongTien+"₫" + " cho người bán");
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<HoaDon> call = apiService.updateHoaDon(id, new HoaDon(id,"Đã giao"));
                call.enqueue(new Callback<HoaDon>() {
                    @Override
                    public void onResponse(Call<HoaDon> call, Response<HoaDon> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                            getData(id);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<HoaDon> call, Throwable t) {

                    }
                });
            }
        });
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @SuppressLint("MissingInflatedId")
    private void dialogHuyDon(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_xac_nhan_huy, null);
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
        Button btnYes, btnNo;
        btnYes = view.findViewById(R.id.dl_huy_btnYes);
        btnNo = view.findViewById(R.id.dl_huy_btnNo);
        ApiService apiService = ApiRetrofit.getApiService();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<HoaDon> call = apiService.updateHoaDon(id, new HoaDon(id,"Đã hủy"));
                call.enqueue(new Callback<HoaDon>() {
                    @Override
                    public void onResponse(Call<HoaDon> call, Response<HoaDon> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                            getData(id);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<HoaDon> call, Throwable t) {

                    }
                });
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private static String generateOrderCode(String input) {
        try {
            // Tạo một đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());

            // Chuyển đổi byte array thành chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // Trả về mã đơn hàng (có thể cắt chuỗi để lấy độ dài mong muốn)
            return hexString.toString().substring(0, 10); // Ví dụ: Lấy 10 ký tự đầu
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

}