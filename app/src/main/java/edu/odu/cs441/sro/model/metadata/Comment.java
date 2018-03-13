package edu.odu.cs441.sro.model.metadata;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by michael on 2/17/18.
 *
 * Comment class represents misc. notes that user writes about transactions
 */
public class Comment extends Tag implements Serializable {

    private String COMMENT;

    /**
     * Constructor with empty comment
     * @param uuid UUID
     */
    public Comment(UUID uuid) {
        super(uuid);
        COMMENT = "";
    }

    /**
     * Constructor with initial comment
     * @param uuid UUID
     * @param comment String
     */
    public Comment(UUID uuid, String comment) {
        super(uuid);

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
}
