package edu.odu.cs441.sro.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.odu.cs441.sro.model.record.Receipt;
import edu.odu.cs441.sro.repository.ReceiptRepository;

/**
 * Created by michael on 3/12/18.
 */

public class ReceiptController {
    ReceiptRepository receiptRepository;

    public ReceiptController() {
        receiptRepository = new ReceiptRepository();
    }

    public ArrayList<Receipt> getReceipts() {
        defaultDateSort();
        return receiptRepository.findAll();
    }

    public void update(Receipt receipt) {
        receiptRepository.update(receipt);
        defaultDateSort();
    }

    public void addReceipt(Receipt receipt) {
        receiptRepository.save(receipt);
        defaultDateSort();
    }

    public void addReceipts(ArrayList<Receipt> receipts) {
        receiptRepository.saveAll(receipts);
        defaultDateSort();
    }

    public void clearAllReceipts() {
        receiptRepository.clearAll();
    }

    public void refresh() {
        receiptRepository.refresh();
    }

    public void defaultDateSort() {
        Collections.sort(receiptRepository.findAll(), new DefaultSort());
    }

    class DefaultSort implements Comparator<Receipt> {
        public int compare(Receipt firstReceipt, Receipt secondReceipt) {
            if(firstReceipt.getDateTime().compareTo(secondReceipt.getDateTime()) > 0) {
                return -1;
            }

            if(firstReceipt.getDateTime().compareTo(secondReceipt.getDateTime()) < 0) {
                return 1;
            }

            return 0;
        }
    }
}
