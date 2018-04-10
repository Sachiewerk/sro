package edu.odu.cs441.sro.repository;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import edu.odu.cs441.sro.MainActivity;
import edu.odu.cs441.sro.entity.record.Receipt;

/**
 * Created by michael on 3/12/18.
 */
public class ReceiptRepository {

    ArrayList<Receipt> mReceipts;
    File mDataPath;

    public static final String MY_RECEIPT_DATA_SUBDIRECTORY = "TEST";
    public static final String MY_RECEIPT_DATA_FILE_NAME = "e9e29565-dbfc-494b-adae-58714e88f36f.txt";

    public ReceiptRepository() {

        mDataPath = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS),
            MainActivity.MY_SRO_MAIN_DIRECTORY +
            File.separator +
            MainActivity.MY_RECEIPT_DIRECTORY +
            File.separator +
            MY_RECEIPT_DATA_SUBDIRECTORY
        );

        if(!mDataPath.mkdirs()) {
            // TODO Failed to create the directory
        }

        mDataPath = new File(mDataPath,MY_RECEIPT_DATA_FILE_NAME);
        mReceipts = new ArrayList<> ();
        mReceipts = read();
    }

    public ArrayList<Receipt> findAll() {
        return mReceipts;
    }

    public void update(Receipt receipt) {
        for(int i = 0; i < mReceipts.size(); i++) {
            if(mReceipts.get(i).equals(receipt)) {
                mReceipts.set(i, receipt);
            }
        }
    }

    public void updateAll(ArrayList<Receipt> receipts) {

        for(int i = 0; i < receipts.size(); i++) {
            for(int j = 0; j < mReceipts.size(); j++) {
                if(receipts.get(i).equals(mReceipts.get(j))) {
                    mReceipts.set(j, receipts.get(i));
                }
            }
        }
    }

    public void delete(ArrayList<Receipt> receipts) {
        mReceipts.removeAll(receipts);
    }

    public void delete(Receipt receipt) {
        mReceipts.remove(receipt);
    }

    public void addAll(ArrayList<Receipt> receipts) {
        for(Receipt receipt : receipts) {
            if(!mReceipts.contains(receipt)) {
                mReceipts.add(receipt);
            }
        }
    }

    public void add(Receipt receipt) {
        if(!mReceipts.contains(receipt)) {
            mReceipts.add(receipt);
        }
    }

    public void save() {
        write();
    }

    public void save(Receipt receipt) {
        add(receipt);
        write();
    }

    public void saveAll(ArrayList<Receipt> receipts) {
        addAll(receipts);
        write();
    }

    public void clearAll() {
        mReceipts.clear();
    }

    public void refresh() {
        //clearAll();
        ArrayList<Receipt> savedReceipts = read();
        addAll(savedReceipts);
    }

    private ArrayList<Receipt> read() {
        ArrayList<Receipt> result;

        try {
            InputStream file = new FileInputStream(mDataPath.getAbsoluteFile());
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            result = (ArrayList<Receipt>)input.readObject();
            input.close();
            buffer.close();
            file.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            result = new ArrayList<> ();
            //TODO
        } catch(IOException e) {
            e.printStackTrace();
            result = new ArrayList<> ();
            //TODO
        } catch(ClassNotFoundException e) {
            e.printStackTrace();;
            result = new ArrayList<> ();
            //TODO
        }

        return result;
    }

    private void write() {
        try (
            OutputStream file = new FileOutputStream(mDataPath.getAbsoluteFile());
            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput output = new ObjectOutputStream(buffer)
        )
        {
            output.writeObject(mReceipts);
            output.close();
            buffer.close();
            file.close();
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
