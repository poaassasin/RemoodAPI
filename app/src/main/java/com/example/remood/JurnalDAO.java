package com.example.remood;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.remood.model.JurnalModel;

import java.sql.SQLException;
import java.util.List;

@Dao
public interface JurnalDAO {
    @Insert
    public void addJurnal(JurnalModel jurnal);

    @Query("select * from jurnal")
    public List<JurnalModel> getAllJurnal();

    @Query("select * from jurnal where jurnal_id ==:jurnal_id")
    public JurnalModel getJurnal(int jurnal_id);

}
