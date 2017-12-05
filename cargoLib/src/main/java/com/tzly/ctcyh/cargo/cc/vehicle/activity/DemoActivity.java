package com.tzly.ctcyh.cargo.cc.vehicle.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tzly.ctcyh.cargo.R;
import com.tzly.ctcyh.cargo.vehicle.activity.ACameraActivity;
import com.ym.cc.vehicle.vo.VehicleInfo;

public class DemoActivity extends Activity {

    private Button bt_all, bt_b;
    private EditText editText;
    private Intent intent, intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);
        bt_all = (Button) findViewById(R.id.bt_start_all);
        bt_b = (Button) findViewById(R.id.bt_start_b);
        editText = (EditText) findViewById(R.id.et_result);
        intent = new Intent(DemoActivity.this, VehicleCameraActivity.class);
        intent1 = new Intent(DemoActivity.this, ACameraActivity.class);
        bt_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivityForResult(intent, 110);
            }
        });

        bt_b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivityForResult(intent1, 110);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (data != null) {
                VehicleInfo vehicleInfo = (VehicleInfo) data.getSerializableExtra("vehicleInfo");
                //				String rTime = "ʶ��ʱ�� ��  "+data.getLongExtra("testkey_1", 0l);
                if (vehicleInfo != null) {
                    try {
                        String res = new String(vehicleInfo.getCharInfo(), "gbk");
                        editText.setText(res);

                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                } else {
                    editText.setText("vehicleInfo is null");
                }
            } else {
                editText.setText("data is null");
            }
        } else {
            editText.setText("resultCode == " + resultCode);
        }
    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


}
