package za.mobile.remoodver2;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JurnalDao {
    @Insert(onConflict = REPLACE)
    void insertJournal(Jurnal jurnal);

    @Query("SELECT * FROM jurnal")
    List<Jurnal> getAllJournals();

    @Update
    void updateJournal(Jurnal jurnal);

    @Delete
    void deleteJournal(Jurnal jurnal);

    @Query("DELETE FROM jurnal")
    void deleteAllJournals();
}
