package com.example.remood;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        adapter = new JurnalItemAdapter(listData, this::onClick);
        binding.rvList.setAdapter(adapter);
        binding.rvList.setVisibility(View.VISIBLE);
        binding.placeholderContainer.setVisibility(View.INVISIBLE);
        loadFirebase();
    }

    private void loadFirebase() {
        listData.clear();
        FirebaseDatabase.getInstance().getReference("catatan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listData.clear();
                JurnalModel catatan = snapshot.getValue(JurnalModel.class);
                if (catatan == null) {
                    binding.rvList.setVisibility(View.INVISIBLE);
                    binding.placeholderContainer.setVisibility(View.VISIBLE);
                } else {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    JurnalModel catatanLoop = dataSnapshot.getValue(JurnalModel.class);
                        listData.add( new JurnalModel(catatanLoop.getCurhatan(),
                                catatanLoop.getDetailCurhatan(),
                                catatanLoop.getLinkGambarEmosi(),
                                catatanLoop.getWaktu(),
                                catatanLoop.getTanggal()
                                ));
                    }
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        loadFirebase();
    }
    @Override
    public void onClick(int position) {
//        jurnalDB.getJurnalDAO().deleteJurnal(listData.get(position));
        listData.remove(position);
        adapter.notifyDataSetChanged();
        adapter.notifyItemRemoved(position);
//        adapter.notifyItemRangeRemoved(position, listData.size());
    }
}