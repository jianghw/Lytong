package com.zantong.mobile.utils.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.zantong.mobile.R;


public class KeyWordPop extends PopupWindow implements OnClickListener{
    ImageView keyWordClose;
    LinearLayout shanghai;
    LinearLayout zhejiang;
    LinearLayout jiangsu;
    LinearLayout anhui;
    LinearLayout beijing;
    LinearLayout xizang;
    LinearLayout sichuan;
    LinearLayout hubei;
    LinearLayout gansu;
    LinearLayout jiangxi;
    LinearLayout guizhou;
    LinearLayout guangxi;
    LinearLayout heilongjiang;
    LinearLayout jilin;
    LinearLayout hebei;
    LinearLayout tianjin;
    LinearLayout shanxi;
    LinearLayout liaoning;
    LinearLayout shandong;
    LinearLayout neimenggu;
    LinearLayout fujian;
    LinearLayout ningxia;
    LinearLayout qinghai;
    LinearLayout hainan;
    LinearLayout shannxi;
    LinearLayout hunan;
    LinearLayout xinjiang;
    LinearLayout chongqing;
    LinearLayout henan;
    LinearLayout guangzhou;
    LinearLayout yunnan;
    private View view;
    private KeyWordLintener mKeyWordLintener;

    public KeyWordPop(final Context mContext, View parent, KeyWordLintener mKeyWordLintener) {

        view = View
                .inflate(mContext, R.layout.key_word_pop, null);
        view.startAnimation(AnimationUtils.loadAnimation(mContext,
                R.anim.fade_ins));
		LinearLayout ll_popup = (LinearLayout) view
				.findViewById(R.id.ll_popup);
//        ll_popup.setAnimation(AnimationUtils.loadAnimation(mContext,
//				R.style.popwin_anim_style));

        setWidth(LayoutParams.FILL_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
//		Drawable mDrawable = mContext.getResources().getDrawable(R.color.payRecordTimePopBack);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        this.mKeyWordLintener =mKeyWordLintener;
//		//相对某个控件的位置（正左下方），无偏移
//		showAsDropDown(View anchor)：
////相对某个控件的位置，有偏移;xoff表示x轴的偏移，正值表示向左，负值表示向右；yoff表示相对y轴的偏移，正值是向下，负值是向上；
//		showAsDropDown(View anchor, int xoff, int yoff)：
////相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
//		showAtLocation(View parent, int gravity, int x, int y)：
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//		showAsDropDown(parent);
        update();
		init();
//		hideChoose();

        view.setOnTouchListener(new View.OnTouchListener() {
//
            public boolean onTouch(View v, MotionEvent event) {
            	int height = view.findViewById(R.id.ll_popup).getTop();
                int y=(int) event.getY();
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.key_word_close:
                break;
            case R.id.shanghai:
                mKeyWordLintener.onKeyWordLintener("沪");
                break;
            case R.id.zhejiang:
                mKeyWordLintener.onKeyWordLintener("浙");
                break;
            case R.id.jiangsu:
                mKeyWordLintener.onKeyWordLintener("苏");
                break;
            case R.id.anhui:
                mKeyWordLintener.onKeyWordLintener("皖");
                break;
            case R.id.beijing:
                mKeyWordLintener.onKeyWordLintener("京");
                break;
            case R.id.xizang:
                mKeyWordLintener.onKeyWordLintener("藏");
                break;
            case R.id.sichuan:
                mKeyWordLintener.onKeyWordLintener("川");
                break;
            case R.id.hubei:
                mKeyWordLintener.onKeyWordLintener("鄂");
                break;
            case R.id.gansu:
                mKeyWordLintener.onKeyWordLintener("甘");
                break;
            case R.id.jiangxi:
                mKeyWordLintener.onKeyWordLintener("赣");
                break;
            case R.id.guizhou:
                mKeyWordLintener.onKeyWordLintener("贵");
                break;
            case R.id.guangxi:
                mKeyWordLintener.onKeyWordLintener("桂");
                break;
            case R.id.heilongjiang:
                mKeyWordLintener.onKeyWordLintener("黑");
                break;
            case R.id.jilin:
                mKeyWordLintener.onKeyWordLintener("吉");
                break;
            case R.id.hebei:
                mKeyWordLintener.onKeyWordLintener("冀");
                break;
            case R.id.tianjin:
                mKeyWordLintener.onKeyWordLintener("津");
                break;
            case R.id.shanxi:
                mKeyWordLintener.onKeyWordLintener("晋");
                break;
            case R.id.liaoning:
                mKeyWordLintener.onKeyWordLintener("辽");
                break;
            case R.id.shandong:
                mKeyWordLintener.onKeyWordLintener("鲁");
                break;
            case R.id.neimenggu:
                mKeyWordLintener.onKeyWordLintener("蒙");
                break;
            case R.id.fujian:
                mKeyWordLintener.onKeyWordLintener("闽");
                break;
            case R.id.ningxia:
                mKeyWordLintener.onKeyWordLintener("宁");
                break;
            case R.id.qinghai:
                mKeyWordLintener.onKeyWordLintener("青");
                break;
            case R.id.hainan:
                mKeyWordLintener.onKeyWordLintener("琼");
                break;
            case R.id.shannxi:
                mKeyWordLintener.onKeyWordLintener("陕");
                break;
            case R.id.hunan:
                mKeyWordLintener.onKeyWordLintener("湘");
                break;
            case R.id.xinjiang:
                mKeyWordLintener.onKeyWordLintener("新");
                break;
            case R.id.chongqing:
                mKeyWordLintener.onKeyWordLintener("渝");
                break;
            case R.id.henan:
                mKeyWordLintener.onKeyWordLintener("豫");
                break;
            case R.id.guangzhou:
                mKeyWordLintener.onKeyWordLintener("粤");
                break;
            case R.id.yunnan:
                mKeyWordLintener.onKeyWordLintener("云");
                break;
        }
        dismiss();
    }

    public interface KeyWordLintener{
        public void onKeyWordLintener(String cityStr);
    }
    private void init(){
         keyWordClose = (ImageView) view.findViewById(R.id.key_word_close);
         shanghai = (LinearLayout) view.findViewById(R.id.shanghai);
         zhejiang = (LinearLayout) view.findViewById(R.id.zhejiang);
         jiangsu = (LinearLayout) view.findViewById(R.id.jiangsu);
         anhui = (LinearLayout) view.findViewById(R.id.anhui);
         beijing = (LinearLayout) view.findViewById(R.id.beijing);
         xizang = (LinearLayout) view.findViewById(R.id.xizang);
         sichuan = (LinearLayout) view.findViewById(R.id.sichuan);
         hubei = (LinearLayout) view.findViewById(R.id.hubei);
         gansu = (LinearLayout) view.findViewById(R.id.gansu);
         jiangxi = (LinearLayout) view.findViewById(R.id.jiangxi);
         guizhou = (LinearLayout) view.findViewById(R.id.guizhou);
         guangxi = (LinearLayout) view.findViewById(R.id.guangxi);
         heilongjiang = (LinearLayout) view.findViewById(R.id.heilongjiang);
         jilin = (LinearLayout) view.findViewById(R.id.jilin);
         hebei = (LinearLayout) view.findViewById(R.id.hebei);
         tianjin = (LinearLayout) view.findViewById(R.id.tianjin);
         shanxi = (LinearLayout) view.findViewById(R.id.shanxi);
         liaoning = (LinearLayout) view.findViewById(R.id.liaoning);
         shandong = (LinearLayout) view.findViewById(R.id.shandong);
         neimenggu = (LinearLayout) view.findViewById(R.id.neimenggu);
         fujian = (LinearLayout) view.findViewById(R.id.fujian);
         ningxia = (LinearLayout) view.findViewById(R.id.ningxia);
         qinghai = (LinearLayout) view.findViewById(R.id.qinghai);
         hainan = (LinearLayout) view.findViewById(R.id.hainan);
         shannxi = (LinearLayout) view.findViewById(R.id.shannxi);
         hunan = (LinearLayout) view.findViewById(R.id.hunan);
         xinjiang = (LinearLayout) view.findViewById(R.id.xinjiang);
         chongqing = (LinearLayout) view.findViewById(R.id.chongqing);
         henan = (LinearLayout) view.findViewById(R.id.henan);
         guangzhou = (LinearLayout) view.findViewById(R.id.guangzhou);
         yunnan = (LinearLayout) view.findViewById(R.id.yunnan);

        keyWordClose.setOnClickListener(this);
        shanghai.setOnClickListener(this);
         zhejiang.setOnClickListener(this);
         jiangsu.setOnClickListener(this);
         anhui.setOnClickListener(this);
         beijing.setOnClickListener(this);
         xizang.setOnClickListener(this);
         sichuan.setOnClickListener(this);
         hubei.setOnClickListener(this);
         gansu.setOnClickListener(this);
         jiangxi.setOnClickListener(this);
         guizhou.setOnClickListener(this);
         guangxi.setOnClickListener(this);
         heilongjiang.setOnClickListener(this);
         jilin.setOnClickListener(this);
         hebei.setOnClickListener(this);
         tianjin.setOnClickListener(this);
         shanxi.setOnClickListener(this);
         liaoning.setOnClickListener(this);
         shandong.setOnClickListener(this);
         neimenggu.setOnClickListener(this);
         fujian.setOnClickListener(this);
         ningxia.setOnClickListener(this);
         qinghai.setOnClickListener(this);
         hainan.setOnClickListener(this);
         shannxi.setOnClickListener(this);
         hunan.setOnClickListener(this);
         xinjiang.setOnClickListener(this);
         chongqing.setOnClickListener(this);
         henan.setOnClickListener(this);
         guangzhou.setOnClickListener(this);
         yunnan.setOnClickListener(this);
    }
}
