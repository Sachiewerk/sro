package edu.odu.cs441.sro.repository.metadata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;
import edu.odu.cs441.sro.dao.metadata.LocationDao;
import edu.odu.cs441.sro.database.AppDatabase;
import edu.odu.cs441.sro.entity.metadata.Location;

public class LocationRepository {

    private LocationDao locationDao;
    private LiveData<List<Location>> allLocations;

    public LocationRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        locationDao = db.locationDao();
        allLocations = locationDao.findAll();
    }

    public LiveData<List<Location>> findAll() {
        return allLocations;
    }

    public void insert(Location location) {
        new InsertAsyncTask(locationDao).execute(location);
    }

    public void delete(Location ... locations) {
        new DeleteAsyncTask(locationDao).execute(locations);
    }

    public void update(Location location) {
        new UpdateAsyncTask(locationDao).execute(location);
    }

    public int getCount() {
        return locationDao.getCount();
    }

    private static class InsertAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        InsertAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location ... params) {
            locationDao.insert(params[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        DeleteAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location ... params) {
            locationDao.delete(params);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Location, Void, Void> {
        private LocationDao locationDao;

        UpdateAsyncTask(LocationDao locationDao) {
            this.locationDao = locationDao;
        }

        @Override
        protected Void doInBackground(Location ... params) {
            locationDao.update(params[0]);
            return null;
        }
    }
}
