package edu.odu.cs441.sro.viewmodel.record;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

import edu.odu.cs441.sro.entity.record.Subscription;
import edu.odu.cs441.sro.repository.record.SubscriptionRepository;

public class SubscriptionViewModel extends AndroidViewModel {
    private SubscriptionRepository subscriptionRepository;
    private LiveData<List<Subscription>> allSubscriptions;

    public SubscriptionViewModel(Application application) {
        super(application);
        subscriptionRepository = new SubscriptionRepository(application);
        allSubscriptions = subscriptionRepository.findAll();
    }

    public LiveData<List<Subscription>> findAll() {
        return allSubscriptions;
    }

    public void insert(Subscription subscription) {
        subscriptionRepository.insert(subscription);
    }

    public void update(Subscription subscription) {
        subscriptionRepository.update(subscription);
    }

    public void delete(Subscription ... subscriptions) {
        subscriptionRepository.delete(subscriptions);
    }
}
