package edu.odu.cs441.sro.entity.record;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.util.Date;
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
    private Date createdDate;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "price")
    private BigDecimal price;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "method")
    private String method;

    @ColumnInfo(name = "Start date")
    private String startdate;

    @ColumnInfo(name = "End date")
    private String enddate;

    @ColumnInfo(name = "Due date")
    private String duedate;

    @ColumnInfo(name = "comment")
    private String comment;

    public Subscription() {
        subscriptionKey = UUID.randomUUID().toString();
    }

    public Subscription(String subscriptionKey,Date date) {
        this.subscriptionKey = subscriptionKey;
        createdDate = date;

        category = null;
        location = null;
        price = null;
        method = null;
        comment = null;
        title = null;
        startdate = null;
        enddate = null;
        duedate = null;

    }

    public @NonNull String getSubscriptionKey() {
        return  subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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
}

