package com.brandservice;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SearchResult implements Parcelable {

    private Boolean hasResult;

    private Integer costTime;

    private String createTime;

    private String remark;

    public SearchResult(){}
    protected SearchResult(Parcel in) {
        byte tmpHasResult = in.readByte();
        hasResult = tmpHasResult == 0 ? null : tmpHasResult == 1;
        if (in.readByte() == 0) {
            costTime = null;
        } else {
            costTime = in.readInt();
        }
        createTime = in.readString();
        remark = in.readString();
    }

    public static final Creator<SearchResult> CREATOR = new Creator<SearchResult>() {
        @Override
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }

        @Override
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public Boolean getHasResult() {
        return hasResult;
    }

    public void setHasResult(Boolean hasResult) {
        this.hasResult = hasResult;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeByte((byte) (hasResult == null ? 0 : hasResult ? 1 : 2));
        if (costTime == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(costTime);
        }
        parcel.writeString(createTime);
        parcel.writeString(remark);
    }
}
