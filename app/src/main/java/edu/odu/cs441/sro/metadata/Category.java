package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import edu.odu.cs441.sro.metadata.Tag;

/**
 * Created by michael on 2/17/18.
 *
 * Category class represents the category of the transaction.
 * The category can be grocery, meal, electronics, furniture, and etc.
 */
public class Category extends Tag implements Serializable, Comparable<Category> {

    // This location list is shared by all instances of Category class
    private static ArrayList<String> CATEGORIES = new ArrayList<String> ();

    // The selected category string literal of this location object
    private String SELECTED_CATEGORY;

    /**
     * Static method to add the given category to the global category list or if the category
     * already exists, do nothing
     * @param category String
     */
    public static void addCategory(String category) {
        if(!exists(category)) {
            CATEGORIES.add(category.trim());
        }
    }

    /**
     * Static method to remove the given category from the global category list
     * @param category String
     */
    public static void removeCategory(String category) {
        if(exists(category)) {
            CATEGORIES.remove(indexOf(category));
        }
    }

    /**
     * Clear all categories in the global list!
     */
    public static void clearCategories() {
        CATEGORIES = new ArrayList<String> ();
        CATEGORIES.add(NOT_SPECIFIED_LITERAL);
    }

    /**
     * Static method to return true if the given category exists in the global category list
     * @param category String
     * @return boolean true if the category exists
     */
    public static boolean exists(String category) {

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

        for(int i = 0; i < CATEGORIES.size(); i++) {
            if(CATEGORIES.get(i).trim().toUpperCase().equals(category.trim().toUpperCase())) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Default Constructor to create Category object with unspecified category
     * @param uuid UUID
     */
    public Category(UUID uuid) {
        super(uuid);
        SELECTED_CATEGORY = NOT_SPECIFIED_LITERAL;
    }

    /**
     * Constructor to create Category object with specified category -
     * if the given category does not exist in the global, it is added to the list.
     * @param uuid UUID
     * @param category String
     */
    public Category(UUID uuid, String category) {
        super(uuid);

        int index = indexOf(category);

        addCategory(category);
        SELECTED_CATEGORY = CATEGORIES.get(indexOf(category));
    }

    /**
     * Set the selected category to "Not Specified"
     */
    public void clearSelectedCategory() {
        SELECTED_CATEGORY = NOT_SPECIFIED_LITERAL;
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
}
