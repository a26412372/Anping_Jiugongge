package com.example.yo.anping_jiugonggev2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.location.Location;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Level4Activity extends AppCompatActivity {

    public static final String image_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
    TextView textView;
    boolean isSolveCamera = false;
    boolean isSolveLocation = false;
    boolean isPreferencesSolve = false;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level4);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab4);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSolveCamera = true;
                openCamera();
            }
        });
        Button button = (Button) findViewById(R.id.solvebt4);
        sharedPreferences = getSharedPreferences("Level4Preferences",MODE_PRIVATE);
        isPreferencesSolve = sharedPreferences.getBoolean("PreferencesSolve",false);
        if(isPreferencesSolve){
            textView = (TextView) findViewById(R.id.level_tv4);
            textView.setText("安平樹屋愛心");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSolveLocation = getUserLocation();
                if((isSolveCamera&&isSolveLocation)||isPreferencesSolve){
                    sharedPreferences.edit().putBoolean("PreferencesSolve",true).commit();
                    isSolve();
                }else{
                    isNotSolve();
                }
            }
        });
    }

    //過關程式碼
    private void isSolve(){
        textView = (TextView) findViewById(R.id.level_tv4);
        textView.setText("安平樹屋愛心");
        new AlertDialog.Builder(this)
                .setTitle("答案正確!")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
        //Toast.makeText(this,"答案正確!!!",Toast.LENGTH_LONG).show();
        //回傳資料
        setResult(4,getIntent());
    }

    //未過關程式碼
    private void isNotSolve(){
        new AlertDialog.Builder(this)
                .setTitle("答案錯誤!!!")
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
        //Toast.makeText(this,"答案錯誤!!!",Toast.LENGTH_LONG).show();
    }

    //前往拍照
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(image_path, getNowTime() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        //檢查是否有相關應用程式可用
        if(isIntentAvailable(this,intent)){
            startActivity(intent);
        }else{
            Toast.makeText(this,"找不到拍照程式!!!",Toast.LENGTH_SHORT).show();
        }
    }

    //圖片檔名
    private String getNowTime() {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return "IMAGE_"+year+month+day+hours+minute+second;
    }

    //檢查是否有提供功能的應用程式，只要有一個以上就回傳true
    private boolean isIntentAvailable(Context context, Intent intent){
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    //取得位置與判定
    private boolean getUserLocation() {
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation){
            //關卡經緯度
            double level_4_latitude = 22.9960428;
            double level_4_longitude = 120.2524898;
            // 使用者經緯度
            double user_latitude = gps.getLatitude();
            double user_longitude = gps.getLongitude();
            //兩者距離
            double distance = getDistance(level_4_latitude,level_4_longitude,user_latitude,user_longitude);
            //double distance = getDistance(user_latitude+0.0001,user_longitude+0.0001,user_latitude,user_longitude);
            String st = distanceText(distance);
            Toast.makeText(this,"相隔距離:" + st,Toast.LENGTH_LONG).show();
            //判斷距離是否小於10公尺
            if(distance<=40.0&&distance>=0) {
                return true;
            }else{
                return false;
            }
        }else{
            Toast.makeText(this,"未開啟定位!",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //測試距離與提示用method
    private String distanceText(double distance) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        if (distance < 1000){
            return String.valueOf((int) distance) + "m";//大小於一公里回傳公尺
        }else{
            return decimalFormat.format(distance / 1000) + "km";//大於一公尺回傳公里
        }
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results=new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
    }

    //計算距離的算法
    /*public double getDistance(double longitude1, double latitude1, double longitude2,double latitude2) {
        double radLatitude1 = latitude1 * Math.PI / 180;
        double radLatitude2 = latitude2 * Math.PI / 180;
        double l = radLatitude1 - radLatitude2;
        double p = longitude1 * Math.PI / 180 - longitude2 * Math.PI / 180;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                * Math.pow(Math.sin(p / 2), 2)));
        distance = distance * 6378137.0;
        distance = Math.round(distance * 10000) / 10000;

        return distance ;
    }*/
}
