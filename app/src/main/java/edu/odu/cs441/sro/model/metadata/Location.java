package edu.odu.cs441.sro.model.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by michael on 2/16/18.
 *
 * Location class represents the location of the transaction
 */
public class Location extends Tag implements Serializable, Comparable<Location> {

    // This location list is shared by all instances of Location class
    private static ArrayList<String> LOCATIONS = new ArrayList<String> ();

    // The selected location string literal of this location object
    private String SELECTED_LOCATION;

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
        LOCATIONS.add(NOT_SPECIFIED_LITERAL);
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

    /**
     * Default Constructor to create Location object with unspecified location
     */
    public Location(UUID uuid) {
        super(uuid);
        SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
    }

    /**
     * Constructor to create Location object with specified location -
     * if the given location does not exist in the global, it is added to the list.
     * @param location String
     */
    public Location(UUID uuid, String location) {
        super(uuid);

        if(location == null) {
            SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
            return;
        }
        else if(location.trim().isEmpty()) {
            SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
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
            SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
            return;
        }

        if(location.trim().isEmpty()) {
            SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
            return;
        }

        Location.addLocation(location);
        SELECTED_LOCATION = LOCATIONS.get(indexOf(location));
    }

    /**
     * Set the selected location to "Not Specified"
     */
    public void setUnspecified() {
        SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
    }

    /**
     * Return true if this location is unspecified
     * @return boolean
     */
    public boolean isUnspecified() {
        return SELECTED_LOCATION.equals(NOT_SPECIFIED_LITERAL);
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
}
