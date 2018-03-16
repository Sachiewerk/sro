package edu.odu.cs441.sro.controller;

import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import edu.odu.cs441.sro.MainActivity;
import edu.odu.cs441.sro.model.metadata.Category;
import edu.odu.cs441.sro.model.metadata.Location;
import edu.odu.cs441.sro.model.metadata.Method;

/**
 * Created by michael on 3/12/18.
 *
 */
public class MetadataController implements Serializable {

    public static final String MY_LOCATION_DATA_FILE_NAME = "6d7faba8-4b0d-453c-84d0-2b73bb4f92ff";
    public static final String MY_CATEGORY_DATA_FILE_NAME = "2561998f-0d98-482e-c8ad-72afeee65331";
    public static final String MY_METHOD_DATA_FILE_NAME = "b21fe980-c5af-431e-d979-0dafa9f6d3a2";

    private File mMethodDataPath;
    private File mLocationDataPath;
    private File mCategoryDataPath;

    public MetadataController() {

        mMethodDataPath = new File(Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_DOWNLOADS),
            MainActivity.MY_SRO_MAIN_DIRECTORY +
                    File.separator +
                    MainActivity.MY_RECEIPT_DIRECTORY
        );

        mLocationDataPath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS),
                MainActivity.MY_SRO_MAIN_DIRECTORY +
                        File.separator +
                        MainActivity.MY_RECEIPT_DIRECTORY
        );

        mCategoryDataPath = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS),
                MainActivity.MY_SRO_MAIN_DIRECTORY +
                        File.separator +
                        MainActivity.MY_RECEIPT_DIRECTORY
        );

        ArrayList<String> mSavedLocations = new ArrayList<> ();
        ArrayList<String> mSavedMethods = new ArrayList<> ();
        ArrayList<String> mSavedCategories = new ArrayList<> ();

        if(!mMethodDataPath.mkdirs()) {
            //TODO
        }

        if(!mLocationDataPath.mkdirs()) {
            //TODO
        }

        if(!mCategoryDataPath.mkdirs()) {
            //TODO
        }

        mMethodDataPath = new File(mMethodDataPath, MY_METHOD_DATA_FILE_NAME);
        mLocationDataPath = new File(mLocationDataPath, MY_LOCATION_DATA_FILE_NAME);
        mCategoryDataPath = new File(mCategoryDataPath, MY_CATEGORY_DATA_FILE_NAME);

        mSavedLocations = read(mLocationDataPath, mSavedLocations);
        mSavedMethods = read(mMethodDataPath, mSavedMethods);
        mSavedCategories = read(mCategoryDataPath, mSavedCategories);

        for(String location : mSavedLocations) {
            Location.addLocation(location);
        }

        for(String method : mSavedMethods) {
            Method.addMethod(method);
        }

        for(String category : mSavedCategories) {
            Category.addCategory(category);
        }

        Category.addCategory(MainActivity.NOT_SPECIFIED_LITERAL);
        Location.addLocation(MainActivity.NOT_SPECIFIED_LITERAL);
        Method.addMethod(MainActivity.NOT_SPECIFIED_LITERAL);
    }


    private ArrayList<String> read(File path, ArrayList<String> stringList) {
        try {
            InputStream file = new FileInputStream(path.getAbsoluteFile());
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput input = new ObjectInputStream(buffer);

            stringList = (ArrayList<String>)input.readObject();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            stringList = new ArrayList<> ();
            //TODO
        } catch(IOException e) {
            e.printStackTrace();
            stringList = new ArrayList<> ();
            //TODO
        } catch(ClassNotFoundException e) {
            e.printStackTrace();;
            stringList = new ArrayList<> ();
            //TODO
        }

        return stringList;
    }

    public void save() {
        write(mCategoryDataPath, Category.getCategories());
        write(mLocationDataPath, Location.getLocations());
        write(mMethodDataPath, Method.getMethods());
    }

    private void write(File path, ArrayList<String> stringList) {
        try (
                OutputStream file = new FileOutputStream(path.getAbsoluteFile());
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer)
        )
        {
            output.writeObject(stringList);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public String getUnspecifiedStringLiteral() {
        return MainActivity.NOT_SPECIFIED_LITERAL;
    }

    public void addCategory(String category) {
        Category.addCategory(category);
    }

    public void addLocation(String location) {
        Location.addLocation(location);
    }

    public void addMethod(String method) {
        Method.addMethod(method);
    }

    public void deleteCategory(String category) {
        Category.removeCategory(category);
    }

    public void deleteLocation(String location) {
        Location.removeLocation(location);
    }

    public void deleteMethod(String method) {
        Method.removeMethod(method);
    }

    public ArrayList<String> getLocationList() {
        return new ArrayList<String>(Location.getLocations());
    }

    public ArrayList<String> getCategoryList() {
        return new ArrayList<String>(Category.getCategories());
    }

    public ArrayList<String> getMethodList() {
        return new ArrayList<String>(Method.getMethods());
    }
}
