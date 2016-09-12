package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.yufa.mymap.Adapter.Adapter;
import com.yufa.mymap.Adapter.ViewHolder;
import com.yufa.mymap.CustomView.CircleView;
import com.yufa.mymap.Entity.Friend;
import com.yufa.mymap.Entity.LocationInfo;
import com.yufa.mymap.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * Created by luyufa on 2016/8/25.
 */
public class FriendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.friend_search)
    SearchView friendSearch;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private List<Friend> data;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x101: {
                    swipeRefresh.setRefreshing(false);
                    updata();
                    break;
                }
                case 0x110: {
                    break;
                }
            }
        }
    };

    private void updata() {
//        queryData("");
    }

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Bmob.initialize(this.getContext(), "ff5f5d16336bb6a0e6d0a05e839f8b20");
        }
        data = new ArrayList<Friend>();
        data.add(new Friend("李泽明", R.drawable.image, "黄家村C区13栋三单元", new LocationInfo(12.010145, 48.14847398001)));
        data.add(new Friend("卢育发", R.drawable.image, "黄家村C区8栋三单元", new LocationInfo(12.010145, 48.14847398001)));
        data.add(new Friend("朱曦", R.drawable.image, "江科宿舍24-105", new LocationInfo(12.010145, 48.14847398001)));
        data.add(new Friend("朱凉", R.drawable.image, "江科宿舍24-105", new LocationInfo(12.010145, 48.14847398001)));
        data.add(new Friend("钟才", R.drawable.image, "江科宿舍24-106", new LocationInfo(12.010145, 48.14847398001)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, null);
        ButterKnife.bind(this, view);
        return initViews(view);
    }

    private View initViews(View view) {
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setAdapter(new Adapter<Friend>(this.getActivity(), data, R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, Friend friend) {
                setViewHolder(holder, friend);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private void showSnackbar(View v) {
        Snackbar.make(v, "OnClickListener!", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform anything for the action selected
            }
        }).show();
    }

    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(0x101);
    }

    private void setViewHolder(ViewHolder holder, Friend friend) {
        CircleView circleView = (CircleView) holder.getView(R.id.image);
        circleView.setImageResource(friend.getImage());
        TextView bigText = (TextView) holder.getView(R.id.bigText);
        bigText.setText(friend.getName());
        TextView normalText = (TextView) holder.getView(R.id.normalText);
        normalText.setText(friend.getAddress());
        holder.setOnClickListener(R.id.item_group, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar(v);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
