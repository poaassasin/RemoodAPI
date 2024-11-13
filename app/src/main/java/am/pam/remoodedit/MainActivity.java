package am.pam.remoodedit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private TextView moodTextView, titleTextView, descTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moodTextView = findViewById(R.id.textView4);
        titleTextView = findViewById(R.id.textView);
        descTextView = findViewById(R.id.textView2);

        TextView ubahTextView = findViewById(R.id.textView5); // Tombol "Ubah"
        ubahTextView.setOnClickListener(v -> {
            // Setel FrameLayout menjadi terlihat
            findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
            // Kirim data awal ke fragment
            Bundle bundle = new Bundle();
            bundle.putString("currentMood", moodTextView.getText().toString());
            bundle.putString("currentTitle", titleTextView.getText().toString());
            bundle.putString("currentDesc", descTextView.getText().toString());

            EditRemoodFragment fragment = new EditRemoodFragment();
            fragment.setArguments(bundle);

            // Tampilkan EditRemoodFragment di FrameLayout
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null); // Tambahkan ke back stack
            transaction.commit();
        });

        // Listener untuk menerima data dari EditRemoodFragment
        getSupportFragmentManager().setFragmentResultListener("editResult", this, (requestKey, result) -> {
            String newMood = result.getString("newMood");
            String newTitle = result.getString("newTitle");
            String newDesc = result.getString("newDesc");

            moodTextView.setText(newMood);
            titleTextView.setText(newTitle);
            descTextView.setText(newDesc);

            // Update gambar berdasarkan mood baru
            ImageView moodImageView = findViewById(R.id.imageView);
            switch (newMood) {
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
                    moodImageView.setImageResource(R.drawable.happyy);
            }
            // Sembunyikan FrameLayout setelah Fragment selesai
            findViewById(R.id.fragment_container).setVisibility(View.GONE);
        });
    }
}