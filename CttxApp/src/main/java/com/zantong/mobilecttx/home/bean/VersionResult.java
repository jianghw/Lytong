package com.zantong.mobilecttx.home.bean;

import cn.qqtheme.framework.contract.bean.BaseResult;

/**
 * 版本更新响应
 */
public class VersionResult extends BaseResult {
    private Version data;

    public Version getData() {
        return data;
    }

    public void setData(Version data) {
        this.data = data;
    }

    public class Version {
        private String address;
        private int isUpdate;//是否强制更新1是 2否
        private String version; //
        private int versionType;
        private int id;

        public int getVersionType() {
            return versionType;
        }

        public void setVersionType(int versionType) {
            this.versionType = versionType;
        }

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
