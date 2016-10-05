package com.yufa.mymap.UI;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yufa.mymap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by luyufa on 2016/9/29.
 */
public class ThemeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;


    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setAdapter(null);  //
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void onRefresh() {

    }
}
