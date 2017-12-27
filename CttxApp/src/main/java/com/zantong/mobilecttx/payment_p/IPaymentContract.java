package com.zantong.mobilecttx.payment_p;

import com.tzly.ctcyh.router.base.IBasePresenter;
import com.tzly.ctcyh.router.base.IBaseView;
import com.tzly.ctcyh.router.base.IResponseView;
import com.zantong.mobilecttx.weizhang.bean.RspInfoBean;
import com.zantong.mobilecttx.weizhang.dto.LicenseFileNumDTO;

import java.util.List;

import drawthink.expandablerecyclerview.bean.RecyclerViewData;

/**
 * Created by jianghw on 2017/10/12.
 * Description:优惠劵列表
 * Update by:
 * Update day:
 */

public interface IPaymentContract {

    interface IPaymentView extends IBaseView<IPaymentPresenter>, IResponseView {
        LicenseFileNumDTO licenseFileNumDTO();

        void processingSucceed(List<RecyclerViewData> mCarDatas);

        void dataProcessingError(String message);
    }

    interface IPaymentPresenter extends IBasePresenter {
        void bank_v001_01();

        public String initLicenseFileNumDTO();

        void dataProcessing(List<RspInfoBean.ViolationInfoBean> mTempDatas, String s);
    }

}
