package edu.odu.cs441.sro.dao.record;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.odu.cs441.sro.entity.record.Receipt;

/**
 * Created by michael on 3/12/18.
 */
@Dao
public interface ReceiptDao {

    @Query("SELECT * FROM receipt ORDER BY created_date DESC")
    LiveData<List<Receipt>> findAll();

    @Query("SELECT * FROM receipt WHERE receipt_key = :receiptKey")
    Receipt findByKey(String receiptKey);

    @RawQuery(observedEntities = Receipt.class)
    LiveData<List<Receipt>> findByQuery(SupportSQLiteQuery query);

    @Insert
    void insert(Receipt receipt);

    @Update
    void update(Receipt receipt);

    @Delete
    void delete(Receipt ... recepts);
}
