package com.zantong.mobilecttx.contract;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;
import com.zantong.mobilecttx.base.dto.BaseDTO;
import com.zantong.mobilecttx.order.bean.MessageResponse;
import com.zantong.mobilecttx.user.bean.MessageType;
import com.zantong.mobilecttx.user.dto.MegDTO;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IMegTypeAtyContract {

    interface IMegTypeAtyView extends IBaseView<IMegTypeAtyPresenter> ,IResponseView{

        void deleteMessageDetailSucceed(MessageResponse messageResult, int position);

        void deleteMessageDetailError(String message);
    }

    interface IMegTypeAtyPresenter extends IBasePresenter {

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
