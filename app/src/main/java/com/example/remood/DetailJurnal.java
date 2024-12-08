package com.example.remood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.example.remood.model.JurnalModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailJurnal extends AppCompatActivity {
    private TextView tvKembaliDetail, ubahTextView, moodTextView, titleTextView, descTextView,
            dateTextView;
    private ImageView moodImageView;
    private JurnalDatabase db;
    private JurnalModel currentMoodEntity;

    private ArrayList<JurnalModel> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jurnal);

        db = Room.databaseBuilder(this, JurnalDatabase.class,
                "JurnalDB").allowMainThreadQueries().build();
        JurnalDAO jurnalDAO = db.getJurnalDAO();
        tvKembaliDetail = (TextView) findViewById(R.id.tvKembaliDetail);
        ubahTextView = (TextView) findViewById(R.id.tvUbah);
        moodTextView = findViewById(R.id.tvKetMood);
        titleTextView = findViewById(R.id.tvJudulDetail);
        descTextView = findViewById(R.id.tvDescDetail);
        moodImageView = findViewById(R.id.ivMood);
        dateTextView = findViewById(R.id.dateDetail);
        Bundle bundle = getIntent().getExtras();
        currentMoodEntity = db.getJurnalDAO().findByWaktu(bundle.getString("currentTime"));
        tvKembaliDetail.setOnClickListener(v -> finish());
        ubahTextView.setOnClickListener(v -> {
            Intent intent = new Intent(DetailJurnal.this, EditJurnal.class);
            intent.putExtra("position", bundle.getInt("position"));
            intent.putExtra("currentMood", bundle.getString("currentMood"));
            intent.putExtra("currentTitle", bundle.getString("currentTitle"));
            intent.putExtra("currentDesc", bundle.getString("currentDesc"));
            intent.putExtra("currentDate", bundle.getString("currentDate"));
            intent.putExtra("currentTime", bundle.getString("currentTime"));
            startActivity(intent);
        });
        updateUI(currentMoodEntity);
    }

    private void updateUI(JurnalModel jurnal) {
        moodTextView.setText(jurnal.getLinkGambarEmosi());
        titleTextView.setText(jurnal.getCurhatan());
        descTextView.setText(jurnal.getDetailCurhatan());
        dateTextView.setText(jurnal.getTanggal());
        updateMoodImage(jurnal.getLinkGambarEmosi());
    }

    private void updateMoodImage(String image) {
        switch (image) {
            case "Happy!":
                moodImageView.setImageResource(R.drawable.perasaansenang);
                break;
            case "Oke!":
                moodImageView.setImageResource(R.drawable.perasaanoke);
                break;
            case "Meh":
                moodImageView.setImageResource(R.drawable.perasaandatar);
                break;
            case "Sedih:(":
                moodImageView.setImageResource(R.drawable.perasaansedih);
                break;
            case "Marah!":
                moodImageView.setImageResource(R.drawable.perasaanmarah);
                break;
            default:
                moodImageView.setImageResource(R.drawable.image_perasaan);
        }
    }
}