/*
 * File Name: 		ACamera.java
 * 
 * Copyright(c) 2011 Yunmai Co.,Ltd.
 * 
 * 		 All rights reserved.
 * 					
 */

package com.tzly.ctcyh.cargo.vehicle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.vehicle.base.BaseActivity;
import com.tzly.ctcyh.cargo.vehicle.base.Config;
import com.ym.vehicle.manager.CameraManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * 拍摄图像
 *
 * @author fangcm
 */
public class ACameraActivity extends BaseActivity implements SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Button camera_shutter_a;
    private Button camera_shutter_b;
    private Button camera_recog;
    private Button bt_test;

    private CameraManager mCameraManager;
    private String flashModel = Parameters.FLASH_MODE_AUTO;

    private ArrayList<String> imagesName = new ArrayList<String>();
    private byte[] idcardA = null;
    private byte[] idcardB = null;
    private final int A_TYPE = 100;
    private final int B_TYPE = 101;
    private Bitmap bm;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == A_TYPE) { // 抓取图像成功
                imagesName.add((String) msg.obj);
                idcardA = msg.getData().getByteArray("img_data");
                saveImg(idcardA);

                Intent aRecognize2 = new Intent(ACameraActivity.this, ARecognizeActivity.class);
                //				aRecognize2.putExtra("imagesName", imagesName);
                aRecognize2.putExtra("idcardA", idcardA);
                //				aRecognize2.putExtra("idcardB", idcardB);
                startActivityForResult(aRecognize2, REQUEST_CODE_RECOG);
            } else if (msg.what == B_TYPE) {
                imagesName.add((String) msg.obj);
                idcardB = msg.getData().getByteArray("img_data");
                //				saveImg(idcardB);
                //				try {
                //					Bitmap bm = BitmapFactory.decodeByteArray(idcardB, 0, idcardB.length);
                //					File imageFile = new File("/sdcard/aatest.jpg");
                //			        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
                //			        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                //			        bos.flush();
                //			        bos.close();
                //			        bm.recycle();
                //				} catch (Exception e) {
                //					// TODO: handle exception
                //				}
            } else {
                Toast.makeText(ACameraActivity.this,
                        R.string.camera_take_picture_error, Toast.LENGTH_SHORT)
                        .show();
            }
            mCameraManager.startDisplay();
        }

    };


    private void saveImg(byte[] data) {
        try {
            bm = BitmapFactory.decodeByteArray(data, 0, data.length);
            File imageFile = new File("/sdcard/zanhua/" + newImageName());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            bm.recycle();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (bm != null && !bm.isRecycled()) {
                bm.recycle();
                bm = null;
            }
        }
    }

    public String newImageName() {
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyMMddHHmmss");
        return "zh" + timeStampFormat.format(new Date()) + ".jpg";
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargo_activity_bcr_camera);

        mCameraManager = new CameraManager(ACameraActivity.this, mHandler);
        flashModel = Config.getFlashMode(ACameraActivity.this);
        initViews();
        File file = new File("/sdcard/zanhua/");
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        imagesName.clear();
        idcardA = null;
        idcardB = null;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        safeRelease();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        mCameraManager.startDisplay();
    }

    private void initViews() {
        // find view
        camera_shutter_a = (Button) findViewById(R.id.camera_shutter_a);
        camera_shutter_b = (Button) findViewById(R.id.camera_shutter_b);
        camera_recog = (Button) findViewById(R.id.camera_recog);
        bt_test = (Button) findViewById(R.id.camera_bt_test);
        bt_test.setOnClickListener(mLsnClick);

        // set view
        camera_shutter_a.setOnClickListener(mLsnClick);
        camera_shutter_b.setOnClickListener(mLsnClick);
        camera_recog.setOnClickListener(mLsnClick);

        mSurfaceView = (SurfaceView) findViewById(R.id.camera_preview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(ACameraActivity.this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private OnClickListener mLsnClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.camera_shutter_a) {
                mCameraManager.autoFocusAndTakePic(A_TYPE);
            } else if (v.getId() == R.id.camera_shutter_b) {
                mCameraManager.autoFocusAndTakePic(B_TYPE);
            } else if (v.getId() == R.id.camera_recog) {
                if (idcardA == null) {
                    Toast.makeText(ACameraActivity.this, "请拍摄证件正面", Toast.LENGTH_LONG).show();
                    return;
                }
                if (idcardB == null) {
                    Toast.makeText(ACameraActivity.this, "请拍摄证件反面", Toast.LENGTH_LONG).show();
                    return;
                }
                System.out.println("-------------recognize-------------");
                System.out.println(idcardA.equals(idcardB));
                Intent aRecognize2 = new Intent(ACameraActivity.this, ARecognizeActivity.class);
                aRecognize2.putExtra("imagesName", imagesName);
                aRecognize2.putExtra("idcardA", idcardA);
                aRecognize2.putExtra("idcardB", idcardB);
                startActivityForResult(aRecognize2, REQUEST_CODE_RECOG);
            } else if (v.getId() == R.id.camera_bt_test) {
                try {
                    Intent intent = new Intent(ACameraActivity.this, ARecognizeActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_RECOG);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "图片解析异常！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public void surfaceCreated(SurfaceHolder holder) {
        // Debug.i(TAG, "surfaceCreated");
        try {
            mCameraManager.openCamera(holder);
            if (flashModel == null
                    || !mCameraManager.isSupportFlash(flashModel)) {
                flashModel = mCameraManager.getDefaultFlashMode();
            }
            mCameraManager.setCameraFlashMode(flashModel);
        } catch (RuntimeException e) {
            Toast.makeText(ACameraActivity.this, R.string.camera_open_error,
                    Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(ACameraActivity.this, R.string.camera_open_error,
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (width > height) {
            mCameraManager.setPreviewSize(width, height);
        } else {
            mCameraManager.setPreviewSize(height, width);
        }
        mCameraManager.startDisplay();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mCameraManager.closeCamera();
    }

    private void safeRelease() {
        if (imagesName != null) {
            imagesName.clear();
            imagesName = null;
        }
    }

}