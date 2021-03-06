package com.yufa.mymap.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.yufa.mymap.Entity.UserBase;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.JudgeTool;
import com.yufa.mymap.Util.SPManger;
import com.yufa.mymap.Util.ShowTool;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by luyufa on 2016/9/5.
 * 登录
 */
public class LoginsActivity extends BaseActivity {

    @BindView(R.id.login_logining)
    Button loginLogining;
    @BindView(R.id.login_register)
    Button loginRegister;
    @BindView(R.id.login_look)
    Button loginLook;

    @Override
    public void initVariables() {
        super.initVariables();
        hideActionBar();
    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Bmob.initialize(LoginsActivity.this, "ff5f5d16336bb6a0e6d0a05e839f8b20");
    }

    private void hideActionBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();
    }

    @OnClick({R.id.login_logining, R.id.login_register,R.id.login_look})
    public void onClick(View view) {
        ShowTool showTool = new ShowTool();
        switch (view.getId()) {
            case R.id.login_logining:
                loginDialog(this);
                break;
            case R.id.login_register:
                toNewActivity(RegisterActivity.class);
                break;
            case R.id.login_look:
                toNewActivity(MainActivity.class);
                break;
        }
    }

    private void loginDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_login, null);
        builder.setView(layout);
        builder.setMessage("请输入您的用户名和密码：");
        final TextInputLayout textInputLayout1 = (TextInputLayout) layout.findViewById(R.id.login_phoneNumber);
        final TextInputLayout textInputLayout2 = (TextInputLayout) layout.findViewById(R.id.login_password);
        textInputLayout1.setHint("请输入您的用户名");
        textInputLayout2.setHint("请输入您的密码");
        builder.setView(layout);
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

        textInputLayout2.getEditText().setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String phonenumber = textInputLayout1.getEditText().getText().toString();
                final String password = textInputLayout2.getEditText().getText().toString();
                new ShowTool().showToast(LoginsActivity.this, phonenumber + password);
                login(phonenumber, password);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }

    private void login(final String phoneNumber, final String password) {
        BmobQuery<UserBase> query = new BmobQuery<UserBase>();
        query.addWhereEqualTo("phoneNumber", phoneNumber);
        query.findObjects(new FindListener<UserBase>() {
            @Override
            public void done(List<UserBase> list, BmobException e) {
                if (e != null) {
                    Log.d("ERROR", e.getErrorCode() + "");
                } else {
                    if (password.equals(list.get(0).getPassword())) {
                        SPManger spManger = new SPManger(LoginsActivity.this, "Login");
                        spManger.put("username",phoneNumber);
                        spManger.put("password",password);
                        toNewActivity(MainActivity.class);
                    }
                }
            }
        });
    }
}
