package com.example.partyplaner;

//FILE HEADER COMMENT
//FILE            : Message.java
//PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//FIRST VERSION   : 2021-02-13 (Rev.01)
//AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//DESCRIPTION     : Return Toast messages for errors and confirmations

import android.content.Context;
import android.widget.Toast;

public class Message
{
    public static void message(Context context, String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}
