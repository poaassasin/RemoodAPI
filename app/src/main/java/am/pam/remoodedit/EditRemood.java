package am.pam.remoodedit;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

public class EditRemood extends AppCompatActivity {
    String[] item = {"Happy", "Sad", "So-So", "Good"};
    private EditText titleEditText, descEditText;
    private MaterialAutoCompleteTextView moodAutoCompleteTextView;
    private ImageView moodImageView;  // ImageView untuk mood

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_remood);

        titleEditText = findViewById(R.id.textView9); // Menggunakan EditText di XML
        descEditText = findViewById(R.id.textView10); // Menggunakan EditText di XML

        TextInputLayout textInputLayout = findViewById(R.id.inputLayout);
        moodAutoCompleteTextView = findViewById(R.id.inputTV);
        Button simpanButton = findViewById(R.id.button);
        moodImageView = findViewById(R.id.imageView2);  // Ambil referensi untuk ImageView

        // Terima data dari MainActivity
        Intent intent = getIntent();
        String currentMood = intent.getStringExtra("currentMood");
        String currentTitle = intent.getStringExtra("currentTitle");
        String currentDesc = intent.getStringExtra("currentDesc");

        // Tampilkan data awal pada form edit
        titleEditText.setText(currentTitle);
        descEditText.setText(currentDesc);
        moodAutoCompleteTextView.setText(currentMood, false);

        // Siapkan dropdown adapter
        String[] options = getResources().getStringArray(R.array.options_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, options);
        moodAutoCompleteTextView.setAdapter(adapter);


        // Saat klik tombol Simpan
        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMood = moodAutoCompleteTextView.getText().toString();
                String newTitle = titleEditText.getText().toString();
                String newDesc = descEditText.getText().toString();

                if (newMood.isEmpty() || newTitle.isEmpty() || newDesc.isEmpty()) {
                    Toast.makeText(EditRemood.this, "Pastikan semua field terisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kirim hasil edit kembali ke MainActivity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("newMood", newMood);
                resultIntent.putExtra("newTitle", newTitle);
                resultIntent.putExtra("newDesc", newDesc);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        // Set OnItemClickListener untuk menangani ketika sebuah opsi dipilih dari dropdown
        moodAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedOption = parent.getItemAtPosition(position).toString();
            // Mengganti hint ketika opsi dipilih
            textInputLayout.setHint(selectedOption);
            // Menghapus pesan error jika ada
            textInputLayout.setError(null);
        });

        //Saat klik tombol Kembali agar kembali ke activity_main
        // Ambil referensi dari TextView dengan id textView7 (yang berfungsi sebagai tombol "Kembali")
        TextView kembaliTextView = findViewById(R.id.textView7);

        // Set onClickListener untuk tombol "Kembali"
        kembaliTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengakhiri activity saat ini dan kembali ke activity sebelumnya (MainActivity)
                finish();
            }
        });

        // Set OnItemClickListener untuk menangani ketika sebuah opsi dipilih dari dropdown
        moodAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedOption = parent.getItemAtPosition(position).toString();

            // Mengganti hint ketika opsi dipilih
            textInputLayout.setHint(selectedOption);

            // Menghapus pesan error jika ada
            textInputLayout.setError(null);

            // Update imageView berdasarkan opsi yang dipilih
            switch (selectedOption) {
                case "Happy":
                    moodImageView.setImageResource(R.drawable.happyy);
                    break;
                case "Sad":
                    moodImageView.setImageResource(R.drawable.sadd);
                    break;
                case "So-So":
                    moodImageView.setImageResource(R.drawable.meh);
                    break;
                case "Good":
                    moodImageView.setImageResource(R.drawable.good);
                    break;
                case "Bad":
                    moodImageView.setImageResource(R.drawable.angry);
                    break;
                default:
                    moodImageView.setImageResource(R.drawable.happy); // Jika tidak ada pilihan yang cocok
                    break;
            }
        });
    }
}