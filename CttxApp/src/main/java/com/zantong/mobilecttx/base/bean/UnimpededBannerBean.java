package com.zantong.mobilecttx.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 首页返回实体
 */
public class UnimpededBannerBean implements Parcelable {

    /**
     * id : 50
     * parentId : 100
     * title : 洗车服务
     * subTitle : 更多优惠折扣
     * showType : 3
     * moduleType : 1
     * img : http://h5.liyingtong.com/alipay/img/pic_2.jpg
     * targetType : 1
     * targetPath : http://dev.liyingtong.com/h5/car_wash/car_wash.html
     * state : 1
     * sort : 1
     * clickVolume : 3
     * statisticsId : 0
     */

    private int id;
    private int parentId;
    private String title;
    private String subTitle;
    private int showType;
    private int moduleType;
    private String img;
    private int targetType;
    private String targetPath;
    private int state;
    private int sort;
    private int clickVolume;
    private int statisticsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getClickVolume() {
        return clickVolume;
    }

    public void setClickVolume(int clickVolume) {
        this.clickVolume = clickVolume;
    }

    public int getStatisticsId() {
        return statisticsId;
    }

    public void setStatisticsId(int statisticsId) {
        this.statisticsId = statisticsId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.parentId);
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeInt(this.showType);
        dest.writeInt(this.moduleType);
        dest.writeString(this.img);
        dest.writeInt(this.targetType);
        dest.writeString(this.targetPath);
        dest.writeInt(this.state);
        dest.writeInt(this.sort);
        dest.writeInt(this.clickVolume);
        dest.writeInt(this.statisticsId);
    }

    public UnimpededBannerBean() {
    }

    protected UnimpededBannerBean(Parcel in) {
        this.id = in.readInt();
        this.parentId = in.readInt();
        this.title = in.readString();
        this.subTitle = in.readString();
        this.showType = in.readInt();
        this.moduleType = in.readInt();
        this.img = in.readString();
        this.targetType = in.readInt();
        this.targetPath = in.readString();
        this.state = in.readInt();
        this.sort = in.readInt();
        this.clickVolume = in.readInt();
        this.statisticsId = in.readInt();
    }

    public static final Parcelable.Creator<UnimpededBannerBean> CREATOR = new Parcelable.Creator<UnimpededBannerBean>() {
        @Override
        public UnimpededBannerBean createFromParcel(Parcel source) {
            return new UnimpededBannerBean(source);
        }

        @Override
        public UnimpededBannerBean[] newArray(int size) {
            return new UnimpededBannerBean[size];
        }
    };
}
