package com.example.yo.anping_jiugonggev2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_FINE_LOCATION_PERMISSION = 0;
    ImageButton go_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationPermission();
        checkGPSopen();
        setAnimation();
    }
    private void setAnimation(){
        go_btn = (ImageButton) findViewById(R.id.Go_btn);
        TranslateAnimation animation = new TranslateAnimation(10f,10f,-10,85);
        animation.setDuration(500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        go_btn.setAnimation(animation);
        animation.start();
        go_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setintroduction_interface();
            }
        });

    }

    RollCall_Dialog rollCall_dialog;
    FloatingActionButton floatingActionButton;
    private void setintroduction_interface(){
        LayoutInflater inflater = LayoutInflater.from(this);
        final View layout = inflater.inflate(R.layout.introduction_interface,null);
        rollCall_dialog = new RollCall_Dialog(layout.getContext());
        rollCall_dialog.setView(layout);
        rollCall_dialog.setCancelable(false);
        rollCall_dialog.setCancelable(true);
        rollCall_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rollCall_dialog.show();
        floatingActionButton = (FloatingActionButton)layout.findViewById(R.id.key);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f,1.0f);
        alphaAnimation.setDuration(500);
        alphaAnimation.setRepeatCount(Animation.INFINITE);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        floatingActionButton.setAnimation(alphaAnimation);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent().setClass(MainActivity.this,LevelActivity.class));
            }
        });
    }

    private boolean checkLocationPermission() {
        int hasPermission = ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION);
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},
                    REQUEST_FINE_LOCATION_PERMISSION);
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case REQUEST_FINE_LOCATION_PERMISSION:
                if(!(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    new AlertDialog.Builder(this)
                            .setMessage("必須允許定位限才能取得位置")
                            .setPositiveButton("OK",null)
                            .show();
                }
        }
    }

    private void checkGPSopen(){
        LocationManager status = (LocationManager)(this.getSystemService(Context.LOCATION_SERVICE));
        if(status.isProviderEnabled(LocationManager.GPS_PROVIDER)||status.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            //Toast.makeText(this, "Get GPS", Toast.LENGTH_LONG).show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle(" 未 開 始 定 位 服 務 ! !")
                    .setMessage(" 要 前 往 開 啟 嗎 ? ")
                    .setCancelable(false)
                    .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("關閉", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) { // 攔截返回鍵
            new AlertDialog.Builder(this)
                    .setTitle("確認視窗")
                    .setMessage("確定要結束應用程式嗎?")
                    .setPositiveButton("確定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub

                                }
                            }).show();
        }
        return true;
    }
}
