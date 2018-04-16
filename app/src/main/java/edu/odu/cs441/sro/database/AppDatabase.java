package edu.odu.cs441.sro.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import edu.odu.cs441.sro.dao.metadata.CategoryDao;
import edu.odu.cs441.sro.dao.metadata.LocationDao;
import edu.odu.cs441.sro.dao.metadata.MethodDao;
import edu.odu.cs441.sro.dao.record.ReceiptDao;
import edu.odu.cs441.sro.dao.record.SubscriptionDao;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.entity.record.Subscription;

@Database(entities = {Receipt.class, Subscription.class, Location.class, Category.class, Method.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ReceiptDao receiptDao();
    public abstract SubscriptionDao subscriptionDao();

    public abstract LocationDao locationDao();
    public abstract CategoryDao categoryDao();
    public abstract MethodDao methodDao();

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
