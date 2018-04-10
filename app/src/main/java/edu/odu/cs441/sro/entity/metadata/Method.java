package edu.odu.cs441.sro.entity.metadata;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by michael on 2/17/18.
 *
 * Method class represents the payment method of a transaction
 */
@Entity(tableName = "method")
public class Method {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "method")
    private String method;

    public Method(@NonNull String method) {
        this.method = method;
    }

    @NonNull
    public String getMethod() {
        return method;
    }

    public void setMethod(@NonNull String method) {
        this.method = method;
    }
}