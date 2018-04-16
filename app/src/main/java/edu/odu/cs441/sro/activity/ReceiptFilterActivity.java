package edu.odu.cs441.sro.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import java.util.ArrayList;
import java.util.List;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.utility.data.NumberTextWatcher;
import edu.odu.cs441.sro.utility.data.StringPriceParser;
import edu.odu.cs441.sro.utility.view.CategoryArrayAdapter;
import edu.odu.cs441.sro.utility.view.LocationArrayAdapter;
import edu.odu.cs441.sro.utility.view.MethodArrayAdapter;
import edu.odu.cs441.sro.viewmodel.metadata.CategoryViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.LocationViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.MethodViewModel;

public class ReceiptFilterActivity extends AppCompatActivity {

    private LocationViewModel locationViewModel;
    private MethodViewModel methodViewModel;
    private CategoryViewModel categoryViewModel;

    private CheckBox locationEnableCheckBox;
    private Spinner locationSpinner;
    private LocationArrayAdapter locationArrayAdapter;
    private List<Location> locationList = new ArrayList<>();

    private CheckBox methodEnableCheckBox;
    private Spinner methodSpinner;
    private MethodArrayAdapter methodArrayAdapter;
    private List<Method> methodList = new ArrayList<>();

    private CheckBox categoryEnableCheckBox;
    private Spinner categorySpinner;
    private CategoryArrayAdapter categoryArrayAdapter;
    private List<Category> categoryList = new ArrayList<>();

    private CheckBox dateEnableCheckBox;
    private DateTime todayDateTime = new DateTime();
    private DateTime afterDateTime = new DateTime();
    private DateTime beforeDateTime = new DateTime();
    private EditText afterDateEditText;
    private EditText beforeDateEditText;

    private CheckBox priceEnableCheckBox;
    private EditText greaterPriceEditText;
    private EditText lessPriceEditText;

