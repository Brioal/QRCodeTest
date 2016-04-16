package com.brioal.qrcodetest.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brioal.qrcodetest.R;
import com.brioal.qrcodetest.util.BrioalUtil;

import zxing.QRCodeEncoder;
import zxing.QRScanActivity;


public class MainActivity extends AppCompatActivity {
    private TextView tv_result;
    private CheckBox mCheck;
    private EditText mEt;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BrioalUtil.init(this);
        tv_result = (TextView) findViewById(R.id.main_tv_result);
        mCheck = (CheckBox) findViewById(R.id.main_check);
        mEt = (EditText) findViewById(R.id.main_et);
        mImage = (ImageView) findViewById(R.id.main_qrcode);
    }

    //打开二维码扫描页面
    public void Scan(View view) {
        //启动扫描的Activity 成功的返回码为 Result.OK
        startActivityForResult(new Intent(MainActivity.this, QRScanActivity.class), 0);
    }

    //产生二维码图片
    public void Get(View view) {
        if (mEt.getText().toString().equals("")) {
            mEt.setError("输入不能为空");
            return;
        }
        Window window = getWindow();
        int mWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        if (mCheck.isChecked()) {
            //生成带logo的二维码
            QRCodeEncoder.encodeQRCode(mEt.getText().toString(), mWidth, getResources().getColor(R.color.colorAccent), BitmapFactory.decodeResource(getResources(), R.drawable.logo), new QRCodeEncoder.Delegate() {
                @Override
                public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                    mImage.setImageBitmap(bitmap);
                }

                @Override
                public void onEncodeQRCodeFailure() {
                    Toast.makeText(MainActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            //生成默认的二维码
            QRCodeEncoder.encodeQRCode(mEt.getText().toString(), mWidth, getResources().getColor(R.color.colorAccent), new QRCodeEncoder.Delegate() {
                @Override
                public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                    mImage.setImageBitmap(bitmap);
                }

                @Override
                public void onEncodeQRCodeFailure() {
                    Toast.makeText(MainActivity.this, "生成二维码失败", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    //接受扫描页面返回的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String result = bundle.getString("result");
            tv_result.setText(result);
        }
    }
}
