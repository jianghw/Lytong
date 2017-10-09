package com.zantong.mobile.msg_v;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.zantong.mobile.R;
import com.zantong.mobile.api.CallBack;
import com.zantong.mobile.api.CarApiClient;
import com.zantong.mobile.base.dto.BaseDTO;
import com.zantong.mobile.base.fragment.BaseListFragment;
import com.zantong.mobile.application.MemoryData;
import com.zantong.mobile.contract.IMegTypeAtyContract;
import com.zantong.mobile.user.adapter.MegAdapter;
import com.zantong.mobile.order.bean.MessageResult;
import com.zantong.mobile.user.bean.MessageType;
import com.zantong.mobile.user.bean.MessageTypeBean;
import com.zantong.mobile.user.bean.MessageTypeResult;
import com.tzly.annual.base.util.ToastUtils;
import com.zantong.mobile.utils.rsa.RSAUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息类别列表
 */

public class MegTypeFragment extends BaseListFragment<MessageType>
        implements IMegTypeAtyContract.IMegTypeAtyView {

    public static final int MESSAGE_REQUEST_CODE = 200;
    public static final int MESSAGE_RESULT_CODE = 400;
    private IMegTypeAtyContract.IMegTypeAtyPresenter mPresenter;

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
        Intent intent = new Intent(getActivity(), MegDetailActivity.class);
        intent.putExtra("messageDetailId", item.getMessageDetailId());
        intent.putExtra("title", item.getTitle());
        startActivityForResult(intent, MESSAGE_REQUEST_CODE);
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
        if (requestCode == MESSAGE_REQUEST_CODE && resultCode == MESSAGE_RESULT_CODE) {
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
     * 获取消息类别列表
     *
     * @deprecated 不用
     */
    private void getMsgTypeList() {
        BaseDTO dto = new BaseDTO();
        dto.setUsrId(RSAUtils.strByEncryption(MemoryData.getInstance().userID, true));
        CarApiClient.getMsgTypeList(this.getActivity(), dto, new CallBack<MessageTypeResult>() {
            @Override
            public void onSuccess(MessageTypeResult result) {
                if (result.getResponseCode() == 2000) {
                    setDataResult(result.getData().getMessageList());
                }
            }
        });
    }

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

    /**
     * 加载数据成功
     *
     * @param messageTypeResult
     */
    @Override
    public void findAllMessageSucceed(MessageTypeResult messageTypeResult) {
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
    public void deleteMessageDetailSucceed(MessageResult messageResult, int position) {
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
