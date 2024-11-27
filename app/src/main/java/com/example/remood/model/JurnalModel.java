package com.example.remood.model;


import androidx.room.Ignore;


public class JurnalModel {
    private long id;

    public String tanggal;

    public String waktu;

    public String curhatan;

    public String link_gambar_emosi;

    public String detail_curhatan;

    @Ignore
    public JurnalModel() {

    }

    public JurnalModel(String curhatan, String detail_curhatan, String link_gambar_emosi,
                       String tanggal, String waktu) {
        this.id = 0;
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

    public void setDetailCurhatan(String detail_curhatan) {
        this.detail_curhatan = detail_curhatan;
    }

    public String getLinkGambarEmosi() {
        return link_gambar_emosi;
    }

    public void setLinkGambarEmosi(String link_gambar_emosi) {
        this.link_gambar_emosi = link_gambar_emosi;
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
