package edu.odu.cs441.sro.model.metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by michael on 2/17/18.
 * Title class represents the title of the transaction.
 * By default, the title of any saved receipt is the location that
 * the receipt was obtained from unless user specifies the title.
 */
public class Title implements Serializable, Parcelable {

    private static final long serialVersionUID = 7L;

    private String TITLE;
    private String mUUID;


    public Title() {
        mUUID = UUID.randomUUID().toString();
    }

    /**
     * Default Constructor
     * @param uuid UUID
     */
    public Title(String uuid) {
        mUUID = uuid;
        TITLE = "";
    }

    /**
     * Constructor that accepts initial title
     * @param uuid UUID
     * @param title String
     */
    public Title(String uuid, String title) {
        mUUID = uuid;

        if(title == null || title.trim().isEmpty()) {
            TITLE = "";
        } else {
            TITLE = title.trim();
        }
    }

    /**
     * Return true if the title is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return TITLE.isEmpty();
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

        if(title == null || title.trim().isEmpty()) {
            TITLE = "";
        } else {
            TITLE = title.trim();
        }
    }

    /**
     * Return the title as String
     * @return String title
     */
    @Override
    public String toString() {
        return TITLE;
    }

    protected Title(Parcel in) {
        TITLE = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TITLE);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Title> CREATOR = new Parcelable.Creator<Title>() {
        @Override
        public Title createFromParcel(Parcel in) {
            return new Title(in);
        }

        @Override
        public Title[] newArray(int size) {
            return new Title[size];
        }
    };
}