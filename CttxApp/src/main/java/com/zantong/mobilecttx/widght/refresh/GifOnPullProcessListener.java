package com.zantong.mobilecttx.widght.refresh;

import android.view.View;


/**
 * 用于gif动画的控制
 *
 * @author LynnChurch
 */
public class GifOnPullProcessListener implements OnPullProcessListener {
//    private GifDrawable mGifDrawable;
    private int lastFramePosition; // 上一帧的位置

//    public GifOnPullProcessListener(GifDrawable gifDrawable) {
//        mGifDrawable = gifDrawable;
//    }

    @Override
    public void onPrepare(View v, int which) {
        // TODO Auto-generated method stub
//        mGifDrawable.stop();
    }

    @Override
    public void onStart(View v, int which) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onHandling(View v, int which) {
        // TODO Auto-generated method stub
//        mGifDrawable.start();
    }

    @Override
    public void onFinish(View v, int which) {
        // TODO Auto-generated method stub
//        mGifDrawable.stop();
    }

    @Override
    public void onPull(View view, float pullDistance, int which) {
//        // TODO Auto-generated method stub
//        // 动画总帧数
//        int frames = mGifDrawable.getNumberOfFrames();
//        RelativeLayout headView = (RelativeLayout) view.findViewById(R.id.head_view);
//        int headViewHeight = headView.getHeight();
//        // 算出下拉过程中对应的动画进度
//        float progress = Math.abs(pullDistance % headViewHeight / headViewHeight);
//        // 当前播放帧
//        int currentFrame = (int) (frames * progress) + 1;
//
//        // 当前帧与上一帧不同时才进行播放，保证流畅度
//        if (lastFramePosition != currentFrame) {
//            // 需要进行动画重置，否则不能够自由地定位到每一帧
//            mGifDrawable.reset();
//            mGifDrawable.seekToFrame(currentFrame);
//            lastFramePosition = currentFrame;
//        }
    }

}
