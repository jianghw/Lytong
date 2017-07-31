package com.zantong.mobilecttx.contract;
import com.zantong.mobilecttx.base.interf.IMvpPresenter;
import com.zantong.mobilecttx.base.interf.IMvpView;
import com.zantong.mobilecttx.user.bean.Meg;
import com.zantong.mobilecttx.order.bean.MessageResult;
import com.zantong.mobilecttx.user.dto.MegDTO;

import java.util.List;

/**
 * Created by jianghw on 2017/4/26.
 */

public interface IMegSecondLevelAtyContract {

    interface IMegSecondLevelAtyView extends IMvpView<IMegSecondLevelAtyPresenter> {

        void onShowLoading();

        /**
         * 请求响应成功
         *
         * @param messageResult
         */
        void findMessageDetailByMessageIdSucceed(MessageResult messageResult);

        void findMessageDetailByMessageIdError(String message);

        void deleteMessageDetailSucceed(MessageResult messageResult, int position);

        void deleteMessageDetailError(String message);

        String getIdByArguments();

        /**
         * 设置要显示数据
         *
         * @param megList
         */
        void setListDataResult(List<Meg> megList);

        void showLoadingDialog();

        void dismissLoadingDialog();
    }

    interface IMegSecondLevelAtyPresenter extends IMvpPresenter {

        void findMessageDetailByMessageId();

        /**
         * 初始化请求bean
         *
         * @return BaseDTO
         */
        MegDTO initMegDTO();

        MegDTO initDeleteMegDTO(Meg meg);

        /**
         * 数据处理
         *
         * @param megList
         */
        void processingDataFiltrate(List<Meg> megList);

        /**
         * 删除消息数据
         *
         * @param meg
         * @param position
         */
        void deleteMessageDetail(Meg meg, int position);
    }

}
