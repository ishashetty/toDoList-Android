package com.example.acerpc.to_do_list;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Acer PC on 17/09/2017.
 */

public class Notification_service extends Service {
    private NotificationManager mNM;

    // Unique Identification Number for the Notification.
    // We use it on Notification start, and to cancel it.
  //  private int NOTIFICATION = R.string.local_service_started;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Log.i("LocalService", "Received start id " + startId + ": " + intent);
         startId= intent.getIntExtra("Flag", 0);
        if(startId==1)
        {
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Intent repeat = new Intent(this.getApplicationContext(), Main3Activity.class);
            repeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, repeat, 0);
            android.app.Notification builder = new NotificationCompat.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.arrow_up_float)
                    .setContentTitle("Notification")
                    .setContentText("show")
                    .setAutoCancel(true)
                    .build();
            nm.notify(0,builder);
        }
            // nm.notify(100,builder.build());
            //nm.notify(0, builder);
            return START_NOT_STICKY;
        }








}
