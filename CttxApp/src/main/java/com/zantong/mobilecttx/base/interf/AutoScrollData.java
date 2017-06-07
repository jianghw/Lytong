package com.zantong.mobilecttx.base.interf;

/**
 * 自动滚动TextView的数据
 */
public interface AutoScrollData<T> {

	/**
	 * 获取内容
	 * 
	 * @param data
	 * @return
	 */
	public String getTextInfo(T data);

}
