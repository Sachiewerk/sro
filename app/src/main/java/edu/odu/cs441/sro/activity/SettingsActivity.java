package edu.odu.cs441.sro.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class SettingsActivity extends AppCompatActivity {
    ReceiptViewModel receiptViewModel;
    LocationViewModel locationViewModel;
    CategoryViewModel categoryViewModel;
    MethodViewModel methodViewModel;

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
        setSpinner();


        Button addMethodButton = (Button) findViewById(R.id.addMethodButton);
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addOrDeleteMethod();
            }
        });

        Button addLocationButton = (Button) findViewById(R.id.addLocationButton);
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        final Button backToMainButton = (Button) findViewById(R.id.backButton);
        {
            backToMainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    backToMain();
                }
            });
        }

    }

    public void addOrDeleteMethod() {


        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        final Button addMethodButton = (Button) mView.findViewById(R.id.addSomethingButton);
        final ListView addMethodListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText methodEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        //=================================================================================
        final ArrayList<String> methodList = new ArrayList<>(mMethodList);

        mMethodAutoCompleteAdapter = new ArrayAdapter(
                getApplicationContext(), android.R.layout.simple_list_item_1, methodList);
        ListView myListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        myListView.setAdapter(mMethodAutoCompleteAdapter);
        //==================================================================================

        methodViewModel.findAll().observe(this, new Observer<List<Method>>() {
            @Override
            public void onChanged(@Nullable List<Method> methods) {
                methodList.clear();
                for(Method Method : methods) {
                    methodList.add(Method.getMethod());
                    mMethodAutoCompleteAdapter.notifyDataSetChanged();

                }
            }
        });
        
        LocationLabel.setText("Method");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                    Method newMethod = new Method(methodEntryBox.getText().toString());
                    methodViewModel.insert(newMethod);
                    methodEntryBox.setText("");

                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Method",Toast.LENGTH_LONG).show();
            }
        });
        addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toBeDeleted = (String)parent.getItemAtPosition(position);
                methodViewModel.delete(new Method(toBeDeleted));
                addMethodListView.invalidateViews();
                Toast.makeText(getApplicationContext(),toBeDeleted + "Deleted",Toast.LENGTH_LONG);
                methodEntryBox.setText("");
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    public void addOrDeleteCategory() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        final Button addCategoryButton = (Button) mView.findViewById(R.id.addSomethingButton);
        final ListView addCategory = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText categoryEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView CategoryLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        categoryEntryBox.setHint("Enter the Category Here");

        final ArrayList<String> categoryList = new ArrayList(mCategoryList);

       //========================================================================
        mCategoryAutoCompleteAdapter = new ArrayAdapter(
                getApplicationContext(), android.R.layout.simple_list_item_1, categoryList);
        ListView myListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        myListView.setAdapter(mCategoryAutoCompleteAdapter);
        //=======================================================================

        categoryViewModel.findAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                categoryList.clear();
                for(Category category : categories) {
                    categoryList.add(category.getCategory());
                    mCategoryAutoCompleteAdapter.notifyDataSetChanged();

                }
            }
        });


        addCategory.setAdapter(mCategoryAutoCompleteAdapter);

        CategoryLabel.setText("Category");
        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!categoryEntryBox.getText().toString().isEmpty()) {
                    Category newCategory = new Category(categoryEntryBox.getText().toString());
                    categoryViewModel.insert(newCategory);
                    addCategory.invalidateViews();
                    categoryEntryBox.setText("");

                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Category",Toast.LENGTH_LONG).show();
            }
        });

        addCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Category toDelete = new Category(mCategoryList.get(position));
                Log.d("CATEGORY", toDelete.getCategory());
                categoryViewModel.delete(toDelete);
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
        final Button addLocationButton = (Button) mView.findViewById(R.id.addSomethingButton);
        final ListView addLocationListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText LocationEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        LocationEntryBox.setHint("Enter the Location to be added here");
        final ArrayList<String> locationList = new ArrayList(mLocationList);
        //==========================================================================================
        mLocationAutoCompleteAdapter = new ArrayAdapter(
                getApplicationContext(), android.R.layout.simple_list_item_1, locationList);

        ListView myListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        myListView.setAdapter(mLocationAutoCompleteAdapter);
        //==========================================================================================

        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locations) {
                locationList.clear();
                for(Location location : locations) {
                    locationList.add(location.getLocation());
                    mLocationAutoCompleteAdapter.notifyDataSetChanged();

                }
            }
        });
        
        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {

            public void onChanged(@Nullable List<Location> locations) {
                locationList.clear();
                for(Location location : locations) {
                    locationList.add(location.getLocation());
                    mLocationAutoCompleteAdapter.notifyDataSetChanged();

                }

            }
        });

        LocationLabel.setText("Location");
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LocationEntryBox.getText().toString().isEmpty()) {
                   Location newLocation = new Location(LocationEntryBox.getText().toString());
                   locationViewModel.insert(newLocation);
                    LocationEntryBox.setText("");
                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Location",Toast.LENGTH_LONG).show();
            }
        });

        addLocationListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               String toBeDeleted = (String)parent.getItemAtPosition(position);
               Location toDelete = new Location(toBeDeleted);
               locationViewModel.delete(toDelete);
               Toast.makeText(getApplicationContext(),toBeDeleted + "Deleted",Toast.LENGTH_LONG);
               return false;
           }
       });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    public void initializeLists()
    {

        receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);

        mLocationList = new ArrayList<>();
        mMethodList = new ArrayList<>();
        mCategoryList = new ArrayList<>();

        final ArrayAdapter newCategoryAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mCategoryList);
        final ArrayAdapter newLocationAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mLocationList);
        final ArrayAdapter newMethodAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mMethodList);

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

        ListView newCategoryListView = (ListView)findViewById(R.id.CategoryDisplay);
        ListView newLocationListView = (ListView)findViewById(R.id.LocationDisplay);
        ListView newMethodListView = (ListView)findViewById(R.id.MethodDisplay);

        newCategoryListView.setAdapter(newCategoryAdapter);
        newLocationListView.setAdapter(newLocationAdapter);
        newMethodListView.setAdapter(newMethodAdapter);


    }

    public void backToMain()
    {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
public void setSpinner()
{
    Spinner mySpinner = (Spinner)findViewById(R.id.userChoiceSpinner);
    ArrayAdapter spinnerAdapter = new ArrayAdapter(
            getApplicationContext(), android.R.layout.select_dialog_singlechoice,sortByList);

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
}


