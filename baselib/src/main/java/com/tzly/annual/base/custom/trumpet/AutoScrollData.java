package com.tzly.annual.base.custom.trumpet;

/**
 * 自动滚动TextView的数据
 */
public interface AutoScrollData<T> {

    /**
     * 获取内容
     */
    public String getTextInfo(T data);

}
