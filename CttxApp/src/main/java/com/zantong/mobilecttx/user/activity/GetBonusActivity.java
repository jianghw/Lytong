package com.zantong.mobilecttx.user.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.api.CallBack;
import com.zantong.mobilecttx.api.CarApiClient;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.common.activity.CommonProblemDetailActivity;
import com.zantong.mobilecttx.common.bean.CommonProblem;
import com.zantong.mobilecttx.huodong.bean.ActivityCarResult;
import com.zantong.mobilecttx.huodong.dto.ActivityCarDTO;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.user.bean.BonusResult;
import com.zantong.mobilecttx.user.dto.BonusDTO;
import com.zantong.mobilecttx.utils.DialogMgr;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.rsa.RSAUtils;
import com.zantong.mobilecttx.wxapi.WXEntryActivity;
import com.zantong.mobilecttx.zxing.EncodingUtils;

import butterknife.Bind;
import butterknife.OnClick;
import cn.qqtheme.framework.util.LogUtils;

/**
 * 分享领红包
 *
 * @author Sandy
 *         create at 16/12/23 下午2:45
 */
public class GetBonusActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements View.OnClickListener, IBaseView {

    @Bind(R.id.mine_getbonus_code)
    ImageView mCodeImg;
    @Bind(R.id.mine_getbonus_count)
    TextView mCountText;
    @Bind(R.id.mine_getbonus_succ_count)
    TextView mSuccCountText;
    @Bind(R.id.getbonus_count_points)
    TextView mCountPoints;
    @Bind(R.id.mine_getbonus_desc)
    TextView mDesc;
    @Bind(R.id.mine_getbnus_share_desc)
    TextView mShareDesc;
    @Bind(R.id.getbonus_count_layout)
    View mBottomLayout;

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }


    @Override
    protected int getContentResId() {
        return R.layout.mine_getbonus_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.mine_getbonus_description, R.id.mine_getbnus_goto_friend})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_getbonus_description:
                CommonProblem commonProblem = new CommonProblem(1, "活动规则说明", "", "1.      活动时间：截止2017年5月31日；\n" +
                        "2.      邀请规则：每成功邀请2人送5元红包，单数忽略不计；\n" +
                        "3.      红包发放：金额按月统计，在每个月初将上月激励发放至账号绑定的畅通卡；\n" +
                        "4.      统计方法：被邀请人注册App成功绑定畅通卡即可视为邀请成功；同一张畅通卡绑定多个用户接受邀请视为绑定成功一次，红包发放给初次绑卡成功时的邀请人；\n" +
                        "5. 在法律允许的范围内本公司对活动具有最终解释权；");
                Intent intent = new Intent(this, CommonProblemDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("commonproblem", commonProblem);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.mine_getbnus_goto_friend:
                clickShare();
                break;
        }

    }

    @Override
    public void initView() {
        setTitleText("推荐领积分");
//        StringFormatUtils spanStr = new StringFormatUtils(this, "每成功邀请2人，即可获得5元红包",
//                "5元红包", R.color.red).fillColor();
//        mDesc.setText(spanStr.getResult());
        mDesc.setText("用户名："+ PublicData.getInstance().mLoginInfoBean.getNickname());
        mShareDesc.setText(Html.fromHtml(getString(R.string.getbouns_share_desc)));
        getSignStatus();
    }

    //获取活动报名状态
    private void getSignStatus() {
        ActivityCarDTO activityCarDTO = new ActivityCarDTO();
        activityCarDTO.setUsrnum(PublicData.getInstance().userID);
        CarApiClient.getActivityCar(this, activityCarDTO, new CallBack<ActivityCarResult>() {
            @Override
            public void onSuccess(ActivityCarResult result) {
                if (result.getResponseCode() == 2000 && !TextUtils.isEmpty(result.getData().getPlateNo())) {
                    mBottomLayout.setVisibility(View.VISIBLE);
                    mShareDesc.setVisibility(View.VISIBLE);
                }
            }

        });
    }
    @Override
    public void initData() {
        getCode();
        getBonus();
    }

    private void getCode() {
        String contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        if (PublicData.getInstance().loginFlag) {
            contentString = "http://liyingtong.com:8081/h5/share/share.html?phoneNum="
                    + Des3.encode(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        } else {
            contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }

        if (!contentString.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, 350, 350,
                    BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));
            mCodeImg.setImageBitmap(qrCodeBitmap);
        } else {
            Toast.makeText(this, "Text can not be empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void getBonus() {
        BonusDTO dto = new BonusDTO();
        String phone = PublicData.getInstance().mLoginInfoBean.getPhoenum();
        dto.setUserPhone(RSAUtils.strByEncryptionLiYing(this, phone, true));
        CarApiClient.getBonusInfo(this, dto, new CallBack<BonusResult>() {
            @Override
            public void onSuccess(BonusResult result) {
                if (result.getResponseCode() == 2000) {
                    mCountText.setText(result.getData().getSharecount());
                    mSuccCountText.setText(result.getData().getSuccesscount());
                    int count = Integer.valueOf(result.getData().getSharecount());
                    int succCount = Integer.valueOf(result.getData().getSuccesscount());
                    mCountPoints.setText(Integer.valueOf(200*count+500*succCount)+"分");
                } else if (result.getResponseCode() == 4000) {
                    LogUtils.i("code:" + result.getResponseDesc());
                    mCountText.setText("0");
                    mSuccCountText.setText("0");
                    mCountPoints.setText("0.0");
                } else {
                    LogUtils.i("code:" + result.getResponseDesc());
                }

            }
        });
    }

    private void clickShare(){
        new DialogMgr(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(0);
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare(1);
            }
        });
    }

    /**
     * 微信分享
     * @param flag(0:分享到微信好友，1：分享到微信朋友圈)
     */
    private void wechatShare(int flag){
        IWXAPI api = WXAPIFactory.createWXAPI(this, WXEntryActivity.APP_ID, true);
        api.registerApp(WXEntryActivity.APP_ID);

        if (!api.isWXAppInstalled()) {
            ToastUtils.showShort(this, "您还未安装微信客户端");
            return;
        }

        WXWebpageObject webpage = new WXWebpageObject();
        if(PublicData.getInstance().loginFlag){
            webpage.webpageUrl = "http://liyingtong.com:8081/h5/share/share.html?phoneNum="
                    + Des3.encode(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        }else{
            webpage.webpageUrl = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "掌上违章缴费，销分一步到位，与你只有一个App的距离";
        msg.description = "畅通车友会——有我在手，一路畅通畅通车友会由工银安盛与中国工商银行" +
                "上海分行联手打造，旨在为牡丹畅通卡用户提供便捷的驾乘金融服务体验。功能覆盖了" +
                "交通违章缴费、驾乘人员保险保障、特色增值服务等多项方便快捷的在线服务，" +
                "使车主的驾车生活更便捷、更丰富，更畅通!";
        //这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_sharelogo);
        msg.setThumbImage(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }
}
