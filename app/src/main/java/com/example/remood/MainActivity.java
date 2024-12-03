package com.example.remood;

import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.remood.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.edulife) {
                replaceFragment(new EdulifeFragment());
            } else if (itemId == R.id.jurnal) {
                replaceFragment(new JurnalFragment());
            } else if (itemId == R.id.forum) {
                replaceFragment(new ForumFragment());
            }
            return true;
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomDialog();
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.detach(fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.commit();
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        LinearLayout happyLayout = dialog.findViewById(R.id.layoutHappy);
        LinearLayout goodLayout = dialog.findViewById(R.id.layoutGood);
        LinearLayout datarLayout = dialog.findViewById(R.id.layoutDatar);
        LinearLayout sedihLayout = dialog.findViewById(R.id.layoutSedih);
        LinearLayout marahLayout = dialog.findViewById(R.id.layoutMarah);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        happyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast toast = new Toast(getApplicationContext());
                Toast.makeText(MainActivity.this, "Hari Ini Anda Senang!", Toast.LENGTH_SHORT).show();
                String perasaan = "Happy!";
                Intent i = new Intent(MainActivity.this, Buatjurnal.class);
                i.putExtra("resId",R.drawable.perasaansenang);
                i.putExtra("resId2", perasaan);
                startActivity(i);
            }
        });

        goodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Hari Ini Anda Oke!", Toast.LENGTH_SHORT).show();
                String perasaan = "Oke!";
                Intent i = new Intent(MainActivity.this, Buatjurnal.class);
                i.putExtra("resId",R.drawable.perasaanoke);
                i.putExtra("resId2", perasaan);
                startActivity(i);
            }
        });

        datarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Hari Ini Anda Biasa!", Toast.LENGTH_SHORT).show();
                String perasaan = "Meh";
                Intent i = new Intent(MainActivity.this, Buatjurnal.class);
                i.putExtra("resId",R.drawable.perasaandatar);
                i.putExtra("resId2", perasaan);
                startActivity(i);
            }
        });

        sedihLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Hari Ini Anda Sedih:( Semangat Ya!", Toast.LENGTH_SHORT).show();
                String perasaan = "Sedih:(";
                Intent i = new Intent(MainActivity.this, Buatjurnal.class);
                i.putExtra("resId",R.drawable.perasaansedih);
                i.putExtra("resId2", perasaan);
                startActivity(i);
            }
        });

        marahLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Hari Ini Anda Marah! Santai ya!", Toast.LENGTH_SHORT).show();
                String perasaan = "Marah!";
                Intent i = new Intent(MainActivity.this, Buatjurnal.class);
                i.putExtra("resId",R.drawable.perasaanmarah);
                i.putExtra("resId2", perasaan);
                startActivity(i);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}