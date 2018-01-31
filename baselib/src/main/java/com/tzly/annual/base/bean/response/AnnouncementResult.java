package com.tzly.annual.base.bean.response;

import com.tzly.annual.base.bean.BaseResult;

import java.util.List;

/**
 * Created by jianghw on 18-1-31.
 */

public class AnnouncementResult extends BaseResult {
    private List<AnnouncementBean> data;

    public void setData(List<AnnouncementBean> data) {
        this.data = data;
    }

    public List<AnnouncementBean> getData() {
        return data;
    }
}
