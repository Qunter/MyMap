package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luyufa on 2016/8/25.
 *
 */
public class FriendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {



    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private List<User> data;
    private String userName;

    @SuppressLint("HandlerLeak")
    private Handler refresh = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x101: {
                    BmobQuery<Relationship> query = new BmobQuery<Relationship>();
                    query.addWhereEqualTo("userName", userName);
                    query.setLimit(50);
                    query.findObjects( new FindListener<Relationship>() {
                        @Override
                        public void done(List<Relationship> list, BmobException e) {
                            if (e == null){
                                for(int i = 0;i<list.size();i++){
                                    data.add(list.get(i).getFriend());
                                }
                                update.sendEmptyMessage(0x110);
                            }
                        }
                    });
                    break;
                }
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler update = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x110){
                initViews();
            }
        }
    };

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Bmob.initialize(this.getContext(), "ff5f5d16336bb6a0e6d0a05e839f8b20");
        }
        SPManger spManger = new SPManger(this.getActivity(),"Login");
        userName = (String) spManger.get("userName");
        data = new ArrayList<User>();
        refresh.sendEmptyMessage(0x101);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, null);
        ButterKnife.bind(this, view);
        swipeRefresh.setOnRefreshListener(this);
        return view;
    }

    private void initViews() {
        recyclerView.setAdapter(new Adapter<User>(this.getActivity(), data, R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, User friend) {
                setViewHolder(holder, friend);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
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
                Intent intent = new Intent();
                intent.putExtra("friend",friend);
                intent.setClass(FriendsFragment.this.getActivity(),FriendInfo.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh() {
        refresh.sendEmptyMessage(0x101);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        refresh.removeCallbacks(null);
        update.removeCallbacks(null);
    }
}
