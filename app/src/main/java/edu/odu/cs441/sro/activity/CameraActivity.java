package edu.odu.cs441.sro.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import edu.odu.cs441.sro.R;
import edu.odu.cs441.sro.utility.view.CameraPreview;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Most of the codebase was copied and pasted from
 * https://developer.android.com/guide/topics/media/camera.html
 * with some modifications to suit SRO.
 *
 * We as SRO developers do not need to concern ourselves with the details of the
 * camera code too much, but you can go to Android Developer website if you are interested.
 *
 * We do need to do some error handling though.
 * See //TODO below for more information
 *
 * Michael Park
 */
public class CameraActivity extends AppCompatActivity {

    // Abstraction of Camera hardware
    private Camera mCamera;

    // Abstraction of Camera Preview (What shows on the screen before clicking "Capture")
    private CameraPreview mPreview;

    // Tag for Log.d() method. This can be removed once we replace all Logs with actual
    // Error Handling
    private final String TAG = "CAMERA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        initializeCamera();


        // Add a listener to the Capture button
        Button captureButton = (Button) findViewById(R.id.button_capture);
        captureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );
    }

    private void initializeCamera() {
        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
    }

    /**
     * Open the camera hardware and return the instance
     * @return Camera camera instance
     */
    public static Camera getCameraInstance(){
        Camera camera = null;
        try {
            camera = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return camera; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            // Create UUID for the name of the receipt and to identify the receipt
            UUID uuid = UUID.randomUUID();

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE, uuid);

            if (pictureFile == null){
                //TODO Handle this error by displaying the message in a Toast
                //TODO If this if statement was trigered it means there was a problem
                //TODO producing image file from getOutputMediaFile() method.

                Log.d(TAG, "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                //TODO Handle this error by displaying the message in a Toast
                //TODO If this if statement was trigered it means there was a problem
                //TODO finding the file path defined in pictureFile variable
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                //TODO Handle this error by displaying the message in a Toast
                //TODO If this if statement was trigered it means there was a problem
                //TODO accessing the file path defined in pictureFile variable
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }

            // We are finished, so release the camera resource.
            releaseCamera();

            // Save the image File and UUID and return them as intent data
            Intent returnData = new Intent();
            returnData.putExtra(MainActivity.MY_UUID_INTENT_IDENTIFIER, uuid);
            returnData.putExtra(MainActivity.MY_IMAGE_FILE_INTENT_IDENTIFIER, pictureFile);
            setResult(RESULT_OK, returnData);
            finish();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }



    /**
     * This method creates a file on the device that can save the picture data.
     * @param type int
     * @param uuid UUID
     * @return File image file
     */
    private File getOutputMediaFile(int type, UUID uuid){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        // Do not manually change the directory path here. Use the static variables in MainActivity!
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS),
                MainActivity.MY_SRO_MAIN_DIRECTORY +
                        File.separator +
                        MainActivity.MY_IMAGE_DIRECTORY);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                //TODO There was a problem creating a directory here. Do we not have
                //TODO WRITE_EXTERNAL_STORAGE permission granted by user already?
                //TODO Handle this error by creating a Toast and double-check the codes
                //TODO in MainActivity to make sure we request proper permission before
                //TODO opening the CameraActivity.
                Log.d("Directory", "failed to create directory");
                return null;
            }
        }

        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() +
                    File.separator + uuid.toString() + ".jpg");
        } else {
            // We do not deal with video type files here!!!
            return null;
        }

        return mediaFile;
    }



    /**
     * Release the camera so other applications can use it.
     * Call this in onPause() method.
     */
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
