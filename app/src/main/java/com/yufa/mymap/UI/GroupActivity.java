package com.yufa.mymap.UI;


import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.yufa.mymap.Adapter.Adapter;
import com.yufa.mymap.Adapter.ViewHolder;
import com.yufa.mymap.CustomView.CircleView;
import com.yufa.mymap.Entity.Relationship;
import com.yufa.mymap.Entity.User;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.SPManger;

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
public class GroupActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private List<User> groups;
    private  String groupName;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x101) {
                init();
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_group, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_group) {
            showEditDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View layout = layoutInflater.inflate(R.layout.content_addgroup,null);
        TextInputLayout textInputLayout = (TextInputLayout)layout.findViewById(R.id.addgroup_text);
        final String text = textInputLayout.getEditText().getText().toString().trim();
        builder.setView(layout);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                groupName = text;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        groups = new ArrayList<User>();
        SPManger spManger = new SPManger(this,"Login");
        doit((String)spManger.get("userName"));
    }

    private void doit(String userName) {
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("userName", userName);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                groups = list;
                handler.sendEmptyMessage(0x101);
            }
        });
    }

    private void init() {
        recyclerView.setAdapter(new Adapter<User>(this, groups, R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, User groups) {
                setViewHolder(holder, groups);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    private void setViewHolder(ViewHolder holder, final User user) {
        CircleView circleView = (CircleView) holder.getView(R.id.image);
        circleView.setImageResource(R.drawable.image);
        TextView bigText = (TextView) holder.getView(R.id.bigText);
        bigText.setText(user.getName());
        TextView normalText = (TextView) holder.getView(R.id.normalText);
        normalText.setText(user.getPersonality());
        holder.setOnClickListener(R.id.item_group, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(user);
            }
        });
    }

    private void save(User friend){
        SPManger spManger = new SPManger(this,"Login");
        String userName = (String) spManger.get("userName");
        Relationship relationship = new Relationship();
        relationship.setUserName(userName);
        relationship.setFriend(friend);
        relationship.save(new SaveListener() {

            @Override
            public void done(Object o, BmobException e) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }
}