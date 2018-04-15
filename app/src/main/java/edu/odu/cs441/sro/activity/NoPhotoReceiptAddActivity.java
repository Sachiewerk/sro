package edu.odu.cs441.sro.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.utility.data.NumberTextWatcher;
import edu.odu.cs441.sro.utility.data.StringPriceParser;
import edu.odu.cs441.sro.viewmodel.metadata.CategoryViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.LocationViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.MethodViewModel;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class NoPhotoReceiptAddActivity extends AppCompatActivity {

    // ViewModels
    ReceiptViewModel receiptViewModel;
    LocationViewModel locationViewModel;
    CategoryViewModel categoryViewModel;
    MethodViewModel methodViewModel;

    // The UUID of this receipt, which is also part of the image file name
    UUID mUUID;

    private ArrayList<String> mLocationList;
    private ArrayList<String> mCategoryList;
    private ArrayList<String> mMethodList;

    private ArrayAdapter<String> mLocationAutoCompleteAdapter;
    private ArrayAdapter<String> mCategoryAutoCompleteAdapter;
    private ArrayAdapter<String> mMethodAutoCompleteAdapter;

    private DateTime mDate;
    private EditText mTitleEditText;
    private AutoCompleteTextView mLocationAutoCompleteTextView;
    private AutoCompleteTextView mPriceAutoCompleteTextView;
    private AutoCompleteTextView mCategoryAutoCompleteTextView;
    private AutoCompleteTextView mMethodAutoCompleteTextView;
    private EditText mCommentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_photo_receipt_add);

        mUUID = (UUID)getIntent().getSerializableExtra(MainActivity.MY_UUID_INTENT_IDENTIFIER);

        mDate = new DateTime();

        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);

        mLocationList = new ArrayList<> ();
        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                mLocationList.clear();
                for(Location location : locations) {
                    mLocationList.add(location.getLocation());
                }
            }
        });

        mCategoryList = new ArrayList<> ();
        categoryViewModel.findAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                mCategoryList.clear();
                for(Category category : categories) {
                    mCategoryList.add(category.getCategory());
                }
            }
        });


        mMethodList = new ArrayList<> ();
        methodViewModel.findAll().observe(this, new Observer<List<Method>>() {
            @Override
            public void onChanged(@Nullable List<Method> methods) {
                mMethodList.clear();
                for(Method method : methods) {
                    mMethodList.add(method.getMethod());
                }
            }
        });


        mTitleEditText = findViewById(R.id.no_photo_receipt_add_edittext_title);
        mLocationAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.no_photo_receipt_add_autoCompleteTextview_location);
        mPriceAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.no_photo_receipt_add_autoCompleteTextview_price);
        mCategoryAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.no_photo_receipt_add_autoCompleteTextview_category);
        mMethodAutoCompleteTextView =
                (AutoCompleteTextView) findViewById(
                        R.id.no_photo_receipt_add_autoCompleteTextview_method);
        mCommentEditText = findViewById(R.id.no_photo_receipt_add_edittext_comment);


        mLocationAutoCompleteAdapter = new ArrayAdapter<>(
                this,android.R.layout.select_dialog_singlechoice, mLocationList);
        mCategoryAutoCompleteAdapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, mCategoryList);
        mMethodAutoCompleteAdapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, mMethodList);

        setReceiptDate();
        initializeTitleViews();
        initializeAutoCompleteTextViews();
        initializeButtonEventListener();
    }

    private void setReceiptDate() {
        TextView dateView = findViewById(R.id.no_photo_receipt_add_textview_date);

        // Create a temporary DateTime for date formatting
        dateView.setText(mDate.toString(DateTimeFormat.shortDateTime()));
    }

    private void initializeTitleViews() {
        final TextView titleView = findViewById(R.id.no_photo_receipt_add_receipt_title);

        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                titleView.setText(mTitleEditText.getText().toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing Necessary here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                titleView.setText(mTitleEditText.getText().toString());
            }
        });
    }

    private void initializeAutoCompleteTextViews() {

        // Add the currency TextWatcher for the price AutoCompleteTextView
        mPriceAutoCompleteTextView.addTextChangedListener
                (new NumberTextWatcher(mPriceAutoCompleteTextView));

        // Initialize the location AutoCompleteTextView
        initializeAutoCompleteTextView
                (
                        mLocationAutoCompleteTextView,
                        mLocationAutoCompleteAdapter,
                        mLocationList
                );

        // Initialize the Category AutoCompleteTextView
        initializeAutoCompleteTextView
                (
                        mCategoryAutoCompleteTextView,
                        mCategoryAutoCompleteAdapter,
                        mCategoryList
                );

        // Initialize the Method AutoCompleteTextView
        initializeAutoCompleteTextView
                (
                        mMethodAutoCompleteTextView,
                        mMethodAutoCompleteAdapter,
                        mMethodList
                );
    }

    private void initializeAutoCompleteTextView
            (final AutoCompleteTextView acTextView,
             ArrayAdapter<String> adapter,
             ArrayList<String> list) {

        acTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View paramView) {
                acTextView.showDropDown();

                return false;
            }
        });

        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);
    }

    private void initializeButtonEventListener() {
        final Button saveButton = findViewById(R.id.no_photo_receipt_add_Button_Save);
        final Button discardButton = findViewById(R.id.no_photo_receipt_add_Button_Discard);


        // What should happen when user clicks "Save" button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = mTitleEditText.getText().toString();
                String category = mCategoryAutoCompleteTextView.getText().toString();
                String location = mLocationAutoCompleteTextView.getText().toString();
                String method = mMethodAutoCompleteTextView.getText().toString();
                String price = mPriceAutoCompleteTextView.getText().toString();
                String comment = mCommentEditText.getText().toString();

                categoryViewModel.insert(new Category(category));
                locationViewModel.insert(new Location(location));
                methodViewModel.insert(new Method(method));

                Receipt receipt = new Receipt(mUUID.toString(), mDate.getMillis());
                receipt.setTitle(title);
                receipt.setCategory(category);
                receipt.setLocation(location);
                receipt.setMethod(method);
                receipt.setPrice(new StringPriceParser(price).getDecimalValue());
                receipt.setComment(comment);

                receiptViewModel.insert(receipt);

                setResult(RESULT_OK);
                finish();
            }
        });

        // What should happen when user clicks "Discard" button
        discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set the result to canceled
                setResult(RESULT_CANCELED);

                // Close this activty and return to parent activity
                finish();
            }
        });
    }

}
