package com.example.appkhachhang.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Adapter.ListPhoneAdapter;
import com.example.appkhachhang.Adapter.StoreAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Interface_Adapter.IItemStoreListener;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.SearchResponse;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DetailScreen;
import com.example.appkhachhang.activity.ShopActivity;
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

public class SearchFragment extends Fragment {
    RecyclerView rc_danhSachDienThoai, rc_danhSachCuaHang;
    EditText edSearch;
    TextView tv_entry;
    LinearLayout ln_boLoc, ln_locGia , ln_locDlRam , ln_locBoNho, ln_sxGiaCao , ln_sxGiaThap, ln_sxDiemDanhGia, ln_sxUuDai;
    ProgressBar progressBar;
    private static final long SEARCH_DELAY = 500;
    private Handler handler = new Handler();
    private Runnable searchRunnable;
    String giaMin, giaMax, ram, boNho, sortByPrice, uuDaiHot, maHangSanXuat, sortDanhGia;
    SearchResponse searchResponse;
    List<Store> cuaHangList;
    List<Root> dienThoaiList;
    ListPhoneAdapter adapter;
    StoreAdapter storeAdapter;
    GridLayoutManager manager, managerStore;
    ProgressDialog progressDialog;
    int quantity = 0;
    MySharedPreferences mySharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);

        edSearch.requestFocus();
        edSearch.addTextChangedListener(searchTextWatcher);
        initVariable();
        actionFilter();
        return view;
    }
    private void initView(View view){
        rc_danhSachDienThoai = view.findViewById(R.id.rc_danhSachDienThoai);
        rc_danhSachCuaHang = view.findViewById(R.id.rc_danhSachCuaHang);
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
        tv_entry = view.findViewById(R.id.tv_entry);
    }
    private void initVariable(){
        dienThoaiList = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_danhSachDienThoai.setLayoutManager(manager);
        adapter = new ListPhoneAdapter(getContext());
        adapter.setData(dienThoaiList);
        adapter.setOnClickListener(new IItemListPhoneListener() {
            @Override
            public void onClickDetail(Root root) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idChiTietDienThoai", root.getChiTietDienThoai());
                Intent intent = new Intent(getContext(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rc_danhSachDienThoai.setAdapter(adapter);

        cuaHangList = new ArrayList<>();
        managerStore = new GridLayoutManager(getContext(), 2);
        rc_danhSachCuaHang.setLayoutManager(managerStore);
        storeAdapter = new StoreAdapter(getContext());
        storeAdapter.setData(cuaHangList);
        storeAdapter.setOnClickListener(new IItemStoreListener() {
            @Override
            public void getSanPhamTheoCuaHang(Store store) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cuaHang", store);
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rc_danhSachCuaHang.setAdapter(storeAdapter);
    }
    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            progressBar.setVisibility(View.VISIBLE);
            performSearch(editable.toString());
        }
    };
    private void performSearch(String query) {
        progressBar.setVisibility(View.VISIBLE);

        if (searchRunnable != null) {
            handler.removeCallbacks(searchRunnable);
        }
        searchRunnable = new Runnable() {
            @Override
            public void run() {
                ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(query.trim(), giaMin, giaMax, ram, boNho, sortByPrice, uuDaiHot, maHangSanXuat, sortDanhGia).enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            searchResponse = response.body();
                            dienThoaiList.clear();
                            dienThoaiList.addAll(searchResponse.getDienThoais());
                            adapter.notifyDataSetChanged();

                            cuaHangList.clear();
                            cuaHangList.addAll(searchResponse.getCuaHangs());
                            storeAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            tv_entry.setVisibility(View.GONE);
                        } else {
                            tv_entry.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        Log.e("zzzz", "onFailure: " + t.getMessage());
                    }
                });
            }
        };

        handler.postDelayed(searchRunnable, SEARCH_DELAY);
    }
    private void actionFilter() {
        ln_boLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialogFilterBoLoc();
            }
        });
        ln_sxDiemDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDanhGia();
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

    private void sortDanhGia() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Root>> call = apiService.getDanhGiaFilter("true", "");
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                        "" ,"", "", "",
                        "", "", "", "true").enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            searchResponse = response.body();
                            dienThoaiList.clear();
                            dienThoaiList.addAll(searchResponse.getDienThoais());
                            adapter.notifyDataSetChanged();

                            cuaHangList.clear();
                            cuaHangList.addAll(searchResponse.getCuaHangs());
                            storeAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            tv_entry.setVisibility(View.GONE);
                        } else {
                            tv_entry.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.e("filter dung luong ram", t.getLocalizedMessage());
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
                    selectedBoNho.append("512,");
                    selectedBoNho.append("10000,");
                }
                if (isOnclick128_258[0]) {
                    selectedBoNho.append("128,");
                    selectedBoNho.append("256,");
                }
                if (isOnclick32_64[0]) {
                    selectedBoNho.append("32,");
                    selectedBoNho.append("64,");
                }
                if (isOnclick4_6[0]) {
                    selectedRAMs.append("4,");
                    selectedRAMs.append("6,");
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

                if (selectedBoNho.length() > 0) {
                    selectedBoNho.deleteCharAt(selectedBoNho.length() - 1);
                }

               ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                       String.valueOf(minValue[0] * 1000000),String.valueOf(maxValue[0] * 1000000), selectedRAMs.toString(), selectedBoNho.toString(),
                       "", "", "", "").enqueue(new Callback<SearchResponse>() {
                   @Override
                   public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                       if (response.isSuccessful() && response.body() != null) {
                           searchResponse = response.body();
                           dienThoaiList.clear();
                           dienThoaiList.addAll(searchResponse.getDienThoais());
                           adapter.notifyDataSetChanged();

                           cuaHangList.clear();
                           cuaHangList.addAll(searchResponse.getCuaHangs());
                           storeAdapter.notifyDataSetChanged();

                           progressBar.setVisibility(View.GONE);
                           tv_entry.setVisibility(View.GONE);
                       } else {
                           tv_entry.setVisibility(View.VISIBLE);
                       }
                   }

                   @Override
                   public void onFailure(Call<SearchResponse> call, Throwable t) {

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
                ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                        String.valueOf(minValue[0] * 1000000),String.valueOf(maxValue[0] * 1000000), "", "",
                        "", "", "", "").enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            searchResponse = response.body();
                            dienThoaiList.clear();
                            dienThoaiList.addAll(searchResponse.getDienThoais());
                            adapter.notifyDataSetChanged();

                            cuaHangList.clear();
                            cuaHangList.addAll(searchResponse.getCuaHangs());
                            storeAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            tv_entry.setVisibility(View.GONE);
                        } else {
                            tv_entry.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

                    }
                });
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
                StringBuilder selectedBoNho = new StringBuilder();

                if (isOnclick512[0]) {
                    selectedBoNho.append("512,");
                    selectedBoNho.append("10000, " );
                }
                if (isOnclick128_258[0]) {
                    selectedBoNho.append("128,");
                    selectedBoNho.append("256,");
                }
                if (isOnclick32_64[0]) {
                    selectedBoNho.append("32,");
                    selectedBoNho.append("64,");
                }

                if (selectedBoNho.length() > 0) {
                    selectedBoNho.deleteCharAt(selectedBoNho.length() - 1);
                }


                ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                        "","", "", selectedBoNho.toString(),
                        "", "", "", "").enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            searchResponse = response.body();
                            dienThoaiList.clear();
                            dienThoaiList.addAll(searchResponse.getDienThoais());
                            adapter.notifyDataSetChanged();

                            cuaHangList.clear();
                            cuaHangList.addAll(searchResponse.getCuaHangs());
                            storeAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            tv_entry.setVisibility(View.GONE);
                        } else {
                            tv_entry.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

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

                ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                        "","", selectedRAMs.toString(), "",
                        "", "", "", "").enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            searchResponse = response.body();
                            dienThoaiList.clear();
                            dienThoaiList.addAll(searchResponse.getDienThoais());
                            adapter.notifyDataSetChanged();

                            cuaHangList.clear();
                            cuaHangList.addAll(searchResponse.getCuaHangs());
                            storeAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            tv_entry.setVisibility(View.GONE);
                        } else {
                            tv_entry.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {

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
        ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                "","", "", "",
                "desc", "", "", "").enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchResponse = response.body();
                    dienThoaiList.clear();
                    dienThoaiList.addAll(searchResponse.getDienThoais());
                    adapter.notifyDataSetChanged();

                    cuaHangList.clear();
                    cuaHangList.addAll(searchResponse.getCuaHangs());
                    storeAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                    tv_entry.setVisibility(View.GONE);
                } else {
                    tv_entry.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    private void sortGiaTienThapCao() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                "","", "", "",
                "asc", "", "", "").enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchResponse = response.body();
                    dienThoaiList.clear();
                    dienThoaiList.addAll(searchResponse.getDienThoais());
                    adapter.notifyDataSetChanged();

                    cuaHangList.clear();
                    cuaHangList.addAll(searchResponse.getCuaHangs());
                    storeAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                    tv_entry.setVisibility(View.GONE);
                } else {
                    tv_entry.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

    private void uuDaiHot() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(edSearch.getText().toString().trim(),
                "","", "", "",
                "", "true", "", "").enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchResponse = response.body();
                    dienThoaiList.clear();
                    dienThoaiList.addAll(searchResponse.getDienThoais());
                    adapter.notifyDataSetChanged();

                    cuaHangList.clear();
                    cuaHangList.addAll(searchResponse.getCuaHangs());
                    storeAdapter.notifyDataSetChanged();

                    progressBar.setVisibility(View.GONE);
                    tv_entry.setVisibility(View.GONE);
                } else {
                    tv_entry.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }

