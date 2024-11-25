package com.example.remood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.remood.model.JurnalModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Buatjurnal extends AppCompatActivity {

    ImageView i1;
    TextView i2;
    EditText judul, detailCerita;
    Button simpan;
    private ActivityResultLauncher<Intent> resultLauncher;

    JurnalDatabase jurnalDB;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buatjurnal);
        judul = (EditText)findViewById(R.id.judulJurnal);
        detailCerita = (EditText)findViewById(R.id.detailCerita);
        i1 = (ImageView)findViewById(R.id.moodUser);
        i2 = (TextView)findViewById(R.id.ketMood);
        simpan = (Button)findViewById(R.id.btnsimpanJurnal);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            int resid = bundle.getInt("resId");
            String resid2 = bundle.getString("resId2");
            i1.setImageResource(resid);
            i2.setText(resid2);
        }

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

        jurnalDB = Room.databaseBuilder(getApplicationContext(), JurnalDatabase.class,
                "JurnalDB").allowMainThreadQueries().addCallback(myCallBack).build();


        simpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String judulCerita = judul.getText().toString();
                String detailJudul = detailCerita.getText().toString();
                Date dateAndTime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                String tanggal = dateFormat.format(dateAndTime);
                String waktu = timeFormat.format(dateAndTime);
                String emosi = bundle.getString("resId2");
                Toast toast = new Toast(getApplicationContext());
                Toast.makeText(Buatjurnal.this, emosi, Toast.LENGTH_SHORT).show();
                JurnalModel p1 = new JurnalModel(judulCerita, detailJudul, emosi, tanggal, waktu);
                jurnalDB.getJurnalDAO().addJurnal(p1);
                finish();
            }
        });

    }

}