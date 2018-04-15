package edu.odu.cs441.sro.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import edu.odu.cs441.sro.converter.DateConverter;
import edu.odu.cs441.sro.converter.PriceConverter;
import edu.odu.cs441.sro.dao.metadata.CategoryDao;
import edu.odu.cs441.sro.dao.metadata.DuedateDao;
import edu.odu.cs441.sro.dao.metadata.LocationDao;
import edu.odu.cs441.sro.dao.metadata.MethodDao;
import edu.odu.cs441.sro.dao.record.ReceiptDao;
import edu.odu.cs441.sro.dao.record.SubscriptionDao;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Duedate;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.entity.record.Receipt;

@Database(entities = {Receipt.class, Location.class, Category.class, Method.class}, version = 2)
@TypeConverters({DateConverter.class, PriceConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ReceiptDao receiptDao();
    public abstract SubscriptionDao subscriptionDao();
    public abstract LocationDao locationDao();
    public abstract CategoryDao categoryDao();
    public abstract MethodDao methodDao();
    public abstract DuedateDao duedateDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "sro_database")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
