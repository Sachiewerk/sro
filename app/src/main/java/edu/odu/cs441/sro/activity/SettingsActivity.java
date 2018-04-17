package edu.odu.cs441.sro.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.viewmodel.metadata.CategoryViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.LocationViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.MethodViewModel;

public class SettingsActivity extends AppCompatActivity {
    private LocationViewModel locationViewModel;
    private CategoryViewModel categoryViewModel;
    private MethodViewModel methodViewModel;

    private ArrayList<String>sortByList;

    private ArrayAdapter<String> mLocationAutoCompleteAdapter;
    private ArrayAdapter<String> mCategoryAutoCompleteAdapter;
    private ArrayAdapter<String> mMethodAutoCompleteAdapter;

    private ArrayList<String> mMethodList ;
    private ArrayList<String> mCategoryList ;
    private ArrayList<String> mLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeLists();
        initializeSpinner();

        Button addMethodButton = findViewById(R.id.addMethodButton);
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMethodEditDialog();
            }
        });

        Button addLocationButton = findViewById(R.id.addLocationButton);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLocationEditDialog();
            }
        });

        Button addCategoryButton = findViewById(R.id.addCategoryButton);
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCategoryEditDialog();
            }
        });

        final Button backToMainButton = findViewById(R.id.backButton);
        {
            backToMainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public void initializeLists()
    {
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);

        mLocationList = new ArrayList<>();
        mMethodList = new ArrayList<>();
        mCategoryList = new ArrayList<>();

        final ArrayAdapter<String> newCategoryAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mCategoryList);
        final ArrayAdapter<String> newLocationAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mLocationList);
        final ArrayAdapter<String> newMethodAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mMethodList);

        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {

            public void onChanged(@Nullable List<Location> locations) {
                mLocationList.clear();
                for(Location location : locations) {
                    mLocationList.add(location.getLocation());
                    newLocationAdapter.notifyDataSetChanged();
                }
            }
        });

        categoryViewModel.findAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                mCategoryList.clear();
                for(Category category : categories) {
                    mCategoryList.add(category.getCategory());
                    newCategoryAdapter.notifyDataSetChanged();
                }
            }
        });

        methodViewModel.findAll().observe(this, new Observer<List<Method>>() {
            @Override
            public void onChanged(@Nullable List<Method> methods) {
                mMethodList.clear();
                for(Method method : methods) {
                    mMethodList.add(method.getMethod());
                    newMethodAdapter.notifyDataSetChanged();
                }
            }
        });

        sortByList = new ArrayList<>();
        sortByList.add("DATE - ASCENDING");
        sortByList.add("DATE - DESCENDING");
        sortByList.add("LOCATION");
        sortByList.add("CATEGORY");
        sortByList.add("METHOD");

        ListView newCategoryListView = findViewById(R.id.CategoryDisplay);
        ListView newLocationListView = findViewById(R.id.LocationDisplay);
        ListView newMethodListView = findViewById(R.id.MethodDisplay);

        newCategoryListView.setAdapter(newCategoryAdapter);
        newLocationListView.setAdapter(newLocationAdapter);
        newMethodListView.setAdapter(newMethodAdapter);
    }

    public void initializeSpinner()
    {
        Spinner mySpinner = findViewById(R.id.userChoiceSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.select_dialog_singlechoice, sortByList);

        mySpinner.setAdapter(spinnerAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0: Toast.makeText(getApplicationContext(),"Set to Ascending",Toast.LENGTH_LONG).show();

                        break ;
                    case 1: Toast.makeText(getApplicationContext(),"Set to Descinding",Toast.LENGTH_LONG).show();
                        break ;
                    case 2:  Toast.makeText(getApplicationContext(),"Set to Location",Toast.LENGTH_LONG).show();
                        break ;
                    case 3:  Toast.makeText(getApplicationContext(),"Set to Category",Toast.LENGTH_LONG).show();
                        break ;
                    case 4: Toast.makeText(getApplicationContext(),"Set to Method",Toast.LENGTH_LONG).show();
                        break ;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void startCategoryEditDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.edit_metadata_dialog, null);

        final EditText categoryEntryBox = mView.findViewById(R.id.edit_metadata_dialog_value_edittext);
        final TextView LocationLabel = mView.findViewById(R.id.edit_metadata_dialog_label_textview);
        mCategoryAutoCompleteAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, mCategoryList);


        categoryViewModel.findAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                mCategoryList.clear();
                for(Category Category : categories) {
                    mCategoryList.add(Category.getCategory());
                    mCategoryAutoCompleteAdapter.notifyDataSetChanged();

                }
            }
        });
        final Button addCategoryButton = mView.findViewById(R.id.edit_metadata_dialog_add_metadata_button);
        final ListView addCategoryListView = mView.findViewById(R.id.edit_metadata_dialog_metadata_listview);
        addCategoryListView.setAdapter(mCategoryAutoCompleteAdapter);

        LocationLabel.setText("Category");
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!categoryEntryBox.getText().toString().isEmpty()) {
                    Category newCategory = new Category(categoryEntryBox.getText().toString());
                    categoryViewModel.insert(newCategory);
                    categoryEntryBox.setText("");
                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Category" ,Toast.LENGTH_LONG).show();
            }
        });
        addCategoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toBeDeleted = (String)parent.getItemAtPosition(position);
                categoryViewModel.delete(new Category(toBeDeleted));
                Toast.makeText(getApplicationContext(),toBeDeleted + "Deleted",Toast.LENGTH_LONG).show();
                categoryEntryBox.setText("");
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void startMethodEditDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.edit_metadata_dialog, null);

        final EditText methodEntryBox = mView.findViewById(R.id.edit_metadata_dialog_value_edittext);
        final TextView LocationLabel = mView.findViewById(R.id.edit_metadata_dialog_label_textview);
        mMethodAutoCompleteAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, mMethodList);


        methodViewModel.findAll().observe(this, new Observer<List<Method>>() {
            @Override
            public void onChanged(@Nullable List<Method> methods) {
                mMethodList.clear();
                for(Method Method : methods) {
                    mMethodList.add(Method.getMethod());
                    mMethodAutoCompleteAdapter.notifyDataSetChanged();

                }
            }
        });
        final Button addMethodButton = mView.findViewById(R.id.edit_metadata_dialog_add_metadata_button);
        final ListView addMethodListView = mView.findViewById(R.id.edit_metadata_dialog_metadata_listview);
        addMethodListView.setAdapter(mMethodAutoCompleteAdapter);

        LocationLabel.setText("Method");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                    Method newMethod = new Method(methodEntryBox.getText().toString());
                    methodViewModel.insert(newMethod);
                    methodEntryBox.setText("");
                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Method" ,Toast.LENGTH_LONG).show();
            }
        });
        addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toBeDeleted = (String)parent.getItemAtPosition(position);
                methodViewModel.delete(new Method(toBeDeleted));
                Toast.makeText(getApplicationContext(),toBeDeleted + "Deleted",Toast.LENGTH_LONG).show();
                methodEntryBox.setText("");
                return false;
            }
        });
        
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void startLocationEditDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.edit_metadata_dialog, null);

        final EditText locationEntryBox = mView.findViewById(R.id.edit_metadata_dialog_value_edittext);
        final TextView LocationLabel = mView.findViewById(R.id.edit_metadata_dialog_label_textview);
        mLocationAutoCompleteAdapter = new ArrayAdapter<>(
                getApplicationContext(), android.R.layout.simple_list_item_1, mLocationList);


        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                mLocationList.clear();
                for(Location Location : locations) {
                    mLocationList.add(Location.getLocation());
                    mLocationAutoCompleteAdapter.notifyDataSetChanged();

                }
            }
        });
        final Button addLocationButton = mView.findViewById(R.id.edit_metadata_dialog_add_metadata_button);
        final ListView addLocationListView = mView.findViewById(R.id.edit_metadata_dialog_metadata_listview);
        addLocationListView.setAdapter(mLocationAutoCompleteAdapter);

        LocationLabel.setText("Location");
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!locationEntryBox.getText().toString().isEmpty()) {
                    Location newLocation = new Location(locationEntryBox.getText().toString());
                    locationViewModel.insert(newLocation);
                    locationEntryBox.setText("");
                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Location" ,Toast.LENGTH_LONG).show();
            }
        });
        addLocationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toBeDeleted = (String)parent.getItemAtPosition(position);
                locationViewModel.delete(new Location(toBeDeleted));
                Toast.makeText(getApplicationContext(),toBeDeleted + " Deleted",Toast.LENGTH_LONG).show();
                locationEntryBox.setText("");
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }
}


