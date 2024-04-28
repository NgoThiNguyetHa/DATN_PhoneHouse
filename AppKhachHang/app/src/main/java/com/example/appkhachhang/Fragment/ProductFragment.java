package com.example.appkhachhang.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.ListPhoneAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.LoginScreen;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DetailScreen;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mohammedalaa.seekbar.DoubleValueSeekBarView;
import com.mohammedalaa.seekbar.OnDoubleValueSeekBarChangeListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    EditText danhSach_edSearch;
    List<Root> listFilter;
    TextView tv_entry;
    RecyclerView rc_danhSachDienThoai;
    EditText edSearch;
    LinearLayout ln_boLoc, ln_locGia, ln_locDlRam, ln_locBoNho, ln_sxGiaCao, ln_sxGiaThap, ln_sxDiemDanhGia, ln_sxUuDai;

    GridLayoutManager manager;

    ProgressDialog progressDialog;
    ProgressBar progressBar;
    MySharedPreferences mySharedPreferences;
    int quantity = 0; // Khởi tạo quantity với giá trị ban đầu là 0
    List<Root> list;
    ListPhoneAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ((Activity) getContext()).setTitle("Danh Sách Điện Thoại");
        initView(view);
        actionFilter();
        if (getActivity() != null) {
            getActivity().setTitle("Danh sách sản phẩm");
        }
        sanPham();
        listFilter = new ArrayList<>();

        danhSach_edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Gọi phương thức để tìm kiếm sản phẩm mỗi khi người dùng thay đổi nội dung của EditText
                searchProducts(s.toString().trim());

//                if (before > count) {
//                    // Nếu có, lọc danh sách sản phẩm
//                    searchProducts(s.toString().trim());
//                }
                if (s.toString().isEmpty()) {
                    sanPham();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                listFilter.clear();

                tv_entry.setVisibility(View.VISIBLE);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getChiTietDienThoai().getMaDienThoai().getTenDienThoai().toString().toLowerCase().contains(danhSach_edSearch.getText().toString().toLowerCase()) && danhSach_edSearch.getText().length() != 0) {
                        listFilter.add(list.get(i));
                        tv_entry.setVisibility(View.GONE);

                    }
                }
                if (danhSach_edSearch.getText().toString().trim().isEmpty()) {
                    tv_entry.setVisibility(View.GONE);
                    updateList(list);
                } else {
                    if (listFilter.size() == 0) {
                        tv_entry.setVisibility(View.VISIBLE);

                    } else {
                        tv_entry.setVisibility(View.GONE);
                        updateList(listFilter);
                    }

                }
            }
        });
    }

    void sanPham() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager = new GridLayoutManager(getContext(), 2);
        rc_danhSachDienThoai.setLayoutManager(manager);
        list = new ArrayList<>();
        getListSanPham();
