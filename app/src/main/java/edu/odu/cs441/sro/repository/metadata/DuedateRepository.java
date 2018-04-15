package edu.odu.cs441.sro.repository.metadata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import edu.odu.cs441.sro.dao.metadata.DuedateDao;
import edu.odu.cs441.sro.database.AppDatabase;
import edu.odu.cs441.sro.entity.metadata.Duedate;

public class DuedateRepository {

    private DuedateDao duedateDao;
    private LiveData<List<Duedate>> allDuedates;

    public DuedateRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        duedateDao = db.duedateDao();
        allDuedates = duedateDao.findAll();
    }

    public LiveData<List<Duedate>> findAll() {
        return allDuedates;
    }

    public void insert(Duedate duedate) {
        new LocationRepository.InsertAsyncTask(duedateDao).execute(duedate);
    }

    public void delete(Duedate ... duedates) {
        new LocationRepository.DeleteAsyncTask(duedateDao).execute(duedates);
    }

    public void update(Duedate duedate) {
        new LocationRepository.UpdateAsyncTask(duedateDao).execute(duedate);
    }

    private static class InsertAsyncTask extends AsyncTask<Duedate, Void, Void> {
        private DuedateDao duedateDao;

        InsertAsyncTask(DuedateDao duedateDao) {
            this.duedateDao = duedateDao;
        }

        @Override
        protected Void doInBackground(Duedate ... params) {
            duedateDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Duedate, Void, Void> {
        private DuedateDao duedateDao;

        DeleteAsyncTask(DuedateDao duedateDao) {
            this.duedateDao = duedateDao;
        }

        @Override
        protected Void doInBackground(Duedate ... params) {
            duedateDao.delete(params);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Duedate, Void, Void> {
        private DuedateDao duedateDao;

        UpdateAsyncTask(DuedateDao duedateDao) {
            this.duedateDao = duedateDao;
        }

        @Override
        protected Void doInBackground(Duedate ... params) {
            duedateDao.update(params[0]);
            return null;
        }
    }
}
