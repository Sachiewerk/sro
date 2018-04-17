package edu.odu.cs441.sro.entity.record;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.UUID;

/**
 * Created by michael on 2/17/18.
 */

@Entity(tableName = "subscription")
public class Subscription {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "subscription_key")
    private String subscriptionKey;

    @ColumnInfo(name = "created_date")
    private Long createdDate;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "price")
    private Double price;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "method")
    private String method;

    @ColumnInfo(name = "Start date")
    private Long startDate;

    @ColumnInfo(name = "End date")
    private Long endDate;

    @ColumnInfo(name = "Due date")
    private Long dueDate;

    @ColumnInfo(name = "comment")
    private String comment;

    public Subscription() {
        this.subscriptionKey = UUID.randomUUID().toString();
    }

    public Subscription(String subscriptionKey,Long date, Long startDate, Long endDate) {
        this.subscriptionKey = subscriptionKey;
        createdDate = date;

        category = null;
        location = null;
        price = null;
        method = null;
        comment = null;
        title = null;
        this.startDate = startDate;
        this.endDate = endDate;
        dueDate = null;

    }

    public @NonNull String getSubscriptionKey() {
        return  subscriptionKey;
    }

    public void setSubscriptionKey(@NonNull String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location.toUpperCase();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category.toUpperCase();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method.toUpperCase();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(Long dueDate) {
        this.dueDate = dueDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }
}

