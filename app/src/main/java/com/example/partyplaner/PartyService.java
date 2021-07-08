package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : PartyService.java
//  PROJECT         : A-03 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-04-21 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Notifies the user's device when they create a party letting them know they have registered.
//                    If they click the notification they are brought to Guest page displaying their newly submitted party.

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Debug;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.partyplaner.GlobalState.CHANNEL_ID;
import android.os.Handler;

public class PartyService extends Service
{

    // Initialize variables
    public static int beginNumOfClients;
    private Timer timer;
    private Handler mTimerHandler = new Handler();
    private TimerTask task;
    private Notification notification;
    private Intent notificationIntent;
    private PendingIntent pendingIntent;
    private NotificationManager manager;


    @Override
    public void onCreate() {
        super.onCreate();

        // Start timer
        startTimer();

        // Setup the notification
        notificationIntent = new Intent(PartyService.this, Guest.class);
        pendingIntent = PendingIntent.getActivity(PartyService.this,
                0, notificationIntent, 0);
        notification = new NotificationCompat.Builder(PartyService.this, CHANNEL_ID)
                .setContentTitle("You just registered a party!")
                .setContentText("Check out your party in the list.")
                .setSmallIcon(R.drawable.partyhat01)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Run the thread
        // StartForeground(1, notification);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop timer
        stopTimer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    // START TIMER FUNCTION
    //==============================================================
    private void startTimer()
    {
        task = new TimerTask() {
            public void run() {
                mTimerHandler.post(new Runnable() {
                    public void run()
                    {
                        // Check if user created a new party
                        GlobalState gs = (GlobalState) getApplication();
                        if(gs.userCreatedParty == true)
                        {
                            // Notify the user's device
                            manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                            manager.notify(1, notification);
                            gs.userCreatedParty = false;
                        }
                    }
                });
            }
        };
        // New timer object
        timer = new Timer();
        timer.schedule(task, 5000, 5000);
    }

    //DATE PICKER
    //==============================================================
    private void stopTimer()
    {
        if(timer != null)
        {
            stopSelf();
            timer.cancel();
        }
    }
}




