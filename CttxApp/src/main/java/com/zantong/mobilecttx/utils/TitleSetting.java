package com.zantong.mobilecttx.utils;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tzly.ctcyh.router.util.Tools;
import com.zantong.mobilecttx.R;


/**
 * Created by Administrator on 2016/5/26.
 */
public class TitleSetting {

    private ImageView backBtn;//回退图片
    private TextView tv_back;//回退汉字
    private TextView titleTv;//主标题
    private TextView subtitle;//副标题
    private Button btn_right;//标题右面的按钮
    private RelativeLayout rl_image_right;//标题右面区域
    private RelativeLayout back;//标题背景
    private ImageView image_right;//标题右面的图片
    private TextView text_right;//标题右面的文字

    private static TitleSetting instance;

    public static TitleSetting getInstance(){
        if(null==instance){
            instance=new TitleSetting();
        }
        return instance;
    }

    private TitleSetting(){
    }
    public void initTitle(Activity mActivity, String titles, int backBtnImage, String tvback, String subtitles, String textRight){
        this.initTitle(mActivity, titles, backBtnImage, tvback, subtitles, textRight, 0);
    }
    public void initTitle(Activity mActivity, String titles, int backBtnImage, String tvback, String subtitles, String textRight, int backgroundRes){

//        backBtn = (ImageView) mActivity.findViewById(R.id.backBtn);
        tv_back = (TextView) mActivity.findViewById(R.id.tv_back);
        titleTv = (TextView) mActivity.findViewById(R.id.titleTv);
        subtitle = (TextView) mActivity.findViewById(R.id.subtitle);
        btn_right = (Button) mActivity.findViewById(R.id.btn_right);
        rl_image_right = (RelativeLayout) mActivity.findViewById(R.id.rl_image_right);
        back = (RelativeLayout) mActivity.findViewById(R.id.back);
        image_right = (ImageView) mActivity.findViewById(R.id.image_right);
        text_right = (TextView) mActivity.findViewById(R.id.text_right);

        if(Tools.isStrEmpty(titles)){
            titleTv.setVisibility(View.GONE);
        }else{
            titleTv.setVisibility(View.VISIBLE);
            titleTv.setText(titles);
        }
//        if(0 == backBtnImage){
//            backBtn.setVisibility(View.GONE);
//        }else{
//            backBtn.setVisibility(View.VISIBLE);
//            backBtn.setImageResource(backBtnImage);
//        }
        if(Tools.isStrEmpty(tvback)){
            tv_back.setVisibility(View.GONE);
        }else{
            tv_back.setVisibility(View.VISIBLE);
            tv_back.setText(tvback);
        }
        if(Tools.isStrEmpty(subtitles)){
            subtitle.setVisibility(View.GONE);
        }else{
            subtitle.setVisibility(View.VISIBLE);
            subtitle.setText(subtitles);
        }
        if(Tools.isStrEmpty(textRight)){
            text_right.setVisibility(View.GONE);
        }else{
            text_right.setVisibility(View.VISIBLE);
            text_right.setText(textRight);
        }
        if(0 != backgroundRes){
//            title1_all.setBackgroundColor(R.color.black);
            back.setBackgroundColor(mActivity.getResources().getColor(R.color.black));
//            rl_image_right.setBackgroundColor(mActivity.getResources().getColor(R.color.black));
        }


    }

}
