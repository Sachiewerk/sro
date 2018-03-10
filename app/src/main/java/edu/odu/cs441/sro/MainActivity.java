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

/**
 * This is the main Activity of this application.
 */
public class MainActivity extends AppCompatActivity {

    // Navigation Drawer
    private DrawerLayout mDrawerLayout;

    // Request codes for user permissions
    private final int MY_CAMERA_REQUEST_CODE = 100;
    private final int MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 200;

    // Request code for child activity
    private final int MY_CAMERA_ACTIVITY_REQUEST_CODE = 1000;
    private final int MY_PHOTO_RECEIPT_ADD_ACTIVITY_REQUEST_CODE = 2000;

    // Identifiers for Intent data
    public static final String MY_UUID_INTENT_IDENTIFIER = "UUID";
    public static final String MY_DATE_INTENT_IDENTIFIER = "DATE";
    public static final String MY_IMAGE_FILE_INTENT_IDENTIFIER = "IMAGE_FILE";

    // File Directory names
    public static final String MY_SRO_MAIN_DIRECTORY = "SRO";
    public static final String MY_IMAGE_DIRECTORY = "Images";
    public static final String MY_RECEIPT_DIRECTORY = "Data";


    /**
     * This method is invoked when this Activity is first created.
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add predefined toolbar layout that has the App title and Drawer buttons
        // as the action bar
        setSupportActionBar((Toolbar)findViewById(R.id.main_toolbar));

        // Get the action bar
        ActionBar actionbar = getSupportActionBar();

        // Enable the home button and display it. Even though this is a home button, it will
        // be customized.
        actionbar.setDisplayHomeAsUpEnabled(true);

        // Change the default icon of the home button to the predefined headline icon
        actionbar.setHomeAsUpIndicator(R.drawable.ic_view_headline_black_24dp);
        actionbar.setDisplayShowCustomEnabled(true);

        // Initialize Custom Navigation Drawers
        initializeDrawers();
    }

    /**
     * Initialize the Navigation Drawers and add Event Handlers
     */
    private void initializeDrawers() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        initializeNavigationView();
        initializeAddView();
    }

    /**
     * Initialize the Navigation View. This is the left-hand side navigation that shows
     * main menus.
     *
     * Menu items are
     * 1. Manage Receipts
     * 2. Manage Subscriptions
     * 3. Settings
     */
    private void initializeNavigationView() {
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Event handlers when a menu item is selected
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

    /**
     * Initialize the Add Navigation View. This is the right-hand side navigation that shows
     * menus for adding new items.
     *
     * Menu items are
     * 1. Add Photo Receipt
     * 2. Add Receipt without Photo
     * 3. Add Subscription
     */
    private void initializeAddView() {
        NavigationView addView = findViewById(R.id.add_view);

        // Event handlers when a menu item is selected
        addView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);

                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Perform action depending on which item user selected
                        switch (menuItem.getItemId()) {
                            case R.id.add_photo_receipt:
                                if(checkCameraHardware(getApplicationContext())) {
                                    startCameraActivity();


                                } else {
                                    //TODO Camera was not found on this device. Create no photo
                                    //TODO receipt instead
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

    /**
     * Inflate the Toolbar menu when it is created
     * @param menu Menu
     * @return boolean true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    /**
     * When a action bar menu is selected open the corresponding nagivation drawer
     * @param item MenuItem
     * @return boolean true
     */
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


    public void startCameraActivity() {

        // Start the Camera activity after Camera and Write External Storage permissions
        // have been granted.
        if(checkForCameraPermission() && checkForWriteExternalStoragePermission()) {
            Intent intent = new Intent(this, CameraActivity.class);
            startActivityForResult(intent, MY_CAMERA_ACTIVITY_REQUEST_CODE);
        }
    }


    /**
     * This method is invoked when an Activity started for result returns a result
     * @param requestCode int
     * @param resultCode int
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check if the request code is for the Camera Activity
        if (requestCode == MY_CAMERA_ACTIVITY_REQUEST_CODE) {

            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                // Receive the UUIID, Created Date, and the Image file
                UUID uuid = (UUID)data.getSerializableExtra(MY_UUID_INTENT_IDENTIFIER);
                File imageFile = (File)data.getSerializableExtra(MY_IMAGE_FILE_INTENT_IDENTIFIER);

                // Add UUID and Image file data to the intent
                Intent intent = new Intent(this, PhotoReceiptAddActivity.class);
                intent.putExtra(MY_IMAGE_FILE_INTENT_IDENTIFIER, imageFile);
                intent.putExtra(MY_UUID_INTENT_IDENTIFIER, uuid);

                // Start PhotoReceiptAddActivity
                startActivityForResult(intent, MY_PHOTO_RECEIPT_ADD_ACTIVITY_REQUEST_CODE);
            }
            // Unsuccessful Result
            else {
                //TODO what should we do if the CameraActivity returns bad result?
                //TODO Maybe image file was not successfully saved or camera stopped working.
            }
        }

        // Check if the request code is for the PhotoReceiptAddActivity
        if(requestCode == MY_PHOTO_RECEIPT_ADD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //TODO Receive the Receipt object and save the receipt
            }
        }
    }

    /**
     * Checks to see if the device has camera hardware
     *
     * @param context Context
     * @return boolean true if the camera exists, otherwise false
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            // this device has a camera
            return true;
        }

        // no camera on this device
        return false;
    }

    /**
     * This method is invoked when user grants or denies requested permission.
     *
     * @param requestCode int
     * @param permissions String[]
     * @param grantResults int[]
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            // Grant result came back for CAMERA_REQUEST, check to see if it has been granted.
            case MY_CAMERA_REQUEST_CODE: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Request was granted, so attempt to start CameraActivity again
                    startCameraActivity();

                } else {
                    //TODO Permission is denied. You are not able to use the camera, so create
                    //TODO a receipt with no photo.
                }

                return;
            }

            // Grant result came back for WRITE_EXTERNAL_STORAGE, check to see if it has been granted.
            case MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE:  {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Request was granted, so attempt to start CameraActivity again
                    startCameraActivity();

                } else {
                    //TODO Permission is denied. You are not able to save any data on user's SD card.
                    //TODO Go ahead and store all information in its own private internal storage.
                }

                return;
            }
        }
    }

    /**
     * Checks to see if CAMERA_Permission is granted and if not, requests user for permission.
     * Since permission request works asynchronously, this method will still return false regardless
     * of user grants permission or not if the permission was not already granted at the time of
     * calling this method.
     *
     * @return boolean true if the camera permission already has been granted.
     */
    private boolean checkForCameraPermission() {

        // Check if the permission is already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Check to see if this permission requires an explicit explanation to user
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                //TODO Show an explanation to the user *asynchronously* -- don't block
                //TODO this thread waiting for the user's response! After the user
                //TODO sees the explanation, try again to request the permission.

            }
            // If the permission does not require additional explanation, go ahead and request it
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);
            }

            // Return false since the permission was not initially granted
            // The action performing method can call this again and hopefully, permission is
            // already granted next time.
            return false;
        } else {
            // Return true since the permission was initially granted
            return true;
        }
    }

    /**
     * Checks to see if WRITE_EXTERNAL_STORAGE permission is granted and if not,
     * requests user for permission.
     * Since permission request works asynchronously, this method will still return false regardless
     * of user grants permission or not if the permission was not already granted at the time of
     * calling this method.
     *
     * @return boolean true if the camera permission already has been granted.
     */
    private boolean checkForWriteExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            // Check to see if this permission requires an explicit explanation to user
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                //TODO Show an explanation to the user *asynchronously* -- don't block
                //TODO  this thread waiting for the user's response! After the user
                //TODO  sees the explanation, try again to request the permission.

            }
            // If the permission does not require additional explanation, go ahead and request it
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);

            }

            // Return false since the permission was not initially granted
            // The action performing method can call this again and hopefully, permission is
            // already granted next time.
            return false;
        } else {
            // Return true since the permission was initially granted
            return true;
        }
    }
}
