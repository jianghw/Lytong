package com.zantong.mobile.contract;

import com.zantong.mobile.base.dto.BaseDTO;
import com.zantong.mobile.base.interf.IMvpPresenter;
import com.zantong.mobile.base.interf.IMvpView;
import com.zantong.mobile.order.bean.MessageResult;
import com.zantong.mobile.user.bean.MessageType;
import com.zantong.mobile.user.bean.MessageTypeResult;
import com.zantong.mobile.user.dto.MegDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IMegTypeAtyContract {

    interface IMegTypeAtyView extends IMvpView<IMegTypeAtyPresenter> {

        void onShowLoading();

        /**
         * 请求响应成功
         *
         * @param messageTypeResult
         */
        void findAllMessageSucceed(MessageTypeResult messageTypeResult);

        void findAllMessageError(String message);

        void showLoadingDialog();

        void dismissLoadingDialog();

        void deleteMessageDetailSucceed(MessageResult messageResult, int position);

        void deleteMessageDetailError(String message);
    }

    interface IMegTypeAtyPresenter extends IMvpPresenter {

        void messageFindAll();

        /**
         * 初始化请求bean
         *
         * @return BaseDTO
         */
        BaseDTO initBaseDTO();

        /**
         * 删除消息数据
         *
         * @param meg
         * @param position
         */
        void deleteMessageDetail(MessageType meg, int position);

        MegDTO initDeleteMegDTO(MessageType meg);
    }

}
