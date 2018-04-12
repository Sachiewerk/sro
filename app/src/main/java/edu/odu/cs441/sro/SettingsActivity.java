package edu.odu.cs441.sro;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.viewmodel.metadata.CategoryViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.LocationViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.MethodViewModel;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class SettingsActivity extends AppCompatActivity {

    ReceiptViewModel receiptViewModel;
    LocationViewModel locationViewModel;
    CategoryViewModel categoryViewModel;
    MethodViewModel methodViewModel;

    ArrayList<String> mySortByList ;
    ArrayAdapter<String> mySortListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);

        //add onClickListeners

        //add ListView with onItemClickListeners

        //add backButton

    }

    public ArrayList<String> getMySortByList(ArrayList<String> myList) {
        myList.add("PRICE_DESC");
        myList.add("PRICE_ASCE");
        myList.add("DATE_ASCE");
        myList.add("DATE_DESC");
        return myList;
    }

    public void addMethod()
    {
        Method newMethod = new Method("BitCoin");


    }
    public void addCategory()
    {

    }

    public void addLocation()
    {

    }

    public String changeSortOrder()
    {
     return "";
    }

}
