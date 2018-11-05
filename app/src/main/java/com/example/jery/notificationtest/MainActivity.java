package com.example.jery.notificationtest;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification("1000", "group-1000", "1", "channel-1", "这是channel-01");
            }
        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification("1000", "group-1000", "2", "channel-2", "这是channel-02");
            }
        }, 4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification("2000", "group-2000", "2", "channel-2", "这是channel-02");
            }
        }, 5000);
    }


    private void showNotification(String groupId, String groupName, String channelId, String channelName, String channelDesc) {
        NotificationUtil.createNotificationChannelGroup(this, groupId, groupName);
        NotificationChannel notificationChannel = NotificationUtil.createNotificationChannel(this, channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT, channelDesc);
        NotificationUtil.bindChannelToGroup(groupId, notificationChannel);
        Intent resultIntent = new Intent(this, ReceivePushActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationUtil.showNotification(this, channelId, "this is title", "this is content", pendingIntent);
    }
}
