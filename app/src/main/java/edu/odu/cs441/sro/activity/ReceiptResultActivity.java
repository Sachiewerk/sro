package edu.odu.cs441.sro.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.utility.view.ReceiptBaseAdapter;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class ReceiptResultActivity extends AppCompatActivity {

    public static String DATE_SPECIFIED = "DATE SPECIFIED";
    public static String LOCATION_SPECIFIED = "LOCATION SPECIFIED";
    public static String METHOD_SPECIFIED = "METHOD SPECIFIED";
    public static String CATEGORY_SPECIFIED = "CATEGORY_SPECIFIED";
    public static String PRICE_SPECIFIED = "PRICE SPECIFIED";

    public static String AFTER_DATE = "AFTER DATE";
    public static String BEFORE_DATE = "BEFORE DATE";
    public static String SELECTED_LOCATION = "SELECTED_LOCATION";

    private ReceiptViewModel receiptViewModel;

    private SimpleSQLiteQuery SQLiteQuery;
    private ArrayList<Object> args = new ArrayList<>();
    private boolean firstFilter = true;

    private boolean dateSpecified = false;
    private boolean locationSpecified = false;
    private boolean methodSpecified = false;
    private boolean categorySpecified = false;
    private boolean priceSpecified = false;

    private DateTime afterDateTime;
    private DateTime beforeDateTime;
    private String selectedLocation;

    private ListView listView;
    private ReceiptBaseAdapter receiptBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_result);

        Intent data = getIntent();
        dateSpecified = data.getBooleanExtra(DATE_SPECIFIED, false);
        locationSpecified = data.getBooleanExtra(LOCATION_SPECIFIED,false);
        methodSpecified = data.getBooleanExtra(METHOD_SPECIFIED, false);
        categorySpecified = data.getBooleanExtra(CATEGORY_SPECIFIED, false);
        priceSpecified = data.getBooleanExtra(PRICE_SPECIFIED, false);

        if(dateSpecified) {
            afterDateTime = DateTime.parse(data.getStringExtra(AFTER_DATE));
            beforeDateTime = DateTime.parse(data.getStringExtra(BEFORE_DATE));
        }

        if(locationSpecified) {
            selectedLocation = data.getStringExtra(SELECTED_LOCATION);
        }

        buildQuery();

        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        listView = findViewById(R.id.activity_receipt_result_listView);
        receiptBaseAdapter = new ReceiptBaseAdapter(this);

        receiptViewModel.findByQuery(SQLiteQuery).observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(@Nullable List<Receipt> receipts) {
                receiptBaseAdapter.setItems(receipts);
                receiptBaseAdapter.notifyDataSetChanged();
            }
        });

        listView.setAdapter(receiptBaseAdapter);
    }


    private void buildQuery() {
        firstFilter = true;
        String query = "SELECT * FROM receipt";
        if(dateSpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }

            query += "created_date BETWEEN ? AND ? ";
            args.add(afterDateTime.getMillis());
            args.add(beforeDateTime.getMillis());
        }

        if(locationSpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }

            query += "location = ? ";
            args.add(selectedLocation);
        }

        if(categorySpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }


        }

        if(methodSpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }


        }

        if(priceSpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }


        }

        SQLiteQuery = new SimpleSQLiteQuery(query, args.toArray());
    }
}
