package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : PartyReceiver.java
//  PROJECT         : A-03 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-04-21 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Checks that when the timezone is changed on the device the user is notified that their party
//                    times may be in different timezones than the one changed to.

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

//import static android.content.Context.NOTIFICATION_SERVICE;
import static android.content.ContextWrapper.NOTIFICATION_SERVICE;
import static com.example.partyplaner.GlobalState.CHANNEL_ID;



public class PartyReceiver extends BroadcastReceiver {


    // Initialize variables
    Notification notification;
    PendingIntent pendingIntent;
    Intent notificationIntent;


    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("tg", "You have changed the time zone, the starting party time might differ from your current time.");

        //Setup the notification
        notificationIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, 0);
        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("You have changed the time zone")
                .setContentText("The starting party time might differ from your current time.")
                .setSmallIcon(R.drawable.partyhat01)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // NotificationID is a unique int for each notification that is defined
        notificationManager.notify(2, notification);
    }
}