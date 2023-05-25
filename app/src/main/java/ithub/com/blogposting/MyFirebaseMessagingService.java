package ithub.com.blogposting;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private NotificationManager notificationManager;
    SharedPreferences sharedPreferences;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getNotification());

    }

    private void showNotification(RemoteMessage.Notification notification) {
         // for get current time
            DateFormat df = new SimpleDateFormat("h:mm a");
            String date = df.format(Calendar.getInstance().getTime());

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 3333, intent, 0);
            Icon icon_1 = Icon.createWithResource(MyFirebaseMessagingService.this, R.mipmap.ic_launcher);
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            Notification notification_f = null;// new Notification.Builder(MainActivity.this)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(mChannel);
                notification_f = new Notification.Builder(MyFirebaseMessagingService.this)

                        .setContentTitle("New blog posted. Please Open App & check.")
                        .setTicker("Learn and share")
                        .setSubText(date)
                        .setChannelId("channel_id")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(icon_1)
                        .setContentIntent(pendingIntent)
                        // .setPriority(Notification.PRIORITY_HIGH)
                        .build();

                notificationManager.notify(1212, notification_f);
                //notificationManager.cancel(1212);
            } else {
                notification_f = new Notification.Builder(MyFirebaseMessagingService.this)

                        .setContentTitle("New blog posted. Please Open App & check.")
                        .setTicker("Learn and share")
                        .setSubText(date)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(icon_1)
                        .setContentIntent(pendingIntent)
                        .build();

                notificationManager.notify(1212, notification_f);
            }
//        Intent intent = new Intent(this,Notification_activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                .setContentTitle(notification.getTitle())
//                .setContentText(notification.getBody())
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.notify(0,builder.build());

    }
}

