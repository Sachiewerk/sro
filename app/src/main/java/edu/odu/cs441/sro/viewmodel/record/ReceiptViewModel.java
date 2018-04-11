package edu.odu.cs441.sro.viewmodel.record;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
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
