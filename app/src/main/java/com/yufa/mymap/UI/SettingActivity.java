package com.yufa.mymap.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yufa.mymap.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luyufa on 2016/9/29.
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.setting_notification)
    Button settingNotification;
    @BindView(R.id.setting_privacy)
    Button settingPrivacy;
    @BindView(R.id.setting_retrieve)
    Button settingRetrieve;
    @BindView(R.id.setting_security)
    Button settingSecurity;
    @BindView(R.id.setting_update)
    Button settingUpdate;
    @BindView(R.id.setting_outlogin)
    Button settingOutlogin;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.setting_notification, R.id.setting_privacy, R.id.setting_retrieve, R.id.setting_security, R.id.setting_update, R.id.setting_outlogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_notification:
                break;
            case R.id.setting_privacy:
                break;
            case R.id.setting_retrieve:
                break;
            case R.id.setting_security:
                break;
            case R.id.setting_update:
                break;
            case R.id.setting_outlogin:
                break;
        }
    }
}
