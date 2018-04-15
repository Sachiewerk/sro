package edu.odu.cs441.sro.dao.record;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.entity.record.Subscription;

/**
 * Created by michael on 3/12/18.
 */

public interface SubscriptionDao {
    @Query("SELECT * FROM subscription ORDER BY created_date DESC")
    LiveData<List<Subscription>> findAll();

    @Query("SELECT * FROM SUBSCRIPTION WHERE subscription_key = :subscriptionKey")
    Subscription findByKey(String subscriptionKey);

    @RawQuery(observedEntities = Subscription.class)
    LiveData<List<Subscription>> findByQuery(SupportSQLiteQuery query);

    @Insert
    void insert(Subscription subscription);

    @Update
    void update(Subscription subscription);

    @Delete
    void delete(Subscription ... subscriptions);
}
