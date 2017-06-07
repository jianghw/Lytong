package com.zantong.mobilecttx.common.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zantong.mobilecttx.R;
import com.zantong.mobilecttx.utils.DensityUtils;
import com.zantong.mobilecttx.utils.DialogUtils;
import com.zantong.mobilecttx.utils.ImageTools;
import com.zantong.mobilecttx.utils.LogUtils;
import com.zantong.mobilecttx.utils.SystemBarTintManager;
import com.zantong.mobilecttx.utils.ToastUtils;
import com.zantong.mobilecttx.widght.Viewfinder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 拍照扫描
 *
 * @author Sandy
 *         create at 16/6/12 下午5:10
 */
public class OcrCameraActivity extends Activity implements View.OnClickListener {

    Button mDrivingBtn;
    Button mDriverBtn;
    SurfaceView mSurfaceView;
    ImageView mPreView;
    ImageView mFlashBtn;
    TextView mCancelBtn;
    TextView mConfirmBtn;
    Viewfinder mViewfinder;

    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private Camera.Parameters parameters = null;
    public static File file = null;
    int res;

    protected SystemBarTintManager tintManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarColor();
        setContentView(R.layout.ocr_camera_activity);
        mDrivingBtn = (Button) findViewById(R.id.ocr_driving_btn);
        mDriverBtn = (Button) findViewById(R.id.ocr_driver_btn);
        mCancelBtn = (TextView) findViewById(R.id.ocr_cancel_btn);
        mConfirmBtn = (TextView) findViewById(R.id.ocr_confirm_btn);
        mSurfaceView = (SurfaceView) findViewById(R.id.ocr_surfaceview);
        mPreView = (ImageView) findViewById(R.id.ocr_preview);
        mFlashBtn = (ImageView) findViewById(R.id.ocr_flash_btn);
        mViewfinder = (Viewfinder) findViewById(R.id.ocr_viewfinder);
        mDrivingBtn.setOnClickListener(this);
        mDriverBtn.setOnClickListener(this);
        mFlashBtn.setOnClickListener(this);
        mCancelBtn.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);

        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceView.setFocusable(true);
        mSurfaceView.setBackgroundColor(TRIM_MEMORY_BACKGROUND);
        mSurfaceHolder.addCallback(new SurfaceCallback());//为SurfaceView的句柄添加一个回调函数
        //设置参数,并拍照
        res = getIntent().getIntExtra("ocr_resource", 0);
        if (res == 0){
            mDriverBtn.setVisibility(View.GONE);
        }else{
            mDrivingBtn.setVisibility(View.GONE);
        }
        mViewfinder.setValue(res);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.trans));
        }
    }

    //相机参数的初始化设置
    private void initCamera() {
        parameters = mCamera.getParameters();
        parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setPictureSize(mSurfaceView.getWidth(),mSurfaceView.getHeight()); //部分定制手机，无法正常识别该方法。
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); // 1连续对焦
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        Camera.Size size = sizes.get(getPictureSize(sizes));
        parameters.setPreviewSize(size.width,size.height);
        parameters.setPictureSize(size.width,size.height);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
        mCamera.cancelAutoFocus(); // 2如果要实现连续的自动对焦，这一句必须加上

    }

    boolean isOpenFlash;

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ocr_flash_btn:
                parameters = mCamera.getParameters();
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); // 1连续对焦
                if (isOpenFlash) {
                    isOpenFlash = false;
                    mFlashBtn.setBackgroundResource(R.mipmap.icon_flash_off);
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                } else {
                    isOpenFlash = true;
                    mFlashBtn.setBackgroundResource(R.mipmap.icon_flash_on);
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                }

                mCamera.setParameters(parameters);
                mCamera.startPreview();
                mCamera.cancelAutoFocus(); // 2如果要实现连续的自动对焦，这一句必须加上
                break;
            case R.id.ocr_driving_btn:
                mCamera.takePicture(null, null, jpeg);
                mDrivingBtn.setVisibility(View.GONE);
                break;
            case R.id.ocr_driver_btn:
                mCamera.takePicture(null, null, jpeg);
                mDriverBtn.setVisibility(View.GONE);
                break;
            case R.id.ocr_cancel_btn:
                mPreView.setVisibility(View.GONE);
                mSurfaceView.setVisibility(View.VISIBLE);
                mCancelBtn.setVisibility(View.GONE);
                mConfirmBtn.setVisibility(View.GONE);
                if (res == 0){
                    mDrivingBtn.setVisibility(View.VISIBLE);
                }else{
                    mDriverBtn.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ocr_confirm_btn:
                mPreView.setVisibility(View.GONE);
                mSurfaceView.setVisibility(View.VISIBLE);
                mCancelBtn.setVisibility(View.GONE);
                mConfirmBtn.setVisibility(View.GONE);
                if (res == 0) {//行驶证
                    setResult(1202, new Intent());
                } else if (res == 1) {//驾驶证
                    setResult(1206, new Intent());
                } else if (res == 2) {//驾驶证
                    setResult(1204, new Intent());
                }
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mPreView.getVisibility() == View.VISIBLE) {
                mPreView.setVisibility(View.GONE);
                mSurfaceView.setVisibility(View.VISIBLE);
                mCancelBtn.setVisibility(View.GONE);
                mConfirmBtn.setVisibility(View.GONE);
                return false;
            } else {
                finish();
                return false;
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    private final class SurfaceCallback implements SurfaceHolder.Callback {

        // 拍照状态变化时调用该方法
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            try{
                mCamera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        if (success) {
                            initCamera(); //实现相机的参数初始化
                            camera.cancelAutoFocus(); //只有加上了这一句，才会自动对焦。
                        }
                    }
                });

            }catch (Exception e){
                DialogUtils.createDialog(OcrCameraActivity.this, "请先设置拍照权限", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (res == 0) {//行驶证
                            setResult(1202, new Intent());
                        } else if (res == 1) {//驾驶证
                            setResult(1206, new Intent());
                        } else if (res == 2) {//驾驶证
                            setResult(1204, new Intent());
                        }
                        finish();
                    }
                });
            }


        }

        // 开始拍照时调用该方法
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                if (mCamera == null) {
                    mCamera = Camera.open(); // 打开摄像头
                }
                mCamera.setPreviewDisplay(mSurfaceHolder); // 设置用于显示拍照影像的SurfaceHolder对象
                initCamera();
                mCamera.startPreview(); // 开始预览
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        // 停止拍照时调用该方法
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            try{
                if (mCamera != null) {
                    mCamera.stopPreview();
                    mCamera.release(); // 释放照相机
                    mCamera = null;
                }
            }catch (Exception e){

            }

        }
    }

    //创建jpeg图片回调数据对象
    private Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera Camera) {
            // 获得图片
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap bitmap = ImageTools.compressImage(bmp,3000);
            Drawable drawable = new BitmapDrawable(bitmap);
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File jpgFile = new File(Environment.getExternalStorageDirectory() + "/DCIM/camera");
                if (!jpgFile.exists()) {
                    jpgFile.mkdir();
                }
                file = new File(jpgFile.getAbsoluteFile(), res + "car.jpg");
                try {
                    if (!file.exists()) {
                        LogUtils.i("创建新文件");
                        file.createNewFile();
                    }
                    FileOutputStream outputStream = new FileOutputStream(file); // 文件输出流
                    outputStream.write(data); // 写入sd卡中
                    outputStream.close(); // 关闭输出

                } catch (IOException e) {
                    LogUtils.i("创建失败");
                }

                mPreView.setVisibility(View.VISIBLE);
                mSurfaceView.setVisibility(View.GONE);
                mCancelBtn.setVisibility(View.VISIBLE);
                mConfirmBtn.setVisibility(View.VISIBLE);
                mPreView.setBackgroundDrawable(drawable);

            } else {
                Toast.makeText(OcrCameraActivity.this, "没有检测到内存卡", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int getPictureSize(List<Camera.Size> sizes) {
        // 屏幕的宽度
        int screenWidth = DensityUtils.getScreenWidth(this);

        int index = -1;

        for (int i = 0; i < sizes.size(); i++) {
            if (Math.abs(sizes.get(i).width - screenWidth) == 0) {
                index = i;
                break;
            }
        }
        // 当未找到与手机分辨率相等的数值,取列表中间的分辨率
//        if (index == -1) {
//            index = sizes.size()  - 1;
//        }
        if (sizes.size() >= 2){
            if (sizes.get(0).width > sizes.get(1).width){
                index = 0;
            }else{
                index = sizes.size() - 1;
            }

        }
        return index;
    }
}