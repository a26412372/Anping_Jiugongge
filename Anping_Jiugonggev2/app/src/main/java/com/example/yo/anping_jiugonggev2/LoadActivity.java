package com.example.yo.anping_jiugonggev2;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        setDelayTime();
    }
    private void setDelayTime(){
        new CountDownTimer(2000,1000){
            @Override
            public void onTick(long millisUntilFinished){

            }
            @Override
            public void onFinish(){
                finish();
                startActivity(new Intent().setClass(LoadActivity.this,MainActivity.class));
            }
        }.start();
    }
}
