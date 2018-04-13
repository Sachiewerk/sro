package edu.odu.cs441.sro.repository.metadata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;
import edu.odu.cs441.sro.dao.metadata.MethodDao;
import edu.odu.cs441.sro.database.AppDatabase;
import edu.odu.cs441.sro.entity.metadata.Method;

public class MethodRepository {

    private MethodDao methodDao;
    private LiveData<List<Method>> allMethods;

    public MethodRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        methodDao = db.methodDao();
        allMethods = methodDao.findAll();
    }

    public LiveData<List<Method>> findAll() {
        return allMethods;
    }

    public void insert(Method method) {
        new InsertAsyncTask(methodDao).execute(method);
    }

    public void delete(Method ... methods) {
        new DeleteAsyncTask(methodDao).execute(methods);
    }

    public void update(Method method) {
        new UpdateAsyncTask(methodDao).execute(method);
    }

    public int getCount() {
        return methodDao.getCount();
    }

    private static class InsertAsyncTask extends AsyncTask<Method, Void, Void> {
        private MethodDao methodDao;

        InsertAsyncTask(MethodDao methodDao) {
            this.methodDao = methodDao;
        }

        @Override
        protected Void doInBackground(Method ... params) {
            methodDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Method, Void, Void> {
        private MethodDao methodDao;

        DeleteAsyncTask(MethodDao methodDao) {
            this.methodDao = methodDao;
        }

        @Override
        protected Void doInBackground(Method ... params) {
            methodDao.delete(params);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Method, Void, Void> {
        private MethodDao methodDao;

        UpdateAsyncTask(MethodDao methodDao) {
            this.methodDao = methodDao;
        }

        @Override
        protected Void doInBackground(Method ... params) {
            methodDao.update(params[0]);
            return null;
        }
    }
}
