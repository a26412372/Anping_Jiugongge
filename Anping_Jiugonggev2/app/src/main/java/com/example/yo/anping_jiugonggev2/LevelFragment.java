package com.example.yo.anping_jiugonggev2;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFragment extends Fragment{
    boolean isLevel1Solve,isLevel2Solve,isLevel3Solve,isLevel4Solve,isLevel5Solve,isLevel6Solve,isLevel7Solve,isLevel8Solve,isLevel9Solve;
    ImageButton level_1,level_2,level_3,level_4,level_5,level_6,level_7,level_8,level_9;
    SharedPreferences sharedPreferences;

    public LevelFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        LevelSelect();
        setImagePreferencesSolve();
    }

    private void setImagePreferencesSolve(){
        sharedPreferences = getActivity().getSharedPreferences("LevelImagePreferences", Context.MODE_PRIVATE);

        isLevel1Solve = sharedPreferences.getBoolean("Level1ImageSolve",false);
        isLevel2Solve = sharedPreferences.getBoolean("Level2ImageSolve",false);
        isLevel3Solve = sharedPreferences.getBoolean("Level3ImageSolve",false);
        isLevel4Solve = sharedPreferences.getBoolean("Level4ImageSolve",false);
        isLevel5Solve = sharedPreferences.getBoolean("Level5ImageSolve",false);
        isLevel6Solve = sharedPreferences.getBoolean("Level6ImageSolve",false);
        isLevel7Solve = sharedPreferences.getBoolean("Level7ImageSolve",false);
        isLevel8Solve = sharedPreferences.getBoolean("Level8ImageSolve",false);
        isLevel9Solve = sharedPreferences.getBoolean("Level9ImageSolve",false);

        if(isLevel1Solve)
            level_1.setBackgroundResource(R.mipmap.level_1_swordlion_pass);
        if(isLevel2Solve)
            level_2.setBackgroundResource(R.mipmap.level_2_swordlion_pass);
        if(isLevel3Solve)
            level_3.setBackgroundResource(R.mipmap.level_3_swordlion_pass);
        if(isLevel4Solve)
            level_4.setBackgroundResource(R.mipmap.level_4_swordlion_pass);
        if(isLevel5Solve)
            level_5.setBackgroundResource(R.mipmap.level_5_swordlion_pass);
        if(isLevel6Solve)
            level_6.setBackgroundResource(R.mipmap.level_6_swordlion_pass);
        if(isLevel7Solve)
            level_7.setBackgroundResource(R.mipmap.level_7_swordlion_pass);
        if(isLevel8Solve)
            level_8.setBackgroundResource(R.mipmap.level_8_swordlion_pass);
        if(isLevel9Solve)
            level_9.setBackgroundResource(R.mipmap.level_9_swordlion_pass);
    }

    //選擇關卡
    private void LevelSelect(){
        level_1 = (ImageButton)getActivity().findViewById(R.id.level_1_button);
        level_2 = (ImageButton)getActivity().findViewById(R.id.level_2_button);
        level_3 = (ImageButton)getActivity().findViewById(R.id.level_3_button);
        level_4 = (ImageButton)getActivity().findViewById(R.id.level_4_button);
        level_5 = (ImageButton)getActivity().findViewById(R.id.level_5_button);
        level_6 = (ImageButton)getActivity().findViewById(R.id.level_6_button);
        level_7 = (ImageButton)getActivity().findViewById(R.id.level_7_button);
        level_8 = (ImageButton)getActivity().findViewById(R.id.level_8_button);
        level_9 = (ImageButton)getActivity().findViewById(R.id.level_9_button);
        ImageButton.OnClickListener SelcetButton = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.level_1_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level1Activity.class),1);
                        break;
                    case R.id.level_2_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level2Activity.class),2);
                        break;
                    case R.id.level_3_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level3Activity.class),3);
                        break;
                    case R.id.level_4_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level4Activity.class),4);
                        break;
                    case R.id.level_5_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level5Activity.class),5);
                        break;
                    case R.id.level_6_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level6Activity.class),6);
                        break;
                    case R.id.level_7_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level7Activity.class),7);
                        break;
                    case R.id.level_8_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level8Activity.class),8);
                        break;
                    case R.id.level_9_button:
                        startActivityForResult(new Intent().setClass(getActivity(),Level9Activity.class),9);
                        break;
                }
            }
        };
        level_1.setOnClickListener(SelcetButton);
        level_2.setOnClickListener(SelcetButton);
        level_3.setOnClickListener(SelcetButton);
        level_4.setOnClickListener(SelcetButton);
        level_5.setOnClickListener(SelcetButton);
        level_6.setOnClickListener(SelcetButton);
        level_7.setOnClickListener(SelcetButton);
        level_8.setOnClickListener(SelcetButton);
        level_9.setOnClickListener(SelcetButton);
    }

    //接收回傳資料
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                sharedPreferences.edit().putBoolean("Level1ImageSolve",true).commit();
                level_1.setBackgroundResource(R.mipmap.level_1_swordlion_pass);
                break;
            case 2:
                sharedPreferences.edit().putBoolean("Level2ImageSolve",true).commit();
                level_2.setBackgroundResource(R.mipmap.level_2_swordlion_pass);
                break;
            case 3:
                sharedPreferences.edit().putBoolean("Level3ImageSolve",true).commit();
                level_3.setBackgroundResource(R.mipmap.level_3_swordlion_pass);
                break;
            case 4:
                sharedPreferences.edit().putBoolean("Level4ImageSolve",true).commit();
                level_4.setBackgroundResource(R.mipmap.level_4_swordlion_pass);
                break;
            case 5:
                sharedPreferences.edit().putBoolean("Level5ImageSolve",true).commit();
                level_5.setBackgroundResource(R.mipmap.level_5_swordlion_pass);
                break;
            case 6:
                sharedPreferences.edit().putBoolean("Level6ImageSolve",true).commit();
                level_6.setBackgroundResource(R.mipmap.level_6_swordlion_pass);
                break;
            case 7:
                sharedPreferences.edit().putBoolean("Level7ImageSolve",true).commit();
                level_7.setBackgroundResource(R.mipmap.level_7_swordlion_pass);
                break;
            case 8:
                sharedPreferences.edit().putBoolean("Level8ImageSolve",true).commit();
                level_8.setBackgroundResource(R.mipmap.level_8_swordlion_pass);
                break;
            case 9:
                sharedPreferences.edit().putBoolean("Level9ImageSolve",true).commit();
                level_9.setBackgroundResource(R.mipmap.level_9_swordlion_pass);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_level, container, false);
    }

}