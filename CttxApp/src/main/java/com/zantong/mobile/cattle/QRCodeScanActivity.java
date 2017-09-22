package com.zantong.mobile.cattle;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tzly.annual.base.util.LogUtils;
import com.zantong.mobile.R;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;

public class QRCodeScanActivity extends AppCompatActivity {

    private ZBarView customZBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qr_scan);

        initView();

        initData();
    }

    private void initView() {
        customZBarView = (ZBarView) findViewById(R.id.custom_z_barView);
    }

    /**
     * 扫描回调
     */
    QRCodeView.Delegate delegate = new QRCodeView.Delegate() {
        @Override
        public void onScanQRCodeSuccess(String result) {
            LogUtils.e("=======" + result);
        }

        @Override
        public void onScanQRCodeOpenCameraError() {

        }
    };

    private void initData() {
        customZBarView.setDelegate(delegate);
        customZBarView.showScanRect();
        customZBarView.startCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    @Override
    protected void onStart() {
        super.onStart();
        customZBarView.startSpot();
    }

    @Override
    protected void onStop() {
        super.onStop();
        customZBarView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        customZBarView.onDestroy();
    }
}
