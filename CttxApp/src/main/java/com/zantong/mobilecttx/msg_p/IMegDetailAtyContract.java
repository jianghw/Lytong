package com.zantong.mobilecttx.msg_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;
import com.zantong.mobilecttx.user.dto.MessageDetailDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IMegDetailAtyContract {

    interface IMegDetailAtyView extends IBaseView<IMegDetailAtyPresenter>,IResponseView {

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
