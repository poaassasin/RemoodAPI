package com.example.remood;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
            loadDatabase();
            Toast.makeText(this.getActivity(), "Tidak Ditemukan/Kosong", Toast.LENGTH_SHORT).show();
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
                showDialogHapus(position);
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
<<<<<<< HEAD
=======
            case "lihatDetail":
                Intent intentDetail = new Intent(getActivity().getApplicationContext(), DetailJurnal.class);
                intentDetail.putExtra("position", position);
                intentDetail.putExtra("currentMood", listData.get(position).getLinkGambarEmosi());
                intentDetail.putExtra("currentTitle", listData.get(position).getCurhatan());
                intentDetail.putExtra("currentDesc", listData.get(position).getDetailCurhatan());
                intentDetail.putExtra("currentDate", listData.get(position).getTanggal());
                intentDetail.putExtra("currentTime", listData.get(position).getWaktu());
                getActivity().startActivity(intentDetail);
>>>>>>> d1831e0 (Menambahkan RecyclerView, Tambah Edit Hapus Pilihan Mood, dan Detail Jurnal punya Alya dan Zahrina)
            default:
                Toast.makeText(getActivity().getApplicationContext(), "Tidak ada pilihan", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogHapus(int position) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_delete);

        Button btHapus = dialog.findViewById(R.id.btnHapusDialog);
        Button btCancel = dialog.findViewById(R.id.btnCancelDialog);
        ImageView ivEmoji = dialog.findViewById(R.id.emosiDialog);
        TextView tvDate = dialog.findViewById(R.id.dialogDate);
        TextView tvJudul = dialog.findViewById(R.id.dialogJudul);
        TextView tvDeskripsi = dialog.findViewById(R.id.dialogDeskripsi);
        jurnalDB = Room.databaseBuilder(getActivity().getApplicationContext(), JurnalDatabase.class,
                "JurnalDB").allowMainThreadQueries().build();
        JurnalDAO jurnalDAO = jurnalDB.getJurnalDAO();
        int emotionResource = 0;
        switch (listData.get(position).getLinkGambarEmosi()) {
            case "Marah!":
                emotionResource = R.drawable.perasaanmarah;
                break;
            case "Happy!":
                emotionResource = R.drawable.perasaansenang;
                break;
            case "Meh":
                emotionResource = R.drawable.perasaandatar;
                break;
            case "Oke!":
                emotionResource = R.drawable.perasaanoke;
                break;
            case "Sedih:(":
                emotionResource = R.drawable.perasaansedih;
                break;
            default:
                emotionResource = R.drawable.image_perasaan;
        }
        ivEmoji.setImageResource(emotionResource);
        tvDate.setText(listData.get(position).getTanggal() + " | " + listData.get(position).getWaktu());
        tvJudul.setText(listData.get(position).getCurhatan());
        tvDeskripsi.setText(listData.get(position).getDetailCurhatan());

        btHapus.setOnClickListener(v -> {
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
                Toast.makeText(getActivity(), "Tidak Berhasil", Integer.parseInt(e.getMessage()));
            }
            adapter.notifyDataSetChanged();
            adapter.notifyItemRemoved(position);
            dialog.dismiss();
        });
        btCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}