package com.example.appcuahang.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.appcuahang.R;


public class MoreFragment extends Fragment {

    //    CardView cv_hangSanXuat, cv_hoaDon , cv_ThongTinCaNhan;
    CardView cv_hangSanXuat, cv_hoaDon, cv_mau, cv_loaiRam, cv_ThongTinCaNhan, cv_thongKe, cv_DungLuong , cv_dienThoai, cv_client, cv_uudai;

    //    CardView cv_hangSanXuat, cv_hoaDon , cv_ThongTinCaNhan;

    String _idStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_more, container, false);
        ((Activity) getContext()).setTitle("Nhiều Hơn");
        initView(view);
        action();
        return view;
    }

    public void initView(View view) {
        cv_hangSanXuat = view.findViewById(R.id.cv_hangSanXuat);
        cv_hoaDon = view.findViewById(R.id.cv_hoaDon);
        cv_thongKe = view.findViewById(R.id.cv_thongKe);
        cv_ThongTinCaNhan = view.findViewById(R.id.cv_thongTinCaNhan);
        cv_DungLuong = view.findViewById(R.id.cv_DungLuong);
        cv_loaiRam = view.findViewById(R.id.cv_loaiRam);
        cv_mau = view.findViewById(R.id.cv_mau);
        cv_dienThoai = view.findViewById(R.id.cv_dienThoai);
        cv_client = view.findViewById(R.id.cv_client);
        cv_uudai = view.findViewById(R.id.cv_uudai);

    }

    private void action() {
        cv_hangSanXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BrandFragment());
            }
        });

        cv_hoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BillOrderFragment());
            }
        });
        cv_thongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new StatisticalFragment());
            }
        });
        cv_ThongTinCaNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new InfoStoreFragment());
            }
        });
        cv_DungLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new DungLuongFragament());
            }
        });
        cv_loaiRam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new RamFragment());
            }
        });
        cv_mau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MauFragment());
            }
        });
        cv_dienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               replaceFragment(new PhoneFragment());
            }
        });
        cv_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ClientFragment());
            }
        });
        cv_uudai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new UuDaiFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dialog, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_add) {
        }
        return super.onOptionsItemSelected(item);
    }

}