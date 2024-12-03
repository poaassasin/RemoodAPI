package com.example.remood;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.remood.model.JurnalModel;

import java.util.List;

@Dao
public interface JurnalDAO {
    @Insert
    public void addJurnal(JurnalModel jurnal);

    @Delete
    public void deleteJurnal(JurnalModel jurnal);

    @Update
    public void updateJurnal(JurnalModel jurnal);

    @Query("select * from jurnal where waktu like :waktu limit 1")
    public JurnalModel findByWaktu(String waktu);

    @Query("select * from jurnal")
    public List<JurnalModel> getAllJurnal();

    @Query("select * from jurnal where jurnal_id ==:jurnal_id")
    public JurnalModel getJurnal(long jurnal_id);

    @Query("select * from jurnal where jurnal_id = (SELECT jurnal_id from jurnal order by jurnal_id DESC limit 1)")
    public  JurnalModel getLatestJurnal();

}
