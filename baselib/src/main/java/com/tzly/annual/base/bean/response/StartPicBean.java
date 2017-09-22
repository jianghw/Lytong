package com.tzly.annual.base.bean.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jianghw on 2017/6/1.
 * Description: 首页获取图片
 * Update by:
 * Update day:
 */

public class StartPicBean implements Parcelable {

    /**
     * id : 2
     * picNum : 1
     * picState : 1
     * picUrl : http://pic55.nipic.com/file/20141208/19462408_171130083000_2.jpg
     */

    private int id;
    private int picNum;
    private int picState;
    private String picUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public int getPicState() {
        return picState;
    }

    public void setPicState(int picState) {
        this.picState = picState;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.picNum);
        dest.writeInt(this.picState);
        dest.writeString(this.picUrl);
    }

    public StartPicBean() {
    }

    protected StartPicBean(Parcel in) {
        this.id = in.readInt();
        this.picNum = in.readInt();
        this.picState = in.readInt();
        this.picUrl = in.readString();
    }

    public static final Parcelable.Creator<StartPicBean> CREATOR = new Parcelable.Creator<StartPicBean>() {
        @Override
        public StartPicBean createFromParcel(Parcel source) {
            return new StartPicBean(source);
        }

        @Override
        public StartPicBean[] newArray(int size) {
            return new StartPicBean[size];
        }
    };
}
