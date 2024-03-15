package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.UuDaiAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiUuDaiService;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemUuDaiListenner;
import com.example.appcuahang.model.UuDai;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UuDaiFragment extends Fragment {
    RecyclerView rc_uuDai;
    List<UuDai> list;
    List<UuDai> listBackUp;
    UuDaiAdapter adapter;
    GridLayoutManager manager;
    EditText edGiamGia, edThoiGian;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uudai, container, false);
        ((Activity) getContext()).setTitle("Ưu đãi");
        getData();
        initView(view);
        initVariable();
        return view;
    }

    private void initView(View view) {
        rc_uuDai = view.findViewById(R.id.rc_uuDai);
    }

    private void initVariable() {
        list = new ArrayList<>();
        listBackUp = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 1);
        rc_uuDai.setLayoutManager(manager);

        adapter = new UuDaiAdapter(getContext(), new IItemUuDaiListenner() {
            @Override
            public void showDetail(String idUuDai) {

            }

            @Override
            public void editUuDai(UuDai idUuDai) {
                updateData(idUuDai);
                Log.d("zzz", "uudai");

            }
        });
        adapter.setData(list);
        rc_uuDai.setAdapter(adapter);

    }
    private void getData(){
        ApiUuDaiService apiUuDaiService = ApiRetrofit.getApiUuDaiService();
        Call<List<UuDai>> call = apiUuDaiService.getUuDai();
        call.enqueue(new Callback<List<UuDai>>() {
            @Override
            public void onResponse(Call<List<UuDai>> call, Response<List<UuDai>> response) {
                if(response.isSuccessful()){
                    List<UuDai> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UuDai>> call, Throwable t) {
                Log.e("uudai", t.getMessage());
            }
        });
    }

    private void updateData(UuDai uuDai){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_uudai, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        edGiamGia = view.findViewById(R.id.dl_uuDai_edGiamGia);
        edThoiGian = view.findViewById(R.id.dl_uuDai_edThoiGian);


        Button btnSave = view.findViewById(R.id.dl_uuDai_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_uuDai_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_uuDai_imageView);

        tvTitle.setText("Cập nhật Ưu Đãi");
        edGiamGia.setText(uuDai.getGiamGia());

        edThoiGian.setText(uuDai.getThoiGian());
        edThoiGian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Validate()){

                    String giamGia = edGiamGia.getText().toString().trim();
                    String thoiGian = edThoiGian.getText().toString().trim();
                    String trangThai;

                    // Kiểm tra nếu thời gian nhập từ người dùng lớn hơn hoặc bằng thời gian hiện tại
                    if (isValidDate(thoiGian)) {
                        trangThai = "Hoạt động";
                    } else {
                        trangThai = "Không hoạt động";
                    }
                    ApiUuDaiService apiUuDaiService = ApiRetrofit.getApiUuDaiService();
                    Call<UuDai> call = apiUuDaiService.putUuDai(uuDai.get_id(), new UuDai(giamGia, thoiGian, trangThai));

                    call.enqueue(new Callback<UuDai>() {
                        @Override
                        public void onResponse(Call<UuDai> call, Response<UuDai> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                getData();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<UuDai> call, Throwable t) {
                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dialog,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_add){
            dialog();
        }
        return super.onOptionsItemSelected(item);
    }

  private void dialog(){
      AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
      View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_uudai, null);
      builder.setView(view);
      Dialog dialog = builder.create();
      dialog.show();
      Window window = dialog.getWindow();
      if(window == null){
          return;
      }
      window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
      window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      WindowManager.LayoutParams windowAttributes = window.getAttributes();
      windowAttributes.gravity = Gravity.CENTER;
      window.setAttributes(windowAttributes);

      edGiamGia = view.findViewById(R.id.dl_uuDai_edGiamGia);
      edThoiGian = view.findViewById(R.id.dl_uuDai_edThoiGian);
      edThoiGian.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              showDatePickerDialog();
          }
      });

      Button btnSave = view.findViewById(R.id.dl_uuDai_btnSave);
      TextView tvTitle = view.findViewById(R.id.dl_uuDai_tvTitle);
      ImageView imgView = view.findViewById(R.id.dl_uuDai_imageView);

      tvTitle.setText("Dialog Thêm Ưu Đãi");
      btnSave.setText("Thêm mới");

      btnSave.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
           if(Validate()){
               String giamGia = edGiamGia.getText().toString().trim();
               String thoiGian = edThoiGian.getText().toString().trim();
               String trangThai;

               // Kiểm tra nếu thời gian nhập từ người dùng lớn hơn hoặc bằng thời gian hiện tại
               if (isValidDate(thoiGian)) {
                   trangThai = "Hoạt động";
               } else {
                   trangThai = "Không hoạt động";
               }


               ApiUuDaiService apiUuDaiService = ApiRetrofit.getApiUuDaiService();
               Call<UuDai> call = apiUuDaiService.postUuDai(new UuDai(giamGia, thoiGian,trangThai));

               call.enqueue(new Callback<UuDai>() {
                   @Override
                   public void onResponse(Call<UuDai> call, Response<UuDai> response) {
                       if (response.isSuccessful()) {
                           Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                           getData();
                           dialog.dismiss();
                       }
                   }

                   @Override
                   public void onFailure(Call<UuDai> call, Throwable t) {
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

    private boolean isValidDate(String inputDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date currentDate = new Date();
        try {
            Date userDate = sdf.parse(inputDate);
            return userDate != null && userDate.after(currentDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
//        return false;
    }

    private void showDatePickerDialog() {
        // Lấy ngày hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Hiển thị DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    String selectedDate = selectedDayOfMonth + "-" + (selectedMonth + 1) + "-" + selectedYear;
                    edThoiGian.setText(selectedDate);
                },
                year,
                month,
                dayOfMonth);
        datePickerDialog.show();
    }


    private boolean Validate() {
        if (edGiamGia.getText().toString().isEmpty()) {
            edGiamGia.setError("Không được để trống!!");
            return false;
        } else if (!Pattern.matches("\\d+", edGiamGia.getText().toString())) {
            edGiamGia.setError("Phải nhập là số!!");
            return false;
        }
        return true;
    }

}