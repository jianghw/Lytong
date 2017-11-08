package com.zantong.mobilecttx.user.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.contract.IMegTypeAtyContract;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.order.bean.MessageResponse;
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

public class MegTypeFragment extends BaseListFragment<MessageType>
        implements IMegTypeAtyContract.IMegTypeAtyView {

    private IMegTypeAtyContract.IMegTypeAtyPresenter mPresenter;

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onSubscribe();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAdapter != null) mAdapter.cleanListData();
        mPresenter.unSubscribe();
    }

    @Override
    protected void onLoadMoreData() {
        getData();
    }

    @Override
    protected void onRefreshData() {
        getData();
    }

    public static MegTypeFragment newInstance() {
        return new MegTypeFragment();
    }

    @Override
    public void setPresenter(IMegTypeAtyContract.IMegTypeAtyPresenter presenter) {
        mPresenter = presenter;
    }

    /**
     * 可下拉刷新
     *
     * @return true
     */
    @Override
    protected boolean isRefresh() {
        return true;
    }

    /**
     * 不可加载更多
     *
     * @return false
     */
    @Override
    protected boolean isLoadMore() {
        return false;
    }

    /**
     * 侧滑 可删除
     *
     * @return
     */
    @Override
    protected boolean isDeleteItem() {
        return true;
    }

    /**
     * item 点击事件 页面跳转
     *
     * @param view
     * @param data
     */
    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof MessageType)) return;

        MessageType item = (MessageType) data;
        if (TextUtils.isEmpty(item.getMessageDetailId())) {
            ToastUtils.toastShort("数据出错,请重新加载~");
            return;
        }
        MainRouter.gotoMegDetailActivity(getActivity(), item.getTitle(), item.getMessageDetailId());
    }

    /**
     * 和布局文件对应
     *
     * @return，
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
            mPresenter.deleteMessageDetail(meg, position);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainGlobal.requestCode.meg_detail_del
                && resultCode == MainGlobal.resultCode.meg_detail_del) {
            mCurrentPage = 1;//标记全部刷新
            getData();
        }
    }

    @Override
    public BaseAdapter<MessageType> createAdapter() {
        return new MegAdapter();
    }

    @Override
    protected void getData() {
        if (mPresenter != null) mPresenter.messageFindAll();
    }

    /**
     * 加载数据成功
     *
     * @param messageTypeResult
     */
    @Override
    public void findAllMessageSucceed(MessageTypeResponse messageTypeResult) {
        MessageTypeBean messageTypeBean = messageTypeResult.getData();
        if (messageTypeBean != null) {
            List<MessageType> megList = messageTypeBean.getMessageList();
            setDataResult(megList);
        } else {
            setDataResult(null);
        }
    }

    /**
     * 获取数据失败
     *
     * @param message
     */
    @Override
    public void findAllMessageError(String message) {
        onShowFailed();
        ToastUtils.toastShort(message);
    }


    @Override
    public void showLoadingDialog() {
        showDialogLoading();
    }

    @Override
    public void dismissLoadingDialog() {
        hideDialogLoading();
    }

    @Override
    public void deleteMessageDetailSucceed(MessageResponse messageResult, int position) {
        mAdapter.remove(position);
        ToastUtils.toastShort(messageResult.getResponseDesc());

        if (position == 0 && mAdapter.getAll().isEmpty()) getData();
    }

    @Override
    public void deleteMessageDetailError(String message) {
        ToastUtils.toastShort(message);
        dismissLoadingDialog();
    }

}
