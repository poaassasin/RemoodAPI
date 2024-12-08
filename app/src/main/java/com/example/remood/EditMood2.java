package com.example.remood;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditMood2 extends AppCompatActivity {

    private EditText moodEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mood2);
        ImageView moodImageView = findViewById(R.id.ivMood);
        moodEditText = findViewById(R.id.etMoodEdit);
        saveButton = findViewById(R.id.btSimpan2);

        // Get data from intent
        Intent intent = getIntent();
        String moodName = intent.getStringExtra("moodName");
        int moodImage = intent.getIntExtra("moodImage", R.drawable.perasaansenang);

        moodEditText.setText(moodName);
        moodImageView.setImageResource(moodImage);

        // Save button action
        saveButton.setOnClickListener(v -> {
            String updatedMoodName = moodEditText.getText().toString();
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedMoodName", updatedMoodName);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}