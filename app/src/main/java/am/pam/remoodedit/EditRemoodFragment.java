package am.pam.remoodedit;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditRemoodFragment extends Fragment {

    private EditText titleEditText, descEditText, ed1;
    private MaterialAutoCompleteTextView moodAutoCompleteTextView;
    private ImageView mood;
    private DatabaseReference databaseReference;
    FirebaseDatabase fD;
    final Calendar myCalender = Calendar.getInstance();
    private TextView kembali;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_remood, container, false);

        // Inisialisasi FirebaseDatabase dan DatabaseReference
        fD = FirebaseDatabase.getInstance(
                "https://remood-34cfa-default-rtdb.asia-southeast1.firebasedatabase.app/"
        );
        databaseReference = fD.getReference("moods");

        // Inisialisasi UI elements
        titleEditText = view.findViewById(R.id.textView9);
        descEditText = view.findViewById(R.id.textView10);
        kembali = view.findViewById(R.id.textView7);
        moodAutoCompleteTextView = view.findViewById(R.id.inputTV);
        mood = view.findViewById(R.id.imageView2);

        Button simpanButton = view.findViewById(R.id.button);
        ed1 = view.findViewById(R.id.etDate);


        // Menerima data
        Bundle bundle = getArguments();
        if (bundle != null) {
            titleEditText.setText(bundle.getString("currentTitle"));
            descEditText.setText(bundle.getString("currentDesc"));
            moodAutoCompleteTextView.setText(bundle.getString("currentMood"), false);
            ed1.setText(bundle.getString("currentDate"));
            // Set gambar awal berdasarkan mood yang diterima
            updateMoodImage(bundle.getString("currentMood"));
        }

        // Dropdown setup
        String[] options = getResources().getStringArray(R.array.options_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, options);
        moodAutoCompleteTextView.setAdapter(adapter);

        // Listener untuk pilihan dropdown
        moodAutoCompleteTextView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedMood = parent.getItemAtPosition(position).toString();

            // Perbarui gambar sesuai pilihan
            updateMoodImage(selectedMood);
        });

        // Listener untuk memilih tanggal
        ed1.setOnClickListener(v -> {
            new DatePickerDialog(
                    requireContext(), // Gunakan requireContext() untuk konteks fragment
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            myCalender.set(Calendar.YEAR, year);
                            myCalender.set(Calendar.MONTH, month);
                            myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            // Format tanggal yang dipilih
                            String myFormat = "dd MMMM yyyy";
                            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                            String selectedDate = (dateFormat.format(myCalender.getTime()));

                            // Update tanggal yang dipilih di EditText
                            ed1.setText(selectedDate);

//                            // Kirimkan tanggal yang dipilih ke MainActivity
//                            Bundle result = new Bundle();
//                            result.putString("selectedDate", selectedDate);
//                            getParentFragmentManager().setFragmentResult("dateSelected", result);

                        }
                    },
                    myCalender.get(Calendar.YEAR),
                    myCalender.get(Calendar.MONTH),
                    myCalender.get(Calendar.DAY_OF_MONTH)
            ).show(); // Tambahkan .show() untuk menampilkan dialog
        });

        kembali.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        // Save button logic
        simpanButton.setOnClickListener(v -> {
            String newMood = moodAutoCompleteTextView.getText().toString();
            String newTitle = titleEditText.getText().toString();
            String newDesc = descEditText.getText().toString();
            String selectedDate = ed1.getText().toString();

            if (newMood.isEmpty() || newTitle.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(getActivity(), "Pastikan semua field terisi", Toast.LENGTH_SHORT).show();
                return;
            }
            // Update data ke Firebase
            Remood updatedMood = new Remood(newMood, newTitle, newDesc, selectedDate);
            updatedMood.id = 1; // ID tetap sama
            databaseReference.child(String.valueOf(updatedMood.id))
                    .setValue(updatedMood)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show();
                            getParentFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getActivity(), "Gagal memperbarui data", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        return view;
    }



    private void updateMoodImage(String moodImageView) {
        switch (moodImageView) {
            case "Happy":
                mood.setImageResource(R.drawable.happyy);
                break;
            case "Good":
                mood.setImageResource(R.drawable.good);
                break;
            case "So-So":
                mood.setImageResource(R.drawable.meh);
                break;
            case "Sad":
                mood.setImageResource(R.drawable.sadd);
                break;
            case "Bad":
                mood.setImageResource(R.drawable.angry);
                break;
            default:
                mood.setImageResource(R.drawable.happyy); // Default image
        }
    }
}
