package com.streaming.better.honey.wedget.autoupdate.product.custom.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.streaming.better.honey.utils.FileProvider;
import com.streaming.better.honey.wedget.autoupdate.product.AbstractAutoUpdateProduct;
import com.streaming.better.honey.wedget.autoupdate.product.custom.CustomUpdateProduct;
import com.streaming.better.honey.wedget.autoupdate.product.custom.builder.INotificationBuilder;


/**
 * <br> ClassName:   DefaultUpdateNotification
 * <br> Description: 默认通知器
 * <br>
 * <br> Author:      yexiaochuan
 * <br> Date:        2017/6/8 17:57
 */
public class DefaultUpdateNotification implements INotificationBuilder {
    private final Context mContext;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder;

    /**
     * <br> Description: 初始化
     * <br> Author:      yexiaochuan
     * <br> Date:        2017/6/8 17:58
     *
     * @param context 上下文
     */
    public DefaultUpdateNotification(Context context) {
        mContext = context;
    }

    @Override
    public void onNotificationInit(AbstractAutoUpdateProduct setting) {
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        String channelID = "1";
        String channelName = "channel_update_app";
        //8.0(26)及以上系统需要为通知创建渠道channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName,
                    NotificationManager.IMPORTANCE_MIN);
            mNotifyManager.createNotificationChannel(channel);
            mBuilder = new NotificationCompat.Builder(mContext.getApplicationContext(),
                    channelID);
            mBuilder.setChannelId(channelID);
        } else {
            mBuilder = new NotificationCompat.Builder(mContext.getApplicationContext(), channelID);
        }
        String appName = mContext.getString(mContext.getApplicationInfo().labelRes);
        mBuilder.setContentTitle(appName);
        int icon = mContext.getApplicationInfo().icon;
        mBuilder.setSmallIcon(setting.getNotifySmallIcon() == 0 ? icon : setting.getNotifySmallIcon());
        if (setting.getNotifyLargeIcon() != null) {
            mBuilder.setLargeIcon(setting.getNotifyLargeIcon());
        }

    }

    @Override
    public void onNotificationStart() {

    }

    @Override
    public void onNotificationProgress(int progress) {
        //"正在下载:" + progress + "%"
        mBuilder.setContentText("正在下载中：" + progress + "%").setProgress(100, progress, false);
        //setContentInent如果不设置在4.0+上没有问题，在4.0以下会报异常
        PendingIntent pendingintent = PendingIntent.getActivity(mContext.getApplicationContext(),
                0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(pendingintent);
        mNotifyManager.notify(0, mBuilder.build());
    }

    @Override
    public void onNotificationFinish(String apkFile) {
        // 下载完成
        mBuilder.setContentText("下载完成，点击安装").setProgress(0, 0, false);
        Intent installAPKIntent = FileProvider.getInstallApkIntent(mContext, apkFile);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext.getApplicationContext(),
                0, installAPKIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        Notification noti = mBuilder.build();
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        mNotifyManager.notify(0, noti);
    }

    @Override
    public void onNotificationFail(int errorCode) {
        if (errorCode == CustomUpdateProduct.ERROR_HTTP_ERROR) {
            mBuilder.setContentText("下载失败").setProgress(0, 0, false);
            mNotifyManager.notify(0, mBuilder.build());
        }
    }
}
