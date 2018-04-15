package edu.odu.cs441.sro.dao.record;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.odu.cs441.sro.entity.record.Subscription;

public interface SubscriptionDao {

    @Query("SELECT * FROM subscription ORDER BY created_date DESC")
    LiveData<List<Subscription>> findAll();

    @Insert
    void insert(Subscription subscription);

    @Update
    void update(Subscription subscription);

    @Delete
    void delete(Subscription... subscription);


}