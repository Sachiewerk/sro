package edu.odu.cs441.sro.viewmodel.metadata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.repository.metadata.CategoryRepository;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> allCategorys;

    public CategoryViewModel(Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        allCategorys = categoryRepository.findAll();
    }

    public LiveData<List<Category>> findAll() {
        return allCategorys;
    }

    public void insert(Category category) {
        categoryRepository.insert(category);
    }

    public void update(Category category) {
        categoryRepository.update(category);
    }

    public void delete(Category ... categorys) {
        categoryRepository.delete(categorys);
    }

    public int getCount() { return categoryRepository.getCount(); }
}
