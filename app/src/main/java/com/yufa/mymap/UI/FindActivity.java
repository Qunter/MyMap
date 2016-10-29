package com.yufa.mymap.UI;


import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.yufa.mymap.Adapter.Adapter;
import com.yufa.mymap.Adapter.ViewHolder;
import com.yufa.mymap.CustomView.CircleView;
import com.yufa.mymap.Entity.Relationship;
import com.yufa.mymap.Entity.User;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.SPManger;
import com.yufa.mymap.Util.ShowTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by luyufa on 2016/9/29.
 */
public class FindActivity extends BaseActivity {


    @BindView(R.id.friend_search)
    SearchView friendSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<User> users;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x101){
                init();
            }
        }
    };

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        users = new ArrayList<User>();
        friendSearch.setFocusable(false);
        friendSearch.setSubmitButtonEnabled(true);
        friendSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doit(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void doit(String userName){
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("userName", userName);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                users = list;
                handler.sendEmptyMessage(0x101);
            }
        });
    }

    private void init() {
        recyclerView.setAdapter(new Adapter<User>(this,users, R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, User friend) {
                setViewHolder(holder, friend);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setViewHolder(ViewHolder holder, final User friend) {
        CircleView circleView = (CircleView) holder.getView(R.id.image);
        circleView.setImageResource(R.drawable.image);
        TextView bigText = (TextView) holder.getView(R.id.bigText);
        bigText.setText(friend.getName());
        TextView normalText = (TextView) holder.getView(R.id.normalText);
        normalText.setText(friend.getPersonality());
        holder.setOnClickListener(R.id.item_group, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(friend.getUserName());
            }
        });
    }

    private void save(final String  userName){
        Relationship relationship = new Relationship();
        SPManger spManger = new SPManger(this,"Login");
        final String user = (String) spManger.get("username");
        relationship.setUserName(user);
        relationship.setFriend(userName);
        relationship.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                new ShowTool().showToast(FindActivity.this,"添加好友成功！:" + userName + ":" + user);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }
}
