package edu.odu.cs441.sro.model.metadata;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import edu.odu.cs441.sro.MainActivity;

/**
 * Created by michael on 2/17/18.
 *
 * Category class represents the category of the transaction.
 * The category can be grocery, meal, electronics, furniture, and etc.
 */
public class Category implements Serializable, Comparable<Category>, Parcelable {

    private static final long serialVersionUID = 1L;

    // This location list is shared by all instances of Category class
    private static ArrayList<String> CATEGORIES = new ArrayList<String> ();

    // The selected category string literal of this location object
    private String SELECTED_CATEGORY;
    private String mUUID;

    /**
     * Return the shallow copy of the list of categories
     * @return ArrayList
     */
    public static ArrayList<String> getCategories() {
        return CATEGORIES;
    }

    /**
     * Static method to add the given category to the global category list or if the category
     * already exists, do nothing
     * @param category String
     */
    public static void addCategory(String category) {

        if(category == null) {
            return;
        }

        if(category.trim().isEmpty()) {
            return;
        }

        if(!exists(category)) {
            CATEGORIES.add(category.trim());
        }
    }

    /**
     * Static method to remove the given category from the global category list
     * @param category String
     */
    public static void removeCategory(String category) {

        if(category == null) {
            return;
        }

        if(category.trim().isEmpty()) {
            return;
        }

        if(exists(category)) {
            CATEGORIES.remove(indexOf(category));
        }
    }

    /**
     * Clear all categories in the global list!
     */
    public static void clearCategories() {
        CATEGORIES = new ArrayList<String> ();
        CATEGORIES.add(MainActivity.NOT_SPECIFIED_LITERAL);
    }

    /**
     * Static method to return true if the given category exists in the global category list
     * @param category String
     * @return boolean true if the category exists
     */
    public static boolean exists(String category) {

        if(category == null) {
            return false;
        }

        if(category.trim().isEmpty()) {
            return false;
        }

        if(indexOf(category) < 0) {
            return false;
        }

        return true;
    }

    /**
     * Static method to get the index of the given category string literal -
     * it is private because the user does not need to concern with list indices.
     * @param category String
     * @return int index of the given category or -1 if not found
     */
    private static int indexOf(String category) {

        if(category == null) {
            return -1;
        }

        if(category.trim().isEmpty()) {
            return -1;
        }

        for(int i = 0; i < CATEGORIES.size(); i++) {
            if(CATEGORIES.get(i).trim().toUpperCase().equals(category.trim().toUpperCase())) {
                return i;
            }
        }

        return -1;
    }

    public Category() {
        mUUID = UUID.randomUUID().toString();
    }

    /**
     * Default Constructor to create Category object with unspecified category
     * @param uuid UUID
     */
    public Category(String uuid) {
        mUUID = uuid;
        SELECTED_CATEGORY = MainActivity.NOT_SPECIFIED_LITERAL;
    }

    /**
     * Constructor to create Category object with specified category -
     * if the given category does not exist in the global, it is added to the list.
     * @param uuid UUID
     * @param category String
     */
    public Category(String uuid, String category) {
        mUUID = uuid;

        if(category == null) {
            SELECTED_CATEGORY = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        if(category.trim().isEmpty()) {
            SELECTED_CATEGORY = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        addCategory(category);
        SELECTED_CATEGORY = CATEGORIES.get(indexOf(category));
    }

    /**
     * Set the selected category to "Not Specified"
     */
    public void setUnspecified() {
        SELECTED_CATEGORY = MainActivity.NOT_SPECIFIED_LITERAL;
    }

    /**
     * Return the string literal of current selected category
     * @return String selected category
     */
    public String getSelectedCategory() {
        return SELECTED_CATEGORY;
    }

    /**
     * Set the selected category to the given category
     * @param category String
     */
    public void setSelectedCategory(String category) {

        if(category == null) {
            SELECTED_CATEGORY = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        if(category.trim().isEmpty()) {
            SELECTED_CATEGORY = MainActivity.NOT_SPECIFIED_LITERAL;
            return;
        }

        addCategory(category);
        SELECTED_CATEGORY = CATEGORIES.get(indexOf(category));
    }

    /**
     * Returns true if this category is unspecified
     * @return boolean
     */
    public boolean isUnspecified() {
        return SELECTED_CATEGORY.equals(MainActivity.NOT_SPECIFIED_LITERAL);
    }

    /**
     * Override the compareTo method to compare two category objects
     * @param category Category
     * @return int
     */
    @Override
    public int compareTo(Category category) {
        return this.SELECTED_CATEGORY.toUpperCase().compareTo(
                category.SELECTED_CATEGORY.toUpperCase());
    }

    /**
     * Return the selected category of this object as string
     * @return String string literal of the current selected category
     */
    @Override
    public String toString() {
        return SELECTED_CATEGORY;
    }

    protected Category(Parcel in) {
        SELECTED_CATEGORY = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(SELECTED_CATEGORY);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}