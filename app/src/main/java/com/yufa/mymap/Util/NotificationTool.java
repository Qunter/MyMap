package com.yufa.mymap.Util;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.yufa.mymap.R;
import com.yufa.mymap.UI.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2016/10/7.
 */

public class NotificationTool {

    @SuppressLint("NewApi")
    public void simple(final Context context, Intent intent, String title, String text){
        Notification.Builder builder = new Notification.Builder(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        builder.setSmallIcon(R.drawable.ic_map_blue_500_24dp);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_supervisor_account_blue_500_24dp));
        builder.setContentText(text);
        builder.setContentTitle(title);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());

    }
    public void send(Context context,Intent intent,String string) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,intent, 0);
        mBuilder.setContentIntent(pendingIntent);// 设置通知栏点击意图

        mBuilder.setContentTitle("标题");// 设置通知栏标题

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//悬挂式Notification，5.0后显示

            mBuilder.setContentText(string + "点击查看。").setFullScreenIntent(pendingIntent, true);
            mBuilder.setCategory(NotificationCompat.CATEGORY_MESSAGE);
            mBuilder.setSmallIcon(R.drawable.ic_map_blue_500_24dp);// 设置通知小ICON（5.0必须采用白色透明图片）

        }else{

            mBuilder.setSmallIcon(R.drawable.ic_supervisor_account_blue_500_24dp);// 设置通知小ICON
            mBuilder.setContentText(string );

        }

        mBuilder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_map_blue_500_24dp));// 设置通知大ICON

        mBuilder.setTicker(string + "有警报！"); // 通知首次出现在通知栏，带上升动画效果的

        mBuilder.setWhen(System.currentTimeMillis());// 通知产生的时间，会在通知信息里显示，一般是系统获取到的时间

        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX); // 设置该通知优先级

        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);//在任何情况下都显示，不受锁屏影响。

        mBuilder.setAutoCancel(true);// 设置这个标志当用户单击面板就可以让通知将自动取消

        mBuilder.setOngoing(false);// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
        // 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用NotificationCompat.DEFAULT_ALL属性，可以组合
        mBuilder.setVibrate(new long[] { 0, 100, 500, 100 });//振动效果需要振动权限

//        mBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_alarm));   //声音


        mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS);//闪灯

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //Notification notification = mBuilder.getNotification();//API 11

        Notification notification = mBuilder.build();//API 16

        mNotificationManager.notify(1, notification);
    }

}
