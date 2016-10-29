package com.yufa.mymap.UI;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yufa.mymap.CustomView.CircleView;
import com.yufa.mymap.CustomView.SelectPicPopupWindow;
import com.yufa.mymap.Entity.User;
import com.yufa.mymap.R;
import com.yufa.mymap.Util.FileTool;
import com.yufa.mymap.Util.SPManger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by luyufa on 2016/9/29.
 */
public class UserInfoActivity extends BaseActivity {


    @BindView(R.id.userImage)
    CircleView userImage;
    @BindView(R.id.userinfo_shortcall)
    TextView userinfoShortcall;
    @BindView(R.id.userinfo_personality)
    TextView userinfoPersonality;
    private SelectPicPopupWindow menuWindow;
    private static final int REQUESTCODE_PICK = 0;
    private static final int REQUESTCODE_TAKE = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";
    private String urlpath = "";
    private SPManger spManger;
    private String userName;

    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_userinfo);
        ButterKnife.bind(this);
        spManger = new SPManger(this, "UserInfo");
        queryData();
        loading();
    }

    private void queryData(){
        final SPManger sp = new SPManger(this,"Login");
        userName = (String)sp.get("username");
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("userName", userName);
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null && list.size() != 0){
                    User user = list.get(0);
                    spManger.put("username", "昵称：" + user.getName());
                    spManger.put("personality","个性签名:" + user.getPersonality());
                    BmobFile image = user.getImage();
                    final String dateFolder = new SimpleDateFormat("yyyyMMdd", Locale.CHINA).format(new Date());
                    image.download(new File(Environment.getExternalStorageDirectory() + "/JiaXT/" + dateFolder + "/"),new DownloadFileListener() {
                        @Override
                        public void done(String s, BmobException e) {
                            spManger.put("imagePath",Environment.getExternalStorageDirectory() + "/JiaXT/" + dateFolder + "/");
                        }

                        @Override
                        public void onProgress(Integer integer, long l) {

                        }
                    });
                }
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void loading(){
        String url = (String) spManger.get("imageLocationPath");
        if(url != null){
            userImage.setImageBitmap(BitmapFactory.decodeFile(url));
        }else if ((String) spManger.get("imagePath") != null){
            userImage.setImageBitmap(BitmapFactory.decodeFile((String) spManger.get("imagePath")));
        }else{
            userImage.setImageResource(R.drawable.image);
        }
        String shortcall = (String) spManger.get("username");
        if (shortcall != null){
            userinfoShortcall.setText(shortcall);
        }else{
            userinfoShortcall.setText("昵称：18079733121");
        }
        String personality = (String) spManger.get("personality");
        if(personality !=null){
            userinfoPersonality.setText(personality);
        }else{
            userinfoPersonality.setText("个性签名:");
        }
    }

    @OnClick(R.id.userImage)
    public void onClick() {
        menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
        menuWindow.showAtLocation(findViewById(R.id.userImage), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
            case REQUESTCODE_TAKE:
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }


    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileTool.saveFile(this, "temphead.jpg", photo);
            spManger.put("imageLocationPath",urlpath);
            upload(urlpath);
            userImage.setImageDrawable(drawable);
        }
    }

    private void upload(String picPath){
        BmobFile bmobFile = new BmobFile(new File(picPath));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    Toast.makeText(UserInfoActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void download(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.Menu_edit) {
            showEditDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showEditDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setMessage("请输入您的要修改的信息：");
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialog_edituserinfo, null);
        final TextInputLayout userName = (TextInputLayout) view.findViewById(R.id.edituserinfo_username);
        final TextInputLayout personality = (TextInputLayout) view.findViewById(R.id.edituserinfo_personality);
        userName.getEditText().setText(userinfoShortcall.getText().toString().substring(3));
        personality.getEditText().setText(userinfoPersonality.getText().toString().substring(5));
        builer.setView(view);
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = userName.getEditText().getText().toString();
                String personal = personality.getEditText().getText().toString();
                beforeSave(name, personal);
            }
        });
        builer.create().show();

    }

    private void beforeSave(String username, String personality) {

        if (!username.equals("")) {
            username = "昵称：" + username;
            userinfoShortcall.setText(username);
            spManger.put("username", username);
        }
        if (!personality.equals("")) {
            personality = "个性签名：" + personality;
            userinfoPersonality.setText(personality);
            spManger.put("personality", personality);
        }
        save(username, personality);
    }

    private void save(String username, String personality){
        User user = new User();
        user.setName(username);
        user.setPersonality(personality);
        user.update(new UpdateListener() {

            @Override
            public void done(BmobException e) {

            }
        });
    }

}
