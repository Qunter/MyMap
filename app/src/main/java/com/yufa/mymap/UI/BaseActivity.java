package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Window;

/**
 * Created by luyufa on 2016/9/5.
 *
 */
public class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transition();
        initVariables();
        initViews();
        loadData();
    }

    private void transition(){
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide(Gravity.LEFT));
            getWindow().setExitTransition(new Slide(Gravity.RIGHT));
        }
    }

    public void initVariables(){
        //接收页面之间的数据传递
    }

    public void initViews(){
        //初始化控件
    }

    public void loadData(){
        //访问API数据
    }
    @SuppressLint("NewApi")
    protected void toNewActivity(Class classes){
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), classes);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
    protected void SPManger(String filename,String key,String value){
        SharedPreferences sharedPreferences = getSharedPreferences(filename,MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        editor.commit();
    }
    protected void SPManger(String filename,String key,Integer value){
        SharedPreferences sharedPreferences = getSharedPreferences(filename,MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key,value);
        editor.commit();
    }
    protected void SPManger(String filename,String key,Long value){
        SharedPreferences sharedPreferences = getSharedPreferences(filename,MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key,value);
        editor.commit();
    }
    protected void SPManger(String filename,String key,Boolean value){
        SharedPreferences sharedPreferences = getSharedPreferences(filename,MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    protected void SPManger(String filename,String key,Float value){
        SharedPreferences sharedPreferences = getSharedPreferences(filename,MODE_APPEND);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key,value);
        editor.commit();
    }
}
