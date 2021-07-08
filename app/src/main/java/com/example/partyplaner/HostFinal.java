package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : HostFinal.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Host option for results of the details made for a party


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HostFinal extends AppCompatActivity
{
    //Declarations
    TextView txtSummary;
    TextView txtCode;
    Button btnConfirm;

    //Labels
    TextView replaceName;
    TextView replaceAddress;
    TextView replaceGuest;
    TextView replaceDate;
    TextView replaceTime;
    TextView replaceBeverages;
    TextView replaceEntryFee;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_final);

        txtCode = (TextView) findViewById(R.id.txt_code);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        LinearLayout hiddenLayout;

        //Labels
        replaceName = (TextView) findViewById(R.id.replaceName);
        replaceAddress = (TextView) findViewById(R.id.replaceAddress);
        replaceGuest = (TextView) findViewById(R.id.replaceGuest);
        replaceDate = (TextView) findViewById(R.id.replaceDate);
        replaceTime = (TextView) findViewById(R.id.replaceTime);
        replaceBeverages = (TextView) findViewById(R.id.replaceBeverages);
        replaceEntryFee = (TextView) findViewById(R.id.replaceEntryFee);


        if(getIntent().getExtras() != null)
        {
            Party tempParty = (Party) getIntent().getSerializableExtra("PartyObj");

            //Display information
            replaceName.setText(tempParty.getPartyName());
            replaceAddress.setText(tempParty.getAddress());
            replaceGuest.setText("" + tempParty.getPeople());


            replaceDate.setText(tempParty.getPartyDate().getYear() + "-" + (tempParty.getPartyDate().getMonth() + 1) +  "-" +  tempParty.getPartyDate().getDate());

            String AM_PM ;
            int hourOfDay = tempParty.getPartyTime().getHours();
            int minute = tempParty.getPartyTime().getMinutes();
            String minuteString;

            if(hourOfDay < 12)
            {
                AM_PM = "AM";
            }
            else
            {
                AM_PM = "PM";
                hourOfDay = hourOfDay - 12;
            }
            if(minute < 10)
            {
                minuteString = "0" + minute;
            }
            else
            {
                minuteString = "" + minute;
            }

            replaceTime.setText(hourOfDay + ":" + minuteString + " " + AM_PM);
            replaceBeverages.setText(tempParty.getDrinksString());
            replaceEntryFee.setText("" + tempParty.getEntryFee());

            int tmpCode = tempParty.getRandCode();
            txtCode.setText("" + tmpCode);
        }


        //Complete party button
        //==============================================================
        btnConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(HostFinal.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}