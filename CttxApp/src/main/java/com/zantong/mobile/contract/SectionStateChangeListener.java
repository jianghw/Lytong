package com.zantong.mobile.contract;

/**
 * Created by bpncool on 2/24/2016.
 */

import com.zantong.mobile.weizhang.bean.ViolationCarInfo;

/**
 * interface to listen changes in state of sections
 */
public interface SectionStateChangeListener {
    void onSectionStateChanged(ViolationCarInfo section, boolean isOpen);
}