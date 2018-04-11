package edu.odu.cs441.sro.repository.record;

import android.app.Application;
import android.arch.lifecycle.LiveData;
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

    public void insert(Receipt receipt) {
        new InsertAsyncTask(receiptDao).execute(receipt);
    }

    public void delete(Receipt ... receipts) {
        new DeleteAsyncTask(receiptDao).execute(receipts);
    }

    public void update(Receipt receipt) {
        new UpdateAsyncTask(receiptDao).execute(receipt);
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
