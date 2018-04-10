package edu.odu.cs441.sro.entity.metadata;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by michael on 2/17/18.
 *
 * Category class represents the category of the transaction.
 * The category can be grocery, meal, electronics, furniture, and etc.
 */
@Entity(tableName = "category")
public class Category {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "category")
    private String category;

    public Category(@NonNull String category) {
        this.category = category;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }
}