package com.yufa.mymap.UI;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.SPManger;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setNavigationView();
        setBottomNavigationBar();
    }

    private void setBottomNavigationBar() {
        assert bottomNavigationBar != null;
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_person_blue_500_24dp, "好友"))
                .addItem(new BottomNavigationItem(R.drawable.ic_map_blue_500_24dp, "地图"))
                .addItem(new BottomNavigationItem(R.drawable.ic_supervisor_account_blue_500_24dp, "群组"))
                .initialise();
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_linearlayout, getFragment(position));
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_linearlayout, getFragment(position));
                transaction.commit();
            }
        });
        setDefaultFragment();
    }

    private void setNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerView.setBackgroundResource(R.color.colorAccent);
        SPManger spManger = new SPManger(this,"UserInfo");
        ImageView icon = (ImageView) headerView.findViewById(R.id.nav_head_main_imageView);
        TextView name = (TextView) headerView.findViewById(R.id.nav_head_main_text);
        TextView text = (TextView)headerView.findViewById(R.id.nav_head_main_textView);
        String url = (String) spManger.get("imagePath");
        if(url != null){
            icon.setImageBitmap(BitmapFactory.decodeFile(url));
        }else {
            icon.setImageResource(R.drawable.image);
        }
        String shortcall = (String) spManger.get("username");
        if (shortcall != null){
            name.setText(shortcall.substring(3,shortcall.length()));
        }else{
            name.setText("错觉");
        }
        String personality = (String) spManger.get("personality");
        if(personality !=null){
            text.setText(personality.substring(5,personality.length()));
        }else{
            text.setText("朋友多，就是好");
        }
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,UserInfoActivity.class);
                startActivity(intent);
            }
        });
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_userInfo:        //个人资料
                    {
                        toNewActivity(UserInfoActivity.class);
                        break;
                    }
                    case R.id.nav_friend:{       //主题切换
                        toNewActivity(FindActivity.class);
                        break;
                    }
                    case R.id.nav_group:{
                        toNewActivity(GroupActivity.class);
                        break;
                    }
                    case R.id.nav_settings:     //设置
                    {
                        toNewActivity(SettingActivity.class);
                        break;
                    }
                    case R.id.nav_share:        //分享
                    {
                        toNewActivity(ShareActvity.class);
                        break;
                    }
                    case R.id.nav_send:     //联系我们
                    {
                        toNewActivity(SendActivity.class);
                        break;
                    }
                    case R.id.nav_community:        //关于我们
                    {
                        toNewActivity(CommunityActivity.class);
                        break;
                    }
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        }
        );
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.main_linearlayout, getFragment(0));
        transaction.commit();
    }

    private Fragment getFragment(int index) {
        Fragment fragment = null;
        switch (index) {
            case 0: {
                fragment = new FriendsFragment();
                break;
            }
            case 1: {
                fragment = new MapFragment();
                break;
            }
            case 2: {
                fragment = new GroupFragment();
                break;
            }
            default:
                break;
        }
        return fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void showSnackbar(View v) {
        Snackbar.make(v, "您确定退出吗？", Snackbar.LENGTH_SHORT).setAction("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        }).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            showSnackbar(drawerLayout);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
