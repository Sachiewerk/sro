package edu.odu.cs441.sro.entity.record;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import edu.odu.cs441.sro.entity.metadata.Category;
import edu.odu.cs441.sro.entity.metadata.Location;
import edu.odu.cs441.sro.entity.metadata.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by michael on 2/17/18.
 */
@Entity(tableName = "receipt")
public class Receipt {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "receipt_key")
    private String receiptKey;

    @ColumnInfo(name = "subscription_key")
    private String subscriptionKey;

    @ColumnInfo(name = "image_file_path")
    private String imageFilePath;

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

    @ColumnInfo(name = "comment")
    private String comment;

    public Receipt() {
        receiptKey = UUID.randomUUID().toString();
    }

    public Receipt(@NonNull String receiptKey, Long date) {
        this.receiptKey = receiptKey;
        createdDate = date;
        subscriptionKey = null;
        this.imageFilePath = null;
        category = null;
        location = null;
        price = null;
        method = null;
        comment = null;
        title = null;
    }

    public Receipt(@NonNull String receiptKey, Long date, String imageFilePath) {
        this.receiptKey = receiptKey;
        createdDate = date;
        subscriptionKey = null;
        this.imageFilePath = imageFilePath;

        category = null;
        location = null;
        price = null;
        method = null;
        comment = null;
        title = null;
    }

    public Receipt(@NonNull String receiptKey, String subscriptionKey, Long date) {
        this.receiptKey = receiptKey;
        this.subscriptionKey = subscriptionKey;
        this.imageFilePath = null;

        createdDate = date;
        category = null;
        location = null;
        price = null;
        method = null;
        comment = null;
        title = null;
    }

    public @NonNull String getReceiptKey() {
        return  receiptKey;
    }

    public void setReceiptKey(@NonNull String receiptKey) {
        this.receiptKey = receiptKey;
    }

    public String getSubscriptionKey() {
        return subscriptionKey;
    }

    public void setSubscriptionKey(String subscriptionKey) {
        this.subscriptionKey = subscriptionKey;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
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
}