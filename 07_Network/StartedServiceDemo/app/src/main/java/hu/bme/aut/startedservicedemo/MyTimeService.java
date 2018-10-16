package hu.bme.aut.startedservicedemo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Date;

public class MyTimeService extends Service {

    private static final String NOTIFICATION_CHANNEL_ID = "time_service_notifications";
    private static final String NOTIFICATION_CHANNEL_NAME = "Time Service notifications";

    private static final int NOTIF_FOREGROUND_ID = 101;

    private boolean enabled = false;

    private MyTimeThread myTimeThread = null;

    private class MyTimeThread extends Thread {
        public void run() {
            Handler h = new Handler(MyTimeService.this.getMainLooper());

            while (enabled) {
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyTimeService.this,
                                new Date(System.currentTimeMillis()).toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

                updateNotification(
                        new Date(System.currentTimeMillis()).toString());

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIF_FOREGROUND_ID,
                getMyNotification("starting..."));

        enabled = true;
        if (myTimeThread == null) {
            myTimeThread = new MyTimeThread();
            myTimeThread.start();
        }
        return START_STICKY;
    }

    private void updateNotification(String text) {
        Notification notification = getMyNotification(text);
        NotificationManager notifMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifMan.notify(NOTIF_FOREGROUND_ID, notification);
    }

    private Notification getMyNotification(String text) {
        Intent notificationIntent = new Intent(this, MyActivity.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }


        PendingIntent contentIntent = PendingIntent.getActivity(this,
                NOTIF_FOREGROUND_ID,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setContentTitle("This the MyTimeService")
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVibrate(new long[]{1000, 2000, 1000})
                .setContentIntent(contentIntent).build();
        return notification;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        final NotificationChannel channel =
                new NotificationChannel(
                        NOTIFICATION_CHANNEL_ID,
                        NOTIFICATION_CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);

        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        enabled = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
