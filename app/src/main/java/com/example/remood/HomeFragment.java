package com.example.remood;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    TextView judul;
    TextView detailJudul;

    ImageView emojiMood;

    JurnalDatabase jurnalDB;

    int emotionResource;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        judul = (TextView) v.findViewById(R.id.text_judulcurhat);
        detailJudul = (TextView) v.findViewById(R.id.text_detailcurhat);
        emojiMood = (ImageView) v.findViewById(R.id.image_perasaan);

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

        jurnalDB = Room.databaseBuilder(requireActivity(), JurnalDatabase.class,
                "JurnalDB").allowMainThreadQueries().addCallback(myCallBack).build();
        if (jurnalDB.getJurnalDAO().getAllJurnal().isEmpty()) {
            judul.setText("Ga ada catatanmu");
            detailJudul.setText("Belum ada saat ini:)");
            emojiMood.setImageResource(R.drawable.image_perasaan);
        } else {
            judul.setText(jurnalDB.getJurnalDAO().getLatestJurnal().getCurhatan());
            detailJudul.setText(jurnalDB.getJurnalDAO().getLatestJurnal().getDetailCurhatan());
            emotionResource = 0;
            switch (jurnalDB.getJurnalDAO().getLatestJurnal().getLinkGambarEmosi()) {
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
                    emotionResource = R.drawable.amico;
            }
            emojiMood.setImageResource(emotionResource);

        }
        return v;
    }

}