package edu.odu.cs441.sro.controller;

import java.io.Serializable;
import java.util.ArrayList;

import edu.odu.cs441.sro.model.metadata.Category;
import edu.odu.cs441.sro.model.metadata.Location;
import edu.odu.cs441.sro.model.metadata.Method;
import edu.odu.cs441.sro.model.metadata.Tag;

/**
 * Created by michael on 3/12/18.
 */
public class MetadataController implements Serializable {

    public MetadataController() {
        Category.addCategory(Tag.getNotSpecifiedLiteral());
        Location.addLocation(Tag.getNotSpecifiedLiteral());
        Method.addMethod(Tag.getNotSpecifiedLiteral());
    }

    public String getUnspecifiedStringLiteral() {
        return Tag.getNotSpecifiedLiteral();
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
