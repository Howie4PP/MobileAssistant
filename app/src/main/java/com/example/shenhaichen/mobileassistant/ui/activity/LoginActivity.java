package com.example.shenhaichen.mobileassistant.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.Button;
import android.widget.Toast;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.common.util.DeviceUtils;
import com.example.shenhaichen.mobileassistant.dagger.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private static final int READ_PHONE_STATE_CODE = 1000;
    @BindView(R.id.login_btn)
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void setupAcitivtyComponent(AppComponent appComponent) {

    }

    @Override
    public void init() {

    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {

//        RxPermissions rxPermissions = new RxPermissions(this);
//        rxPermissions.request(Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean){
//                            Toast.makeText(LoginActivity.this,"yes",Toast.LENGTH_LONG).show();
//                        }else {
//                            Toast.makeText(LoginActivity.this,"no",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });

//        // 没有授权

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_PHONE_STATE},READ_PHONE_STATE_CODE);
        }
        else{
            // 已经授权
            String imei = DeviceUtils.getIMEI(this);
            Toast.makeText(LoginActivity.this,"imei="+imei,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == READ_PHONE_STATE_CODE){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                String imei  = DeviceUtils.getIMEI(this);
                Toast.makeText(LoginActivity.this,"imei="+imei,Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(LoginActivity.this,"用户拒绝授权",Toast.LENGTH_LONG).show();
            }

        }
    }
}
