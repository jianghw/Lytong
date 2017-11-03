package com.zantong.mobilecttx.user.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jcodecraeer.xrecyclerview.BaseAdapter;
import com.tzly.ctcyh.router.util.ToastUtils;
import com.tzly.ctcyh.router.util.rea.RSAUtils;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.application.LoginData;
import com.zantong.mobilecttx.base.fragment.BaseListFragment;
import com.zantong.mobilecttx.contract.IMegSecondLevelAtyContract;
import com.zantong.mobilecttx.global.MainGlobal;
import com.zantong.mobilecttx.order.bean.MessageBean;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.router.MainRouter;
import com.zantong.mobilecttx.user.adapter.MegSecondLevelAdapter;
import com.zantong.mobilecttx.user.bean.Meg;
import com.zantong.mobilecttx.user.dto.MegDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息分类第二步
 */

public class MegSecondLevelFragment extends BaseListFragment<Meg>
        implements IMegSecondLevelAtyContract.IMegSecondLevelAtyView {

    private static String STR_TYPE = "id";
    private static String STR_TITLE = "title";
    private IMegSecondLevelAtyContract.IMegSecondLevelAtyPresenter mPresenter;

    public static MegSecondLevelFragment newInstance(String id, String type) {
        MegSecondLevelFragment fragment = new MegSecondLevelFragment();
        Bundle bundle = new Bundle();
        bundle.putString(STR_TYPE, id);
        bundle.putString(STR_TITLE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onLoadMoreData() {
        getData();
    }

    @Override
    protected void onRefreshData() {
        getData();
    }

    @Override
    public void setPresenter(IMegSecondLevelAtyContract.IMegSecondLevelAtyPresenter presenter) {
        mPresenter = presenter;
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
     * 和布局文件对应
     *
     * @return
     */
    @Override
    protected int resetDeleteItemHeight() {
        return getResources().getDimensionPixelSize(R.dimen.ds_200);
    }

    @Override
    protected void onRecyclerItemClick(View view, Object data) {
        if (!(data instanceof Meg)) return;
        Meg item = (Meg) data;
        MainRouter.gotoMegDetailActivity(
                getActivity(), getArguments().getString(STR_TITLE), item.getMessageDetailId());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainGlobal.requestCode.meg_detail_del
                && resultCode == MainGlobal.resultCode.meg_detail_deled) {
            mCurrentPage = 1;//标记全部刷新
            getData();
            setResultForRefresh();
        }
    }

    private void setResultForRefresh() {
        getActivity().setResult(MainGlobal.resultCode.meg_detail_deled);
    }

    /**
     * 删除点击
     */
    @Override
    protected void onSwipeItemClickListener(int adapterPosition, int menuPosition, int direction) {
        ArrayList<Meg> messageList = mAdapter.getAll();
        int position = adapterPosition - 1;
        if (messageList != null && messageList.size() >= adapterPosition && position >= 0) {
            Meg meg = messageList.get(position);
            mPresenter.deleteMessageDetail(meg, position);
        }
    }

    @Override
    protected void getData() {
        if (mPresenter != null) mPresenter.findMessageDetailByMessageId();
    }

    @Override
    public BaseAdapter<Meg> createAdapter() {
        return new MegSecondLevelAdapter();
    }

    /**
     * @deprecated 不用
     */
    private void getMsgList() {
        MegDTO dto = new MegDTO();
        dto.setUsrId(RSAUtils.strByEncryption(LoginData.getInstance().userID, true));
        String id = getArguments().getString(STR_TYPE);
        dto.setId(id);
        CarApiClient.getMsgList(this.getActivity(), dto, new CallBack<MessageResponse>() {
            @Override
            public void onSuccess(MessageResponse result) {
                if (result.getResponseCode() == 2000) {
                    setDataResult(result.getData().getMessageDetailList());
                }
            }
        });
    }

    @Override
    public String getIdByArguments() {
        return getArguments().getString(STR_TYPE);
    }

    /**
     * 数据成功
     *
     * @param messageResult
     */
    @Override
    public void findMessageDetailByMessageIdSucceed(MessageResponse messageResult) {
        MessageBean messageBean = messageResult.getData();
        if (messageBean != null) {
            List<Meg> megList = messageBean.getMessageDetailList();
            mPresenter.processingDataFiltrate(megList);
        } else {
            setDataResult(null);
        }
    }

    /**
     * 设置显示数据
     *
     * @param megList
     */
    @Override
    public void setListDataResult(List<Meg> megList) {
        setDataResult(megList);
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
    public void findMessageDetailByMessageIdError(String message) {
        onShowFailed();
        ToastUtils.toastShort(message);
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
