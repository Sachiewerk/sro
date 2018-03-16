package edu.odu.cs441.sro.model.metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import edu.odu.cs441.sro.MainActivity;

/**
 * Created by michael on 2/17/18.
 *
 * Method class represents the payment method of a transaction
 */
public class Method implements Serializable, Comparable<Method>, Parcelable {

    private static final long serialVersionUID = 5L;

    // This method list is shared by all instances of Method class
    private static ArrayList<String> METHODS = new ArrayList<String> ();

    // The selected method string literal of this method object
    private String SELECTED_METHOD;

    private String mUUID;

    /**
     * Return a shallow copy of the method list
     * @return ArrayList
     */
    public static ArrayList<String> getMethods() {
        return METHODS;
    }

    /**
     * Static method to add the given method to the global method list or if the method
     * already exists, do nothing
     * @param method String
     */
    public static void addMethod(String method) {

        if(method == null) {
            return;
        }

        if(method.trim().isEmpty()) {
            return;
        }

        if(!exists(method)) {
            METHODS.add(method.trim());
        }
    }

    /**
     * Static method to remove the given method from the global method list
     * @param method String
     */
    public static void removeMethod(String method) {

        if(method == null) {
            return;
        }

        if(method.trim().isEmpty()) {
            return;
        }

        if(exists(method)) {
            METHODS.remove(indexOf(method));
        }
    }

    /**
     * Clear all methods in the global list!
     */
    public static void clearMethods() {
        METHODS = new ArrayList<String> ();
        METHODS.add(MainActivity.NOT_SPECIFIED_LITERAL);
    }

    /**
     * Static method to return true if the given method exists in the global method list
     * @param method String
     * @return boolean true if the method exists
     */
    public static boolean exists(String method) {

        if(method == null) {
            return false;
        }

        if(method.trim().isEmpty()) {
            return false;
        }

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

        if(method == null) {
            return -1;
        }

        if(method.trim().isEmpty()) {
            return -1;
        }

        for(int i = 0; i < METHODS.size(); i++) {
            if(METHODS.get(i).trim().toUpperCase().equals(method.trim().toUpperCase())) {
                return i;
            }
        }

        return -1;
    }


    public Method() {
        mUUID = UUID.randomUUID().toString();
    }

    /**
     * Default Constructor to create Method object with unspecified method
     */
    public Method(String uuid) {
        mUUID = uuid;
        SELECTED_METHOD = MainActivity.NOT_SPECIFIED_LITERAL;
    }

    /**
     * Constructor to create Method object with specified method -
     * if the given method does not exist in the global, it is added to the list.
     * @param method String
     */
    public Method(String uuid, String method) {
        mUUID = uuid;

        if(method == null) {
            SELECTED_METHOD = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        if(method.trim().isEmpty()) {
            SELECTED_METHOD = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        Method.addMethod(method);
        SELECTED_METHOD = METHODS.get(indexOf(method));
    }

    /**
     * Set this method as the given string
     * @param method String
     */
    public void setSelectedMethod(String method) {
        if(method == null) {
            SELECTED_METHOD = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        if(method.trim().isEmpty()) {
            SELECTED_METHOD = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        Method.addMethod(method);
        SELECTED_METHOD = METHODS.get(indexOf(method));
    }

    /**
     * Return the selected method as string literal
     * @return String
     */
    public String getSelectedMethod() {
        return SELECTED_METHOD;
    }

    /**
     * Set the selected method to "Not Specified"
     */
    public void setUnspecified() {
        SELECTED_METHOD = MainActivity.NOT_SPECIFIED_LITERAL;
    }

    /**
     * Return true if this method is unspecified
     * @return boolean
     */
    public boolean isUnspecified() {
        return SELECTED_METHOD.equals(MainActivity.NOT_SPECIFIED_LITERAL);
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

    protected Method(Parcel in) {
        SELECTED_METHOD = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SELECTED_METHOD);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Method> CREATOR = new Parcelable.Creator<Method>() {
        @Override
        public Method createFromParcel(Parcel in) {
            return new Method(in);
        }

        @Override
        public Method[] newArray(int size) {
            return new Method[size];
        }
    };
}