package edu.odu.cs441.sro.dao.metadata;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import edu.odu.cs441.sro.entity.metadata.Method;

@Dao
public interface MethodDao {
    @Query("SELECT * FROM method ORDER BY method ASC")
    LiveData<List<Method>> findAll();

    @Insert
    void insert(Method method);

    @Update
    void update(Method method);

    @Delete
    void delete(Method ... methods);
}
