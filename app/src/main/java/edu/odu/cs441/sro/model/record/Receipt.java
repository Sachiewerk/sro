package edu.odu.cs441.sro.model.record;

import android.os.Parcel;
import android.os.Parcelable;
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
public class Receipt implements Comparable<Receipt>, Serializable, Cloneable, Parcelable {
    private static final long serialVersionUID = 6529685098267757690L;

    private String mUUID;
    private boolean mPhotoAvailable;
    private boolean mFromSubscription;
    private String mSubscriptionUUID;
    private String mImageFile;
    private DateTime mDateTime;
    private Title mTitle;
    private Location mLocation;
    private Price mPrice;
    private Category mCategory;
    private Method mMethod;
    private Comment mComment;

    public Receipt() {
        mUUID = UUID.randomUUID().toString();
    }

    public Receipt(String uuid, Date date, File imageFile) {
        mUUID = uuid;

        mFromSubscription = false;
        mSubscriptionUUID = null;

        if(imageFile != null && imageFile.exists()) {
            mImageFile = imageFile.getAbsolutePath();
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

    public Receipt(Receipt receipt) {
        mUUID = receipt.mUUID;
        mPhotoAvailable = receipt.mPhotoAvailable;
        mFromSubscription = receipt.mFromSubscription;
        mSubscriptionUUID = receipt.mSubscriptionUUID;

        mImageFile = receipt.mImageFile;

        mDateTime = receipt.mDateTime;
        mTitle = receipt.mTitle;
        mLocation = receipt.mLocation;
        mPrice = receipt.mPrice;
        mCategory = receipt.mCategory;
        mMethod = receipt.mMethod;
        mComment = receipt.mComment;
    }

    public boolean isPhotoAvailable() {
        return mPhotoAvailable;
    }

    public void setImageFile(File file) {
        if(file != null) {
            mImageFile = file.getAbsolutePath();
            mPhotoAvailable = true;
        }
        else {
            mPhotoAvailable = false;
            mImageFile = null;
        }
    }

    public File getImageFile() {
        return new File(mImageFile);
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


    public String getUUID() {
        return mUUID;
    }

    public void setUUID(String uuid) {
        mUUID = uuid;
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

    public String getSubscriptionUUID() {
        if(mFromSubscription) {
            return mSubscriptionUUID;
        } else {
            return null;
        }
    }

    public int compareTo(Receipt receipt) {
        return this.getUUID().compareTo(receipt.getUUID());
    }

    public int hashCode() {
        return this.mUUID.hashCode();
    }

    public boolean equals(Object obj) {

        if(obj == null) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Receipt)) {
            return false;
        }

        Receipt receipt = (Receipt) obj;

        return this.mUUID.equals(receipt.mUUID);
    }


    protected Receipt(Parcel in) {
        mUUID = in.readString();
        mPhotoAvailable = in.readByte() != 0x00;
        mFromSubscription = in.readByte() != 0x00;
        mSubscriptionUUID = in.readString();
        mImageFile = in.readString();
        mDateTime = (DateTime) in.readValue(DateTime.class.getClassLoader());
        mTitle = (Title) in.readValue(Title.class.getClassLoader());
        mLocation = (Location) in.readValue(Location.class.getClassLoader());
        mPrice = (Price) in.readValue(Price.class.getClassLoader());
        mCategory = (Category) in.readValue(Category.class.getClassLoader());
        mMethod = (Method) in.readValue(Method.class.getClassLoader());
        mComment = (Comment) in.readValue(Comment.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUUID);
        dest.writeByte((byte) (mPhotoAvailable ? 0x01 : 0x00));
        dest.writeByte((byte) (mFromSubscription ? 0x01 : 0x00));
        dest.writeString(mSubscriptionUUID);
        dest.writeString(mImageFile);
        dest.writeValue(mDateTime);
        dest.writeValue(mTitle);
        dest.writeValue(mLocation);
        dest.writeValue(mPrice);
        dest.writeValue(mCategory);
        dest.writeValue(mMethod);
        dest.writeValue(mComment);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Receipt> CREATOR = new Parcelable.Creator<Receipt>() {
        @Override
        public Receipt createFromParcel(Parcel in) {
            return new Receipt(in);
        }

        @Override
        public Receipt[] newArray(int size) {
            return new Receipt[size];
        }
    };
}