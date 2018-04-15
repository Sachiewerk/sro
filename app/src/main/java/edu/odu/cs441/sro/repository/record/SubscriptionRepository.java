package edu.odu.cs441.sro.repository.record;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import edu.odu.cs441.sro.dao.record.SubscriptionDao;
import edu.odu.cs441.sro.database.AppDatabase;
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

    public void insert(Subscription subscription) {
        new SubscriptionRepository.InsertAsyncTask(subscriptionDao).execute(subscription);
    }

    public void delete(Subscription ... subscription) {
        new SubscriptionRepository.DeleteAsyncTask(subscriptionDao).execute(subscription);
    }

    public void update(Subscription subscription) {
        new SubscriptionRepository.UpdateAsyncTask(subscriptionDao).execute(subscription);
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
