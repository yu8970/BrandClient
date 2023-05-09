package com.brandservice;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DetectorResult implements Parcelable {

    private Boolean hasResult;
    private Bitmap  detectedImage;

    private Integer costTime;

    private String createTime;

    private List<DetectionTarget> targets = new ArrayList<>();

    private String remark;
    public DetectorResult(){}

    protected DetectorResult(Parcel in) {
        byte tmpHasResult = in.readByte();
        hasResult = tmpHasResult == 0 ? null : tmpHasResult == 1;
        detectedImage = in.readParcelable(Bitmap.class.getClassLoader());
        if (in.readByte() == 0) {
            costTime = null;
        } else {
            costTime = in.readInt();
        }
        createTime = in.readString();
        targets = in.createTypedArrayList(DetectionTarget.CREATOR);
        remark = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (hasResult == null ? 0 : hasResult ? 1 : 2));
        dest.writeParcelable(detectedImage, flags);
        if (costTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(costTime);
        }
        dest.writeString(createTime);
        dest.writeTypedList(targets);
        dest.writeString(remark);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DetectorResult> CREATOR = new Creator<DetectorResult>() {
        @Override
        public DetectorResult createFromParcel(Parcel in) {
            return new DetectorResult(in);
        }

        @Override
        public DetectorResult[] newArray(int size) {
            return new DetectorResult[size];
        }
    };

    public Boolean getHasResult() {
        return hasResult;
    }

    public void setHasResult(Boolean hasResult) {
        this.hasResult = hasResult;
    }

    public Bitmap getDetectedImage() {
        return detectedImage;
    }

    public void setDetectedImage(Bitmap detectedImage) {
        this.detectedImage = detectedImage;
    }

    public List<DetectionTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<DetectionTarget> targets) {
        this.targets = targets;
    }

    public Integer getCostTime() {
        return costTime;
    }

    public void setCostTime(Integer costTime) {
        this.costTime = costTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "DetectorResult{" +
                "hasResult=" + hasResult +
                ", detectedImage=" + detectedImage +
                ", costTime=" + costTime +
                ", createTime='" + createTime + '\'' +
                ", targets=" + targets +
                ", remark='" + remark + '\'' +
                '}';
    }
}
