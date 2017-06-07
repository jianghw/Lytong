package com.zantong.mobilecttx.weizhang.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 驾驶证查分 请求bean
 */

public class LicenseFileNumDTO implements Parcelable, Serializable {

    private String filenum;
    private String strtdt;
    private String enddt;

    public String getFilenum() {
        return filenum;
    }

    public void setFilenum(String filenum) {
        this.filenum = filenum;
    }

    public String getStrtdt() {
        return strtdt;
    }

    public void setStrtdt(String strtdt) {
        this.strtdt = strtdt;
    }

    public String getEnddt() {
        return enddt;
    }

    public void setEnddt(String enddt) {
        this.enddt = enddt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filenum);
        dest.writeString(this.strtdt);
        dest.writeString(this.enddt);
    }

    public LicenseFileNumDTO() {
    }

    protected LicenseFileNumDTO(Parcel in) {
        this.filenum = in.readString();
        this.strtdt = in.readString();
        this.enddt = in.readString();
    }

    public static final Creator<LicenseFileNumDTO> CREATOR = new Creator<LicenseFileNumDTO>() {
        @Override
        public LicenseFileNumDTO createFromParcel(Parcel source) {
            return new LicenseFileNumDTO(source);
        }

        @Override
        public LicenseFileNumDTO[] newArray(int size) {
            return new LicenseFileNumDTO[size];
        }
    };
}
