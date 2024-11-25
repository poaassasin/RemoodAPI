package com.example.remood;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Jurnal")
public class JurnalEntity {
    @ColumnInfo(name="jurnal_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "judul")
    private String judul;

    @ColumnInfo(name = "deskripsi")
    private String deskripsi;

    @ColumnInfo(name = "emoji")
    private String imageUri;

    @ColumnInfo(name = "waktu")
    private String waktu;

    @ColumnInfo(name = "tanggal")
    private String tanggal;

    @Ignore
    public JurnalEntity() {

    }

    public JurnalEntity(String judul, String deskripsi){
        this.id = 0;
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul() {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi() {
        this.deskripsi = deskripsi;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


}
