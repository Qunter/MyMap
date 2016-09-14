package com.yufa.mymap.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.yufa.mymap.Entity.DataString;
import com.yufa.mymap.Entity.UserBase;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.JudgeTool;
import com.yufa.mymap.Util.ShowTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


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
        Bmob.initialize(LoginsActivity.this, "6840c60580406097e34490b0573fb70f");
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
                showDialog(this,"请输入您的帐号和密码：",new DataString("请输入您的手机号码","请输入您的密码",true));
                break;
            case R.id.login_register:
                showDialog(this,"请输入您的密码：",new DataString("请输入您的密码", "请确认您的密码", false));
                showTool.showToast(this, "恭喜您注册成功，已自动登录！");
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

    }

    private void register(){
        toNewActivity(this,RegisterActivity.class,true,this);
    }
    private void showDialog(Context context,String str, final DataString dataString){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.item_dialog,null);
        builder.setView(layout);
        builder.setMessage(str);
        final TextInputLayout textInputLayout1 = (TextInputLayout)layout.findViewById(R.id.textInputLayout1);
        final TextInputLayout textInputLayout2 = (TextInputLayout)layout.findViewById(R.id.textInputLayout2);
        textInputLayout1.setHint(dataString.getStr1());
        textInputLayout2.setHint(dataString.getStr2());
        textInputLayout1.setHintEnabled(true);
        textInputLayout2.setErrorEnabled(true);
        if(dataString.getPhone())
            textInputLayout1.getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    JudgeTool judgeTool = new JudgeTool();
                    if (judgeTool.isPhoneNumber(s.toString())) {
                        textInputLayout1.setErrorEnabled(false);
                    } else {
                        textInputLayout1.setError("您输入的格式不正确！");
                    }
                }
            });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!dataString.getPhone() && textInputLayout1.getEditText().getText().toString().equals(textInputLayout2.getEditText().getText().toString())) {
                    save(textInputLayout1.getEditText().getText().toString(),textInputLayout1.getEditText().getText().toString());
                } else {
                    login(textInputLayout1.getEditText().getText().toString(),textInputLayout2.getEditText().getText().toString());
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    private void login(String phoneNumber, final String password){
        BmobQuery<UserBase> query = new BmobQuery<UserBase>();
        query.addWhereEqualTo("UserName",phoneNumber);
        query.findObjects(new FindListener<UserBase>() {
            @Override
            public void done(List<UserBase> list, BmobException e) {
                if (list.size() == 1 && list.get(0).getPassword() == password) {
                    toNewActivity(LoginsActivity.this, MainActivity.class, false, LoginsActivity.this); //登录
                }
            }
        });
    }
    private void save(String phoneNumber,String password){
        UserBase userBase = new UserBase(phoneNumber,password);
        userBase.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                toNewActivity(LoginsActivity.this,MainActivity.class,true,LoginsActivity.this);   //注册与修改密码
            }
        });
    }
}
