package com.example.remood;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.example.remood.databinding.ActivityMainBinding;
import com.example.remood.model.JurnalModel;
import com.google.android.material.badge.BadgeDrawable;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private JurnalDatabase db;
    List<JurnalModel> jurnalSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (savedInstanceState == null){
            replaceFragment(new HomeFragment());
        }else{
            replaceFragment(new JurnalFragment());
        }
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
        db = Room.databaseBuilder(MainActivity.this, JurnalDatabase.class,
                "JurnalDB").allowMainThreadQueries().build();
        jurnalSize = db.getJurnalDAO().getAllJurnal();
        BadgeDrawable badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.jurnal);
        try {
            if (jurnalSize.isEmpty()) {
                badgeDrawable.setNumber(0);
                badgeDrawable.setVisible(true);
            } else {
                badgeDrawable.setNumber(jurnalSize.size());
                badgeDrawable.setVisible(true);
            }
        }catch (Exception e) {
            Log.d("MainActivity", "error : "+e.getMessage());
        }

        binding.floatingActionButton.setOnClickListener(v -> showBottomDialog()); //lambda
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
        ImageView addButton = dialog.findViewById(R.id.addButton);

        happyLayout.setOnClickListener(v -> { //lambda
            dialog.dismiss();
            Toast toast = new Toast(getApplicationContext());
            Toast.makeText(MainActivity.this, "Hari Ini Anda Senang!", Toast.LENGTH_SHORT).show();
            String perasaan = "Happy!";
            Intent i = new Intent(MainActivity.this, Buatjurnal.class);
            i.putExtra("resId",R.drawable.perasaansenang);
            i.putExtra("resId2", perasaan);
            startActivity(i);
        });

        goodLayout.setOnClickListener(v -> { //lambda
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Hari Ini Anda Oke!", Toast.LENGTH_SHORT).show();
            String perasaan = "Oke!";
            Intent i = new Intent(MainActivity.this, Buatjurnal.class);
            i.putExtra("resId",R.drawable.perasaanoke);
            i.putExtra("resId2", perasaan);
            startActivity(i);
        });

        datarLayout.setOnClickListener(v -> { //lambda
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Hari Ini Anda Biasa!", Toast.LENGTH_SHORT).show();
            String perasaan = "Meh";
            Intent i = new Intent(MainActivity.this, Buatjurnal.class);
            i.putExtra("resId",R.drawable.perasaandatar);
            i.putExtra("resId2", perasaan);
            startActivity(i);
        });

        sedihLayout.setOnClickListener(v -> { //lambda
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Hari Ini Anda Sedih:( Semangat Ya!", Toast.LENGTH_SHORT).show();
            String perasaan = "Sedih:(";
            Intent i = new Intent(MainActivity.this, Buatjurnal.class);
            i.putExtra("resId",R.drawable.perasaansedih);
            i.putExtra("resId2", perasaan);
            startActivity(i);
        });

        marahLayout.setOnClickListener(v -> { //lambda
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "Hari Ini Anda Marah! Santai ya!", Toast.LENGTH_SHORT).show();
            String perasaan = "Marah!";
            Intent i = new Intent(MainActivity.this, Buatjurnal.class);
            i.putExtra("resId",R.drawable.perasaanmarah);
            i.putExtra("resId2", perasaan);
            startActivity(i);
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss()); //lambda
        addButton.setOnClickListener(v -> {
            dialog.dismiss();
            Intent i = new Intent(MainActivity.this, EditMood.class);
            startActivity(i);
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
}