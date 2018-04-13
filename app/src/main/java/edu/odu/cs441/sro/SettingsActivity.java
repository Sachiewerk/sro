package edu.odu.cs441.sro.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Location;
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

    private ArrayList<String> mLocationList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


       /* receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);
        mLocationList = new ArrayList<>();
        addLocation();

        ///listener for the add method button
        Button addMethodButton = (Button) findViewById(R.id.addMethodButton);
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrDeleteMethod();
            }
        });

        ///listener for the addLocationButton
        Button addLocationButton = (Button) findViewById(R.id.addLocationButton);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SettingsActivity.this,"Location",Toast.LENGTH_LONG).show();
                addOrDeleteLocation();
            }
        });

        Button addCategoryButton = (Button)findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrDeleteCategory();
            }
        });


        //add onClickListeners

        //add ListView with onItemClickListeners

        //add backButton
*/
    }

    public ArrayList<String> getMySortByList(ArrayList<String> myList) {
        myList.add("PRICE_DESC");
        myList.add("PRICE_ASCE");
        myList.add("DATE_ASCE");
        myList.add("DATE_DESC");
        return myList;
    }

    public void addLocation() {

        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);

        mLocationList = new ArrayList<>();
       /* locationViewModel.findAll().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                mLocationList.clear();
                for(Location location : locations) {
                    mLocationList.add(location.getLocation());

                }

            }
        });
*/
        Location newLocation = new Location("LOGAN");
        locationViewModel.insert(newLocation);
        mLocationList.add(newLocation.getLocation());
        ;
        ArrayAdapter<String> mLocationAutoCompleteAdapter;
        mLocationAutoCompleteAdapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, mLocationList);
        ListView myListView = (ListView) findViewById(R.id.sortByMenu);

        myListView.setAdapter(mLocationAutoCompleteAdapter);


    }

    public void addCategory() {
        Category newCategory = new Category("Freedom");
        categoryViewModel.insert(newCategory);

    }


    public String changeSortOrder() {
        return "";
    }

    private void initializeReceipts() {
        categoryViewModel.insert(new Category("Grocery"));
        categoryViewModel.insert(new Category("Electronics"));
        categoryViewModel.insert(new Category("Furniture"));
        categoryViewModel.insert(new Category("Restaurant"));
        categoryViewModel.insert(new Category("Entertainment"));

        locationViewModel.insert(new Location("Walmart"));
        locationViewModel.insert(new Location("Pizzahut"));
        locationViewModel.insert(new Location("Lowe's"));
        locationViewModel.insert(new Location("McDonald's"));
        locationViewModel.insert(new Location("Foodlion"));

        methodViewModel.insert(new Method("Cash"));
        methodViewModel.insert(new Method("Credit Card"));
        methodViewModel.insert(new Method("Money Order"));
        methodViewModel.insert(new Method("Check"));
        methodViewModel.insert(new Method("Debit Card"));
        methodViewModel.insert(new Method("Paypal"));
    }

    public void addOrDeleteMethod() {
        ArrayList methodList = new ArrayList() ;
        ArrayAdapter myAdapter;
        final ArrayList myMethodList = new ArrayList();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        Button addMethodButton = (Button) mView.findViewById(R.id.addSomethingButton);
        final ListView addMethodListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText methodEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);

        ArrayAdapter myMethodAdapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice, methodList);
                    addMethodListView.setAdapter(myMethodAdapter);

        LocationLabel.setText("Method");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                    myMethodList.add(methodEntryBox.getText().toString());
                    addMethodListView.invalidateViews();
                    ///add the text from the box to the list
                    ///create a new Method and add it to the data base
                }
            }
        });

        addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ///remove the item from the list, and from the database
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    public void addOrDeleteCategory() {
        ///need a list of methods
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        Button addMethodButton = (Button) mView.findViewById(R.id.addSomethingButton);
        ListView addMethodListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText methodEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        LocationLabel.setText("Category");
        methodEntryBox.setHint("Enter the New Category Here");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                    ///add the text from the box to the list
                    ///create a new Method and add it to the data base
                }
            }
        });

        addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ///remove the item from the list, and from the database
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void addOrDeleteLocation() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        Button addMethodButton = (Button) mView.findViewById(R.id.addSomethingButton);
        ListView addMethodListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText methodEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        LocationLabel.setText("Location");
        methodEntryBox.setHint("Enter the New Location Here");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                    ///add the text from the box to the list
                    ///create a new Method and add it to the data base
                }
            }
        });

        addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ///remove the item from the list, and from the database
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();


    }
}
