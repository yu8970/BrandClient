package com.brandservice;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class DetectionTarget  implements Parcelable {

    private Float score;

    private String brandName;

    private String brandId;

    private Bitmap image;

    public DetectionTarget(){}

    protected DetectionTarget(Parcel in) {
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readFloat();
        }
        brandName = in.readString();
        brandId = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<DetectionTarget> CREATOR = new Creator<DetectionTarget>() {
        @Override
        public DetectionTarget createFromParcel(Parcel in) {
            return new DetectionTarget(in);
        }

        @Override
        public DetectionTarget[] newArray(int size) {
            return new DetectionTarget[size];
        }
    };

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (score == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeFloat(score);
        }
        parcel.writeString(brandName);
        parcel.writeString(brandId);
        parcel.writeParcelable(image, i);
    }

    @Override
    public String toString() {
        return "DetectionTarget{" +
                "score=" + score +
                ", brandName='" + brandName + '\'' +
                ", brandId='" + brandId + '\'' +
                ", image=" + image +
                '}';
    }
}