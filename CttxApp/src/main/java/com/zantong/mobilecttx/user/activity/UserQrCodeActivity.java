package com.zantong.mobilecttx.user.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zantong.mobilecttx.common.PublicData;
import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.base.activity.BaseMvpActivity;
import com.zantong.mobilecttx.base.interf.IBaseView;
import com.zantong.mobilecttx.presenter.HelpPresenter;
import com.zantong.mobilecttx.utils.rsa.Des3;
import com.zantong.mobilecttx.utils.StringUtils;
import com.zantong.mobilecttx.utils.Tools;
import com.zantong.mobilecttx.zxing.EncodingUtils;

import butterknife.Bind;

/**
 * 我的二维码
 * Created by zhoujie on 2016/12/1.
 */
public class UserQrCodeActivity extends BaseMvpActivity<IBaseView, HelpPresenter> implements IBaseView {


    @Bind(R.id.user_qr_code_nickname)
    TextView mName;
    @Bind(R.id.user_qr_code_phone)
    TextView mPhoneNum;
    @Bind(R.id.user_qr_code_img)
    ImageView mQrCode;

    @Override
    public void initView() {
        setTitleText("我的二维码");
    }

    @Override
    public void initData() {
        if (PublicData.getInstance().loginFlag){
            if (!Tools.isStrEmpty(PublicData.getInstance().mLoginInfoBean.getNickname())) {
                mName.setText(PublicData.getInstance().mLoginInfoBean.getNickname());
            } else {
                mName.setText(PublicData.getInstance().mLoginInfoBean.getPhoenum().substring(7));
            }
            String phone = StringUtils.getEncrypPhone(PublicData.getInstance().mLoginInfoBean.getPhoenum());
            mPhoneNum.setText(phone);
        }
        getCode();
    }

    @Override
    public HelpPresenter initPresenter() {
        return new HelpPresenter();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_user_qr_code;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void getCode() {
        String contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        if (PublicData.getInstance().loginFlag){
            contentString = "http://liyingtong.com:8081/h5/share/share.html?phoneNum="
                    + Des3.decode(PublicData.getInstance().mLoginInfoBean.getPhoenum());
        }else{
            contentString = "http://a.app.qq.com/o/simple.jsp?pkgname=com.zantong.mobilecttx";
        }

        if (!contentString.equals("")) {
            //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
            Bitmap qrCodeBitmap = EncodingUtils.createQRCode(contentString, 350, 350,
                            BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon));
            mQrCode.setImageBitmap(qrCodeBitmap);
        } else {
            Toast.makeText(this, "Text can not be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
