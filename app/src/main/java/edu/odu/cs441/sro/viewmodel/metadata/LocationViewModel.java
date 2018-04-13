package edu.odu.cs441.sro.viewmodel.metadata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.repository.metadata.LocationRepository;

public class LocationViewModel extends AndroidViewModel {

    private LocationRepository locationRepository;
    private LiveData<List<Location>> allLocations;

    public LocationViewModel(Application application) {
        super(application);
        locationRepository = new LocationRepository(application);
        allLocations = locationRepository.findAll();
    }

    public LiveData<List<Location>> findAll() {
        return allLocations;
    }

    public void insert(Location location) {
        locationRepository.insert(location);
    }

    public void update(Location location) {
        locationRepository.update(location);
    }

    public void delete(Location ... locations) {
        locationRepository.delete(locations);
    }

    public int getCount() { return locationRepository.getCount(); }

}
