package com.zantong.mobilecttx.msg_v;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.base.RecyclerListFragment;
import com.tzly.ctcyh.router.util.Utils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.application.Injection;
import com.zantong.mobilecttx.contract.IMegTypeAtyContract;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.presenter.MegTypeAtyPresenter;
import com.zantong.mobilecttx.push_v.PushTipService;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.adapter.MegAdapter;
import com.zantong.mobilecttx.user.bean.MessageType;
import com.zantong.mobilecttx.user.bean.MessageTypeBean;
import com.zantong.mobilecttx.user.bean.MessageTypeResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息类别列表
 */

public class MegTypeFragment extends RecyclerListFragment<MessageType>
        implements IMegTypeAtyContract.IMegTypeAtyView {

    private IMegTypeAtyContract.IMegTypeAtyPresenter mPresenter;


    @Override
    public BaseAdapter<MessageType> createAdapter() {
        return new MegAdapter();
    }

    public static MegTypeFragment newInstance() {
        return new MegTypeFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) mAdapter.cleanListData();
        mPresenter.unSubscribe();
    }

    @Override
    protected void initPresenter() {
        MegTypeAtyPresenter mPresenter = new MegTypeAtyPresenter(
                Injection.provideRepository(Utils.getContext()), this);
    }

    @Override
    public void setPresenter(IMegTypeAtyContract.IMegTypeAtyPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 侧滑 可删除
     */
    @Override
    protected boolean isDeleteItem() {
        return true;
    }

    /**
     * item 点击事件 页面跳转
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof MessageType)) return;

        MessageType item = (MessageType) data;
        if (TextUtils.isEmpty(item.getMessageDetailId()))
            toastShort("数据出错,请重新加载~");
        else
            MainRouter.gotoMegDetailActivity(getActivity(),
                    item.getTitle(), item.getMessageDetailId());
    }

    /**
     * 和布局文件对应
     */
    @Override
    protected int resetDeleteItemHeight() {
        return getResources().getDimensionPixelSize(R.dimen.ds_215);
    }

    /**
     * 删除点击
     */
    @Override
    protected void onSwipeItemClickListener(int adapterPosition, int menuPosition, int direction) {
        ArrayList<MessageType> messageList = mAdapter.getAll();
        int position = adapterPosition - 1;
        if (messageList != null && messageList.size() >= adapterPosition && position >= 0) {
            MessageType meg = messageList.get(position);
            if (mPresenter != null) mPresenter.deleteMessageDetail(meg, position);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainGlobal.requestCode.meg_detail_del
                && resultCode == MainGlobal.resultCode.meg_detail_del) {
            loadingFirstData();
            //主页小红点
            Intent i = new Intent(getActivity(), PushTipService.class);
            i.setAction("com.custom.service.push.tip");
            getActivity().startService(i);
        }
    }

    @Override
    protected void loadingFirstData() {
        if (mPresenter != null) mPresenter.messageFindAll();
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof MessageTypeResponse) {
            MessageTypeResponse typeResponse = (MessageTypeResponse) response;
            MessageTypeBean typeBean = typeResponse.getData();
            List<MessageType> megList = typeBean.getMessageList();
            setSimpleDataResult(megList);
        } else
            responseError();
    }

    @Override
    public void deleteMessageDetailSucceed(MessageResponse messageResult, int position) {
        mAdapter.remove(position);
        toastShort(messageResult.getResponseDesc());

        if (position == 0 && mAdapter.getAll().isEmpty()) loadingFirstData();
    }

    @Override
    public void deleteMessageDetailError(String message) {
        toastShort(message);
    }

}
