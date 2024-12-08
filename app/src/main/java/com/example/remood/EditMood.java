package com.example.remood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditMood extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MoodAdapter moodAdapter;
    private ArrayList<Mood> moodList;
    private Button addMoodButton;
    private TextView kembali;
    private static final int REQUEST_EDIT_MOOD = 1;
    private int selectedMoodPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addMoodButton = findViewById(R.id.btTambah);
        kembali = findViewById(R.id.tvKembali);

        // Membuat data list
        moodList = new ArrayList<>();
        loadMoods();

        // Mengatur adapter dan layout manager
        moodAdapter = new MoodAdapter(this, moodList, new MoodAdapter.OnMoodActionListener() {
            @Override
            public void onEdit(int position) {
                // Navigate to EditMood2Activity
                selectedMoodPosition = position;
                Mood selectedMood = moodList.get(position);
                Intent intent = new Intent(EditMood.this, EditMood2.class);
                intent.putExtra("moodName", selectedMood.getName());
                intent.putExtra("moodImage", selectedMood.getImageResId());
                startActivityForResult(intent, REQUEST_EDIT_MOOD);
            }
            @Override
            public void onDelete(int position) {
                moodList.remove(position);
                moodAdapter.notifyItemRemoved(position);
                Toast.makeText(EditMood.this, "Mood dihapus", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(moodAdapter);

        // Event Tambah Mood ketika tombol ditekan
        addMoodButton.setOnClickListener(v -> {
            // Menambah data baru ke dalam list
            Mood newMood = new Mood(R.drawable.silly, "Perasaan Konyol");
            moodList.add(newMood);
            moodAdapter.notifyItemInserted(moodList.size() - 1);
            recyclerView.scrollToPosition(moodList.size() - 1);

            // Menampilkan pesan Toast
            Toast.makeText(EditMood.this, "Mood Baru berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
        });

        kembali.setOnClickListener(v -> {
            Intent intent = new Intent(EditMood.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_MOOD && resultCode == RESULT_OK && data != null) {
            String updatedMoodName = data.getStringExtra("updatedMoodName");
            if (selectedMoodPosition != -1 && updatedMoodName != null) {
                moodList.get(selectedMoodPosition).setName(updatedMoodName);
                moodAdapter.notifyItemChanged(selectedMoodPosition);
                Toast.makeText(this, "Mood berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void loadMoods() {
        moodList.add(new Mood(R.drawable.perasaansenang, "Happy!"));
        moodList.add(new Mood(R.drawable.perasaanoke, "Oke!"));
        moodList.add(new Mood(R.drawable.perasaandatar, "Meh"));
        moodList.add(new Mood(R.drawable.perasaansedih, "Sedih:("));
        moodList.add(new Mood(R.drawable.perasaanmarah, "Marah!"));
    }
}