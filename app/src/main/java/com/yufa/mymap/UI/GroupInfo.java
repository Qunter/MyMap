package com.yufa.mymap.UI;

import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufa.mymap.Adapter.Adapter;
import com.yufa.mymap.Adapter.ViewHolder;
import com.yufa.mymap.Entity.Group;
import com.yufa.mymap.Entity.User;
import com.yufa.mymap.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luyufa on 2016/10/25.
 */

public class GroupInfo extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private String str;
    private List<User> users;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x101){
                BmobQuery<Group> query = new BmobQuery<Group>();
                query.addWhereEqualTo("groupName",str);
                query.findObjects(new FindListener<Group>() {
                    @Override
                    public void done(List<Group> list, BmobException e) {
                        if (e == null){
                            for (int i = 0;i<list.size();i++){
                                Log.e("---->",list.size() + "   group size");
                                BmobQuery<User> query = new BmobQuery<User>();
                                query.addWhereEqualTo("userName",list.get(i).getUserName());
                                query.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> list, BmobException e) {
                                        if(e == null){
                                            Log.e("---->",list.size() + "  user size");
                                            for (User user : list){
                                                users.add(user);
                                            }
                                            setData.sendEmptyMessage(0x110);
                                        }else{
                                            Log.e("----->",e.getMessage());
                                        }
                                    }
                                });
                            }

                        }else{
                            Log.e("----->",e.getMessage());
                        }
                    }
                });
            }
        }
    };

    private Handler setData = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0x110){
                setAdapter();
            }
        }
    };

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_groupinfo);
        ButterKnife.bind(this);
        users = new ArrayList<User>();
        str = (String) getIntent().getExtras().get("group");
        Log.e("---->",str);
        handler.sendEmptyMessage(0x101);
    }
    private void doQuery(String groupName){
        BmobQuery<Group> query = new BmobQuery<Group>();
        query.addWhereEqualTo("groupName",groupName);
        query.findObjects(new FindListener<Group>() {
            @Override
            public void done(List<Group> list, BmobException e) {
                if (e == null){
                    for (int i = 0;i<list.size();i++){
                        Log.e("---->",list.size() + "   group size");
                        BmobQuery<User> query = new BmobQuery<User>();
                        query.addWhereEqualTo("userName",list.get(i).getUserName());
                        query.findObjects(new FindListener<User>() {
                            @Override
                            public void done(List<User> list, BmobException e) {
                                if(e == null){
                                    Log.e("---->",list.size() + "  user size");
                                    for (User user : list){
                                        users.add(user);
                                    }
                                }else{
                                    Log.e("----->",e.getMessage());
                                }
                            }
                        });
                    }
                    setAdapter();

                }else{
                    Log.e("----->",e.getMessage());
                }
            }
        });
    }
    private void getData(String user){
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("userName",user);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e == null){
                    Log.e("---->",list.size() + "  user size");
                    users.add(list.get(0));
                }else{
                    Log.e("----->",e.getMessage());
                }
            }
        });
    }

    private void setAdapter(){
        Log.e("---->",users.size() + "  adapter size");
        recyclerView.setAdapter(new Adapter<User>(this, users, R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, final User user) {
                ImageView imageView = holder.getView(R.id.image);
                imageView.setImageResource(R.drawable.image);
                TextView name = holder.getView(R.id.bigText);
                name.setText(user.getName());
                TextView address = holder.getView(R.id.normalText);
                address.setText(user.getPersonality());
                holder.setOnClickListener(R.id.item_group, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(0x101);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
        setData.removeCallbacks(null);
    }
}
