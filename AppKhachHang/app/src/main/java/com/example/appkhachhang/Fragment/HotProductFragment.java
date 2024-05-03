package com.example.appkhachhang.Fragment;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.HotProductAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.ThongKe_API;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPhamHot;
import com.example.appkhachhang.LoginScreen;
import com.example.appkhachhang.Model.ChiTietDienThoai;

import com.example.appkhachhang.Model.SanPhamHot;
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

public class HotProductFragment extends Fragment {
    MySharedPreferences mySharedPreferences;
    RecyclerView rc_danhSachSPHot;
    List<SanPhamHot> listSPHot;

    List<SanPhamHot> listSPHotFilter;
    TextView tv_entry;

    HotProductAdapter adapter;

    EditText edSearch;
    ProgressBar progressBar;
    LinearLayout ln_boLoc, ln_locGia, ln_locDlRam, ln_locBoNho, ln_sxGiaCao, ln_sxGiaThap, ln_sxDiemDanhGia, ln_sxUuDai;
    int quantity = 0;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hot_product, container, false);
    }

    private void initView(View view) {
        rc_danhSachSPHot = view.findViewById(R.id.rc_danhSachDienThoaiHot);
        edSearch = view.findViewById(R.id.danhSachSPHot_edSearch);
        ln_boLoc = view.findViewById(R.id.danhSachSPHot_linearBoLoc);
        ln_locGia = view.findViewById(R.id.danhSachSPHot_linearLocGia);
        ln_locDlRam = view.findViewById(R.id.danhSachSPHot_linearLocDlRam);
        ln_locBoNho = view.findViewById(R.id.danhSachSPHot_linearLocBoNho);
        ln_sxGiaCao = view.findViewById(R.id.danhSachSPHot_linearSXGiaCaoThap);
        ln_sxGiaThap = view.findViewById(R.id.danhSachSPHot_linearSXGiaThapCao);
        ln_sxDiemDanhGia = view.findViewById(R.id.danhSachSPHot_linearSXDiemDanhGia);
        ln_sxUuDai = view.findViewById(R.id.danhSachSPHot_linearSXUuDai);
        progressBar = view.findViewById(R.id.danhSachSPHot_progressBar);


        tv_entry = view.findViewById(R.id.tv_entry);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mySharedPreferences = new MySharedPreferences(getContext());
        initView(view);
        actionFilter();
        ((Activity) getContext()).setTitle("Danh sách sản phẩm hot");
//        if (getActivity() != null) {
//            getActivity().setTitle("Danh sách sản phẩm hot");
//        }
        sanPhamHot();
        listSPHotFilter = new ArrayList<>();
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listSPHotFilter.clear();
                tv_entry.setVisibility(View.VISIBLE);
                for (int i = 0; i < listSPHot.size(); i++) {
                    if (listSPHot.get(i).get_id().getMaDienThoai().getTenDienThoai().toString().trim().toLowerCase().contains(edSearch.getText().toString().trim().toLowerCase()) && edSearch.getText().length() != 0) {
                        listSPHotFilter.add(listSPHot.get(i));
                        tv_entry.setVisibility(View.GONE);

                    }
                }
                if (edSearch.getText().toString().trim().isEmpty()) {
                    tv_entry.setVisibility(View.GONE);
                    updateList(listSPHot);
                } else {
                    if (listSPHotFilter.size() == 0) {
                        tv_entry.setVisibility(View.VISIBLE);

                    } else {
                        tv_entry.setVisibility(View.GONE);
                        updateList(listSPHotFilter);
                    }

                }

            }
        });
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Gọi phương thức để tìm kiếm sản phẩm mỗi khi người dùng thay đổi nội dung của EditText
                searchProducts(s.toString().trim());
                if (s.toString().trim().isEmpty()) {
                    sanPhamHot();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void sanPhamHot() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager = new GridLayoutManager(getContext(), 2);
        rc_danhSachSPHot.setLayoutManager(manager);
        listSPHot = new ArrayList<>();
        getSanPhamHot();
        adapter = new HotProductAdapter(getContext(), listSPHot, new OnItemClickListenerSanPhamHot() {
            @Override
            public void onItemClickSPHot(ChiTietDienThoai chiTietDienThoai) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idChiTietDienThoai", chiTietDienThoai);
                DetailScreenFragment fragmentB = new DetailScreenFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rc_danhSachSPHot.setAdapter(adapter);
    }

    void getSanPhamHot() {
        progressBar.setVisibility(View.VISIBLE);
        ThongKe_API.thongKeApi.getSanPhamHot().enqueue(new Callback<List<SanPhamHot>>() {
            @Override
            public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                listSPHot.clear();
                listSPHot.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {
                Log.e("error", t.getMessage());

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
                replaceFragment(new CartFragment());
            } else {
                Intent intent = new Intent(getContext(), LoginScreen.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.danhSachHot_frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void searchProducts(String query) {
        List<SanPhamHot> danhSachDaLoc = new ArrayList<>();

        // Duyệt qua danh sách ban đầu để tìm các sản phẩm phù hợp
        for (SanPhamHot sanPham : listSPHot) {
            if (sanPham.get_id().getMaDienThoai().getTenDienThoai().toLowerCase().contains(query.toLowerCase())) {
                danhSachDaLoc.add(sanPham);
            }
        }

        // Cập nhật RecyclerView với danh sách đã lọc
        updateList(danhSachDaLoc);
    }

    public void updateList(List<SanPhamHot> newList) {
        listSPHot.clear();
        listSPHot.addAll(newList);
        adapter.notifyDataSetChanged();
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
        ln_sxDiemDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDanhGia();
            }
        });
    }

    private void sortDanhGia() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<SanPhamHot>> call = apiService.getDanhGiaFilterSPHot("true", "");
        call.enqueue(new Callback<List<SanPhamHot>>() {
            @Override
            public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                if (response.isSuccessful()) {
                    List<SanPhamHot> data = response.body();
                    listSPHot.clear();
                    listSPHot.addAll(data);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {
                Log.e("filter dung luong ram", t.getLocalizedMessage());
            }
        });
    }

    private void bottomDialogFilterBoLoc() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_filter_boloc);
        dialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
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
                Call<List<SanPhamHot>> call = apiService.getBoLocFilterSPHot(selectedRAMs.toString(), selectedBoNho.toString(), "");
                call.enqueue(new Callback<List<SanPhamHot>>() {
                    @Override
                    public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                        if (response.isSuccessful()) {
                            List<SanPhamHot> data = response.body();
                            listSPHot.clear();
                            listSPHot.addAll(data);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {
                        Log.e("filter bộ nhớ", t.getLocalizedMessage());
                    }
                });
            }
        });
    }

    private void bottomDialogFilterGiaTien() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_filter_giatien);

        dialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
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
                Call<List<SanPhamHot>> call = apiService.getFilterGiaTienSPHot(minValue[0] * 1000000, maxValue[0] * 1000000);
                call.enqueue(new Callback<List<SanPhamHot>>() {
                    @Override
                    public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                        if (response.isSuccessful()) {
                            List<SanPhamHot> data = response.body();
                            listSPHot.clear();
                            listSPHot.addAll(data);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {
                        Log.e("filter gia tien", t.getLocalizedMessage());
                    }
                });
            }
        });
    }

    private void bottomDialogFilterBoNho() {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(R.layout.layout_filter_bonho);

        dialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
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
                    selectedRAMs.append("512,");
                    selectedRAMs.append("10000,");
                }
                if (isOnclick128_258[0]) {
                    selectedRAMs.append("128,");
                    selectedRAMs.append("256,");
                }
                if (isOnclick32_64[0]) {
                    selectedRAMs.append("32,");
                    selectedRAMs.append("64,");
                }

                if (selectedRAMs.length() > 0) {
                    selectedRAMs.deleteCharAt(selectedRAMs.length() - 1);
                }
                apiService.getFilterBoNhoSPHot(selectedRAMs.toString(), "").enqueue(new Callback<List<SanPhamHot>>() {
                    @Override
                    public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                        if (response.isSuccessful()) {
                            List<SanPhamHot> data = response.body();
                            listSPHot.clear();
                            listSPHot.addAll(data);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {

                    }
                });

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
        ApiService apiService = ApiRetrofit.getApiService();
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
                    selectedRAMs.append("8,");
                }
                if (isOnclick8_12[0]) {
                    selectedRAMs.append("8,");
                    selectedRAMs.append("12,");
                }
                if (isOnclick16[0]) {
                    selectedRAMs.append("16,");
                }

                if (selectedRAMs.length() > 0) {
                    selectedRAMs.deleteCharAt(selectedRAMs.length() - 1);
                }

                apiService.getFilterDlRamSPHot(selectedRAMs.toString(), "").enqueue(new Callback<List<SanPhamHot>>() {
                    @Override
                    public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                        if (response.isSuccessful()) {
                            List<SanPhamHot> data = response.body();
                            listSPHot.clear();
                            listSPHot.addAll(data);
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {

                    }
                });


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
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<SanPhamHot>> call = apiService.getSortDownSPHot("desc", "");
        call.enqueue(new Callback<List<SanPhamHot>>() {
            @Override
            public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                if (response.isSuccessful()) {
                    List<SanPhamHot> data = response.body();
                    listSPHot.clear();
                    listSPHot.addAll(data);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {
                Log.e("filter dung luong ram", t.getLocalizedMessage());
            }
        });
    }

    private void sortGiaTienThapCao() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiRetrofit.getApiService();

    }

    private void uuDaiHot() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<SanPhamHot>> call = apiService.getSortUpSPHot("asc", "");
        call.enqueue(new Callback<List<SanPhamHot>>() {
            @Override
            public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                if (response.isSuccessful()) {
                    List<SanPhamHot> data = response.body();
                    listSPHot.clear();
                    listSPHot.addAll(data);
                    adapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {
                Log.e("filter dung luong ram", t.getLocalizedMessage());
            }
        });
    }
}