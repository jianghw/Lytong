package com.tzly.ctcyh.cargo.license_v;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jianghw.multi.state.layout.MultiState;
import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.bean.response.ScoreCaptchaResponse;
import com.tzly.ctcyh.cargo.bean.response.ScoreResponse;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.cargo.license_p.ILienseGradeContract;
import com.tzly.ctcyh.cargo.license_p.LienseGradePresenter;
import com.tzly.ctcyh.router.BuildConfig;
import com.tzly.ctcyh.router.base.RefreshFragment;
import com.tzly.ctcyh.router.custom.dialog.DialogMgr;
import com.tzly.ctcyh.router.util.Utils;

/**
 * 驾照查分
 */
public class LicenseCargoFragment extends RefreshFragment
        implements View.OnClickListener, ILienseGradeContract.ILienseGradeView {

    private static final String ARG_COOKIE = "ARG_COOKIE";
    private ImageView mImgQuestionNum;
    /**
     * 请输入驾驶证号
     */
    private EditText mEditLicense;
    private ImageView mImgQuestionSerial;
    /**
     * 请输入驾驶证档案编号
     */
    private EditText mEditSerial;
    /**
     * 请输入验证码
     */
    private EditText mEditVerification;
    private ImageView mImgCode;
    private ImageView mImgRefresh;
    private LinearLayout mLayTop;
    /**
     * ?
     */
    private TextView mTvScore;
    private RelativeLayout mLayMid;
    /**
     * 查    询
     */
    private Button mBtnCommit;
    private ILienseGradeContract.ILienseGradePresenter gradePresenter;
    private RelativeLayout mLayCode;
    private TextView mTvCode;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (gradePresenter != null) gradePresenter.unSubscribe();
    }

    public static LicenseCargoFragment newInstance() {
        return new LicenseCargoFragment();
    }

    @MultiState
    protected int initMultiState() {
        return MultiState.CONTENT;
    }

    /**
     * 是否可刷新
     */
    protected boolean isRefresh() {
        return false;
    }

    @Override
    public void onClick(View view) {
        int vId = view.getId();
        if (vId == R.id.img_question_num) {
            new DialogMgr(getActivity(), R.mipmap.ic_mark_jiazhao_idcard);
        } else if (vId == R.id.img_question_serial) {
            new DialogMgr(getActivity(), R.mipmap.ic_mark_driving_license);
        } else if (vId == R.id.lay_code || vId == R.id.img_refresh) {//刷新验证码
            refreshVerificationCode();
        } else if (vId == R.id.btn_commit) {
            verificationSubmitData();
        }
    }

    /**
     * 刷新验证码
     */
    private void refreshVerificationCode() {
        String serial = getmEditSerial();
        if (TextUtils.isEmpty(serial)) {
            toastShort("请填写真实的驾驶证档案编号");
            return;
        }

        if (gradePresenter != null) gradePresenter.scoresCaptcha();
    }

    /**
     * 验证提交数据
     */
    private void verificationSubmitData() {
        String license = getmEditLicense();
        if (TextUtils.isEmpty(license)) {
            toastShort("请填写真实的驾驶证号");
            return;
        }
        String serial = getmEditSerial();
        if (TextUtils.isEmpty(serial)) {
            toastShort("请填写真实的驾驶证档案编号");
            return;
        }
        String code = getmEditVerification();
        if (TextUtils.isEmpty(code)) {
            toastShort("请填写正确的验证码");
            return;
        }

        String cookie = getCookie();
        if (TextUtils.isEmpty(cookie)) {
            toastShort("验证码失效,请重新获取验证码");
            return;
        }
        if (gradePresenter != null) gradePresenter.apiScores();
    }

    @Override
    protected int fragmentView() {
        return R.layout.cargo_fragment_license_grade;
    }

    @Override
    protected void bindFragment(View fragment) {
        initView(fragment);
    }

    @Override
    protected void loadingFirstData() {
        LienseGradePresenter presenter = new LienseGradePresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);

        if (BuildConfig.isDeta) {
            mEditLicense.setText("310109198503162039");
            mEditSerial.setText("310010007285");
        } else {
            mEditLicense.requestFocus();
        }
    }

    @Override
    protected void responseData(Object response) {
        if (response instanceof ScoreCaptchaResponse) {
            ScoreCaptchaResponse captchaResponse = (ScoreCaptchaResponse) response;

            Bundle args = getArguments();
            args.putString(ARG_COOKIE, captchaResponse.getCookie());
            mTvCode.setVisibility(View.INVISIBLE);
            //图片
            String captche = captchaResponse.getCaptche();
            mImgCode.setImageBitmap(stringtoBitmap(captche));

        } else if (response instanceof ScoreResponse) {
            mLayMid.setVisibility(View.VISIBLE);
            ScoreResponse scoreResponse = (ScoreResponse) response;
            ScoreResponse.DataBean data = scoreResponse.getData();
            mTvScore.setText(data.getLjjf());
        } else
            responseError();
    }

    /**
     * 编码后的图片会有” data:image/*;base64, “标识，在进行解码时我们需要去掉这一部分，否则会导致解码失败。
     */
    public Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            string = string.split(",")[1];
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 错误显示
     */
    @Override
    public void responseError(String message) {
        toastShort(message);
    }

    public void initView(View view) {
        mImgQuestionNum = (ImageView) view.findViewById(R.id.img_question_num);
        mImgQuestionNum.setOnClickListener(this);
        mEditLicense = (EditText) view.findViewById(R.id.edit_license);
        mImgQuestionSerial = (ImageView) view.findViewById(R.id.img_question_serial);
        mImgQuestionSerial.setOnClickListener(this);
        mEditSerial = (EditText) view.findViewById(R.id.edit_serial);
        mEditVerification = (EditText) view.findViewById(R.id.edit_verification);

        mLayCode = (RelativeLayout) view.findViewById(R.id.lay_code);
        mLayCode.setOnClickListener(this);
        mImgCode = (ImageView) view.findViewById(R.id.img_code);
        mTvCode = (TextView) view.findViewById(R.id.tv_get_code);
        mImgRefresh = (ImageView) view.findViewById(R.id.img_refresh);
        mImgRefresh.setOnClickListener(this);

        mLayTop = (LinearLayout) view.findViewById(R.id.lay_top);
        mTvScore = (TextView) view.findViewById(R.id.tv_score);
        mLayMid = (RelativeLayout) view.findViewById(R.id.lay_mid);
        mBtnCommit = (Button) view.findViewById(R.id.btn_commit);
        mBtnCommit.setOnClickListener(this);
    }

    @Override
    public String getmEditLicense() {
        return mEditLicense.getText().toString().trim();
    }

    @Override
    public String getmEditSerial() {
        return mEditSerial.getText().toString().trim();
    }

    @Override
    public String getCookie() {
        return getArguments().getString(ARG_COOKIE);
    }

    @Override
    public String getmEditVerification() {
        return mEditVerification.getText().toString().trim();
    }

    @Override
    public void setPresenter(ILienseGradeContract.ILienseGradePresenter presenter) {
        gradePresenter = presenter;
    }
}