    private Spinner orderBySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_filter);

        initializeDateViews();
        initializeLocationViews();
        initializeMethodViews();
        initializeCategoryViews();
        initializeButtons();
        initializePriceViews();
        initializeOrderBySpinner();
    }

    private void initializeDateViews() {
        dateEnableCheckBox = findViewById(R.id.receipt_filter_date_enable_checkbox);
        afterDateEditText = findViewById(R.id.receipt_filter_date_after_edittext);
        beforeDateEditText = findViewById(R.id.receipt_filter_date_before_edittext);

        afterDateEditText.setRawInputType(InputType.TYPE_NULL);
        afterDateEditText.setFocusable(true);
        beforeDateEditText.setRawInputType(InputType.TYPE_NULL);
        beforeDateEditText.setFocusable(true);
        afterDateEditText.setEnabled(false);
        beforeDateEditText.setEnabled(false);

        final DatePickerDialog.OnDateSetListener afterDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                afterDateTime = afterDateTime.year().setCopy(year);
                afterDateTime = afterDateTime.monthOfYear().setCopy(monthOfYear + 1);
                afterDateTime = afterDateTime.dayOfMonth().setCopy(dayOfMonth);
                afterDateEditText.setText(afterDateTime.toString(DateTimeFormat.shortDate()));
            }
        };

        final DatePickerDialog.OnDateSetListener beforeDateDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                beforeDateTime = beforeDateTime.year().setCopy(year);
                beforeDateTime = beforeDateTime.monthOfYear().setCopy(monthOfYear + 1);
                beforeDateTime = beforeDateTime.dayOfMonth().setCopy(dayOfMonth);
                beforeDateEditText.setText(beforeDateTime.toString(DateTimeFormat.shortDate()));
            }
        };

        afterDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ReceiptFilterActivity.this, afterDateDialog,
                        todayDateTime.getYear(),
                        todayDateTime.getMonthOfYear() - 1,
                        todayDateTime.getDayOfMonth()).show();
            }
        });

        beforeDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ReceiptFilterActivity.this, beforeDateDialog,
                        todayDateTime.getYear(),
                        todayDateTime.getMonthOfYear() - 1,
                        todayDateTime.getDayOfMonth()).show();
            }
        });

        dateEnableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    afterDateEditText.setEnabled(true);
                    beforeDateEditText.setEnabled(true);
                } else {
                    afterDateEditText.setEnabled(false);
                    beforeDateEditText.setEnabled(false);
                }
            }
        });
    }

    private void initializeLocationViews() {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationEnableCheckBox = findViewById(R.id.receipt_filter_location_enable_checkbox);
        locationSpinner = findViewById(R.id.receipt_filter_location_spinner);
        locationSpinner.setEnabled(false);

        locationArrayAdapter = new LocationArrayAdapter(this, locationList);
        locationSpinner.setAdapter(locationArrayAdapter);

        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                locationList.clear();
                locationList.addAll(locations);
                locationArrayAdapter.notifyDataSetChanged();
            }
        });

        locationEnableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    locationSpinner.setEnabled(true);
                } else {
                    locationSpinner.setEnabled(false);
                }
            }
        });
    }

    private void initializeMethodViews() {
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);
        methodEnableCheckBox = findViewById(R.id.receipt_filter_method_enable_checkbox);
        methodSpinner = findViewById(R.id.receipt_filter_method_spinner);
        methodSpinner.setEnabled(false);

        methodArrayAdapter = new MethodArrayAdapter(this, methodList);
        methodSpinner.setAdapter(methodArrayAdapter);

        methodViewModel.findAll().observe(this, new Observer<List<Method>>() {
            @Override
            public void onChanged(@Nullable List<Method> methods) {
                methodList.clear();
                methodList.addAll(methods);
                methodArrayAdapter.notifyDataSetChanged();
            }
        });

        methodEnableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    methodSpinner.setEnabled(true);
                } else {
                    methodSpinner.setEnabled(false);
                }
            }
        });
    }

    private void initializeCategoryViews() {
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryEnableCheckBox = findViewById(R.id.receipt_filter_category_enable_checkbox);
        categorySpinner = findViewById(R.id.receipt_filter_category_spinner);
        categorySpinner.setEnabled(false);

        categoryArrayAdapter = new CategoryArrayAdapter(this, categoryList);
        categorySpinner.setAdapter(categoryArrayAdapter);

        categoryViewModel.findAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                categoryList.clear();
                categoryList.addAll(categories);
                categoryArrayAdapter.notifyDataSetChanged();
            }
        });

        categoryEnableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    categorySpinner.setEnabled(true);
                } else {
                    categorySpinner.setEnabled(false);
                }
            }
        });
    }

    private void initializePriceViews() {
        priceEnableCheckBox = findViewById(R.id.receipt_filter_price_enable_checkbox);
        greaterPriceEditText = findViewById(R.id.receipt_filter_price_greater_edittext);
        lessPriceEditText = findViewById(R.id.receipt_filter_price_less_edittext);

        greaterPriceEditText.setEnabled(false);
        lessPriceEditText.setEnabled(false);

        greaterPriceEditText.addTextChangedListener
                (new NumberTextWatcher(greaterPriceEditText));

        lessPriceEditText.addTextChangedListener
                (new NumberTextWatcher(lessPriceEditText));

        priceEnableCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    greaterPriceEditText.setEnabled(true);
                    lessPriceEditText.setEnabled(true);
                } else {
                    greaterPriceEditText.setEnabled(false);
                    lessPriceEditText.setEnabled(false);
                }
            }
        });
    }

    private void initializeOrderBySpinner() {
        orderBySpinner = findViewById(R.id.receipt_filter_order_by_spinner);
        ArrayAdapter<String> orderByArrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[] { "DATE", "LOCATION", "CATEGORY", "METHOD", "SUBTOTAL" });
        orderBySpinner.setAdapter(orderByArrayAdapter);
    }

    private void initializeButtons() {
        Button cancelButton = findViewById(R.id.receipt_filter_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button searchButton = findViewById(R.id.receipt_filter_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReceiptResultActivity();
            }
        });
    }

    private void startReceiptResultActivity() {
        Intent intent = new Intent(this, ReceiptResultActivity.class);
        intent.putExtra(ReceiptResultActivity.DATE_SPECIFIED, dateEnableCheckBox.isChecked());
        intent.putExtra(ReceiptResultActivity.LOCATION_SPECIFIED, locationEnableCheckBox.isChecked());
        intent.putExtra(ReceiptResultActivity.CATEGORY_SPECIFIED, categoryEnableCheckBox.isChecked());
        intent.putExtra(ReceiptResultActivity.METHOD_SPECIFIED, methodEnableCheckBox.isChecked());
        intent.putExtra(ReceiptResultActivity.PRICE_SPECIFIED, priceEnableCheckBox.isChecked());

        RadioGroup radioGroup = findViewById(R.id.receipt_filter_order_by_radio_group);
        RadioButton selectedRadioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        intent.putExtra(ReceiptResultActivity.ORDER_BY, (String) orderBySpinner.getSelectedItem());
        intent.putExtra(ReceiptResultActivity.ORDER_METHOD, selectedRadioButton.getText().toString());

        if(dateEnableCheckBox.isChecked()) {
            intent.putExtra(ReceiptResultActivity.AFTER_DATE, afterDateTime.toString());
            intent.putExtra(ReceiptResultActivity.BEFORE_DATE, beforeDateTime.toString());
        }

        if(locationEnableCheckBox.isChecked()) {
            Location selectedLocation = (Location) locationSpinner.getSelectedItem();
            intent.putExtra(ReceiptResultActivity.SELECTED_LOCATION, selectedLocation.getLocation());
        }

        if(methodEnableCheckBox.isChecked()) {
            Method selectedMethod = (Method) methodSpinner.getSelectedItem();
            intent.putExtra(ReceiptResultActivity.SELECTED_METHOD, selectedMethod.getMethod());
        }

        if(categoryEnableCheckBox.isChecked()) {
            Category selectedCategory = (Category) categorySpinner.getSelectedItem();
            intent.putExtra(ReceiptResultActivity.SELECTED_CATEGORY, selectedCategory.getCategory());
        }

        if(priceEnableCheckBox.isChecked()) {
            intent.putExtra(
                    ReceiptResultActivity.GREATER_PRICE,
                    new StringPriceParser(greaterPriceEditText.getText().toString()).getDecimalValue());
            intent.putExtra(
                    ReceiptResultActivity.LESS_PRICE,
                    new StringPriceParser(lessPriceEditText.getText().toString()).getDecimalValue()
            );
        }

        startActivity(intent);
    }
}
