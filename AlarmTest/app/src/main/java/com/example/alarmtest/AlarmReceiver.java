package com.example.alarmtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("mh", "mm");
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent i = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
                                                                0, i, 0);

        NotificationChannel notificationChannel = new NotificationChannel("my_channel", "name", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(notificationChannel);
        }


        Notification notification = new Notification.Builder(context.getApplicationContext())
                .setContentTitle("Alarmmm")
                .setContentText("Clek mee")
                .setSmallIcon(R.drawable.ic_action_call)
                .setChannelId("my_channel")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        if (notificationManager != null) {
            notificationManager.notify(0, notification);
            Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT).show();
        }
    }
}
