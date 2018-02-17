package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by michael on 2/17/18.
 * Title class represents the title of the transaction.
 * By default, the title of any saved receipt is the location that
 * the receipt was obtained from unless user specifies the title.
 */
public class Title extends Tag implements Serializable {

    private String TITLE;

    /**
     * Default Constructor
     * @param uuid UUID
     */
    public Title(UUID uuid) {
        super(uuid);
        TITLE = "";
    }

    /**
     * Constructor that accepts initial title
     * @param uuid UUID
     * @param title String
     */
    public Title(UUID uuid, String title) {
        super(uuid);
        TITLE = title;
    }

    /**
     * Return the title as String
     * @return String title
     */
    public String getTitle() {
        return TITLE;
    }

    /**
     * Set the title
     * @param title String
     */
    public void setTitle(String title) {
        TITLE = title;
    }

    /**
     * Return the title as String
     * @return String title
     */
    @Override
    public String toString() {
        return TITLE;
    }
}
