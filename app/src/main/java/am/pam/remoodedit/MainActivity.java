package am.pam.remoodedit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView moodTextView, titleTextView, descTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moodTextView = findViewById(R.id.textView4); // Mood TextView (e.g., "Happy")
        titleTextView = findViewById(R.id.textView); // Title TextView (e.g., "Makan Bareng")
        descTextView = findViewById(R.id.textView2); // Description TextView

        // Ambil referensi dari TextView dengan id textView5
        TextView ubahTextView = findViewById(R.id.textView5);

        // Set onClickListener untuk berpindah ke EditRemood activity
        ubahTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Intent untuk berpindah ke EditRemood activity
                Intent intent = new Intent(MainActivity.this, EditRemood.class);

                // Kirim data yang ada ke EditRemood
                intent.putExtra("currentMood", moodTextView.getText().toString());
                intent.putExtra("currentTitle", titleTextView.getText().toString());
                intent.putExtra("currentDesc", descTextView.getText().toString());

                startActivityForResult(intent,1); // Memulai activity baru
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Terima data yang telah diedit dari EditRemood
            String newMood = data.getStringExtra("newMood");
            String newTitle = data.getStringExtra("newTitle");
            String newDesc = data.getStringExtra("newDesc");

            // Update tampilan di MainActivity
            moodTextView.setText(newMood);
            titleTextView.setText(newTitle);
            descTextView.setText(newDesc);

            // Update gambar sesuai dengan mood yang diterima
            ImageView moodImageView = findViewById(R.id.imageView); // pastikan id dari imageView di activity_main benar
            switch (newMood) {
                case "Happy":
                    moodImageView.setImageResource(R.drawable.happy);
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
                    moodImageView.setImageResource(R.drawable.happy);
            }
        }
    }
}