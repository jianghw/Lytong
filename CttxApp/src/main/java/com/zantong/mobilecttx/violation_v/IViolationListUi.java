package com.zantong.mobilecttx.violation_v;

import com.zantong.mobilecttx.weizhang.bean.ViolationBean;

/**
 * Created by jianghw on 2017/11/29.
 * Description:
 * Update by:
 * Update day:
 */

public interface IViolationListUi {
    void refreshListData(int position);

    void doClickPay(ViolationBean bean);

    void findIsValidAdvert();
}
