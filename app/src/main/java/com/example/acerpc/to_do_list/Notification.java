package com.example.acerpc.to_do_list;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Acer PC on 17/09/2017.
 */

public class Notification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        NotificationManager nm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent repeat=new Intent(context,Main3Activity.class);
//        repeat.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeat,PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
//                .setContentIntent(pendingIntent)
//                .setSmallIcon(android.R.drawable.arrow_up_float)
//                .setContentTitle("Notification")
//                .setContentText("show")
//                .setAutoCancel(true);
//        nm.notify(100,builder.build());
        int s= intent.getIntExtra("Flag",0);
        Intent intent_repeat=new Intent(context,Notification_service.class);
        intent_repeat.putExtra("Flag",s);
        context.startService(intent_repeat);
    }
}
