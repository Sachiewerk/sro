package edu.odu.cs441.sro.metadata;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by michael on 2/16/18.
 * Tag represents a single piece of identifying information for a transaction.
 * It could be date, location, category, price, and etc.
 */
public abstract class Tag implements Serializable {

    // The string literal for any tag that user has not specified
    protected static final String NOT_SPECIFIED_LITERAL = "[Not Specified]";

    // Unique Identifier of the record
    private final UUID UNIQUE_IDENTIFIER;

    public Tag(UUID uuid) {
        UNIQUE_IDENTIFIER = uuid;
    }

    public UUID getUUID(){
        return UNIQUE_IDENTIFIER;
    }

    @Override
    abstract public String toString();
}
