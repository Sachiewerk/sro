package edu.odu.cs441.sro.model.record;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import edu.odu.cs441.sro.model.metadata.Category;
import edu.odu.cs441.sro.model.metadata.Comment;
import edu.odu.cs441.sro.model.metadata.DateTime;
import edu.odu.cs441.sro.model.metadata.Location;
import edu.odu.cs441.sro.model.metadata.Method;
import edu.odu.cs441.sro.model.metadata.Price;
import edu.odu.cs441.sro.model.metadata.Title;

/**
 * Created by michael on 2/17/18.
 */
public class Receipt extends Record implements Comparable<Receipt>, Serializable, Cloneable {
    private boolean mPhotoAvailable;
    private boolean mFromSubscription;
    private UUID mSubscriptionUUID;

    private File mImageFile;

    private DateTime mDateTime;
    private Title mTitle;
    private Location mLocation;
    private Price mPrice;
    private Category mCategory;
    private Method mMethod;
    private Comment mComment;

    public Receipt(UUID uuid, Date date, File imageFile) {

        super(uuid);

        mFromSubscription = false;
        mSubscriptionUUID = null;

        if(imageFile != null && imageFile.exists()) {
            mImageFile = imageFile;
            mPhotoAvailable = true;
        }
        else {
            mImageFile = null;
            mPhotoAvailable = false;
        }

        mDateTime = new DateTime(uuid, date);
        mCategory = new Category(uuid);
        mLocation = new Location(uuid);
        mPrice = new Price(uuid);
        mMethod = new Method(uuid);
        mComment = new Comment(uuid);
        mTitle = new Title(uuid);
    }

    public boolean isPhotoAvailable() {
        return mPhotoAvailable;
    }

    public void setImageFile(File file) {
        if(file != null) {
            mImageFile = file;
            mPhotoAvailable = true;
        }
        else {
            mPhotoAvailable = false;
            mImageFile = null;
        }
    }

    public File getImageFile() {
        return mImageFile;
    }

    public void setPrice(String value) {
        mPrice.setValue(value);
    }

    public void setComment(String comment) {
        mComment.setComment(comment);
    }

    public void setCategory(String category) {
        mCategory.setSelectedCategory(category);
    }

    public void setMethod(String method) {
        mMethod.setSelectedMethod(method);
    }

    public void setLocation(String location) {
        mLocation.setSelectedLocation(location);
    }

    public void setTitle(String title) {
        mTitle.setTitle(title);
    }

    public void setDateTime(Date date) {
        mDateTime.setDateTime(date);
    }

    public Date getDateTime() {
        return mDateTime.getDateTime();
    }

    public String getDateTimeFormattedString() {
        return mDateTime.toString();
    }

    public String getCategory() {
        return mCategory.getSelectedCategory();
    }

    public String getComment() {
        return mComment.getComment();
    }

    public String getLocation() {
        return mLocation.getSelectedLocation();
    }

    public String getMethod() {
        return mMethod.getSelectedMethod();
    }

    public String getPriceWithCurrencySymbol() {
        return mPrice.toString();
    }

    public String getPriceWithoutCurrencySymbol() {
        return mPrice.getPrice();
    }

    public BigDecimal getPriceValue() {
        return mPrice.getValue();
    }

    public String getTitle() {
        return mTitle.getTitle();
    }


    public boolean containsDeprecatedTag() {
        return containsDeprecatedCategory() ||
                containsDeprecatedLocation() ||
                containsDeprecatedMethod();
    }

    public boolean containsDeprecatedLocation() {
        if(!Location.exists(mLocation.getSelectedLocation())) {
            return true;
        }

        return false;
    }

    public boolean containsDeprecatedCategory() {
        if(!Category.exists(mCategory.getSelectedCategory())) {
            return true;
        }

        return false;
    }

    public boolean containsDeprecatedMethod() {
        if(!Method.exists(mMethod.getSelectedMethod())) {
            return true;
        }

        return false;
    }

    public boolean isInComplete() {
        return mLocation.isUnspecified() ||
                mCategory.isUnspecified() ||
                mPrice.isUnspecified() ||
                mMethod.isUnspecified();
    }

    public boolean createdFromSubscription() {
        return mFromSubscription;
    }

    public UUID getSubscriptionUUID() {
        if(mFromSubscription) {
            return mSubscriptionUUID;
        } else {
            return null;
        }
    }

    public int compareTo(Receipt receipt) {
        return this.getUUID().compareTo(receipt.getUUID());
    }
}