//    private void dialogBottomDetail(Root root) {
//        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
//        dialog.setContentView(R.layout.layout_themgio_muangay);
//
//        dialog.show();
//        ApiService apiService = ApiRetrofit.getApiService();
//        mySharedPreferences = new MySharedPreferences(getContext());
//        adapterDiaChi = new DiaChiNhanHangAdapter(getContext(), addressDeliveryList);
//        addressDeliveryList = new ArrayList<>();
//        TextView tvTenDienThoai, tvSoLuong, tvSoLuongTon;
//        LinearLayout lnMinius, lnAdd;
//        ImageView imgClose;
//        Button btnAddToCart, btnBuyNow;
//        tvTenDienThoai = dialog.findViewById(R.id.dialogBottomChiTiet_tvDienThoai);
//        tvSoLuong = dialog.findViewById(R.id.dialogBottomChiTiet_tvSoLuong);
//        tvSoLuongTon = dialog.findViewById(R.id.dialogBottomChiTiet_tvSoLuongTon);
//        lnMinius = dialog.findViewById(R.id.dialogBottomChiTiet_btnMinius);
//        lnAdd = dialog.findViewById(R.id.dialogBottomChiTiet_lnAdd);
//        imgClose = dialog.findViewById(R.id.dialogBottomChiTiet_imgClose);
//        btnAddToCart = dialog.findViewById(R.id.dialogBottomChiTiet_btnThemGioHang);
//        btnBuyNow = dialog.findViewById(R.id.dialogBottomChiTiet_btnMuaNgay);
//
//        tvTenDienThoai.setText("" + root.getChiTietDienThoai().getMaDienThoai().getTenDienThoai());
//        tvSoLuongTon.setText("Số lượng còn hàng: " + root.getChiTietDienThoai().getSoLuong());
//        tvSoLuong.setText("" + quantity);
//        lnMinius.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (quantity > 0) {
//                    quantity--;
//                    tvSoLuong.setText("" + quantity);
//                    lnMinius.setVisibility(View.VISIBLE);
//                    lnAdd.setVisibility(View.VISIBLE);
//                } else {
//                    lnMinius.setVisibility(View.GONE);
//                }
//            }
//        });
//        lnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (quantity < root.getChiTietDienThoai().getSoLuong()) {
//                    quantity++;
//                    tvSoLuong.setText("" + quantity);
//                    lnAdd.setVisibility(View.VISIBLE);
//                    lnMinius.setVisibility(View.VISIBLE);
//                } else {
//                    lnAdd.setVisibility(View.GONE);
//                }
//            }
//        });
//        btnBuyNow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//        btnAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
//                    ChiTietGioHang chiTietGioHang = new ChiTietGioHang();
//                    chiTietGioHang.setMaChiTietDienThoai(root.getChiTietDienThoai());
//                    chiTietGioHang.setSoLuong(quantity);
//                    chiTietGioHang.setGiaTien(root.getChiTietDienThoai().getGiaTien());
////                ApiRetrofit.getApiService().addGioHang(chiTietGioHang,mySharedPreferences.getUserId()).enqueue(new Callback<ChiTietGioHang>() {
////                    @Override
////                    public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
////                        if (response.isSuccessful()){
////                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
////                            dialog.dismiss();
////                            ///mới
////                            ShoppingCartManager.saveChiTietGioHangForId(getContext(),mySharedPreferences.getUserId(), chiTietGioHang);
////                        } else {
////                            Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
////
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
////                        Log.d("error", "onFailure: " + t.getMessage());
////                    }
////                });
//                    boolean isSuccess = ShoppingCartManager.saveChiTietGioHangForId(getContext(), mySharedPreferences.getUserId(), chiTietGioHang);
//                    if (isSuccess) {
//                        Toast.makeText(getContext(), "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
//                        quantity = 1;
//                    } else {
//                        Toast.makeText(getContext(), "Thêm vào giỏ hàng thất bại!", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Intent intent = new Intent(getContext(), LoginScreen.class);
//                    startActivity(intent);
//                }
//            }
//        });
//        imgClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                quantity = 1;
//            }
//        });
//    }
}
