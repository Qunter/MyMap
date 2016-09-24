package com.yufa.mymap.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.yufa.mymap.Entity.UserBase;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.JudgeTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luyufa on 2016/9/21.
 * 用户注册
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.register_phoneNumber)
    TextInputLayout registerPhoneNumber;
    @BindView(R.id.register_password)
    TextInputLayout registerPassword;
    @BindView(R.id.register_getVerificationCode)
    Button registerGetVerificationCode;
    @BindView(R.id.register_verification)
    Button registerVerification;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Bmob.initialize(RegisterActivity.this, "ff5f5d16336bb6a0e6d0a05e839f8b20");
        registerPhoneNumber.getEditText().addTextChangedListener(new TextWatcher() {
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
                    registerPhoneNumber.setErrorEnabled(false);
                } else {
                    registerPhoneNumber.setError("您输入的不是一个规范的手机号码");
                }
            }
        });
    }

    @OnClick({R.id.register_getVerificationCode, R.id.register_verification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_getVerificationCode:

                break;
            case R.id.register_verification:
                String phoneNumber = registerPhoneNumber.getEditText().getText().toString();
                String password = registerPassword.getEditText().getText().toString();
                //测试验证码是否正确
                if (isTrue(phoneNumber,password)) {
                    register(phoneNumber);
                }
                break;
        }
    }

    private void register(final String phoneNumber) {
        View view = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.dialog_register, null);
        final TextInputLayout atFirst = (TextInputLayout)view.findViewById(R.id.register_atFirst);
        final TextInputLayout theSecond = (TextInputLayout)view.findViewById(R.id.register_theSecond);
        theSecond.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String atFirsts = atFirst.getEditText().getText().toString();
                if (atFirsts.equals(s.toString())) {
                    theSecond.setErrorEnabled(false);
                } else {
                    theSecond.setError("您两次输入的密码不一致，请检查");
                    if (!theSecond.isErrorEnabled()) theSecond.setErrorEnabled(true);
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setMessage("请输入和确认您的密码：");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!theSecond.isErrorEnabled()){
                    //两次输入的密码相同，提交到服务器
                    toNewActivity(MainActivity.class);
//                    save(phoneNumber,theSecond.getEditText().getText().toString());
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    private void save(String phoneNumber, String password) {
        UserBase userBase = new UserBase(phoneNumber, password);
        userBase.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    //错误,注册失败
                    Log.d("ERROR", e.getErrorCode() + ":" + e.getMessage());
                } else {
                    //注册成功，到主页面
                    toNewActivity(MainActivity.class);
                }
            }
        });
    }
    private Boolean isTrue(String phoneNumber,String password){
        if (phoneNumber.equals("123456")&&password.equals("123456")){
            return true;
        }
        return false;
    };
}
