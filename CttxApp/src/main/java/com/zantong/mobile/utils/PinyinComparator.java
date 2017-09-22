package com.zantong.mobile.utils;

import com.zantong.mobile.car.bean.CarBrandBean;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class PinyinComparator implements Comparator<CarBrandBean> {

	public int compare(CarBrandBean o1, CarBrandBean o2) {
		if (o1.getBrandPinYin().equals("@")
				|| o2.getBrandPinYin().equals("#")) {
			return -1;
		} else if (o1.getBrandPinYin().equals("#")
				|| o2.getBrandPinYin().equals("@")) {
			return 1;
		} else {
			return o1.getBrandPinYin().compareTo(o2.getBrandPinYin());
		}
	}

}
