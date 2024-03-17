package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.ClientAdapter;
import com.example.appcuahang.api.ApiClientService;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemClientListenner;
import com.example.appcuahang.model.Client;
import com.example.appcuahang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClientFragment extends Fragment {
    RecyclerView rc_client;
    List<Client> list;
    List<Client> listBackUp;
    ClientAdapter adapter;
    GridLayoutManager manager;
    EditText edClient;

    MySharedPreferences mySharedPreferences;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_client, container, false);
        ((Activity)getContext()).setTitle("Khách hàng");

        initView(view);
        initVariable();

        mySharedPreferences = new MySharedPreferences(getContext());
        getData(mySharedPreferences.getUserId());
        return view;

    }
    private void initView(View view){
        rc_client = view.findViewById(R.id.rc_client);
    }

    private void initVariable(){
        list = new ArrayList<>();
        listBackUp = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_client.setLayoutManager(manager);

        adapter = new ClientAdapter(getContext(), new IItemClientListenner() {

            @Override
            public void showDetail(String idClient) {

            }
        });
        adapter.setData(list);
        rc_client.setAdapter(adapter);
    }
    private void getData(String id){
        ApiClientService apiClientService = ApiRetrofit.getApiClientService();
        Call<List<Client>> call = apiClientService.getClient(id);

        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                if(response.isSuccessful()){
                    List<Client> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Client>> call, Throwable t) {
                Log.e("client", t.getMessage());
            }
        });
    }

}
