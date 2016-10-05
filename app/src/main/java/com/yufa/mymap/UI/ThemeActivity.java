package com.yufa.mymap.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yufa.mymap.Adapter.Adapter;
import com.yufa.mymap.Adapter.ViewHolder;
import com.yufa.mymap.CustomView.CircleView;
import com.yufa.mymap.Entity.Friend;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.GetAllImages;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

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
    ArrayList<HashMap<String,String>> allImages;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_theme);
        ButterKnife.bind(this);
        swipeRefresh.setOnRefreshListener(this);
        recyclerView.setAdapter(new Adapter<HashMap<String,String>>(this,allImages , R.layout.item_recyclerview) {
            @Override
            public void convert(ViewHolder holder, HashMap<String, String> map) {
                setViewHolder(holder, map);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
    }

    private void showSnackbar(View v) {
        Snackbar.make(v, "OnClickListener!", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform anything for the action selected
            }
        }).show();
    }

    private void setViewHolder(ViewHolder holder, HashMap<String, String> map) {
        CircleView circleView = (CircleView) holder.getView(R.id.image);
        TextView bigText = (TextView) holder.getView(R.id.bigText);
        TextView normalText = (TextView) holder.getView(R.id.normalText);
        circleView.setImageBitmap(getDiskBitmap(map.get("thumbnail_path")));
        bigText.setText(map.get("image_id_path"));
        normalText.setText(map.get("thumbnail_path"));
        holder.setOnClickListener(R.id.item_group, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackbar(v);
            }
        });
    }

    private Bitmap getDiskBitmap(String pathString)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(pathString);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }


        return bitmap;
    }

    @Override
    public void initVariables() {
        super.initVariables();
        GetAllImages getAllImages = new GetAllImages();
        allImages = getAllImages.getAllPictures(this);
    }

    @Override
    public void onRefresh() {

    }
}
