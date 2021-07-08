package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : GlobalState.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Global setup of the default host entries for the app.


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;


public class GlobalState extends Application
{
    //data members
    private ArrayList<Party> Parties = new ArrayList<Party>();
    private ArrayList<ContactItem> Contacts = new ArrayList<ContactItem>();

    public static final String CHANNEL_ID = "PartyServiceChannel";
    public static boolean userCreatedParty = false;

    //Getters/Setters & Methods
    public ArrayList<Party> getParties() {
        return Parties;
    }
    public void addParty(Party p)
    {
        Parties.add(p);
    }

    // Sets up the initial default hosts in the app
    @Override
    public void onCreate()
    {
        //reinitialize variable
        super.onCreate();

        //Create a Party OBJECT
        Date fixedDate = new Date(2021, 10,10);
        Time fixedTime = new Time(20, 30, 0);

        //Create a notificationc channel
        createNotificationChannel();
    }

    private void createNotificationChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
