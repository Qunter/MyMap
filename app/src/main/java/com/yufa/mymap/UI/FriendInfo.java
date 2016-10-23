package com.yufa.mymap.UI;

import android.view.View;
import android.widget.Button;

import com.yufa.mymap.CustomView.CircleView;
import com.yufa.mymap.Entity.User;
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
    @BindView(R.id.friendinfo_qq)
    Button friendinfoQq;
    @BindView(R.id.friendinfo_wechat)
    Button friendinfoWechat;
    @BindView(R.id.friendinfo_sina)
    Button friendinfoSina;
    @BindView(R.id.friendinfo_phone)
    Button friendinfoPhone;
    @BindView(R.id.friendinfo_delete)
    Button friendinfoDelete;
    private User friend;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_friendinfo);
        ButterKnife.bind(this);
        friend = (User) getIntent().getSerializableExtra("friend");
    }


    @OnClick({R.id.friendinfo_qq, R.id.friendinfo_wechat, R.id.friendinfo_sina, R.id.friendinfo_phone, R.id.friendinfo_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.friendinfo_qq:
                break;
            case R.id.friendinfo_wechat:
                break;
            case R.id.friendinfo_sina:
                break;
            case R.id.friendinfo_phone:
                break;
            case R.id.friendinfo_delete:
                break;
        }
    }
}
