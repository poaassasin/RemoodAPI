package com.example.remood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Buatjurnal extends AppCompatActivity {

    ImageView i1;
    TextView i2;
    EditText judul, detailCerita;
    Button simpan;
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
            int resid2 = bundle.getInt("resId2");
            i1.setImageResource(resid);
            i2.setText(resid2);
        }

        simpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String judulCerita = judul.getText().toString();
                String detailJudul = detailCerita.getText().toString();
                Intent intent = new Intent(Buatjurnal.this, BottomNav.class);
                intent.putExtra("keyjudul", judulCerita);
                intent.putExtra("keydetail", detailJudul);
                startActivity(intent);
            }
        });
    }
}