//        adapter = new ProductAdapter(getContext(), list, new OnItemClickListenerSanPham() {
//            @Override
//            public void onItemClickSP(ChiTietDienThoai chiTietDienThoai) {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("idChiTietDienThoai", chiTietDienThoai);
//                DetailScreenFragment fragmentB = new DetailScreenFragment();
//                fragmentB.setArguments(bundle);
//                Intent intent = new Intent(getActivity(), DetailScreen.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
        adapter = new ListPhoneAdapter(getContext());
        adapter.setData(list);
        adapter.setOnClickListener(new IItemListPhoneListener() {
            @Override
            public void onClickDetail(Root root) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idChiTietDienThoai", root.getChiTietDienThoai());
                DetailScreenFragment fragmentB = new DetailScreenFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rc_danhSachDienThoai.setAdapter(adapter);
    }

    void getListSanPham() {
        progressBar.setVisibility(View.VISIBLE);
        ChiTietSanPham_API.chiTietSanPhamApi.getChiTiet().enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                list.clear();
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.e("errorrr", "onFailure: " + t.getMessage());
            }
        });
    }

    void searchProducts(String query) {
        progressBar.setVisibility(View.VISIBLE);
        List<Root> danhSachDaLoc = new ArrayList<>();

        // Duyệt qua danh sách ban đầu để tìm các sản phẩm phù hợp
        for (Root sanPham : list) {
            if (sanPham.getChiTietDienThoai().getMaDienThoai().getTenDienThoai().toLowerCase().contains(query.toLowerCase())) {
                danhSachDaLoc.add(sanPham);
            }
        }

        // Cập nhật RecyclerView với danh sách đã lọc
        updateList(danhSachDaLoc);
        progressBar.setVisibility(View.GONE);
    }

    public void updateList(List<Root> newList) {
        list.clear();
        list.addAll(newList);
        adapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        rc_danhSachDienThoai = view.findViewById(R.id.rc_danhSachDienThoai);
        edSearch = view.findViewById(R.id.danhSach_edSearch);
        ln_boLoc = view.findViewById(R.id.danhSach_linearBoLoc);
        ln_locGia = view.findViewById(R.id.danhSach_linearLocGia);
        ln_locDlRam = view.findViewById(R.id.danhSach_linearLocDlRam);
        ln_locBoNho = view.findViewById(R.id.danhSach_linearLocBoNho);
        ln_sxGiaCao = view.findViewById(R.id.danhSach_linearSXGiaCaoThap);
        ln_sxGiaThap = view.findViewById(R.id.danhSach_linearSXGiaThapCao);
        ln_sxDiemDanhGia = view.findViewById(R.id.danhSach_linearSXDiemDanhGia);
        ln_sxUuDai = view.findViewById(R.id.danhSach_linearSXUuDai);
        progressBar = view.findViewById(R.id.danhSach_progressBar);
        rc_danhSachDienThoai = view.findViewById(R.id.rc_danhSachDienThoai);
        danhSach_edSearch = view.findViewById(R.id.danhSach_edSearch);
        tv_entry = view.findViewById(R.id.tv_entry);
    }

    private void actionFilter() {
        ln_boLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogFilterBoLoc();
            }
        });
        ln_locGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogFilterGiaTien();
            }
        });
        ln_locBoNho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogFilterBoNho();
            }
        });
        ln_locDlRam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogFilterDlRam();
            }
        });
        ln_sxGiaCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGiaTienCaoThap();
            }
        });

        ln_sxGiaThap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGiaTienThapCao();
            }
        });
        ln_sxUuDai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uuDaiHot();
            }
        });
    }

    private void bottomDialogFilterBoLoc() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_filter_boloc);

        dialog.show();
        LinearLayout ln_512GB, ln_128GB_256GB, ln_32GB_64GB;
        ln_512GB = dialog.findViewById(R.id.filterBoLoc_cv512GB);
        ln_128GB_256GB = dialog.findViewById(R.id.filterBoLoc_cv128GB_256GB);
        ln_32GB_64GB = dialog.findViewById(R.id.filterBoLoc_cv32GB_64GB);
        LinearLayout ln4GB_6GB, ln8GB_12GB, ln16GB;
        ln4GB_6GB = dialog.findViewById(R.id.filterBoLoc_cv4GB_6GB);
        ln8GB_12GB = dialog.findViewById(R.id.filterBoLoc_cv8GB_12GB);
        ln16GB = dialog.findViewById(R.id.filterBoLoc_cv16GB);
        Button btnCancel, btnConfirm;
        btnCancel = dialog.findViewById(R.id.filterBoLoc_btnCancel);
        btnConfirm = dialog.findViewById(R.id.filterBoLoc_btnConfirm);
        ApiService apiService = ApiRetrofit.getApiService();
        final boolean[] isOnclick512 = {false};
        final boolean[] isOnclick128_258 = {false};
        final boolean[] isOnclick32_64 = {false};
        final boolean[] isOnclick4_6 = {false};
        final boolean[] isOnclick8_12 = {false};
        final boolean[] isOnclick16 = {false};
        ln_512GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick512[0]) {
                    ln_512GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick512[0] = false;
                } else {
                    ln_512GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick512[0] = true;
                }
            }
        });

        ln_128GB_256GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick128_258[0]) {
                    ln_128GB_256GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick128_258[0] = false;
                } else {
                    ln_128GB_256GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick128_258[0] = true;
                }

            }
        });
        ln_32GB_64GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick32_64[0]) {
                    ln_32GB_64GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick32_64[0] = false;
                } else {
                    ln_32GB_64GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick32_64[0] = true;
                }
            }
        });
        ln4GB_6GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick4_6[0]) {
                    ln4GB_6GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick4_6[0] = false;
                } else {
                    ln4GB_6GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick4_6[0] = true;
                }
            }
        });

        ln8GB_12GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick8_12[0]) {
                    ln8GB_12GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick8_12[0] = false;
                } else {
                    ln8GB_12GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick8_12[0] = true;
                }

            }
        });
        ln16GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick16[0]) {
                    ln16GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick16[0] = false;
                } else {
                    ln16GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick16[0] = true;
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder selectedRAMs = new StringBuilder();
                StringBuilder selectedBoNho = new StringBuilder();
                if (isOnclick512[0]) {
                    selectedBoNho.append("100,");
                    selectedBoNho.append("66,");
                }
                if (isOnclick128_258[0]) {
                    selectedBoNho.append("12,");
                    selectedBoNho.append("128,");
                }
                if (isOnclick32_64[0]) {
                    selectedBoNho.append("32,");
                    selectedBoNho.append("64,");
                }
                if (isOnclick4_6[0]) {
                    selectedRAMs.append("4,");
                    selectedRAMs.append("69,");
                }
                if (isOnclick8_12[0]) {
                    selectedRAMs.append("8,");
                    selectedRAMs.append("12,");
                }
                if (isOnclick16[0]) {
                    selectedRAMs.append("50,");
                }

                if (selectedRAMs.length() > 0) {
                    selectedRAMs.deleteCharAt(selectedRAMs.length() - 1);
                }

                if (selectedBoNho.length() > 0) {
                    selectedBoNho.deleteCharAt(selectedBoNho.length() - 1);
                }

                Toast.makeText(getContext(), "Selected RAMs: " + selectedRAMs.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bottomDialogFilterGiaTien() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_filter_giatien);

        dialog.show();
        TextView tvMin, tvMax;
        tvMin = dialog.findViewById(R.id.filterGiaTien_tvMin);
        tvMax = dialog.findViewById(R.id.filterGiaTien_tvMax);
        DoubleValueSeekBarView doubleValueSeekBarView = dialog.findViewById(R.id.double_range_seekbar);
        final DecimalFormat[] decimalFormat = {new DecimalFormat("#,##0.##")};
        String filterMin = String.valueOf(doubleValueSeekBarView.getMinValue() * 1000000);
        String filterMax = String.valueOf(doubleValueSeekBarView.getMaxValue() * 1000000);
        final int[] minValue = new int[1];
        final int[] maxValue = new int[1];
        try {
            double minNumber = Double.parseDouble(filterMin);
            double maxNumber = Double.parseDouble(filterMax);
            String formattedMinNumber = decimalFormat[0].format(minNumber);
            String formattedMaxNumber = decimalFormat[0].format(maxNumber);
            tvMin.setText("" + formattedMinNumber + "đ");
            tvMax.setText("" + formattedMaxNumber + "đ");

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        doubleValueSeekBarView.setOnRangeSeekBarViewChangeListener(new OnDoubleValueSeekBarChangeListener() {
            @Override
            public void onValueChanged(@Nullable DoubleValueSeekBarView seekBar, int min, int max, boolean fromUser) {
                if (min < 0) {
                    doubleValueSeekBarView.setMinValue(0);
                }
                // Kiểm tra nếu giá trị tối đa lớn hơn 999,999,999, đặt lại giá trị tối đa thành 999,999,999
                if (max > 999999999) {
                    doubleValueSeekBarView.setMaxValue(999999999);
                }
                minValue[0] = min;
                maxValue[0] = max;
                DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
                String filterMin = String.valueOf(min * 1000000);
                String filterMax = String.valueOf(max * 1000000);
                try {
                    double minNumber = Double.parseDouble(filterMin);
                    double maxNumber = Double.parseDouble(filterMax);
                    String formattedMinNumber = decimalFormat.format(minNumber);
                    String formattedMaxNumber = decimalFormat.format(maxNumber);
                    tvMin.setText("" + formattedMinNumber + "đ");
                    tvMax.setText("" + formattedMaxNumber + "đ");

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(@Nullable DoubleValueSeekBarView seekBar, int min, int max) {

            }

            @Override
            public void onStopTrackingTouch(@Nullable DoubleValueSeekBarView seekBar, int min, int max) {
            }
        });
        minValue[0] = doubleValueSeekBarView.getCurrentMinValue() * 1000000;
        maxValue[0] = doubleValueSeekBarView.getCurrentMaxValue() * 1000000;
        Button btnCancel, btnConfirm;
        btnCancel = dialog.findViewById(R.id.filterGiaTien_btnCancel);
        btnConfirm = dialog.findViewById(R.id.filterGiaTien_btnConfirm);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void bottomDialogFilterBoNho() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_filter_bonho);

        dialog.show();
        LinearLayout ln_512GB, ln_128GB_256GB, ln_32GB_64GB;
        ln_512GB = dialog.findViewById(R.id.filterBoNho_cv512GB);
        ln_128GB_256GB = dialog.findViewById(R.id.filterBoNho_cv128GB_256GB);
        ln_32GB_64GB = dialog.findViewById(R.id.filterBoNho_cv32GB_64GB);
        Button btnCancel, btnConfirm;
        btnCancel = dialog.findViewById(R.id.filterBoNho_btnCancel);
        btnConfirm = dialog.findViewById(R.id.filterBoNho_btnConfirm);
        final boolean[] isOnclick512 = {false};
        final boolean[] isOnclick128_258 = {false};
        final boolean[] isOnclick32_64 = {false};
        ln_512GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick512[0]) {
                    ln_512GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick512[0] = false;
                } else {
                    ln_512GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick512[0] = true;
                }
            }
        });

        ln_128GB_256GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick128_258[0]) {
                    ln_128GB_256GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick128_258[0] = false;
                } else {
                    ln_128GB_256GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick128_258[0] = true;
                }

            }
        });
        ln_32GB_64GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick32_64[0]) {
                    ln_32GB_64GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick32_64[0] = false;
                } else {
                    ln_32GB_64GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick32_64[0] = true;
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder selectedRAMs = new StringBuilder();

                if (isOnclick512[0]) {
                    selectedRAMs.append("100,");
                    selectedRAMs.append("66,");
                }
                if (isOnclick128_258[0]) {
                    selectedRAMs.append("12,");
                    selectedRAMs.append("128,");
                }
                if (isOnclick32_64[0]) {
                    selectedRAMs.append("32,");
                    selectedRAMs.append("64,");
                }

                if (selectedRAMs.length() > 0) {
                    selectedRAMs.deleteCharAt(selectedRAMs.length() - 1);
                }

                Toast.makeText(getContext(), "Selected RAMs: " + selectedRAMs.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void bottomDialogFilterDlRam() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_filter_dl_ram);

        dialog.show();
        LinearLayout ln4GB_6GB, ln8GB_12GB, ln16GB;
        ln4GB_6GB = dialog.findViewById(R.id.filterDlRam_cv4GB_6GB);
        ln8GB_12GB = dialog.findViewById(R.id.filterDlRam_cv8GB_12GB);
        ln16GB = dialog.findViewById(R.id.filterDlRam_cv16GB);
        Button btnCancel, btnConfirm;
        btnCancel = dialog.findViewById(R.id.filterDlRam_btnCancel);
        btnConfirm = dialog.findViewById(R.id.filterDlRam_btnConfirm);
        final boolean[] isOnclick4_6 = {false};
        final boolean[] isOnclick8_12 = {false};
        final boolean[] isOnclick16 = {false};
        ln4GB_6GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick4_6[0]) {
                    ln4GB_6GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick4_6[0] = false;
                } else {
                    ln4GB_6GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick4_6[0] = true;
                }
            }
        });

        ln8GB_12GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick8_12[0]) {
                    ln8GB_12GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick8_12[0] = false;
                } else {
                    ln8GB_12GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick8_12[0] = true;
                }

            }
        });
        ln16GB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnclick16[0]) {
                    ln16GB.setBackgroundResource(R.drawable.shape_custom);
                    isOnclick16[0] = false;
                } else {
                    ln16GB.setBackgroundResource(R.drawable.linear_filter);
                    isOnclick16[0] = true;
                }
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder selectedRAMs = new StringBuilder();
                if (isOnclick4_6[0]) {
                    selectedRAMs.append("4,");
                    selectedRAMs.append("6,");
                }
                if (isOnclick8_12[0]) {
                    selectedRAMs.append("8,");
                    selectedRAMs.append("12,");
                }
                if (isOnclick16[0]) {
                    selectedRAMs.append("50,");
                }

                if (selectedRAMs.length() > 0) {
                    selectedRAMs.deleteCharAt(selectedRAMs.length() - 1);
                }

                Toast.makeText(getContext(), "Selected RAMs: " + selectedRAMs.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void sortGiaTienCaoThap() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void sortGiaTienThapCao() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void uuDaiHot() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void dialogBottomDetail(Root root) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_themgio_muangay);

        dialog.show();
        mySharedPreferences = new MySharedPreferences(getContext());
