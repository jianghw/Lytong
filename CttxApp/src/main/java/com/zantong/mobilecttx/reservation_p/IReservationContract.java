package com.zantong.mobilecttx.reservation_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;

/**
 * Created by jianghw on 2017/10/12.
 * Description:预约 列表
 * Update by:
 * Update day:
 */

public interface IReservationContract {

    interface IReservationView extends IBaseView<IReservationPresenter>, IResponseView {
    }

    interface IReservationPresenter extends IBasePresenter {

        public void getBespeakList();
    }

}
