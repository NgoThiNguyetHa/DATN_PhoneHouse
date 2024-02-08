package com.example.appcuahang.fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;


public class MoreFragment extends Fragment {
//    CardView cv_hangSanXuat, cv_hoaDon , cv_ThongTinCaNhan;
    CardView cv_hangSanXuat, cv_hoaDon , cv_mau , cv_loaiRam , cv_ThongTinCaNhan , cv_thongKe;
    String _idStore;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_more, container, false);
        ((Activity)getContext()).setTitle("Nhiều Hơn");
        initView(view);
        action();
        return view;
    }

    public void initView(View view){
        cv_hangSanXuat = view.findViewById(R.id.cv_hangSanXuat);
        cv_hoaDon = view.findViewById(R.id.cv_hoaDon);
        cv_thongKe = view.findViewById(R.id.cv_thongKe);
    }

    private void action(){
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
    }

    private void replaceFragment(Fragment fragment){
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