//        adapterDiaChi = new DiaChiNhanHangAdapter(getContext(), addressDeliveryList);
//        addressDeliveryList = new ArrayList<>();
        TextView tvTenDienThoai, tvSoLuong, tvSoLuongTon;
        LinearLayout lnMinius, lnAdd;
        ImageView imgClose;
        Button btnAddToCart, btnBuyNow;
        tvTenDienThoai = dialog.findViewById(R.id.dialogBottomChiTiet_tvDienThoai);
        tvSoLuong = dialog.findViewById(R.id.dialogBottomChiTiet_tvSoLuong);
        tvSoLuongTon = dialog.findViewById(R.id.dialogBottomChiTiet_tvSoLuongTon);
        lnMinius = dialog.findViewById(R.id.dialogBottomChiTiet_btnMinius);
        lnAdd = dialog.findViewById(R.id.dialogBottomChiTiet_lnAdd);
        imgClose = dialog.findViewById(R.id.dialogBottomChiTiet_imgClose);
        btnAddToCart = dialog.findViewById(R.id.dialogBottomChiTiet_btnThemGioHang);
        btnBuyNow = dialog.findViewById(R.id.dialogBottomChiTiet_btnMuaNgay);

        tvTenDienThoai.setText("" + root.getChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        tvSoLuongTon.setText("Số lượng còn hàng: " + root.getChiTietDienThoai().getSoLuong());

        lnMinius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity--;
                    tvSoLuong.setText("" + quantity);
                    lnMinius.setVisibility(View.VISIBLE);
                    lnAdd.setVisibility(View.VISIBLE);
                } else {
                    lnMinius.setVisibility(View.GONE);
                }
            }
        });
        lnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < root.getChiTietDienThoai().getSoLuong()) {
                    quantity++;
                    tvSoLuong.setText("" + quantity);
                    lnAdd.setVisibility(View.VISIBLE);
                    lnMinius.setVisibility(View.VISIBLE);
                } else {
                    lnAdd.setVisibility(View.GONE);
                }
            }
        });
        btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
                    ChiTietGioHang chiTietGioHang = new ChiTietGioHang();
                    chiTietGioHang.setMaChiTietDienThoai(root.getChiTietDienThoai());
                    chiTietGioHang.setSoLuong(quantity);
                    chiTietGioHang.setGiaTien(root.getChiTietDienThoai().getGiaTien());
                    ApiRetrofit.getApiService().addGioHang(chiTietGioHang, mySharedPreferences.getUserId()).enqueue(new Callback<ChiTietGioHang>() {
                        @Override
                        public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                            Log.d("error", "onFailure: " + t.getMessage());
                        }
                    });
                } else {
                    Intent intent = new Intent(getContext(), LoginScreen.class);
                    startActivity(intent);
                }
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
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
        mySharedPreferences = new MySharedPreferences(getContext());
        if (item.getItemId() == R.id.gioHang) {
            if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
                replaceFragment(new CartFragment());
            } else {
                Intent intent = new Intent(getContext(), LoginScreen.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}