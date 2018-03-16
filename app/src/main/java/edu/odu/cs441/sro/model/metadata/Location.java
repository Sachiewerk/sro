package edu.odu.cs441.sro.model.metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import edu.odu.cs441.sro.MainActivity;

/**
 * Created by michael on 2/16/18.
 *
 * Location class represents the location of the transaction
 */
public class Location implements Serializable, Comparable<Location>, Parcelable {

    private static final long serialVersionUID = 4L;

    // This location list is shared by all instances of Location class
    private static ArrayList<String> LOCATIONS = new ArrayList<String> ();

    // The selected location string literal of this location object
    private String SELECTED_LOCATION;

    private String mUUID;

    /**
     * Return the shallow copy of the static location list
     * @return ArrayList LOCATIONS
     */
    public static ArrayList<String> getLocations() {
        return LOCATIONS;
    }

    /**
     * Static method to add the given location to the global location list or if the location
     * already exists, do nothing
     * @param location String
     */
    public static void addLocation(String location) {

        if(location == null) {
            return;
        }

        if(location.trim().isEmpty()) {
            return;
        }

        if(!exists(location)) {
            LOCATIONS.add(location.trim());
        }
    }

    /**
     * Static method to remove the given location from the global location list
     * @param location String
     */
    public static void removeLocation(String location) {

        if(location == null) {
            return;
        }

        if(location.trim().isEmpty()) {
            return;
        }

        if(exists(location)) {
            LOCATIONS.remove(indexOf(location));
        }
    }

    /**
     * Clear all locations in the global list!
     */
    public static void clearLocations() {
        LOCATIONS = new ArrayList<String> ();
        LOCATIONS.add(MainActivity.NOT_SPECIFIED_LITERAL);
    }

    /**
     * Static method to return true if the given location exists in the global location list
     * @param location String
     * @return boolean true if the location exists
     */
    public static boolean exists(String location) {

        if(location == null) {
            return false;
        }

        if(location.trim().isEmpty()) {
            return false;
        }

        if(indexOf(location.trim()) < 0) {
            return false;
        }

        return true;
    }

    /**
     * Static method to get the index of the given location string literal -
     * it is private because the user does not need to concern with list indices.
     * @param location String
     * @return int index of the given location or -1 if not found
     */
    private static int indexOf(String location) {

        if(location == null) {
            return -1;
        }

        if(location.trim().isEmpty()) {
            return -1;
        }

        for(int i = 0; i < LOCATIONS.size(); i++) {
            if(LOCATIONS.get(i).trim().toUpperCase().equals(location.trim().toUpperCase())) {
                return i;
            }
        }

        return -1;
    }


    public Location() {
        mUUID = UUID.randomUUID().toString();
    }

    /**
     * Default Constructor to create Location object with unspecified location
     */
    public Location(String uuid) {
        mUUID = uuid;
        SELECTED_LOCATION = MainActivity.NOT_SPECIFIED_LITERAL;
    }

    /**
     * Constructor to create Location object with specified location -
     * if the given location does not exist in the global, it is added to the list.
     * @param location String
     */
    public Location(String uuid, String location) {
        mUUID = uuid;

        if(location == null) {
            SELECTED_LOCATION = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }
        else if(location.trim().isEmpty()) {
            SELECTED_LOCATION = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        } else {
            Location.addLocation(location);
            SELECTED_LOCATION = LOCATIONS.get(indexOf(location));
        }
    }

    public String getSelectedLocation() {
        return SELECTED_LOCATION;
    }

    /**
     * Set the selected location to the given category
     * @param location String
     */
    public void setSelectedLocation(String location) {

        if(location == null) {
            SELECTED_LOCATION = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        if(location.trim().isEmpty()) {
            SELECTED_LOCATION = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        Location.addLocation(location);
        SELECTED_LOCATION = LOCATIONS.get(indexOf(location));
    }

    /**
     * Set the selected location to "Not Specified"
     */
    public void setUnspecified() {
        SELECTED_LOCATION = MainActivity.NOT_SPECIFIED_LITERAL;
    }

    /**
     * Return true if this location is unspecified
     * @return boolean
     */
    public boolean isUnspecified() {
        return SELECTED_LOCATION.equals(MainActivity.NOT_SPECIFIED_LITERAL);
    }

    /**
     * Override compareTo method to compare two location by the selected location.
     * @param location Location
     * @return int
     */
    @Override
    public int compareTo(Location location) {
        return this.SELECTED_LOCATION.toUpperCase().compareTo(
                location.SELECTED_LOCATION.toUpperCase());
    }

    /**
     * Return the selected location of this object as string
     * @return String string literal of the current selected location
     */
    @Override
    public String toString() {
        return SELECTED_LOCATION;
    }

    protected Location(Parcel in) {
        SELECTED_LOCATION = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SELECTED_LOCATION);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}