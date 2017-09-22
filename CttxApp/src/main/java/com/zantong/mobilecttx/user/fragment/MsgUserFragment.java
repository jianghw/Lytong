package com.zantong.mobilecttx.user.fragment;

import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.user.adapter.MsgAdapter;
import com.zantong.mobilecttx.user.bean.MsgBean;

import java.util.ArrayList;

import com.tzly.annual.base.util.ToastUtils;

/**
 * 用户消息页面
 * @author Sandy
 * create at 16/6/6 下午4:08
 */
public class MsgUserFragment extends BaseListFragment<MsgBean> {

    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        MsgBean msg = (MsgBean)data;
        ToastUtils.showShort(this.getActivity(),msg.getMsg_title());
    }

    @Override
    public BaseAdapter<MsgBean> createAdapter() {
        return new MsgAdapter();
    }

    @Override
    public void initData() {
        super.initData();
        ArrayList<MsgBean> list = new ArrayList<MsgBean>();
//        for (int i = 0; i < 20 ;i++){
//            MsgBean bean = new MsgBean();
//            bean.setMsg_title("用户消息标题"+i);
//            bean.setMsg_content("用户消息内容" + i);
//            list.add(bean);
//        }
        setDataResult(list);
    }

    @Override
    protected boolean isRefresh() {
        return true;
    }

    @Override
    protected boolean isLoadMore() {
        return false;
    }
}
