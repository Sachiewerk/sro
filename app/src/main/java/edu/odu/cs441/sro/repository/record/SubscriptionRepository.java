package edu.odu.cs441.sro.repository.record;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.odu.cs441.sro.activity.MainActivity;
import edu.odu.cs441.sro.dao.record.ReceiptDao;
import edu.odu.cs441.sro.dao.record.SubscriptionDao;
import edu.odu.cs441.sro.database.AppDatabase;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.entity.record.Subscription;

/**
 * Created by michael on 3/12/18.
 */

public class SubscriptionRepository {
    private SubscriptionDao subscriptionDao;
    private LiveData<List<Subscription>> allSubscriptions;

    public SubscriptionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        subscriptionDao = db.subscriptionDao();
        allSubscriptions = subscriptionDao.findAll();
    }

    public LiveData<List<Subscription>> findAll() {
        return allSubscriptions;
    }

    public LiveData<List<Subscription>> findByQuery(SimpleSQLiteQuery query) {
        try {
            return new SubscriptionRepository.SimpleQueryAsyncTask(subscriptionDao).execute(query).get();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Subscription subscription) {
        new SubscriptionRepository.InsertAsyncTask(subscriptionDao).execute(subscription);
    }

    public void delete(Subscription[] subscriptions) {
        new SubscriptionRepository.DeleteAsyncTask(subscriptionDao).execute(subscriptions);
    }

    public void update(Subscription subscription) {
        new SubscriptionRepository.UpdateAsyncTask(subscriptionDao).execute(subscription);
    }

    public Subscription findByKey(String subscriptionKey) {
        return subscriptionDao.findByKey(subscriptionKey);
    }

    private static class InsertAsyncTask extends AsyncTask<Subscription, Void, Void> {
        private SubscriptionDao subscriptionDao;

        InsertAsyncTask(SubscriptionDao subscriptionDao) {
            this.subscriptionDao = subscriptionDao;
        }

        @Override
        protected Void doInBackground(final Subscription ... params) {
            this.subscriptionDao.insert(params[0]);
            return null;
        }
    }

    private static class SimpleQueryAsyncTask extends AsyncTask<SimpleSQLiteQuery, Void, LiveData<List<Subscription>>> {
        private SubscriptionDao subscriptionDao;

        SimpleQueryAsyncTask(SubscriptionDao subscriptionDao) {
            this.subscriptionDao = subscriptionDao;
        }

        @Override
        protected LiveData<List<Subscription>> doInBackground(SimpleSQLiteQuery ... params) {
            return this.subscriptionDao.findByQuery(params[0]);
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Subscription, Void, Void> {
        private SubscriptionDao subscriptionDao;

        DeleteAsyncTask(SubscriptionDao subscriptionDao) {
            this.subscriptionDao = subscriptionDao;
        }

        @Override
        protected Void doInBackground(final Subscription ... params) {
            this.subscriptionDao.delete(params);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Subscription, Void, Void> {
        private SubscriptionDao subscriptionDao;

        UpdateAsyncTask(SubscriptionDao subscriptionDao) {
            this.subscriptionDao = subscriptionDao;
        }

        @Override
        protected Void doInBackground(final Subscription ... params) {
            this.subscriptionDao.update(params[0]);
            return null;
        }
    }
}
