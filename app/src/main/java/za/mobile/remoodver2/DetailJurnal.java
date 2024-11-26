package za.mobile.remoodver2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailJurnal extends AppCompatActivity {
    private TextView tvJudul, tvIsi, tvTanggal, tvMoodLable;
    private ImageView ivMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailjurnal);

        // Dapatkan data yang dikirim dari intent
        String judul = getIntent().getStringExtra("judul");
        String isi = getIntent().getStringExtra("isiText");
        String tanggal = getIntent().getStringExtra("waktu");
        int mood = getIntent().getIntExtra("ivMood", R.drawable.fill);
        String moodLable = getIntent().getStringExtra("moodLable");

        // Inisialisasi komponen UI
        tvJudul = findViewById(R.id.tvJudulJurnal);
        tvIsi = findViewById(R.id.tvIsiDetail);
        tvTanggal = findViewById(R.id.tvTanggalDetail);
        tvMoodLable = findViewById(R.id.tvMoodLable);
        ivMood = findViewById(R.id.ivMoodDetail);

        // Set data ke tampilan
        tvJudul.setText(judul);
        tvIsi.setText(isi);
        tvTanggal.setText(tanggal);
        tvMoodLable.setText(moodLable);
        ivMood.setImageResource(mood);


        TextView backButton = findViewById(R.id.btBack);
        backButton.setOnClickListener(v -> {
            // Tutup DetailJurnal dan kembali ke DaftarRiwayat
            finish();
        });
    }
}
