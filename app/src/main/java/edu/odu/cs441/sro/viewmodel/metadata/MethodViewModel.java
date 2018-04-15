package edu.odu.cs441.sro.viewmodel.metadata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.repository.metadata.MethodRepository;

public class MethodViewModel extends AndroidViewModel {

    private MethodRepository methodRepository;
    private LiveData<List<Method>> allMethods;

    public MethodViewModel(Application application) {
        super(application);
        methodRepository = new MethodRepository(application);
        allMethods = methodRepository.findAll();
    }

    public LiveData<List<Method>> findAll() {
        return allMethods;
    }

    public void insert(Method method) {
        methodRepository.insert(method);
    }

    public void update(Method method) {
        methodRepository.update(method);
    }

    public void delete(Method ... methods) {
        methodRepository.delete(methods);
    }

    public int getCount() { return methodRepository.getCount(); }

}
