package com.zantong.mobilecttx.interf;

import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.user.bean.MessageDetailResult;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IMegDetailAtyContract {

    interface IMegDetailAtyView extends IMvpView<IMegDetailAtyPresenter> {

        void onShowLoading();

        /**
         * 请求响应成功
         *
         * @param messageResult
         */
        void findMessageDetailSucceed(MessageDetailResult messageResult);

        void findMessageDetailError(String message);

        int getIdByArguments();
    }

    interface IMegDetailAtyPresenter extends IMvpPresenter {

        void findMessageDetail();

        /**
         * 初始化请求bean
         *
         * @return BaseDTO
         */
        MessageDetailDTO initMessageDetailDTO();

    }

}
