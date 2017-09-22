package com.zantong.mobile.user.fragment;

import android.view.View;

import com.zantong.mobile.user.adapter.MsgAdapter;
import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobile.base.fragment.BaseListFragment;
import com.zantong.mobile.user.bean.MsgBean;

import java.util.ArrayList;

/**
 * 系统消息页面
 * @author Sandy
 * create at 16/6/6 下午4:08
 */
public class MsgSysFragment extends BaseListFragment<MsgBean> {

//    static MsgSysFragment mSingleFragment;
//
//    private MsgSysFragment(){
//
//    }
//    public static MsgSysFragment getSingleInstance(){
//        if (mSingleFragment == null){
//            synchronized (MsgSysFragment.class){
//                if (mSingleFragment == null){
//                    mSingleFragment = new MsgSysFragment();
//                }
//            }
//        }
//        return mSingleFragment;
//    }


    @Override
    protected void onLoadMoreData() {

    }

    @Override
    protected void onRefreshData() {

    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {

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
//            bean.setMsg_title("系统消息标题"+i);
//            bean.setMsg_content("系统消息内容" + i);
//            list.add(bean);
//        }
        setDataResult(list);
    }
}
