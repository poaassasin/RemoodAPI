package com.example.remood;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.remood.model.JurnalModel;

import java.sql.SQLException;
import java.util.List;

@Dao
public interface JurnalDAO {
    @Insert
    public void addJurnal(JurnalModel jurnal);

    @Delete
    public void deleteJurnal(JurnalModel jurnal);

    @Update
    public void updateJurnal(JurnalModel jurnal);

    @Query("select * from jurnal where jurnal_id LIKE :jurnal_id LIMIT 1")
    public JurnalModel findById(long jurnal_id);

    @Query("select * from jurnal where judul like :judul limit 1")
    public JurnalModel findByName(String judul);

    @Query("select * from jurnal where waktu like :waktu limit 1")
    public JurnalModel findByWaktu(String waktu);

    @Query("SELECT EXISTS(SELECT * FROM jurnal WHERE jurnal_id = :jurnal_id)")
    Boolean is_exist(long jurnal_id);

    @Query("DELETE FROM jurnal WHERE jurnal_id = :jurnal_id")
    public void deleteById(long jurnal_id);

    @Query("select * from jurnal")
    public List<JurnalModel> getAllJurnal();

    @Query("select * from jurnal where jurnal_id ==:jurnal_id")
    public JurnalModel getJurnal(long jurnal_id);

    @Query("select * from jurnal where jurnal_id = (SELECT jurnal_id from jurnal order by jurnal_id DESC limit 1)")
    public  JurnalModel getLatestJurnal();

}
