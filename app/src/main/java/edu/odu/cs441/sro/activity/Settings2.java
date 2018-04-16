package edu.odu.cs441.sro.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.dao.record.ReceiptDao;
import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Method;
import edu.odu.cs441.sro.entity.record.Receipt;
import edu.odu.cs441.sro.viewmodel.metadata.CategoryViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.LocationViewModel;
import edu.odu.cs441.sro.viewmodel.metadata.MethodViewModel;
import edu.odu.cs441.sro.viewmodel.record.ReceiptViewModel;

public class Settings2 extends AppCompatActivity {
    ReceiptViewModel receiptViewModel;
    LocationViewModel locationViewModel;
    CategoryViewModel categoryViewModel;
    MethodViewModel methodViewModel;

    private ArrayList<Method> newMethodList;
    private ArrayList<Location>newLocationList;
    private ArrayList<Category>newCategoryList;

    private ArrayList<String>sortByList;


    private ArrayAdapter<String> mLocationAutoCompleteAdapter;
    private ArrayAdapter<String> mCategoryAutoCompleteAdapter;
    private ArrayAdapter<String> mMethodAutoCompleteAdapter;

    private ArrayList mMethodList ;
    private ArrayList mCategoryList ;
    private ArrayList<String> mLocationList;

    private String sortQuery ;
    //private ArrayList mCategoryList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
      /*  receiptViewModel = ViewModelProviders.of(this).get(ReceiptViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        methodViewModel = ViewModelProviders.of(this).get(MethodViewModel.class);*/
        initializeLists();
        setSpinner();







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



    public void changeSortOrder() {



    }



    public void addOrDeleteMethod() {


        final ArrayList myMethodList = new ArrayList();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings2.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        final Button addMethodButton = (Button) mView.findViewById(R.id.addSomethingButton);
        final ListView addMethodListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText methodEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        //=================================================================================
       // mLocationList = new ArrayList<>();
        //mMethodList = new ArrayList<>();



       ArrayAdapter adapter = new ArrayAdapter(
                this, android.R.layout.select_dialog_singlechoice, mMethodList); ;
        mLocationAutoCompleteAdapter = new ArrayAdapter(
                getApplicationContext(), android.R.layout.select_dialog_singlechoice, mMethodList);
        ListView myListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        myListView.setAdapter(mLocationAutoCompleteAdapter);

        //==================================================================================

        LocationLabel.setText("Method");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                    Method newMethod = new Method(methodEntryBox.getText().toString());
                    methodViewModel.insert(newMethod);
                    mMethodList.add(newMethod.getMethod());
                    methodEntryBox.setText("");

                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Method",Toast.LENGTH_LONG).show();
            }
        });
        addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toBeDeleted = (String)parent.getItemAtPosition(position);
                //remove from database
                mMethodList.remove(position);
                addMethodListView.invalidateViews();
                methodEntryBox.setText("");
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    public void addOrDeleteCategory() {
        ///need a list of methods

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings2.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        final Button addMethodButton = (Button) mView.findViewById(R.id.addSomethingButton);
        final ListView addMethodListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText methodEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        methodEntryBox.setHint("Enter the Category Here");
        final ArrayList newCategoryList = new ArrayList();


       final ArrayAdapter myMethodAdapter = new ArrayAdapter<>(
                this, android.R.layout.select_dialog_singlechoice,newCategoryList);

       //========================================================================

        mCategoryAutoCompleteAdapter = new ArrayAdapter(
                getApplicationContext(), android.R.layout.select_dialog_singlechoice, mCategoryList);
        ListView myListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        myListView.setAdapter(mLocationAutoCompleteAdapter);


        //=======================================================================




        addMethodListView.setAdapter(mCategoryAutoCompleteAdapter);

        LocationLabel.setText("Category ");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                    Category newCategory = new Category(methodEntryBox.getText().toString());
                    categoryViewModel.insert(newCategory);
                    mCategoryList.add(methodEntryBox.getText().toString());
                    newCategoryList.add(newCategory.getCategory());
                    addMethodListView.invalidateViews();
                    methodEntryBox.setText("");

                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Category",Toast.LENGTH_LONG).show();
            }
        });

        addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toBeDeleted = (String)parent.getItemAtPosition(position);
                mCategoryList.remove(position);
                methodEntryBox.setText("");
                addMethodListView.invalidateViews();
                return false;
            }
        });

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    public void addOrDeleteLocation() {
        ArrayList methodList = new ArrayList() ;
        final ArrayAdapter myAdapter;
        final ArrayList myMethodList = new ArrayList();
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Settings2.this);
        final View mView = getLayoutInflater().inflate(R.layout.addmethodlayout, null);
        final Button addMethodButton = (Button) mView.findViewById(R.id.addSomethingButton);
        final ListView addMethodListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        final EditText methodEntryBox = (EditText) mView.findViewById(R.id.addMethodBox);
        final TextView LocationLabel = (TextView)mView.findViewById(R.id.addMethodLabel);
        methodEntryBox.setHint("Enter the Location to be added here");


        //==========================================================================================
        mLocationAutoCompleteAdapter = new ArrayAdapter(
                getApplicationContext(), android.R.layout.select_dialog_singlechoice, mLocationList);
        ListView myListView = (ListView) mView.findViewById(R.id.addSomethingListview);
        myListView.setAdapter(mLocationAutoCompleteAdapter);
        //==========================================================================================


        LocationLabel.setText("Location");
        addMethodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!methodEntryBox.getText().toString().isEmpty()) {
                   Location newLocation = new Location(methodEntryBox.getText().toString());
                   locationViewModel.insert(newLocation);
                   mLocationList.add(methodEntryBox.getText().toString());

                }
                else Toast.makeText(getApplicationContext(),"Please Enter a Location",Toast.LENGTH_LONG).show();
            }
        });

       addMethodListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               String toBeDeleted = (String)parent.getItemAtPosition(position);
               mLocationList.remove(toBeDeleted);
               addMethodListView.invalidateViews();
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

        locationViewModel.findAll().observe(this, new Observer<List<Location>>() {

            public void onChanged(@Nullable List<Location> locations) {
                mLocationList.clear();
                for(Location location : locations) {
                    mLocationList.add(location.getLocation());


                }

            }
        });


        categoryViewModel.findAll().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                mCategoryList.clear();
                for(Category category : categories) {
                    mCategoryList.add(category.getCategory());
                }
            }
        });

        methodViewModel.findAll().observe(this, new Observer<List<Method>>() {
            @Override
            public void onChanged(@Nullable List<Method> methods) {
                mMethodList.clear();
                for(Method method : methods) {
                    mMethodList.add(method.getMethod());
                }
            }
        });

        sortByList = new ArrayList<>();
        sortByList.add("DATE - ASCENDING");
        sortByList.add("DATE - DESCENDING");

        ArrayAdapter newCategoryAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mCategoryList);
        ArrayAdapter newLocationAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mLocationList);
        ArrayAdapter newMethodAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mMethodList);

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


  /*  mySpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position)
            {
                case 1: Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
                break ;
                case 2: Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                    break ;
            }
        }
    });*/
  mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          switch (position)
          {
              case 0: Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_LONG).show();
                  break ;
              case 1: Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_LONG).show();
                  break ;
          }

      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
  });
}

}


