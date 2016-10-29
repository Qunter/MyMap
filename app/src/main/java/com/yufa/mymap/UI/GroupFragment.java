package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yufa.mymap.Adapter.Adapter;
import com.yufa.mymap.Adapter.ViewHolder;
import com.yufa.mymap.Entity.Group;
import com.yufa.mymap.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by luyufa on 2016/8/25.
 */
public class GroupFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private List<String> data;
    private StringBuffer sb;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x101) {
                BmobQuery<Group> query = new BmobQuery<Group>();
                query.findObjects(new FindListener<Group>() {
                    @Override
                    public void done(List<Group> list, BmobException e) {
                        Set<String> set = new HashSet<String>();
                        for (Group group : list) {
                            set.add(group.getGroupName());
                        }
                        Iterator<String> iterator = set.iterator();
                        while (iterator.hasNext()) {
                            data.add(iterator.next());
                        }
                        setAdapter();
                    }
                });
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
        data = new ArrayList<String>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, null);
        ButterKnife.bind(this, view);
        handler.sendEmptyMessage(0x101);
        return view;
    }

    private void setAdapter() {
        recyclerView.setAdapter(new Adapter<String>(this.getActivity(), data, R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, final String data) {
                ImageView imageView = holder.getView(R.id.image);
                imageView.setImageResource(R.drawable.image);
                TextView name = holder.getView(R.id.bigText);
                name.setText(data);
                TextView address = holder.getView(R.id.normalText);
                holder.setOnClickListener(R.id.item_group, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("group", data);
                        intent.setClass(GroupFragment.this.getActivity(), GroupInfo.class);
                        startActivity(intent);
                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(null);
    }
}
