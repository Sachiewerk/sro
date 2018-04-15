package edu.odu.cs441.sro.dao.metadata;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.odu.cs441.sro.entity.metadata.Duedate;

public interface DuedateDao {
    @Query("SELECT * FROM `due date` ORDER BY `Due date` ASC")
    LiveData<List<Duedate>> findAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Duedate category);

    @Update
    void update(Duedate category);

    @Delete
    void delete(Duedate ... categories);
}
