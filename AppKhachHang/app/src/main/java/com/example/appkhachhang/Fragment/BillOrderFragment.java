package com.example.appkhachhang.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.appkhachhang.Adapter.ViewPagerAdapter;
import com.example.appkhachhang.R;
import com.google.android.material.tabs.TabLayout;


public class BillOrderFragment extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bill_order, container, false);
        ((Activity)getContext()).setTitle("Quản Lý Hóa Đơn");
        init(view);
        action();
        customTabLayout();
        return  view;

    }

    private void init(View view){
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.viewPager2);
    }

    private void action(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void customTabLayout(){
        int tabCount = mTabLayout.getTabCount();

        for (int i = 0; i < tabCount; i++) {
            View tabView = ((ViewGroup) mTabLayout.getChildAt(0)).getChildAt(i);
            tabView.requestLayout();
            ViewCompat.setBackground(tabView, setImageButtonStateNew(requireContext()));
            ViewCompat.setPaddingRelative(tabView, tabView.getPaddingStart(), tabView.getPaddingTop(), tabView.getPaddingEnd(), tabView.getPaddingBottom());
        }
    }

    public StateListDrawable setImageButtonStateNew(Context mContext) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_selected}, ContextCompat.getDrawable(mContext, R.drawable.tab_bg_normal_blue));
        states.addState(new int[]{-android.R.attr.state_selected}, ContextCompat.getDrawable(mContext, R.drawable.tab_bg_normal));

        return states;
    }
}