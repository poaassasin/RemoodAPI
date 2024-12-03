package com.example.remood;

import android.content.Intent;
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
import com.example.remood.databinding.FragmentJurnalBinding;
import com.example.remood.model.JurnalModel;
import java.util.ArrayList;
import java.util.List;

public class JurnalFragment extends Fragment implements JurnalItemAdapter.ItemCallbackListener {
    private FragmentJurnalBinding binding;
    private ArrayList<JurnalModel> listData;
    private JurnalItemAdapter adapter;

    private JurnalDatabase jurnalDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };
        jurnalDB = Room.databaseBuilder(requireActivity().getApplicationContext(), JurnalDatabase.class,
                "JurnalDB").allowMainThreadQueries().addCallback(myCallBack).build();
        binding = FragmentJurnalBinding.inflate(inflater,container,false);
        View v = binding.getRoot();
        binding.searchBar.clearFocus();
        binding.searchBar.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        return v;

    }

    private void searchList(String text) {
        List<JurnalModel> dataSearchList = new ArrayList<>();
        for (JurnalModel jurnal : listData) {
            if (jurnal.getCurhatan().toLowerCase().contains(text.toLowerCase())) {
                dataSearchList.add(jurnal);
            }
        }
        if (dataSearchList.isEmpty()) {
            Toast.makeText(this.getActivity(), "Tidak Ditemukan", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setSearchList(dataSearchList);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        listData= new ArrayList<>();
        loadDatabase();
    }

    private void loadDatabase() {

        listData.clear();
        List<JurnalModel> jurnal = jurnalDB.getJurnalDAO().getAllJurnal();
        if (jurnal.isEmpty()) {
            binding.rvList.setVisibility(View.INVISIBLE);
            binding.placeholderContainer.setVisibility(View.VISIBLE);
        } else {
            for (JurnalModel jurnalItem : jurnal) {
                listData.add(new JurnalModel(
                        jurnalItem.getCurhatan(),
                        jurnalItem.getDetailCurhatan(),
                        jurnalItem.getLinkGambarEmosi(),
                        jurnalItem.getTanggal(),
                        jurnalItem.getWaktu()
                ));
            }
            adapter = new JurnalItemAdapter(listData, this::onClick, getActivity());
            binding.rvList.setAdapter(adapter);
            binding.rvList.setVisibility(View.VISIBLE);
            binding.placeholderContainer.setVisibility(View.INVISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDatabase();
    }

    @Override
    public void onClick(int position, String pilihan) {
        jurnalDB = Room.databaseBuilder(getActivity().getApplicationContext(), JurnalDatabase.class,
                "JurnalDB").allowMainThreadQueries().build();
        JurnalDAO jurnalDAO = jurnalDB.getJurnalDAO();
        switch (pilihan) {
            case "delete" :
                try {
                    String waktuDiTarik = listData.get(position).getWaktu();
                    Log.d("JurnalFragment", "Deleted item with ID: "+waktuDiTarik);
                    jurnalDAO.deleteJurnal(jurnalDB.getJurnalDAO().findByWaktu(waktuDiTarik));
                    listData.remove(position);
                    List<JurnalModel> listJurnal = jurnalDB.getJurnalDAO().getAllJurnal();
                    if (listJurnal.isEmpty()) {
                        binding.rvList.setVisibility(View.INVISIBLE);
                        binding.placeholderContainer.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e) {
                    Log.d("JurnalFragment", e.getMessage());
                    Toast.makeText(this.getActivity(), "Tidak Berhasil", Integer.parseInt(e.getMessage()));
                }
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(position);
                break;
            case "edit":
                Intent intent = new Intent(getActivity().getApplicationContext(), EditJurnal.class);
                intent.putExtra("position", position);
                intent.putExtra("currentMood", listData.get(position).getLinkGambarEmosi());
                intent.putExtra("currentTitle", listData.get(position).getCurhatan());
                intent.putExtra("currentDesc", listData.get(position).getDetailCurhatan());
                intent.putExtra("currentDate", listData.get(position).getTanggal());
                intent.putExtra("currentTime", listData.get(position).getWaktu());
                getActivity().startActivity(intent);
                break;
            default:
                Toast.makeText(getActivity().getApplicationContext(), "Tidak ada pilihan", Toast.LENGTH_SHORT).show();
        }

    }

}