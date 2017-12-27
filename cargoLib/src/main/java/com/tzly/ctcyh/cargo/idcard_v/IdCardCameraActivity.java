package com.tzly.ctcyh.cargo.idcard_v;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.cc.drivingl.view.ViewfinderView;
import com.tzly.ctcyh.router.util.FileUtils;
import com.tzly.ctcyh.router.util.LogUtils;
import com.tzly.ctcyh.router.util.Utils;
import com.ym.cc.drivingl.controler.CameraManager;
import com.ym.cc.drivingl.controler.OcrConstant;
import com.ym.cc.drivingl.controler.OcrManager;
import com.ym.cc.drivingl.vo.DrivingLicenseInfo;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;


/**
 * 身份证扫描
 */
public class IdCardCameraActivity extends Activity implements SurfaceHolder.Callback {

    private final String TAG = "cc_smart";

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    /**
     * 自定义控制器
     */
    private CameraManager cameraManager;
    private boolean autoFoucs = true;
    private ViewfinderView finderView;
    private OcrManager ocrManager;
    private Rect rect;
    private boolean cameraError = false;
    private TextView barcode_line;
    private Animation barcodeAnimation;

    private Button btnFlash, btnCancel;

    /**
     * 打开相机
     */
    private Thread mCameraOpenThread = new Thread(new Runnable() {
        public void run() {
            try {
                cameraManager.openCamera();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                cameraError = true;
            }
        }
    });
    /**
     * 自动对焦处理器
     */
    private CameraHandler cameraHandler = new CameraHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargo_activity_driving_camera);

        initView();

        cameraManager = new CameraManager(getBaseContext(), cameraHandler);
        mCameraOpenThread.start();
        try {
            mCameraOpenThread.join();
            mCameraOpenThread = null;
        } catch (Exception e) {
            e.printStackTrace();
            cameraError = true;
        }
        if (cameraError) {
            Toast.makeText(getBaseContext(), "打开相机出错,将关闭页面", Toast.LENGTH_SHORT).show();
            finish();
        }
        setParameters();
    }

    private void initView() {
        surfaceView = (SurfaceView) findViewById(R.id.camera_sv);
        finderView = (ViewfinderView) findViewById(R.id.camera_finderView);
        btnCancel = (Button) findViewById(R.id.bt_cancel);
        btnFlash = (Button) findViewById(R.id.bt_flash);
        btnFlash.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        barcode_line = (TextView) findViewById(R.id.camera_barcode_line);
    }

    /**
     * 1、闪光灯模式
     * 2、预览尺寸
     */
    private void setParameters() {
        cameraManager.setCameraFlashModel(Camera.Parameters.FLASH_MODE_OFF);
        cameraManager.setPreviewSize();

        int pWidth = cameraManager.getPreviewWidth();
        int pHeight = cameraManager.getPreviewHeight();

        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (manager == null) return;
        Display display = manager.getDefaultDisplay();
        int wWidth = display.getWidth();
        int wHeight = display.getHeight();
        if (Build.MANUFACTURER.equals("Lenovo") && Build.MODEL.equals("IdeaTabS2110AH")) {
            wHeight = 800;
        }
        Log.d(TAG, wWidth + "<--------W----WindowManager-----H------->" + wHeight);

        int tempWidth = pWidth;
        int tempHeidht = pHeight;

        float x = 100.0f;
        int tempW = pWidth;
        int tempH = pHeight;
        /**
         * 手机尺寸大于预览尺寸时
         */
        if (wWidth > pWidth && wHeight > pHeight) {
            while (wWidth > tempW && wHeight > tempH) {
                x++;
                Log.d(TAG, "---xx----->" + x / 100.0);
                tempW = (int) (pWidth * x / 100.0);
                tempH = (int) (pHeight * x / 100.0);
                if (wWidth > tempW && wHeight > tempH) {
                    tempWidth = tempW;
                    tempHeidht = tempH;
                }
            }
            Log.d(TAG, "<------11--wWidth > pWidth && wHeight > pHeight------>");
        } else {
            while (tempWidth > wWidth || tempHeidht > wHeight) {
                x--;
                Log.d(TAG, "---xx----->" + x / 100.0);
                tempWidth = (int) (pWidth * x / 100.0);
                tempHeidht = (int) (pHeight * x / 100.0);
            }
            Log.d(TAG, "<-----22---tempWidth > wWidth || tempHeidht > wHeight------>");
        }
        Log.d(TAG, tempWidth + "<--------W----setParameters-----H------->" + tempHeidht);

        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) surfaceView.getLayoutParams();
        lp.width = tempWidth;
        lp.height = tempHeidht;
        surfaceView.getHolder().setFixedSize(tempWidth, tempHeidht);
        surfaceView.setLayoutParams(lp);

        surfaceHolder = (SurfaceHolder) surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        finderView.initFinder(tempWidth, tempHeidht, cameraHandler);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 防止内存泄露
     */
    private static class CameraHandler extends Handler {

        private final WeakReference<Activity> weakReference;

        public CameraHandler(Activity activity) {
            weakReference = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            IdCardCameraActivity ac = (IdCardCameraActivity) weakReference.get();
            super.handleMessage(msg);
            if (ac != null) ac.handlerMsg(msg);
        }
    }

    private void handlerMsg(Message msg) {
        if (msg.what == OcrConstant.TAKE_PREVIEW_DATA_OK) {//200
            LogUtils.e("===>" + 200);

            if (ocrManager == null) {
                ocrManager = new OcrManager(cameraHandler);
                rect = cameraManager.getViewfinder(finderView.getFinder());
            }

            byte[] data_p = (byte[]) msg.obj;
            if (data_p != null && data_p.length > 0) {
                ocrManager.recognBC(data_p, cameraManager.getPreviewWidth(), cameraManager.getPreviewHeight(), rect);
                cameraHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 100);
            } else {
                finderView.setLineRect(0);
                Toast.makeText(getBaseContext(), "解析失败,重新对焦", Toast.LENGTH_SHORT).show();
                cameraHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);
            }
        } else if (msg.what == OcrConstant.RECOGN_OK) {//201
            LogUtils.e("===>" + 201);
            cameraHandler.removeMessages(OcrConstant.TAKE_PREVIEW_DATA_OK);
            cameraHandler.removeMessages(OcrConstant.START_AUTOFOCUS);

            String path = "/sdcard/ajztest.jpg";
            DrivingLicenseInfo bankCardInfo = ocrManager.getResult(path);
            try {
                String info = new String(bankCardInfo.getCharInfo(), "gbk").trim();
                Intent data2 = new Intent();
                data2.putExtra("bankcardinfo", info);
                setResult(200, data2);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "数据解析失败", Toast.LENGTH_SHORT).show();
            }
            finish();
        } else if (msg.what == OcrConstant.REPEAT_AUTOFOCUS) {//202 重新对焦
            LogUtils.e("===>" + 202);
            cameraManager.autoFoucs();
            cameraHandler.sendEmptyMessageDelayed(OcrConstant.REPEAT_AUTOFOCUS, 2000);
        } else if (msg.what == OcrConstant.RECOGN_EG_TIME_OUT) {//203
            LogUtils.e("===>" + 203);
            Toast.makeText(getBaseContext(), " 解析超时,扫描失败", Toast.LENGTH_LONG).show();
            finish();
        } else if (msg.what == OcrConstant.RECOGN_EG_LICENSE) {//204
            LogUtils.e("===>" + 204);
            Toast.makeText(getBaseContext(), "��Ȩʧ�ܣ�", Toast.LENGTH_LONG).show();
            finish();
        } else if (msg.what == OcrConstant.RECOGN_EG_INIT_ERROR) {//205
            LogUtils.e("===>" + 205);
            Toast.makeText(getBaseContext(), "�����ʼ��ʧ�ܣ�", Toast.LENGTH_LONG).show();
            finish();
        } else if (msg.what == OcrConstant.START_AUTOFOCUS) {//206 对焦
            LogUtils.e("===>" + 206);
            if (autoFoucs) {
                cameraManager.autoFoucs();
                autoFoucs = false;

                cameraHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);
                cameraHandler.sendEmptyMessageDelayed(OcrConstant.REPEAT_AUTOFOCUS, 1800);
            } else {
                cameraManager.autoFocusAndPreviewCallback();
            }
        } else if (msg.what == OcrConstant.RECOGN_LINE_IN_RECT) {//207
            LogUtils.e("===>" + 207);
            int restult = (Integer) msg.obj;
            finderView.setLineRect(restult);
        } else if (msg.what == 100) {//100
            LogUtils.e("===>" + 100);
            finderView.scan();
            cameraHandler.sendEmptyMessageDelayed(100, 8);
        } else {//开始预览
            LogUtils.e("===>" + 0);
            cameraManager.initDisplay();
            cameraHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);
            Toast.makeText(getBaseContext(), "<>" + msg.what, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isFlashOn = false;
    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.bt_cancel) {
                setResult(998);
                finish();
            } else if (v.getId() == R.id.bt_flash) {
                if (isFlashOn) {
                    if (cameraManager.closeFlashlight()) {
                        btnFlash.setBackgroundDrawable(getResources().getDrawable(R.drawable.flash_on_s));
                        isFlashOn = false;
                    }
                } else {
                    if (cameraManager.openFlashlight()) {
                        btnFlash.setBackgroundDrawable(getResources().getDrawable(R.drawable.flash_off_s));
                        isFlashOn = true;
                    }
                }
            }
        }
    };

    private File getHeadImageFile(ImageView userImage) {
//        String ImgPath = FileUtils.photoImagePath(Utils.getContext(), FileUtils.CROP_DIR);
        String ImgPath = "/sdcard/ajztest.jpg";

        File mCropFile = new File(ImgPath);
        if (!mCropFile.exists()) {
            return null;
        }
        Uri outputUri;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            outputUri = getUriForFileByN(mCropFile);
        } else {
            outputUri = Uri.fromFile(mCropFile);
        }

        Bitmap bitmap = FileUtils.decodeUriAsBitmap(outputUri, Utils.getContext());
        if (bitmap != null) userImage.setImageBitmap(bitmap);
        return mCropFile;
    }

    private Uri getUriForFileByN(File mCameraFile) {
        try {
            return FileProvider.getUriForFile(Utils.getContext(),
                    getApplication().getPackageName() + ".fileprovider", mCameraFile);
        } catch (Exception e) {
            e.printStackTrace();
            return Uri.fromFile(mCameraFile);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d(TAG, "surfaceCreated");
        if (!cameraManager.cameraOpened()) {
            cameraManager.openCamera();
            setParameters();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        if (holder.getSurface() == null) {
            Log.d(TAG, "holder.getSurface() == null");
            return;
        }
        Log.v(TAG, "surfaceChanged. w=" + width + ". h=" + height);
        surfaceHolder = holder;
        cameraManager.setPreviewDisplay(surfaceHolder);
        cameraManager.initDisplay();
        cameraHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d(TAG, "surfaceDestroyed");
        cameraManager.closeCamera();
        surfaceHolder = null;
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        cameraHandler.removeMessages(OcrConstant.START_AUTOFOCUS);
        finishAll();
    }

    private void finishAll() {
        if (cameraManager != null) {
            cameraManager.closeCamera();
        }
    }

}
