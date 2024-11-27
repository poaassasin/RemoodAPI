package com.example.remood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Buatjurnal extends AppCompatActivity {

    ImageView i1;
    TextView i2;
    EditText judul, detailCerita;
    Button simpan;

    String emosi;
    private ActivityResultLauncher<Intent> resultLauncher;

    FirebaseDatabase db;

    DatabaseReference jurnalRef;

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
        emosi = bundle.getString("resId2");

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
                Toast toast = new Toast(getApplicationContext());
                Toast.makeText(Buatjurnal.this, emosi, Toast.LENGTH_SHORT).show();
                addJurnalToDB(judulCerita, detailJudul, emosi, tanggal, waktu);
                finish();
            }
        });

    }

    private void addJurnalToDB(String judulCerita, String detailJudul, String emosi, String tanggal, String waktu) {
        HashMap<String, Object> jurnalHashmap = new HashMap<>();
        jurnalHashmap.put("curhatan", judulCerita);
        jurnalHashmap.put("detail_curhatan", detailJudul);
        jurnalHashmap.put("link_gambar_emosi", emosi);
        jurnalHashmap.put("tanggal", tanggal);
        jurnalHashmap.put("waktu", waktu);


        db = FirebaseDatabase.getInstance();
        jurnalRef = db.getReference("catatan");

        String key = jurnalRef.push().getKey();

        jurnalHashmap.put("key", key);

        jurnalRef.child(key).setValue(jurnalHashmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Buatjurnal.this, "Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Buatjurnal.this,
                                        e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
        });

    }

}