package com.yufa.mymap.UI;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.yufa.mymap.Entity.UserBase;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.ShowTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;


/**
 * Created by luyufa on 2016/9/5.
 * 登录
 */
public class LoginsActivity extends BaseActivity {

    @BindView(R.id.login_logining)
    Button loginLogining;
    @BindView(R.id.login_register)
    Button loginRegister;
    @BindView(R.id.login_qq)
    ImageButton loginQq;
    @BindView(R.id.login_weichat)
    ImageButton loginWeichat;
    @BindView(R.id.login_sinaweibo)
    ImageButton loginSinaweibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        hideActionBar();
        ButterKnife.bind(this);
    }
    private void hideActionBar(){
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    @OnClick({R.id.login_logining, R.id.login_register, R.id.login_qq, R.id.login_weichat, R.id.login_sinaweibo})
    public void onClick(View view) {
        ShowTool showTool = new ShowTool();
        switch (view.getId()) {
            case R.id.login_logining:
                logining();
                break;
            case R.id.login_register:
                register();
                break;
            case R.id.login_qq:
                showTool.showToast(this, "qq");
                break;
            case R.id.login_weichat:
                showTool.showToast(this, "weichat");
                break;
            case R.id.login_sinaweibo:
                showTool.showToast(this, "sinaweibo");
                break;
        }
    }

    private void logining(){
        BmobQuery<UserBase> query = new BmobQuery<UserBase>();
        query.addWhereEqualTo("UserName","");
        toNewActivity(this,MainActivity.class,true,this);
    }

    private void register(){
        toNewActivity(this,RegisterActivity.class,true,this);
    }
}
