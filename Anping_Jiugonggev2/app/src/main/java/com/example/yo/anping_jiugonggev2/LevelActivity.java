package com.example.yo.anping_jiugonggev2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class LevelActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    public static int lastPosition = 0;
    private int[] tabIcons = {R.mipmap.treasure_map,R.mipmap.treasure_box,R.mipmap.gift,R.mipmap.aboutus};
    private Context mContext = this;
    private final int REQUEST_CAMERA_STORAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setViewPager();
        checkPermCameraAndStorage();
    }

    //檢查相機與儲存權限
    private boolean checkPermCameraAndStorage() {
        int permCamera = ActivityCompat.checkSelfPermission(mContext, CAMERA);
        if (permCamera != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {CAMERA,WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_STORAGE);
            return false;
        } else {
            return true;
        }
    }

    //callback method
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CAMERA_STORAGE:
                if (!(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    new AlertDialog.Builder(mContext)
                            .setMessage("必須允許相機及儲存權限才能拍照")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    //openCamera();
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            startActivity(new Intent().setClass(LevelActivity.this,MainActivity.class));
        }
        return true;
    }

    //選擇關卡選單
    private void setViewPager(){
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position,float positionOffset,int positionPixels){}
            @Override
            public void onPageScrollStateChanged(int position){}
            @Override
            public void onPageSelected(int position){
                lastPosition = position;
            }
        });
        setAdapter();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);
        for(int i = 0; i< tabLayout.getTabCount(); i++){
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);//設置tablayout每一個tab的圖片
        }
    }

    private void setAdapter(){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LevelFragment(),"藏寶圖");
        adapter.addFragment(new TreasureFragment(),"秘寶藏");
        adapter.addFragment(new GiftFragment(),"獎勵");
        adapter.addFragment(new AboutFragment(),"關於");
        mViewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }
        //加入標題與場景method
        void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        //獲得每一次Fragment場景
        @Override
        public Fragment getItem(int position){
            return mFragmentList.get(position);
        }
        //獲得Fragment場景次數
        @Override
        public int getCount(){
            return mFragmentList.size();
        }
        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }

}