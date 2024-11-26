package za.mobile.remoodver2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Menandai class ini sebagai entitas database dengan tabel bernama "jurnal"
@Entity(tableName = "jurnal")
public class Jurnal {

    @PrimaryKey(autoGenerate = true)
    private int id; // ID akan diatur otomatis oleh Room

    @SerializedName("title") // Untuk serialisasi/deserialisasi JSON
    private String judul;

    @SerializedName("desc")
    private String isiText;

    @SerializedName("date")
    private String waktu;

    @SerializedName("mood")
    private String tvMoodLabel;

    private int ivMood; // Menyimpan ID atau referensi gambar mood

    // Constructor
    public Jurnal(String judul, String isiText, String waktu, int ivMood, String tvMoodLabel) {
        this.judul = judul;
        this.isiText = isiText;
        this.waktu = waktu;
        this.ivMood = ivMood;
        this.tvMoodLabel = tvMoodLabel;
    }

    // Getter dan Setter untuk semua atribut
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsiText() {
        return isiText;
    }

    public void setIsiText(String isiText) {
        this.isiText = isiText;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTvMoodLabel() {
        return tvMoodLabel;
    }

    public void setTvMoodLabel(String tvMoodLabel) {
        this.tvMoodLabel = tvMoodLabel;
    }

    public int getIvMood() {
        return ivMood;
    }

    public void setIvMood(int ivMood) {
        this.ivMood = ivMood;
    }


    // Mengembalikan waktu dalam format yang lebih terstruktur
    public String getFormattedWaktu() {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMMM dd, yyyy, HH:mm:ss z", Locale.getDefault());
        return formatter.format(new Date(Long.parseLong(waktu))); // Mengonversi waktu jika disimpan dalam milidetik
    }
}
