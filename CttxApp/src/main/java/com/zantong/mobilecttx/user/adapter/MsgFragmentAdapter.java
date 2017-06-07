/*******************************************************************************
 * Copyright (c) 2015 btows.com.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.zantong.mobilecttx.user.adapter;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zantong.mobilecttx.base.fragment.BaseFragment;

import java.util.List;


/**
 * Ö÷Ò³ÊÊÅäÆ÷
 */
public class MsgFragmentAdapter extends PagerAdapter {
	private Activity mContext;
	private LayoutInflater mInflater;
	private PackageManager pkgMgr;
	int flag = 0;
	private List<View> mListViews;
	private List<BaseFragment> mFragment;

	public MsgFragmentAdapter(Activity context) {
		super();
		this.mContext = context;
		mInflater = LayoutInflater.from(mContext);
		pkgMgr = mContext.getPackageManager();
		initView();
	}

	private void initView() {
//		mListViews = new ArrayList<View>();
//		View tabView1 = mInflater.inflate(R.layout.fragment_main_tab1, null);
//		View tabView2 = mInflater.inflate(R.layout.fragment_main_tab2, null);
//		mListViews.add(tabView1);
//		mListViews.add(tabView2);

//		mFragment = new ArrayList<BaseFragment>();
//		MsgUserFragment mMsgUserFragment = MsgUserFragment.getSingleInstance();
//		MsgSysFragment mMsgSysFragment = MsgSysFragment.getSingleInstance();
//		mFragment.add(mMsgUserFragment);
//		mFragment.add(mMsgSysFragment);

	}

	@Override
	public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(mListViews.get(arg1));
	}

	@Override
	public void finishUpdate(ViewGroup arg0) {
	}

	@Override
	public int getCount() {
		return mListViews.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager) container).addView(mListViews.get(position), 0);
		return mListViews.get(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == (arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

}
