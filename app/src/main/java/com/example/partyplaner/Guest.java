package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : Guest.java
//  PROJECT         : A-03 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Guest option to see the list of available parties.


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

public class Guest extends AppCompatActivity
{
    //Declaration of the list
    ListView listView;
    DatabaseHelper mDatabaseHelper;
    Button btnContacts, mapBtn;

    //Lists the parties
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        //Declaration
        listView = (ListView) findViewById(R.id.lst_parties);
        mDatabaseHelper = new DatabaseHelper(this);
        btnContacts = (Button) findViewById(R.id.btnContacts);
        mapBtn = (Button) findViewById(R.id.btn_google);

        // Create the adapter to convert the array to views
        UsersAdapter adapter = new UsersAdapter(this, mDatabaseHelper.getPartyArray());

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lst_parties);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                     Party itemParty = mDatabaseHelper.getPartyArray().get(position);

                     Intent intent = new Intent(Guest.this, GuestJoin.class);
                     intent.putExtra("listObj", itemParty);
                     startActivity(intent);
            }
        });


        //CONTACTS BUTTON
        //==============================================================
        btnContacts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Guest.this, DisplayContacts.class);
                startActivity(intent);
            }
        });

        //LOCATION BUTTON
        //==============================================================
        mapBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Guest.this, GoogleMaps.class);
                startActivity(intent);
            }
        });
    }
}



