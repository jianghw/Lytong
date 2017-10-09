package com.tzly.annual.base.custom.trumpet;

/**
 * 自动滚动TextView的数据
 */
public interface IScrollTextData<T> {

    /**
     * 获取内容
     */
    String getTextInfo(T data);

}
