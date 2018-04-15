package edu.odu.cs441.sro.viewmodel.metadata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.odu.cs441.sro.entity.metadata.Duedate;
import edu.odu.cs441.sro.repository.metadata.DuedateRepository;

public class DuedateViewModel extends AndroidViewModel {

    private DuedateRepository duedateRepository;
    private LiveData<List<Duedate>> allDuedates;



    public DuedateViewModel(Application application) {
        super(application);
        duedateRepository = new DuedateRepository(application);
        allDuedates = duedateRepository.findAll();
    }

    public LiveData<List<Duedate>> findAll() {
        return allDuedates;
    }

    public void insert(Duedate duedate) {
        duedateRepository.insert(duedate);
    }

    public void update(Duedate duedate) {
        duedateRepository.update(duedate);
    }

    public void delete(Duedate ... duedates) {
        duedateRepository.delete(duedates);
    }

    public int getCount() { return duedateRepository.getCount(); }

}

