package edu.odu.cs441.sro.viewmodel.record;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SimpleSQLiteQuery;

import java.util.List;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.repository.record.ReceiptRepository;

public class ReceiptViewModel extends AndroidViewModel {

    private ReceiptRepository receiptRepository;
    private LiveData<List<Receipt>> allReceipts;

    public ReceiptViewModel(Application application) {
        super(application);
        receiptRepository = new ReceiptRepository(application);
        allReceipts = receiptRepository.findAll();
    }

    public LiveData<List<Receipt>> findAll() {
        return allReceipts;
    }

    public LiveData<List<Receipt>> findByQuery(SimpleSQLiteQuery query) {
        return receiptRepository.findByQuery(query);
    }

    public Receipt findByKey(String receiptKey) {
        return receiptRepository.findByKey(receiptKey);
    }

    public List<Receipt> findByDate(Long startDate, Long endDate) {
        return receiptRepository.findByDate(startDate, endDate);
    }

    public List<Receipt> findByDateAndLocation(Long startDate, Long endDate, String location) {
        return receiptRepository.findByDateAndLocation(startDate, endDate, location);
    }

    public List<Receipt> findByDateAndCategory(Long startDate, Long endDate, String category) {
        return receiptRepository.findByDateAndCategory(startDate, endDate, category);
    }

    public List<Receipt> findByDateAndMethod(Long startDate, Long endDate, String method) {
        return receiptRepository.findByDateAndMethod(startDate, endDate, method);
    }

    public void insert(Receipt receipt) {
        receiptRepository.insert(receipt);
    }

    public void update(Receipt receipt) {
        receiptRepository.update(receipt);
    }

    public void delete(Receipt ... receipts) {
        receiptRepository.delete(receipts);
    }
}
