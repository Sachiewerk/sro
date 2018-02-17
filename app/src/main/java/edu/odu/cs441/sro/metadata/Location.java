package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by michael on 2/16/18.
 *
 * Location class represents the location of the transaction
 */
public class Location extends Tag implements Serializable {

    // The string literal that should be display when no location is specified
    private static final String NOT_SPECIFIED_LITERAL = "[Not Specified]";

    // This location list is shared by all instances of Location class
    private static ArrayList<String> LOCATIONS = new ArrayList<String> ();

    // The selected location string literal of this location object
    private String SELECTED_LOCATION;

    /**
     * Static method to add the given location to the global location list or if the location
     * already exists, do nothing
     * @param location String
     */
    public static void addLocation(String location) {
        if(!exists(location)) {
            LOCATIONS.add(location.trim());
        }
    }

    /**
     * Static method to remove the given location from the global location list
     * @param location String
     */
    public static void removeLocation(String location) {
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

        if(indexOf(location) < 0) {
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
    public Location() {
        super("location");
        SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
    }

    /**
     * Constructor to create Location object with specified location -
     * if the given location does not exist in the global, it is added to the list.
     * @param location String
     */
    public Location(String location) {
        super("location");

        int index = indexOf(location);

        addLocation(location);
        SELECTED_LOCATION = LOCATIONS.get(indexOf(location));
    }

    /**
     * Set the selected location to "Not Specified"
     */
    public void clearSelectedLocation() {
        SELECTED_LOCATION = NOT_SPECIFIED_LITERAL;
    }

    /**
     * Return the selected location of this object as string
     * @return String string literal of the current selected location
     */
    public String toString() {
        return SELECTED_LOCATION;
    }
}
