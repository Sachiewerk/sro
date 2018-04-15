package edu.odu.cs441.sro.entity.metadata;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Due date")
public class Duedate {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "Due date")
    private String duedate;

    public Duedate(@NonNull String duedate) {
        this.duedate = duedate.toUpperCase();
    }

    @NonNull
    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(@NonNull String category) {
        this.duedate = category.toUpperCase();
    }
}