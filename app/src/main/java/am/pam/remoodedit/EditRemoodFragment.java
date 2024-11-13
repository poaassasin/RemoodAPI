package am.pam.remoodedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

public class EditRemoodFragment extends Fragment {
    private EditText titleEditText, descEditText;
    private MaterialAutoCompleteTextView moodAutoCompleteTextView;
    private ImageView moodImageView;

    public EditRemoodFragment() {
        // Diperlukan constructor kosong
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout fragment
        View view = inflater.inflate(R.layout.fragment_edit_remood, container, false);

        // Inisialisasi elemen UI
        titleEditText = view.findViewById(R.id.textView9);
        descEditText = view.findViewById(R.id.textView10);
        TextInputLayout textInputLayout = view.findViewById(R.id.inputLayout);
        moodAutoCompleteTextView = view.findViewById(R.id.inputTV);
        Button simpanButton = view.findViewById(R.id.button);
        moodImageView = view.findViewById(R.id.imageView2);

        // Terima data yang dikirim dari MainActivity
        Bundle bundle = getArguments();
        if (bundle != null) {
            titleEditText.setText(bundle.getString("currentTitle"));
            descEditText.setText(bundle.getString("currentDesc"));
            moodAutoCompleteTextView.setText(bundle.getString("currentMood"), false);
        }

        // Set adapter untuk dropdown mood
        String[] options = getResources().getStringArray(R.array.options_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, options);
        moodAutoCompleteTextView.setAdapter(adapter);

        // Saat klik tombol "Simpan"
        simpanButton.setOnClickListener(v -> {
            String newMood = moodAutoCompleteTextView.getText().toString();
            String newTitle = titleEditText.getText().toString();
            String newDesc = descEditText.getText().toString();

            if (newMood.isEmpty() || newTitle.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(getActivity(), "Pastikan semua field terisi", Toast.LENGTH_SHORT).show();
                return;
            }

            // Kirim data ke MainActivity menggunakan FragmentResult API
            Bundle result = new Bundle();
            result.putString("newMood", newMood);
            result.putString("newTitle", newTitle);
            result.putString("newDesc", newDesc);
            getParentFragmentManager().setFragmentResult("editResult", result);

            // Kembali ke MainActivity (pop fragment dari backstack)
            getParentFragmentManager().popBackStack();
        });

        // Update ImageView berdasarkan mood yang dipilih
        moodAutoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedOption = parent.getItemAtPosition(position).toString();
            textInputLayout.setHint(selectedOption); // Ganti hint dengan mood yang dipilih

            // Update gambar sesuai mood
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
                    moodImageView.setImageResource(R.drawable.happyy); // Default jika mood tak sesuai
                    break;
            }
        });

        // Klik tombol "Kembali" untuk menutup fragment
        TextView kembaliTextView = view.findViewById(R.id.textView7);
        kembaliTextView.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        return view;
    }
}
