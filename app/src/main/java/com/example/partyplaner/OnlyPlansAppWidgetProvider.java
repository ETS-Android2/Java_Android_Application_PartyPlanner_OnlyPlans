package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : OnlyPlansAppWidgetProvider.java
//  PROJECT         : A-03 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-04-21 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Widget for the OnlyPlans app displaying current parties and bringing you to the guest page.

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.ArrayList;

public class OnlyPlansAppWidgetProvider extends AppWidgetProvider {
    TextView party1;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // loop through widgets
        for (int appWidgetId : appWidgetIds)
        {
            // create a pending intent for the party list
            Intent intent = new Intent(context, Guest.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            // get the layout and set the listener
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.appwidget, pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId, views);

            // get the names to display on the app widget
            String[] names = {"Devin's", "Test 1", "Test 2"};

            // update the user interface
            views.setTextViewText(R.id.party1View, names[0] == null ? "" : names[0]);
            views.setTextViewText(R.id.party2View, names[1] == null ? "" : names[1]);
            views.setTextViewText(R.id.party3View, names[2] == null ? "" : names[2]);
        }
    }

//    @Override
//    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//
//        if (intent.getAction().equals(DatabaseHelper.TASK_MODIFIED)) {
//            AppWidgetManager manager =
//                    AppWidgetManager.getInstance(context);
//            ComponentName provider =
//                    new ComponentName(context, OnlyPlansAppWidgetProvider.class);
//            int[] appWidgetIds = manager.getAppWidgetIds(provider);
//            onUpdate(context, manager, appWidgetIds);
//        }
//    }
}
