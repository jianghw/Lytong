package com.zantong.mobilecttx.home.bean;

import com.zantong.mobilecttx.base.bean.BaseResult;

/**
 * Created by zhengyingbing on 16/6/1.
 */
public class VersionResult extends BaseResult {
    public Version data;

    public Version getData() {
        return data;
    }

    public void setData(Version data) {
        this.data = data;
    }

    public class Version{
        public String address;
        public int isUpdate;// 0 表示不强制
        public String version; //

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getIsUpdate() {
            return isUpdate;
        }

        public void setIsUpdate(int isUpdate) {
            this.isUpdate = isUpdate;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
