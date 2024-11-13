package com.example.remood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.remood.base.VolleySingleton;
import com.example.remood.databinding.FragmentJurnalBinding;
import com.example.remood.model.JurnalModel;
import com.example.remood.model.JurnalResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

public class JurnalFragment extends Fragment implements JurnalItemAdapter.ItemCallbackListener {
    private FragmentJurnalBinding binding;
    private ArrayList<JurnalModel> listData;
    private JurnalItemAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJurnalBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listData= new ArrayList<>();
        fetchData();
    }

    private void fetchData(){
        String url = "https://api.mockfly.dev/mocks/eba5320f-d497-441d-a903-08796e25d363/Mood";

        // Create a new StringRequest
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("fetchData", "fetchData: "+response.toString());
                    // Parse JSON response with Gson
                    Gson gson = new Gson();
                    JurnalResponse users = gson.fromJson(response, JurnalResponse.class);
                    listData.addAll(users.getData());
                    adapter = new JurnalItemAdapter(listData,this::onClick);
                    binding.rvList.setAdapter(adapter);
                    binding.rvList.setVisibility(View.VISIBLE);
                    binding.placeholderContainer.setVisibility(View.INVISIBLE);
                },
                error -> {
                    binding.rvList.setVisibility(View.INVISIBLE);
                    binding.placeholderContainer.setVisibility(View.VISIBLE);
                    Log.e("Volley Error", error.toString());
                });

        // Add the request to the RequestQueue
        VolleySingleton.getInstance(this.getContext()).getRequestQueue().add(stringRequest);
    }

    @Override
    public void onClick(int position) {
        listData.remove(position);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRemoved(position);
    }
}