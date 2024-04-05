package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ClientFragment extends Fragment {
    RecyclerView rc_client;
    List<Client> list;
    List<Client> listFilter;
    ClientAdapter adapter;
    GridLayoutManager manager;
    EditText edClient;
    TextView tv_entry;
    TextInputEditText client_edSearch;
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


        //Khoi tao bien
        listFilter = new ArrayList<>();

        client_edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listFilter.clear();
                tv_entry.setVisibility(View.VISIBLE);
                for(int i = 0; i< list.size(); i++){
                    if(list.get(i).getUsername().toString().contains(client_edSearch.getText().toString()) && client_edSearch.getText().length() != 0){
                        listFilter.add(list.get(i));
                        tv_entry.setVisibility(View.GONE);
                    }
                }

                if(listFilter.size() == 0){
                    tv_entry.setVisibility(View.VISIBLE);
                }

                adapter = new ClientAdapter(getContext(), new IItemClientListenner() {

                    @Override
                    public void showDetail(String idClient) {

                    }
                });

                if (client_edSearch.getText().toString().trim().isEmpty()) {
                    adapter.setData(list);
                    tv_entry.setVisibility(View.GONE);
                    rc_client.setAdapter(adapter);
                } else {
                    adapter.setData(listFilter);
                    rc_client.setAdapter(adapter);
                }
            }
        });
        return view;

    }
    private void initView(View view){

        rc_client = view.findViewById(R.id.rc_client);
        client_edSearch = view.findViewById(R.id.client_edSearch);
        tv_entry = view.findViewById(R.id.tv_entry);
    }

    private void initVariable(){
        list = new ArrayList<>();
        listFilter = new ArrayList<>();
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
