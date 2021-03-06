package com.yufa.mymap.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yufa.mymap.R;
import com.yufa.mymap.Util.DataCleanManager;
import com.yufa.mymap.Util.SPManger;

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
    @BindView(R.id.setting_clean)
    Button settingClean;
    String notificationType;
    String notificationClasses;
    String privacyType;

    private String filename;
    private SPManger spManger;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        filename = "setting";
        spManger = new SPManger(this,filename);
    }

    @OnClick({R.id.setting_notification, R.id.setting_privacy, R.id.setting_retrieve, R.id.setting_security, R.id.setting_clean, R.id.setting_update, R.id.setting_outlogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_notification:
                notification();     //消息提醒
                break;
            case R.id.setting_privacy:
                privacy();          //隐私
                break;
            case R.id.setting_retrieve:
                retrieve();         //找回密码
                break;
            case R.id.setting_security:
                security();         //安全
                break;
            case R.id.setting_clean:
                clean();            //清除数据
                break;
            case R.id.setting_update:
                update();           //检查更新
                break;
            case R.id.setting_outlogin:
                outLogin();         //退出登录
                break;
        }
    }

    private void notification() {
//        NotificationTool notificationTool = new NotificationTool();
//        Intent intent = new Intent();
//        intent.setClass(SettingActivity.this, MainActivity.class);
//        notificationTool.simple(SettingActivity.this, intent, "您有一条新通知", "错觉想加您为好友");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_notification,null);
        RadioGroup type = (RadioGroup)view.findViewById(R.id.notification_showType);
        RadioGroup classes = (RadioGroup)view.findViewById(R.id.notification_showClass);
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.notification_normal:{
                        notificationType = "notification_normal";
                        break;
                    }
                    case R.id.notification_headsup:{
                        notificationClasses = "notification_headsup";
                        break;
                    }
                }
            }
        });
        classes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.notification_public:{
                        notificationClasses = "notification_public";
                        break;
                    }
                    case R.id.notification_private:{
                        notificationClasses = "notification_private";
                        break;
                    }
                    case R.id.notification_secret:{
                        notificationClasses = "notification_secret";
                        break;
                    }
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spManger.put("notificationType",notificationType);
                spManger.put("notificationClasses",notificationClasses);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(view);
        builder.create().show();
    }

    private void privacy() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_privacy,null);
        RadioGroup type = (RadioGroup)view.findViewById(R.id.privacy_type);
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.privacy_anyone:{
                        privacyType = "privacy_anyone";
                        break;
                    }
                    case R.id.privacy_noone:{
                        privacyType = "privacy_noone";
                        break;
                    }
                    case R.id.privacy_pass:{
                        privacyType = "privacy_pass";
                        break;
                    }
                    case R.id.privacy_answer:{
                        privacyType = "privacy_answer";
                        break;
                    }
                }
            }
        });
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spManger.put("privacyType",privacyType);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }

    private void retrieve() {

    }

    private void security() {

    }

    private void update() {

    }

    private void outLogin() {

    }

    private void clean() {
        DataCleanManager dtatCleanManager = new DataCleanManager();
        dtatCleanManager.cleanApplicationData(this);
        Toast.makeText(this, "正在清除数据...", Toast.LENGTH_SHORT).show();
    }

}
