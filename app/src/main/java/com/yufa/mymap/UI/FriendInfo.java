package com.yufa.mymap.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yufa.mymap.CustomView.CircleView;
import com.yufa.mymap.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by luyufa on 2016/10/21.
 *
 */

public class FriendInfo extends BaseActivity {
    @BindView(R.id.userImage)
    CircleView userImage;
    @BindView(R.id.friendinfo_username)
    Button friendinfoUsername;
    @BindView(R.id.friendinfo_shortcall)
    Button friendinfoShortcall;
    @BindView(R.id.friendinfo_personality)
    Button friendinfoPersonality;
    @BindView(R.id.friendinfo_address)
    Button friendinfoAddress;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_friendinfo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.friendinfo_username, R.id.friendinfo_shortcall, R.id.friendinfo_personality, R.id.friendinfo_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.friendinfo_username:
                break;
            case R.id.friendinfo_shortcall:
                break;
            case R.id.friendinfo_personality:
                break;
            case R.id.friendinfo_address:
                break;
        }
    }
}
