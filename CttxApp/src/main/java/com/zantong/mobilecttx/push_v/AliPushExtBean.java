package com.zantong.mobilecttx.push_v;

/**
 * Created by zhoujie on 2016/11/21.
 */

public class AliPushExtBean {

    /**
     * type : 2
     * id : 10576
     * _ALIYUN_NOTIFICATION_ID_ : 253188
     */

    private String type;
    private String id;
    private String url;
    private String _ALIYUN_NOTIFICATION_ID_;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_ALIYUN_NOTIFICATION_ID_() {
        return _ALIYUN_NOTIFICATION_ID_;
    }

    public void set_ALIYUN_NOTIFICATION_ID_(String _ALIYUN_NOTIFICATION_ID_) {
        this._ALIYUN_NOTIFICATION_ID_ = _ALIYUN_NOTIFICATION_ID_;
    }
}