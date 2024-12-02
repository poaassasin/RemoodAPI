package com.example.remood.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Jurnal")
public class JurnalModel {
    @ColumnInfo(name="jurnal_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name="tanggal")
    public String tanggal;

    @ColumnInfo(name="waktu")
    public String waktu;

    @ColumnInfo(name="judul")
    public String curhatan;

    @ColumnInfo(name="emosi")
    public String link_gambar_emosi;

    @ColumnInfo(name="deskripsi")
    public String detail_curhatan;

    @Ignore
    public JurnalModel() {

    }

    public JurnalModel(String curhatan, String detail_curhatan, String link_gambar_emosi,
                       String tanggal, String waktu) {
        this.curhatan = curhatan;
        this.detail_curhatan = detail_curhatan;
        this.link_gambar_emosi = link_gambar_emosi;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getDetailCurhatan() {
        return detail_curhatan;
    }

    public void setDetailCurhatan(String detailCurhatan) {
        this.detail_curhatan = detailCurhatan;
    }

    public String getLinkGambarEmosi() {
        return link_gambar_emosi;
    }

    public void setLinkGambarEmosi(String linkGambarEmosi) {
        this.link_gambar_emosi = linkGambarEmosi;
    }

    public String getCurhatan() {
        return curhatan;
    }

    public void setCurhatan(String curhatan) {
        this.curhatan = curhatan;
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
