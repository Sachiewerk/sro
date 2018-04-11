package edu.odu.cs441.sro.repository.metadata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;
import edu.odu.cs441.sro.dao.metadata.CategoryDao;
import edu.odu.cs441.sro.database.AppDatabase;
import edu.odu.cs441.sro.entity.metadata.Category;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategorys;

    public CategoryRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        categoryDao = db.categoryDao();
        allCategorys = categoryDao.findAll();
    }

    public LiveData<List<Category>> findAll() {
        return allCategorys;
    }

    public void insert(Category category) {
        new InsertAsyncTask(categoryDao).execute(category);
    }

    public void delete(Category ... categorys) {
        new DeleteAsyncTask(categoryDao).execute(categorys);
    }

    public void update(Category category) {
        new UpdateAsyncTask(categoryDao).execute(category);
    }

    private static class InsertAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        InsertAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category ... params) {
            categoryDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        DeleteAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category ... params) {
            categoryDao.delete(params);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao categoryDao;

        UpdateAsyncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(Category ... params) {
            categoryDao.update(params[0]);
            return null;
        }
    }
}
