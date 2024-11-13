package com.example.remood.model;

public class JurnalModel {
    String tanggal;
    String waktu;
    String curhatan;
    String link_gambar_emosi;
    String detail_curhatan;

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
