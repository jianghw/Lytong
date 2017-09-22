package com.zantong.mobile.order.bean;

import java.util.List;

/**
 * Created by jianghw on 2017/9/7.
 * Description:
 * Update by:
 * Update day:
 */
public class ChildrenBeanX {
    /**
     * code : 110100
     * children : [{"code":110101,"name":"东城区"},{"code":110102,"name":"西城区"},{"code":110105,"name":"朝阳区"},{"code":110106,"name":"丰台区"},{"code":110107,"name":"石景山区"},{"code":110108,"name":"海淀区"},{"code":110109,"name":"门头沟区"},{"code":110111,"name":"房山区"},{"code":110112,"name":"通州区"},{"code":110113,"name":"顺义区"},{"code":110114,"name":"昌平区"},{"code":110115,"name":"大兴区"},{"code":110116,"name":"怀柔区"},{"code":110117,"name":"平谷区"},{"code":110228,"name":"密云县"},{"code":110229,"name":"延庆县"}]
     * name : 北京市
     */

    private int code;
    private String name;
    private List<ChildrenBean> children;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

}
