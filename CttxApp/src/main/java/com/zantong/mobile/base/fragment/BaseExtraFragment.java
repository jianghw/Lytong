package com.zantong.mobile.base.fragment;

/**
 * Fragment基类
 * @author Sandy
 * create at 16/6/6 下午4:10
 */
public abstract class BaseExtraFragment extends BaseFragment {

	protected boolean reload = false;

	/**
	 * 可见时执行（第一次进入时不执行，适用于切换item，当item可见时调用）
	 */
	public void onVisible() {
	}

	/**
	 * （第一次进入时不执行，适用于切换item，当item可见需要重新加载数据时调用），使用前调用fragment.setReload(true);
	 */
	public void reloadData() {
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			onVisible();
			if (reload && isPrepared) {
				reloadData();
				reload = false;
			}
		}
	}

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public boolean getReload() {
		return reload;
	}
}
