package com.zantong.mobilecttx.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zantong.mobilecttx.base.bean.BaseResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class StartPicResult extends BaseResult implements Parcelable {

    private List<StartPicBean> data;

    public void setData(List<StartPicBean> data) {
        this.data = data;
    }

    public List<StartPicBean> getData() {
        return data;
    }

    public StartPicResult() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    protected StartPicResult(Parcel in) {
        this.data = new ArrayList<StartPicBean>();
        in.readList(this.data, StartPicBean.class.getClassLoader());
    }

    public static final Creator<StartPicResult> CREATOR = new Creator<StartPicResult>() {
        @Override
        public StartPicResult createFromParcel(Parcel source) {
            return new StartPicResult(source);
        }

        @Override
        public StartPicResult[] newArray(int size) {
            return new StartPicResult[size];
        }
    };
}
