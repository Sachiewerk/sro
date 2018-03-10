package edu.odu.cs441.sro;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.util.Date;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private final int MY_CAMERA_REQUEST_CODE = 100;
    private final int MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 200;

    private final int MY_CAMERA_ACTIVITY_REQUEST_CODE = 1000;

    public static final String MY_UUID_INTENT_IDENTIFIER = "UUID";
    public static final String MY_DATE_INTENT_IDENTIFIER = "DATE";
    public static final String MY_IMAGE_FILE_INTENT_IDENTIFIER = "IMAGE_FILE";

    public static final String MY_SRO_MAIN_DIRECTORY = "SRO";
    public static final String MY_IMAGE_DIRECTORY = "Images";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.main_toolbar));
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_view_headline_black_24dp);
        actionbar.setDisplayShowCustomEnabled(true);

        initializeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.add_button:
                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeDrawers() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        initializeNavigationView();
        initializeAddView();
    }

    private void initializeNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();

                    switch (menuItem.getItemId()) {
                        case R.id.nav_existing_receipt:

                            return true;

                        case R.id.nav_existing_subscription:

                            return true;

                        case R.id.nav_setting:

                            return true;
                    }
                    return true;
                }
            }
        );
    }

    private void initializeAddView() {
        NavigationView addView = findViewById(R.id.add_view);

        addView.setNavigationItemSelectedListener(
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem) {
                    // set item as selected to persist highlight
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();

                    switch (menuItem.getItemId()) {
                        case R.id.add_photo_receipt:
                            if(checkCameraHardware(getApplicationContext())) {
                                Log.d("ADDBUTTON", "PRESSED!");
                                startCameraActivity();


                            } else {
                                //TODO Camera was not found on this device. Create no photo receipt
                            }
                            return true;

                        case R.id.add_nophoto_receipt:

                            return true;

                        case R.id.add_subscription:

                            return true;
                    }
                    return true;
                }
            }
        );
    }

    private boolean checkForCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            return true;
        }

        return false;
    }

    private boolean checkForWriteExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            return true;
        }

        return false;
    }

    public void startCameraActivity() {
        if(checkForCameraPermission() && checkForWriteExternalStoragePermission()) {
            Log.d("BOTH PERMISSIONS", "GRANTED");
            Intent intent = new Intent(this, CameraActivity.class);
            startActivityForResult(intent, MY_CAMERA_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == MY_CAMERA_ACTIVITY_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                UUID uuid = (UUID)data.getSerializableExtra(MY_UUID_INTENT_IDENTIFIER);
                Date date = (Date)data.getSerializableExtra(MY_DATE_INTENT_IDENTIFIER);
                File imageFile = (File)data.getSerializableExtra(MY_IMAGE_FILE_INTENT_IDENTIFIER);

                Log.d("UUID IS", uuid.toString());
                Log.d("DATE IS", date.toString());
                Log.d("FILENAME IS", imageFile.getAbsolutePath());

                Intent intent = new Intent(this, PhotoReceiptAddActivity.class);
                intent.putExtra(MY_IMAGE_FILE_INTENT_IDENTIFIER, imageFile);

                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_CAMERA_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startCameraActivity();

                } else {
                    // Permission is denied
                }

                return;
            }

            case MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startCameraActivity();

                } else {
                    // Permission is denied
                }

                return;
            }
        }
    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
}
