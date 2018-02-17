package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by michael on 2/17/18.
 *
 * Method class represents the payment method of a transaction
 */
public class Method extends Tag implements Serializable, Comparable<Method> {
    
    // This method list is shared by all instances of Method class
    private static ArrayList<String> METHODS = new ArrayList<String> ();

    // The selected method string literal of this method object
    private String SELECTED_METHOD;

    /**
     * Static method to add the given method to the global method list or if the method
     * already exists, do nothing
     * @param method String
     */
    public static void addMethod(String method) {
        if(!exists(method)) {
            METHODS.add(method.trim());
        }
    }

    /**
     * Static method to remove the given method from the global method list
     * @param method String
     */
    public static void removeMethod(String method) {
        if(exists(method)) {
            METHODS.remove(indexOf(method));
        }
    }

    /**
     * Clear all methods in the global list!
     */
    public static void clearMethods() {
        METHODS = new ArrayList<String> ();
        METHODS.add(NOT_SPECIFIED_LITERAL);
    }

    /**
     * Static method to return true if the given method exists in the global method list
     * @param method String
     * @return boolean true if the method exists
     */
    public static boolean exists(String method) {

        if(indexOf(method) < 0) {
            return false;
        }

        return true;
    }

    /**
     * Static method to get the index of the given method string literal -
     * it is private because the user does not need to concern with list indices.
     * @param method String
     * @return int index of the given method or -1 if not found
     */
    private static int indexOf(String method) {

        for(int i = 0; i < METHODS.size(); i++) {
            if(METHODS.get(i).trim().toUpperCase().equals(method.trim().toUpperCase())) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Default Constructor to create Method object with unspecified method
     */
    public Method(UUID uuid) {
        super(uuid);
        SELECTED_METHOD = NOT_SPECIFIED_LITERAL;
    }

    /**
     * Constructor to create Method object with specified method -
     * if the given method does not exist in the global, it is added to the list.
     * @param method String
     */
    public Method(UUID uuid, String method) {
        super(uuid);

        int index = indexOf(method);

        addMethod(method);
        SELECTED_METHOD = METHODS.get(indexOf(method));
    }

    /**
     * Set the selected method to "Not Specified"
     */
    public void clearSelectedMethod() {
        SELECTED_METHOD = NOT_SPECIFIED_LITERAL;
    }

    /**
     * Override compareTo method to compare two method by the selected method.
     * @param method Method
     * @return int
     */
    @Override
    public int compareTo(Method method) {
        return this.SELECTED_METHOD.toUpperCase().compareTo(
                method.SELECTED_METHOD.toUpperCase());
    }

    /**
     * Return the selected method of this object as string
     * @return String string literal of the current selected method
     */
    @Override
    public String toString() {
        return SELECTED_METHOD;
    }
}
