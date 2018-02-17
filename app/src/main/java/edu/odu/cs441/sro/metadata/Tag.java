package edu.odu.cs441.sro.metadata;

/**
 * Created by michael on 2/16/18.
 * Tag represents a single piece of identifying information for a transaction.
 * It could be date, location, category, price, and etc.
 */
public abstract class Tag {

    private String TAG_NAME;

    public Tag(String tagName) {
        TAG_NAME = tagName;
    }

    public String getTagName(){
        return TAG_NAME;
    }

    @Override
    abstract public String toString();
}
