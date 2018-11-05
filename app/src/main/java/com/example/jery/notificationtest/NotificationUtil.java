package com.example.jery.notificationtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;

public class NotificationUtil {
    /**
     * 创建 NotificationChannel
     *  @param context
     * @param channelId
     * @param name
     * @param importance
     * @param desc
     */
    public static NotificationChannel createNotificationChannel(Context context, String channelId, String name, int importance, String desc) {
        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationChannel = mNotificationManager.getNotificationChannel(channelId);
            if (notificationChannel != null) {
                return notificationChannel;
            }

            notificationChannel = new NotificationChannel(channelId, name, importance);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);

            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationChannel.setShowBadge(true);//开启角标
            notificationChannel.setBypassDnd(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400});
            notificationChannel.setDescription(desc);

            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        return notificationChannel;
    }

    /**
     * 删除 NotificationChannel
     *
     * @param context
     * @param channelId
     */
    public static void deleteNotificationChannel(Context context, String channelId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.deleteNotificationChannel(channelId);
        }
    }


    /**
     * 创建 ChannelGroup
     *
     * @param context
     * @param groupId
     * @param groupName
     */
    public static void createNotificationChannelGroup(Context context, String groupId, String groupName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannelGroup(new NotificationChannelGroup(groupId, groupName));
        }

    }

    /**
     * 绑定ChannelGroup
     *
     * @param groupId
     * @param notificationChannel
     */
    public static void bindChannelToGroup(String groupId, NotificationChannel notificationChannel) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel.setGroup(groupId);
        }
    }

    /**
     * 移除ChannelGroup
     *
     * @param context
     * @param groupId
     */
    public static void deleteChannelGroup(Context context, String groupId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.deleteNotificationChannelGroup(groupId);
        }
    }

    /**
     * 发出通知
     *
     * @param context
     * @param chatChannelId
     * @param title
     * @param content
     */
    public static void showNotification(Context context, String chatChannelId, String title, String content, PendingIntent pendingIntent) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, chatChannelId);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setBadgeIconType(Notification.BADGE_ICON_SMALL);//设置角标样式
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setNumber(1)//设置角标计数
                .setAutoCancel(true);

//        Intent resultIntent = new Intent(context, MainActivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(resultPendingIntent);
        if (pendingIntent != null) {
            builder.setContentIntent(pendingIntent);
        }

        mNotificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    /**
     * 跳转设置
     *
     * @param context
     * @param chatChannelId
     */
    public static void jumpSetting(Context context, String chatChannelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, chatChannelId);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        context.startActivity(intent);
    }
}
