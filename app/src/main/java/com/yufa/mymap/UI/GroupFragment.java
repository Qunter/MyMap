package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.Bmob;

/**
 * Created by luyufa on 2016/8/25.
 */
public class GroupFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private List<Group> data;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Bmob.initialize(this.getContext(), "ff5f5d16336bb6a0e6d0a05e839f8b20");
        }
        data = new ArrayList<Group>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, null);
        ButterKnife.bind(this, view);
        recyclerView.setAdapter(new Adapter<Group>(this.getActivity(), data, R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, Group group) {
                ImageView imageView = holder.getView(R.id.image);
//                imageView.setImageResource(group.getImage());
                TextView name = holder.getView(R.id.bigText);
//                name.setText(group.getName());
                TextView address = holder.getView(R.id.normalText);
//                address.setText(group.getSome());
                holder.setOnClickListener(R.id.item_group, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false));
        return view;
    }
}
