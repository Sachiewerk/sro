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

    @Query("SELECT * FROM receipt ORDER BY created_date ASC")
    List<Receipt> findByASC();

    @Query("SELECT * FROM receipt WHERE receipt_key = :receiptKey")
    Receipt findByKey(String receiptKey);

    @Query("SELECT * FROM receipt WHERE created_date BETWEEN :startDate AND :endDate")
    List<Receipt> findByDate(Long startDate, Long endDate);

    @Query("SELECT * FROM receipt WHERE category = :category AND created_date BETWEEN :startDate AND :endDate")
    List<Receipt> findByDateAndCategory(Long startDate, Long endDate, String category);

    @Query("SELECT * FROM receipt WHERE method = :method AND created_date BETWEEN :startDate AND :endDate")
    List<Receipt> findByDateAndMethod(Long startDate, Long endDate, String method);

    @Query("SELECT * FROM receipt WHERE location = :location AND created_date BETWEEN :startDate AND :endDate")
    List<Receipt> findByDateAndLocation(Long startDate, Long endDate, String location);

    @RawQuery(observedEntities = Receipt.class)
    LiveData<List<Receipt>> findByQuery(SupportSQLiteQuery query);

    @Insert
    void insert(Receipt receipt);

    @Update
    void update(Receipt receipt);

    @Delete
    void delete(Receipt ... recepts);
}
