package za.mobile.remoodver2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvRiwayat;
    private JurnalAdapter jurnalAdapter;
    private List<Jurnal> data;
    private Gson gson;
    private ExecutorService executorService;
    private AppDatabase db;
    private JurnalDao jurnalDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi variabel
        gson = new Gson();
        data = new ArrayList<>();
        rvRiwayat = findViewById(R.id.rvRiwayat);
        executorService = Executors.newSingleThreadExecutor();

        // Setup RecyclerView
        jurnalAdapter = new JurnalAdapter(this, data);
        rvRiwayat.setAdapter(jurnalAdapter);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(this));

        // Setup Room Database
        db = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "remood-db")
                .fallbackToDestructiveMigration()
                .build();
        jurnalDao = db.jurnalDao();

        // Jalankan operasi
        executorService.execute(() -> {
            fetchDataFromApi(); // Ambil data dari API
            loadJournalsFromDatabase(); // Muat data lokal dari database
        });

    }

    // Method untuk mengambil data dari API
    private void fetchDataFromApi() {
        String url = "https://api-pam.portoku.my.id/RemoodVer2.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Parsing JSON menggunakan Gson
                        Type listType = new TypeToken<List<Jurnal>>() {}.getType();
                        List<Jurnal> fetchedData = gson.fromJson(response, listType);

                        // Simpan data ke database secara aman
                        executorService.execute(() -> {
                            for (Jurnal jurnal : fetchedData) {
                                try {
                                    jurnalDao.insertJournal(jurnal); // Masukkan data ke Room
                                } catch (Exception e) {
                                    Log.e("MainActivity", "Data duplicate, skipping ID: " + jurnal.getId());
                                }
                            }

                            // Setelah selesai, muat ulang data
                            loadJournalsFromDatabase();
                        });

                    } catch (Exception e) {
                        Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
                        runOnUiThread(() ->
                                Toast.makeText(this, "Error parsing data", Toast.LENGTH_SHORT).show());
                    }
                },
                error -> {
                    Log.e("MainActivity", "Error fetching data: " + error.getMessage());
                    runOnUiThread(() ->
                            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show());
                });

        queue.add(stringRequest);
    }

    // Method untuk memuat semua jurnal dari database
    private void loadJournalsFromDatabase() {
        executorService.execute(() -> {
            List<Jurnal> journals = jurnalDao.getAllJournals();
            if (journals != null) {
                runOnUiThread(() -> {
                    data.clear();
                    data.addAll(journals); // Perbarui data
                    jurnalAdapter.notifyDataSetChanged(); // Perbarui RecyclerView
                });
            } else {
                Log.w("MainActivity", "No journals found in database");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }
}
