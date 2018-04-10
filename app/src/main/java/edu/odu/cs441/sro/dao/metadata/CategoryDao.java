package edu.odu.cs441.sro.dao.metadata;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import edu.odu.cs441.sro.entity.metadata.Category;

/**
 * Created by michael on 3/12/18.
 *
 */
@Dao
public interface CategoryDao {

    @Query("SELECT * FROM category ORDER BY category ASC")
    LiveData<List<Category>> findAll();

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category ... categories);
}
