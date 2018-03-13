package edu.odu.cs441.sro.model.record;

import java.util.UUID;

import edu.odu.cs441.sro.model.metadata.*;

/**
 * Created by michael on 2/17/18.
 *
 * Record is an abstract class that can be implemented by Receipt and Subscription
 */
public abstract class Record {

    protected UUID mUUID;

    Record(UUID uuid) {
        mUUID = uuid;
    }

    public UUID getUUID() {
        return mUUID;
    }
}
