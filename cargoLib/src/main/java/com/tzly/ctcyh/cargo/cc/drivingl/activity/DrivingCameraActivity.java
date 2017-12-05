package com.tzly.ctcyh.cargo.cc.drivingl.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.camera_p.CameraPresenter;
import com.tzly.ctcyh.cargo.camera_p.ICameraContract;
import com.tzly.ctcyh.cargo.cc.drivingl.view.ViewfinderView;
import com.tzly.ctcyh.cargo.data_m.InjectionRepository;
import com.tzly.ctcyh.router.util.Utils;
import com.ym.cc.drivingl.controler.CameraManager;
import com.ym.cc.drivingl.controler.OcrConstant;
import com.ym.cc.drivingl.controler.OcrManager;
import com.ym.cc.drivingl.vo.DrivingLicenseInfo;

import java.io.UnsupportedEncodingException;


/**
 * 驾驶证
 */
public class DrivingCameraActivity extends Activity
        implements SurfaceHolder.Callback, ICameraContract.ICameraView {

    private final String TAG = "cc_smart";

    private SurfaceView sv_preview;
    private SurfaceHolder surfaceHolder;
    private CameraManager cameraManager;
    private boolean autoFoucs = true;
    private ViewfinderView finderView;
    private OcrManager ocrManager;
    private Rect rect;
    private boolean cameraError = false;
    private TextView barcode_line;
    private Animation barcodeAnimation;
    //	private boolean over = false;

    private Button btnFlash, btnCancel;
    /**
     * 控制器
     */
    private ICameraContract.ICameraPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargo_activity_driving_camera);
        initView();

        CameraPresenter presenter = new CameraPresenter(
                InjectionRepository.provideRepository(Utils.getContext()), this);

        cameraManager = new CameraManager(getBaseContext(), mHandler);
        mCameraOpenThread.start();
        try {
            mCameraOpenThread.join();
            mCameraOpenThread = null;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            cameraError = true;
        }
        if (cameraError) {
            Toast.makeText(getBaseContext(), "�����δ������", Toast.LENGTH_SHORT).show();
            finish();
        }
        setParameters();

        //		if(barcodeAnimation == null){
        //			Rect rect = finderView.getFinder();
        //			android.widget.RelativeLayout.LayoutParams bl =  (android.widget.RelativeLayout.LayoutParams) barcode_line.getLayoutParams();
        ////	    	bl.topMargin = (int) ((rect.bottom - rect.top)/2 - rect.top);
        //	    	bl.leftMargin = (int) (rect.left);
        //	    	barcode_line.setLayoutParams(bl);
        //	    	barcode_line.setHeight((rect.bottom - rect.top)/2);
        //
        //			barcodeAnimation = new TranslateAnimation(0, rect.right - rect.left, 0, 0);
        //			barcodeAnimation.setDuration(4000);
        //			barcodeAnimation.setRepeatCount(60000);
        //			barcodeAnimation.setRepeatMode(Animation.RESTART);
        //		}

        //		barcode_line.startAnimation(barcodeAnimation);
        //		barcode_line.getBackground().setAlpha(255);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        //		barcode_line.startAnimation(barcodeAnimation);
        //		barcode_line.getBackground().setAlpha(255);
    }


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


    private void setParameters() {
        cameraManager.setCameraFlashModel(Camera.Parameters.FLASH_MODE_OFF);
        cameraManager.setPreviewSize();

        int pWidth = cameraManager.getPreviewWidth();
        int pHeight = cameraManager.getPreviewHeight();


        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int wWidth = display.getWidth();
        int wHeight = display.getHeight();
        if (android.os.Build.MANUFACTURER.equals("Lenovo") && android.os.Build.MODEL.equals("IdeaTabS2110AH")) {
            wHeight = 800;
        }


        Log.d(TAG, wWidth + "<--------W----WindowManager-----H------->" + wHeight);
        int tempWidth = pWidth;
        int tempHeidht = pHeight;
        float x = 100.0f;
        int tempW = pWidth;
        int tempH = pHeight;
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
        //		tempWidth = 854;
        //		tempHeidht = 480;
        ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) sv_preview.getLayoutParams();
        lp.width = tempWidth;
        lp.height = tempHeidht;
        sv_preview.getHolder().setFixedSize(tempWidth, tempHeidht);
        sv_preview.setLayoutParams(lp);

        surfaceHolder = (SurfaceHolder) sv_preview.getHolder();
        surfaceHolder.addCallback(DrivingCameraActivity.this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        finderView.initFinder(tempWidth, tempHeidht, mHandler);
    }


    private void initView() {
        sv_preview = (SurfaceView) findViewById(R.id.camera_sv);
        finderView = (ViewfinderView) findViewById(R.id.camera_finderView);
        btnCancel = (Button) findViewById(R.id.bt_cancel);
        btnFlash = (Button) findViewById(R.id.bt_flash);
        btnFlash.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        barcode_line = (TextView) findViewById(R.id.camera_barcode_line);
        //		barcode_line.setBackgroundColor(Color.GREEN);
    }

    private boolean isFlashOn = false;


    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
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

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case OcrConstant.TAKE_PREVIEW_DATA_OK:
                    if (ocrManager == null) {
                        ocrManager = new OcrManager(mHandler);
                        rect = cameraManager.getViewfinder(finderView.getFinder());
                    }

                    byte[] data_p = (byte[]) msg.obj;
                    if (data_p != null && data_p.length > 0) {
                        //					if(over){
                        //						return;
                        //					}
                        ocrManager.recognBC(data_p, cameraManager.getPreviewWidth(), cameraManager.getPreviewHeight(), rect);
                        mHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 100);
                    } else {
                        finderView.setLineRect(0);
                        Toast.makeText(getBaseContext(), "����������⣬�������ֻ���", Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);
                    }
                    break;
                case OcrConstant.RECOGN_OK:
                    mHandler.removeMessages(OcrConstant.TAKE_PREVIEW_DATA_OK);
                    mHandler.removeMessages(OcrConstant.START_AUTOFOCUS);
                    //				if(over){
                    //					return;
                    //				}
                    //				over = true;
                    String path = "/sdcard/ajztest.jpg";
                    //				path = "";
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
                    break;
                case OcrConstant.REPEAT_AUTOFOCUS:
                    cameraManager.autoFoucs();
                    mHandler.sendEmptyMessageDelayed(OcrConstant.REPEAT_AUTOFOCUS, 2000);
                    break;
                case OcrConstant.RECOGN_EG_TIME_OUT:
                    Toast.makeText(getBaseContext(), "������ڣ��뾡����£�", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case OcrConstant.RECOGN_EG_LICENSE:
                    Toast.makeText(getBaseContext(), "��Ȩʧ�ܣ�", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case OcrConstant.RECOGN_EG_INIT_ERROR:
                    Toast.makeText(getBaseContext(), "�����ʼ��ʧ�ܣ�", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case OcrConstant.START_AUTOFOCUS:
                    if (autoFoucs) {
                        cameraManager.autoFoucs();
                        autoFoucs = false;
                        mHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);
                        mHandler.sendEmptyMessageDelayed(OcrConstant.REPEAT_AUTOFOCUS, 1800);
                        //					finderView.scanInit();
                        //					mHandler.sendEmptyMessageDelayed(100, 500);
                    } else {
                        cameraManager.autoFocusAndPreviewCallback();
                    }
                    break;
                case OcrConstant.RECOGN_LINE_IN_RECT:
                    int restult = (Integer) msg.obj;
                    finderView.setLineRect(restult);
                    break;
                case 100:
                    finderView.scan();
                    mHandler.sendEmptyMessageDelayed(100, 8);
                    break;
                default:
                    cameraManager.initDisplay();
                    mHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);
                    Toast.makeText(getBaseContext(), "<>" + msg.what, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    };

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
        mHandler.sendEmptyMessageDelayed(OcrConstant.START_AUTOFOCUS, 500);

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d(TAG, "surfaceDestroyed");
        cameraManager.closeCamera();
        surfaceHolder = null;
    }

    private void finishAll() {
        if (cameraManager != null) {
            cameraManager.closeCamera();
        }

        if (ocrManager != null) {
            //			ocrManager.closeEngine();
        }
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mHandler.removeMessages(OcrConstant.START_AUTOFOCUS);
        finishAll();

    }

    /**
     * +++++++++++++++++++++++++++++
     */
    @Override
    public void showLoading() {}

    @Override
    public void dismissLoading() {}

    @Override
    public void setPresenter(ICameraContract.ICameraPresenter presenter) {
        mPresenter = presenter;
    }
}
