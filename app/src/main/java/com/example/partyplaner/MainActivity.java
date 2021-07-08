package com.example.partyplaner;

//FILE HEADER COMMENT
//FILE            : MainActivity.java
//PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//FIRST VERSION   : 2021-02-13 (Rev.01)
//AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//DESCRIPTION     : Main program activity.  User starts here on app startup.


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;

public class MainActivity extends AppCompatActivity
{
    //Declarations
    Button hostBtn;
    Button guestBtn;
    Button reservationBtn;
    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date fixedDate = new Date(2021, 10,10);
        Time fixedTime = new Time(20, 30, 0);




        //Create default party to display in database
        Party firstParty = new Party("Devins", "Elmira Land", fixedDate, fixedTime,20, 15, "\n\u2022 Juice");
        firstParty.setRandCode(12345);
        mDatabaseHelper = new DatabaseHelper(this);
        if(mDatabaseHelper.check("Devins") == false)
        {
            AddData(firstParty);
        }

        hostBtn = (Button) findViewById(R.id.btn_host);
        guestBtn = (Button) findViewById(R.id.btn_guest);
        reservationBtn = (Button) findViewById(R.id.btn_reservation);

        //Start the service
        Intent serviceIntent = new Intent(MainActivity.this, PartyService.class);
        //ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
        startService(serviceIntent);





        //HOST BUTTON
        //==============================================================
        hostBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CreateParty.class);
                startActivity(intent);
            }
        });

        //GUEST BUTTON
        //==============================================================
        guestBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Guest.class);
                startActivity(intent);
            }
        });

        //RESERVATION BUTTON
        //==============================================================
        reservationBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Reservations.class);
                startActivity(intent);
            }
        });

    }

    //ADD DATA TO DATABASE
    //==============================================================
    public void AddData(Party nParty)
    {
        boolean insertSuccess = mDatabaseHelper.addData(nParty);

        if(insertSuccess)
        {
            Toast.makeText(getApplicationContext(), "Data Successfully Inserted!.", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(getApplicationContext(), "Something went wrong!.", Toast.LENGTH_SHORT).show();
        }
    }
}