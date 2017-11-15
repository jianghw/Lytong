package com.zantong.mobilecttx.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.tzly.ctcyh.router.bean.BaseResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianghw on 2017/6/1.
 * Description:
 * Update by:
 * Update day:
 */

public class StartPicResponse extends BaseResponse implements Parcelable {

    private List<StartPicBean> data;

    public void setData(List<StartPicBean> data) {
        this.data = data;
    }

    public List<StartPicBean> getData() {
        return data;
    }

    public StartPicResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    protected StartPicResponse(Parcel in) {
        this.data = new ArrayList<StartPicBean>();
        in.readList(this.data, StartPicBean.class.getClassLoader());
    }

    public static final Creator<StartPicResponse> CREATOR = new Creator<StartPicResponse>() {
        @Override
        public StartPicResponse createFromParcel(Parcel source) {
            return new StartPicResponse(source);
        }

        @Override
        public StartPicResponse[] newArray(int size) {
            return new StartPicResponse[size];
        }
    };
}
