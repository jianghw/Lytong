package com.zantong.mobilecttx.weizhang.bean;

import cn.qqtheme.framework.bean.BankResponse;

/**
 *
 * @author zyb
 *
 *  违章查询结果
 * 2017-05-08 17:50:15
 *    *  *   *  *
 *  *      *      *
 *  *             *
 *   *           *
 *      *     *
 *         *
 *
 *
 * create at 17/5/8 下午5:50
 */
public class ViolationResultParent extends BankResponse {

    ViolationResult RspInfo;//违章金额

    public void setRspInfo(ViolationResult rspInfo) {
        RspInfo = rspInfo;
    }

    public ViolationResult getRspInfo() {
        return RspInfo;
    }
}
