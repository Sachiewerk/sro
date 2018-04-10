package edu.odu.cs441.sro.entity.metadata;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by michael on 2/16/18.
 *
 * Location class represents the location of the transaction
 */
@Entity(tableName = "location")
public class Location {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "location")
    private String location;

    public Location(@NonNull String location) {
        this.location = location.toUpperCase();
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location.toUpperCase();
    }
}