package edu.odu.cs441.sro.dao.metadata;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import edu.odu.cs441.sro.entity.metadata.Location;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM location ORDER BY location ASC")
    LiveData<List<Location>> findAll();

    @Insert
    void insert(Location location);

    @Update
    void update(Location location);

    @Delete
    void delete(Location ... locations);

}
