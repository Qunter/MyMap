package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.yufa.mymap.R;
import com.yufa.mymap.Util.SPManger;

/**
 * Created by luyufa on 2016/9/7.
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }finally {
                    isLogin();
                }
            }
        };
        thread.start();

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
    @SuppressLint("NewApi")
    private void isLogin() {
        SPManger spManger = new SPManger(LoadingActivity.this,"Login");
        String phoneNumber = (String)spManger.get("userName");
        if (phoneNumber == null||phoneNumber.equals("")){
            Intent intent = new Intent();
            intent.setClass(this,LoginsActivity.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent();
            intent.setClass(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
