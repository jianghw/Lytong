package com.zantong.mobilecttx.weizhang.bean;

import com.zantong.mobilecttx.base.bean.Result;

import java.util.List;

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
public class ViolationResult {
    int untreatamt;//违章金额
    int untreatcount;//违章分数
    String untreatcent;
    List<ViolationBean> ViolationInfo;

    public int getUntreatamt() {
        return untreatamt;
    }

    public void setUntreatamt(int untreatamt) {
        this.untreatamt = untreatamt;
    }

    public int getUntreatcount() {
        return untreatcount;
    }

    public void setUntreatcount(int untreatcount) {
        this.untreatcount = untreatcount;
    }

    public List<ViolationBean> getViolationInfo() {
        return ViolationInfo;
    }

    public void setViolationInfo(List<ViolationBean> violationInfo) {
        ViolationInfo = violationInfo;
    }

    public void setUntreatcent(String untreatcent) {
        this.untreatcent = untreatcent;
    }

    public String getUntreatcent() {
        return untreatcent;
    }
}
