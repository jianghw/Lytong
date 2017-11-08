package com.zantong.mobilecttx.msg_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.zantong.mobilecttx.user.bean.MessageDetailResponse;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IMegDetailAtyContract {

    interface IMegDetailAtyView extends IBaseView<IMegDetailAtyPresenter> {
        /**
         * 请求响应成功
         *
         * @param messageResult
         */
        void findMessageDetailSucceed(MessageDetailResponse messageResult);

        void findMessageDetailError(String message);

        int getIdByArguments();
    }

    interface IMegDetailAtyPresenter extends IBasePresenter {

        void findMessageDetail();

        /**
         * 初始化请求bean
         *
         * @return BaseDTO
         */
        MessageDetailDTO initMessageDetailDTO();

    }

}
