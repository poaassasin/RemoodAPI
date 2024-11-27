package am.pam.remoodedit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private TextView moodTextView, titleTextView, descTextView, dateTextView;
    ImageView moodImageView;
    private FirebaseDatabase fD;
    private Remood currentMoodEntity;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi FirebaseDatabase dan DatabaseReference
        fD = FirebaseDatabase.getInstance(
                "https://remood-34cfa-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );
        databaseReference = fD.getReference("moods");

        // Inisialisasi UI
        moodTextView = findViewById(R.id.textView4);
        titleTextView = findViewById(R.id.textView);
        descTextView = findViewById(R.id.textView2);
        moodImageView = findViewById(R.id.imageView);
        dateTextView = findViewById(R.id.textView3);

        // Mendengarkan data mood dengan ID 1 secara real-time
        databaseReference.child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentMoodEntity = snapshot.getValue(Remood.class);

                if (currentMoodEntity == null) {
                    // Buat data default jika belum ada
                    currentMoodEntity = new Remood("Happy", "Default Title", "Default Description", "01 September 2024");
                    currentMoodEntity.id = 1;
                    databaseReference.child(String.valueOf(currentMoodEntity.id)).setValue(currentMoodEntity);
                }

                // Perbarui UI dengan data yang dimuat
                updateUI(currentMoodEntity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
            }
        });

        // Tombol "Ubah"
        TextView ubahTextView = findViewById(R.id.textView5);
        ubahTextView.setOnClickListener(v -> {
            if (currentMoodEntity != null) {
                openEditFragment();
            } else {
                Toast.makeText(MainActivity.this, "Data belum siap, silakan coba lagi.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(Remood mood) {
        moodTextView.setText(mood.mood);
        titleTextView.setText(mood.title);
        descTextView.setText(mood.description);
        dateTextView.setText(mood.date);
        updateMoodImage(mood.mood);
    }

    private void updateMoodImage(String mood) {
        switch (mood) {
            case "Happy":
                moodImageView.setImageResource(R.drawable.happyy);
                break;
            case "Good":
                moodImageView.setImageResource(R.drawable.good);
                break;
            case "So-So":
                moodImageView.setImageResource(R.drawable.meh);
                break;
            case "Sad":
                moodImageView.setImageResource(R.drawable.sadd);
                break;
            case "Bad":
                moodImageView.setImageResource(R.drawable.angry);
                break;
            default:
                moodImageView.setImageResource(R.drawable.sadd);
        }
    }

    private void openEditFragment() {
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);

        // Kirim data ke fragment
        Bundle bundle = new Bundle();
        bundle.putInt("id", currentMoodEntity.id);
        bundle.putString("currentMood", currentMoodEntity.mood);
        bundle.putString("currentTitle", currentMoodEntity.title);
        bundle.putString("currentDesc", currentMoodEntity.description);
        bundle.putString("currentDate", currentMoodEntity.date);

        EditRemoodFragment fragment = new EditRemoodFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}