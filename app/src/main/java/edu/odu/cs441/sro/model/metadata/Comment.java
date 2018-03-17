package edu.odu.cs441.sro.model.metadata;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * Created by michael on 2/17/18.
 *
 * Comment class represents misc. notes that user writes about transactions
 */
public class Comment implements Serializable, Parcelable {

    private static final long serialVersionUID = 2L;

    private String COMMENT;

    private String mUUID;

    public Comment() {
        mUUID = UUID.randomUUID().toString();
    }

    /**
     * Constructor with empty comment
     * @param uuid UUID
     */
    public Comment(String uuid) {
        mUUID = uuid;
        COMMENT = "";
    }

    /**
     * Constructor with initial comment
     * @param uuid UUID
     * @param comment String
     */
    public Comment(String uuid, String comment) {
        mUUID = uuid;

        if(comment == null) {
            COMMENT = "";
        }else{
            COMMENT = comment.trim();
        }
    }

    /**
     * Return true if the comment is empty
     * @return boolean
     */
    public boolean isEmpty() {
        return COMMENT.isEmpty();
    }

    /**
     * Return comment as string
     * @return String comment
     */
    public String getComment() {
        return COMMENT;
    }

    /**
     * Set comment
     * @param comment String
     */
    public void setComment(String comment) {
        if(comment == null) {
            COMMENT = "";
        }else{
            COMMENT = comment.trim();
        }
    }

    /**
     * Return comment as string
     * @return String comment
     */
    @Override
    public String toString() {
        return COMMENT;
    }

    protected Comment(Parcel in) {
        COMMENT = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(COMMENT);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}