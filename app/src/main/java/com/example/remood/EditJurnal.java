package com.example.remood;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.remood.model.JurnalModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class EditJurnal extends AppCompatActivity {
    private EditText titleEditText, descEditText, dateEditText;
    private MaterialAutoCompleteTextView moodAutoCompleteTextView;
    private ImageView mood;

    private JurnalItemAdapter adapter;
    final Calendar myCalendar = Calendar.getInstance();
    private TextView kembaliTextView;
    private ArrayList<JurnalModel> listData;
    private JurnalDatabase jurnalDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jurnal);
        titleEditText = (EditText) findViewById(R.id.textView9);
        descEditText = (EditText) findViewById(R.id.textView10);
        dateEditText = (EditText) findViewById(R.id.etDate);
        moodAutoCompleteTextView = (MaterialAutoCompleteTextView) findViewById(R.id.inputTV);
        mood = (ImageView) findViewById(R.id.imageView2);
        Button simpanButton = (Button) findViewById(R.id.button);
        kembaliTextView = (TextView) findViewById(R.id.tvKembali);

        //Setup dropdown
        String[] options = getResources().getStringArray(R.array.options_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, options);
        moodAutoCompleteTextView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
                "JurnalDB").allowMainThreadQueries().build();
        JurnalDAO jurnalDAO = jurnalDB.getJurnalDAO();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            titleEditText.setText(bundle.getString("currentTitle"));
            descEditText.setText(bundle.getString("currentDesc"));
            moodAutoCompleteTextView.setText(bundle.getString("currentMood"), false);
            dateEditText.setText(bundle.getString("currentDate"));
            // Set gambar awal berdasarkan mood yang diterima
            updateMoodImage(bundle.getString("currentMood"));
        }

        moodAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedMood = parent.getItemAtPosition(position).toString();
            updateMoodImage(selectedMood);
        });

        dateEditText.setOnClickListener(v -> {
            new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, month);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateLabel();
                    },
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        simpanButton.setOnClickListener(v -> {
            String newMood = moodAutoCompleteTextView.getText().toString();
            String newTitle = titleEditText.getText().toString();
            String newDesc = descEditText.getText().toString();
            String selectedDate = dateEditText.getText().toString();
            String waktuTersimpan = bundle.getString("currentTime");

            if (newMood.isEmpty() || newTitle.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(EditJurnal.this, "Pastikan semua field terisi", Toast.LENGTH_SHORT).show();
                return;
            }
//            JurnalModel remood = new JurnalModel(newTitle, newDesc, newMood, selectedDate, waktuTersimpan);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JurnalModel jurnal = jurnalDB.getJurnalDAO()
                            .findByWaktu(bundle.getString("currentTime"));
                    jurnal.setTanggal(selectedDate);
                    jurnal.setCurhatan(newTitle);
                    jurnal.setDetailCurhatan(newDesc);
                    jurnal.setLinkGambarEmosi(newMood);
                    jurnalDAO.updateJurnal(jurnal);
                }
            }).start();
            adapter.notifyDataSetChanged();
            finish();
        });
        kembaliTextView.setOnClickListener(v -> finish());
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateMoodImage(String moodImageView) {
        switch (moodImageView) {
            case "Happy!":
                mood.setImageResource(R.drawable.perasaansenang);
                break;
            case "Oke!":
                mood.setImageResource(R.drawable.perasaanoke);
                break;
            case "Meh":
                mood.setImageResource(R.drawable.perasaandatar);
                break;
            case "Sedih:(":
                mood.setImageResource(R.drawable.perasaansedih);
                break;
            case "Marah!":
                mood.setImageResource(R.drawable.perasaanmarah);
                break;
            default:
                mood.setImageResource(R.drawable.image_perasaan); // Default image
        }
    }
}