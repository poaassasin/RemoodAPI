package com.example.remood;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.remood.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Menampilkan Fragment default saat Activity pertama kali dibuka
        loadFragment(new HomeFragment());

        Button replaceButton = findViewById(R.id.bottomNavigationView);
        replaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengganti fragment dengan ExampleFragment saat tombol ditekan
                replaceFragment(new HomeFragment());
            }
        });
    }

    // Metode untuk memuat Fragment pertama kali saat Activity dibuka
    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Menambahkan Fragment ke dalam layout container di activity_home.xml
        fragmentTransaction.add(R.id.fragmentHome, fragment);
        fragmentTransaction.commit();
    }

    // Metode untuk mengganti Fragment yang sedang ditampilkan
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Mengganti Fragment yang ada di fragment_container dengan Fragment baru
        fragmentTransaction.replace(R.id.fragmentHome, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}