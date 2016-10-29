package com.yufa.mymap.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yufa.mymap.Entity.User;
import com.yufa.mymap.Entity.UserBase;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.JudgeTool;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by luyufa on 2016/9/21.
 * 用户注册
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.register_phoneNumber)
    TextInputLayout registerPhoneNumber;
    @BindView(R.id.register_verificationCode)
    TextInputLayout verificationCode;
    @BindView(R.id.register_getVerificationCode)
    Button registerGetVerificationCode;
    @BindView(R.id.register_verification)
    Button registerVerification;

    private String phoneNumber;
    private String code;

    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    handler.sendEmptyMessage(0x101);
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                ((Throwable)data).printStackTrace();
            }
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x101:{
                    phoneNumber = registerPhoneNumber.getEditText().getText().toString().trim();
                    register(phoneNumber);
                    break;
                }
            }
        }
    };


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        Bmob.initialize(RegisterActivity.this, "ff5f5d16336bb6a0e6d0a05e839f8b20");
        SMSSDK.initSDK(this, "176c98fbb9730", "ef54fb26acc313048f2bbb221a6aa593");
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
        SMSSDK.registerEventHandler(eh); //注册短信回调
    }

    @OnClick({R.id.register_getVerificationCode, R.id.register_verification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_getVerificationCode:
                //获得验证码
                phoneNumber = registerPhoneNumber.getEditText().getText().toString().trim();
                SMSSDK.getVerificationCode("86", registerPhoneNumber.getEditText().getText().toString());
                break;
            case R.id.register_verification:
                //测试验证码是否正确
                phoneNumber = registerPhoneNumber.getEditText().getText().toString().trim();
                code = verificationCode.getEditText().getText().toString().trim();
                Toast.makeText(this,phoneNumber + ":" + code,Toast.LENGTH_SHORT).show();
                SMSSDK.submitVerificationCode("86", phoneNumber, code);
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
                    save(phoneNumber, theSecond.getEditText().getText().toString());
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

    private void save(final String phoneNumber, String password) {
        UserBase userBase = new UserBase(phoneNumber, password);
        userBase.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e != null) {
                    //错误,注册失败
                    Log.d("ERROR", e.getErrorCode() + ":" + e.getMessage());
                } else {
                    //注册成功，到主页面
                    User user = new User();
                    user.setUserName(phoneNumber);
                    user.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            toNewActivity(MainActivity.class);
                        }
                    });
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
        handler.removeCallbacks(null);
    }
}
