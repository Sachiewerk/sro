package edu.odu.cs441.sro.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SimpleSQLiteQuery;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import org.joda.time.DateTime;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.intent.EmailReceiptIntent;
import edu.odu.cs441.sro.utility.view.ReceiptBaseAdapter;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class ReceiptResultActivity extends AppCompatActivity {

    public static String DATE_SPECIFIED = "DATE SPECIFIED";
    public static String LOCATION_SPECIFIED = "LOCATION SPECIFIED";
    public static String METHOD_SPECIFIED = "METHOD SPECIFIED";
    public static String CATEGORY_SPECIFIED = "CATEGORY_SPECIFIED";
    public static String PRICE_SPECIFIED = "PRICE SPECIFIED";

    public static String ORDER_BY = "ORDER BY";
    public static String ORDER_METHOD = "ORDER METHOD";

    public static String AFTER_DATE = "AFTER DATE";
    public static String BEFORE_DATE = "BEFORE DATE";
    public static String SELECTED_LOCATION = "SELECTED_LOCATION";
    public static String SELECTED_METHOD = "SELECTED_METHOD";
    public static String SELECTED_CATEGORY = "SELECTED CATEGORY";
    public static String GREATER_PRICE = "GREATER PRICE";
    public static String LESS_PRICE = "LESS PRICE";

    private ReceiptViewModel receiptViewModel;

    private SimpleSQLiteQuery SQLiteQuery;
    private ArrayList<Object> args = new ArrayList<>();
    private boolean firstFilter = true;

    private boolean dateSpecified = false;
    private boolean locationSpecified = false;
    private boolean methodSpecified = false;
    private boolean categorySpecified = false;
    private boolean priceSpecified = false;

    private String orderByField;
    private String orderByMethod;
    private DateTime afterDateTime;
    private DateTime beforeDateTime;
    private String selectedLocation;
    private String selectedMethod;
    private String selectedCategory;
    private Double greaterPrice;
    private Double lessPrice;

    private ListView listView;
    private ReceiptBaseAdapter receiptBaseAdapter;

    private List<Receipt> receiptList;
    private int longSelectedItemIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_result);

        Intent data = getIntent();
        getIntentData(data);
        buildQuery();
        initializeListView();
        initializeButton();
    }

    private void getIntentData(Intent data) {
        dateSpecified = data.getBooleanExtra(DATE_SPECIFIED, false);
        locationSpecified = data.getBooleanExtra(LOCATION_SPECIFIED,false);
        methodSpecified = data.getBooleanExtra(METHOD_SPECIFIED, false);
        categorySpecified = data.getBooleanExtra(CATEGORY_SPECIFIED, false);
        priceSpecified = data.getBooleanExtra(PRICE_SPECIFIED, false);
        orderByField = data.getStringExtra(ORDER_BY);
        orderByMethod = data.getStringExtra(ORDER_METHOD);

        if(dateSpecified) {
            afterDateTime = DateTime.parse(data.getStringExtra(AFTER_DATE));
            beforeDateTime = DateTime.parse(data.getStringExtra(BEFORE_DATE));
        }

        if(locationSpecified) {
            selectedLocation = data.getStringExtra(SELECTED_LOCATION);
        }

        if(methodSpecified) {
            selectedMethod = data.getStringExtra(SELECTED_METHOD);
        }

        if(categorySpecified) {
            selectedCategory = data.getStringExtra(SELECTED_CATEGORY);
        }

        if(priceSpecified) {
            greaterPrice = data.getDoubleExtra(GREATER_PRICE, 0.00);
            lessPrice = data.getDoubleExtra(LESS_PRICE, 0.00);
        }
    }

    private void buildQuery() {
        firstFilter = true;
        String query = "SELECT * FROM receipt ";
        if(dateSpecified) {
            if(firstFilter) { query += "WHERE "; firstFilter = false; }

            query += "created_date BETWEEN ? AND ? AND ";
            args.add(afterDateTime.getMillis());
            args.add(beforeDateTime.getMillis());
        }

        if(locationSpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }

            query += "location = ? AND ";
            args.add(selectedLocation);
        }

        if(categorySpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }

            query += "category = ? AND ";
            args.add(selectedCategory);
        }

        if(methodSpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }

            query += "method = ? AND ";
            args.add(selectedMethod);
        }

        if(priceSpecified) {
            if(firstFilter) { query += " WHERE "; firstFilter = false; }

            query += "price BETWEEN ? AND ? AND ";
            args.add(greaterPrice);
            args.add(lessPrice);
        }

        if(!firstFilter) {
            query = query.substring(0, query.length() - 4);
        }

        query += "ORDER BY " + parseOrderByField() + " " + parseOrderByMethod();
        SQLiteQuery = new SimpleSQLiteQuery(query, args.toArray());
    }

    private void initializeListView() {
        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        listView = findViewById(R.id.activity_receipt_result_listView);
        receiptBaseAdapter = new ReceiptBaseAdapter(this);

        receiptViewModel.findByQuery(SQLiteQuery).observe(this, new Observer<List<Receipt>>() {
            @Override
            public void onChanged(@Nullable List<Receipt> receipts) {
                receiptBaseAdapter.setItems(receipts);
                receiptBaseAdapter.notifyDataSetChanged();
                receiptList = receipts;
            }
        });

        listView.setAdapter(receiptBaseAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longSelectedItemIndex = position;
                showMenu(view);
                return false;
            }
        });
    }

    private void initializeButton() {
        Button backButton = findViewById(R.id.activity_receipt_result_back_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Show Action Menu when user long clicks a Receipt in the ListView
     * @param view
     */
    private void showMenu(View view) {
        PopupMenu popup = new PopupMenu(this, view);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Receipt receipt = receiptList.get(longSelectedItemIndex);
                switch (item.getItemId()) {
                    case R.id.item_action_open:
                        startReceiptViewActivity(receipt);
                        return true;
                    case R.id.item_action_edit:
                        startReceiptEditActivity(receipt);
                        return true;
                    case R.id.item_action_send:
                        emailReceipt(receipt);
                        return true;
                    case R.id.item_action_delete:
                        showDeleteConfirmation(receipt);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu.item_action_menu);
        popup.show();
    }

    /**
     * Open Receipt Information
     * @param receipt Receipt
     */
    private void startReceiptViewActivity(Receipt receipt) {
        Intent intent = new Intent(this, ReceiptViewActivity.class);
        intent.putExtra(ReceiptViewActivity.RECEIPT_UNIQUE_IDENTIFIER, receipt.getReceiptKey());
        startActivity(intent);
    }

    /**
     * Open Receipt Edit Activity
     * @param receipt Receipt
     */
    private void startReceiptEditActivity(Receipt receipt) {
        Intent intent = new Intent(this, ReceiptEditActivity.class);
        intent.putExtra(ReceiptEditActivity.RECEIPT_UNIQUE_IDENTIFIER, receipt.getReceiptKey());
        startActivity(intent);
    }

    /**
     * E-mail receipt
     * @param receipt Receipt
     */
    private void emailReceipt(Receipt receipt) {
        EmailReceiptIntent emailReceiptIntent = new EmailReceiptIntent(this);
        emailReceiptIntent.sendEmail(receipt);
    }

    /**
     * Show delete receipt confirmation popup when user selects to delete a receipt
     */
    private void showDeleteConfirmation(final Receipt receipt) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Do you really want to delete the receipt?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(receipt.getImageFilePath() != null) {
                            File file = new File(receipt.getImageFilePath());
                            file.delete();
                        }

                        receiptViewModel.delete(receipt);
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private String parseOrderByMethod() {
        if(orderByMethod.equals("Ascending")) {
            return "ASC";
        } else if(orderByMethod.equals("Descending")) {
            return "DESC";
        } else {
            return null;
        }
    }

    private String parseOrderByField() {
        if(orderByField.equals("CATEGORY")) {
            return "category";
        } else if(orderByField.equals("METHOD")) {
            return "method";
        } else if(orderByField.equals("DATE")) {
            return "created_date";
        } else if(orderByField.equals("LOCATION")) {
            return "location";
        } else if(orderByField.equals("SUBTOTAL")) {
            return "price";
        } else {
            return null;
        }
    }
}
