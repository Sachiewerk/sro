package edu.odu.cs441.sro.repository.record;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.arch.persistence.db.SupportSQLiteQuery;
import android.os.AsyncTask;
import java.util.List;
import edu.odu.cs441.sro.dao.record.ReceiptDao;
import edu.odu.cs441.sro.database.AppDatabase;
import edu.odu.cs441.sro.entity.record.Receipt;

/**
 * Created by michael on 3/12/18.
 */
public class ReceiptRepository {

    private ReceiptDao receiptDao;
    private LiveData<List<Receipt>> allReceipts;

    public ReceiptRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        receiptDao = db.receiptDao();
        allReceipts = receiptDao.findAll();
    }

    public LiveData<List<Receipt>> findAll() {
        return allReceipts;
    }

    public LiveData<List<Receipt>> findByQuery(SimpleSQLiteQuery query) {
        try {
            return new SimpleQueryAsyncTask(receiptDao).execute(query).get();
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Receipt receipt) {
        new InsertAsyncTask(receiptDao).execute(receipt);
    }

    public void delete(Receipt ... receipts) {
        new DeleteAsyncTask(receiptDao).execute(receipts);
    }

    public void update(Receipt receipt) {
        new UpdateAsyncTask(receiptDao).execute(receipt);
    }

    public Receipt findByKey(String receiptKey) {
        return receiptDao.findByKey(receiptKey);
    }

    public List<Receipt> findByDate(Long startDate, Long endDate) {
        return receiptDao.findByDate(startDate, endDate);
    }

    public List<Receipt> findByDateAndCategory(Long startDate, Long endDate, String category) {
        return receiptDao.findByDateAndCategory(startDate, endDate, category);
    }

    public List<Receipt> findByDateAndMethod(Long startDate, Long endDate, String method) {
        return receiptDao.findByDateAndMethod(startDate, endDate, method);
    }

    public List<Receipt> findByDateAndLocation(Long startDate, Long endDate, String location) {
        return receiptDao.findByDateAndLocation(startDate, endDate, location);
    }

    private static class InsertAsyncTask extends AsyncTask<Receipt, Void, Void> {
        private ReceiptDao receiptDao;

        InsertAsyncTask(ReceiptDao receiptDao) {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(final Receipt ... params) {
            this.receiptDao.insert(params[0]);
            return null;
        }
    }

    private static class SimpleQueryAsyncTask extends AsyncTask<SimpleSQLiteQuery, Void, LiveData<List<Receipt>>> {
        private ReceiptDao receiptDao;

        SimpleQueryAsyncTask(ReceiptDao receiptDao) {
            this.receiptDao = receiptDao;
        }

        @Override
        protected LiveData<List<Receipt>> doInBackground(SimpleSQLiteQuery ... params) {
            return this.receiptDao.findByQuery(params[0]);
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Receipt, Void, Void> {
        private ReceiptDao receiptDao;

        DeleteAsyncTask(ReceiptDao receiptDao) {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(final Receipt ... params) {
            this.receiptDao.delete(params);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Receipt, Void, Void> {
        private ReceiptDao receiptDao;

        UpdateAsyncTask(ReceiptDao receiptDao) {
            this.receiptDao = receiptDao;
        }

        @Override
        protected Void doInBackground(final Receipt ... params) {
            this.receiptDao.update(params[0]);
            return null;
        }
    }
}
