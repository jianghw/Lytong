package com.tzly.ctcyh.cargo.vehicle.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.vehicle.base.BaseActivity;
import com.ym.vehicle.ocr.OcrEngine;
import com.ym.vehicle.vo.VehicleInfo;

import java.util.List;

public class ARecognizeActivity extends BaseActivity implements Runnable {

    private TextView mRecoResult;

    private VehicleInfo vehicleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargo_activity_bcr_recognize);

        mRecoResult = (TextView) findViewById(R.id.reco_result);

        new Thread(this).start();
    }

    private Handler mOcrHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case OcrEngine.RECOGN_FAIL:
                    Toast.makeText(ARecognizeActivity.this, R.string.reco_dialog_blur, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOGN_TIME_OUT:
                    Toast.makeText(ARecognizeActivity.this, "引擎过期！", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOGN_OCRMSG_ERROR:
                    Toast.makeText(ARecognizeActivity.this, "授权错误！", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOGN_BLUR:
                    Toast.makeText(ARecognizeActivity.this, R.string.reco_dialog_blur,
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
                case OcrEngine.RECOGN_OK:
                    mRecoResult.setVisibility(View.VISIBLE);
                    mRecoResult.setText(vehicleInfo.getVehicleText());
                    break;
                default:
                    Toast.makeText(ARecognizeActivity.this, R.string.reco_dialog_blur,
                            Toast.LENGTH_SHORT).show();
                    setResult(RESULT_RECOG_FAILED);
                    finish();
                    break;
            }
        }

    };


    @Override
    public void run() {
        OcrEngine ocrEngine = new OcrEngine();
        try {
            //			File file = new File(imagePath);
            //			byte[] imgBuffer = FileUtil.getBytesFromFile(file);
            //			idCard = ocrEngine.recognize(ARecognize.this, imgBuffer);
            //			imgBuffer = null;
            //			idCard = ocrEngine.recognize(ARecognize.this, imagePath);
            List<String> list = ocrEngine.getLicense();
            Log.d("tag", "--License-0----->>" + list.get(0));
            Log.d("tag", "--License-1----->>" + list.get(1));

            vehicleInfo = ocrEngine.getVehicleInfo(ARecognizeActivity.this, getIntent().getByteArrayExtra("idcardA"));
            //			if(getIntent().getByteArrayExtra("idcardA") == null || getIntent().getByteArrayExtra("idcardB") == null){
            //				byte[] aa = FileHelp.getBytesFromFile("/sdcard/OCR/atest.jpg");
            //				byte[] bb = FileHelp.getBytesFromFile("/sdcard/zh/btest.jpg");
            //				vehicleInfo = ocrEngine.getIdCardInfo(ARecognize.this, aa);
            //			}else{
            //				vehicleInfo = ocrEngine.getIdCardInfo(ARecognize.this, getIntent().getByteArrayExtra("idcardA"));
            //			}

            if (vehicleInfo.getYMRecognState() == OcrEngine.RECOGN_OK) {
                mOcrHandler.sendEmptyMessage(OcrEngine.RECOGN_OK);
            } else {
                mOcrHandler.sendEmptyMessage(vehicleInfo.getYMRecognState());
            }
            //		} catch (IOException e) {
            //			mOcrHandler.sendEmptyMessage(OcrEngine.RECOG_FAIL);
        } catch (Exception e) {
            mOcrHandler.sendEmptyMessage(OcrEngine.RECOGN_FAIL);
        }
    }

}