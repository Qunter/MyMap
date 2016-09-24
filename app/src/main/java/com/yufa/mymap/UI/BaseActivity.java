package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
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
            getWindow().setEnterTransition(new Explode());
            getWindow().setExitTransition(new Explode());
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
}
