package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : DisplayContacts.java
//  PROJECT         : A-03 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-04-21 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Gets the permission to access the user's contacts and display them to the page.

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.LauncherActivity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayContacts extends AppCompatActivity {


    ListView contactList;
    ArrayList<ContactItem> listOfContacts;
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Create display contacts page
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);

        // Initialize objects
        contactList = (ListView) findViewById(R.id.lstContacts);
        listOfContacts = new ArrayList<ContactItem>();

        // Construct the data source
        // Create the adapter to convert the array to views
        adapter = new ContactAdapter(this, listOfContacts);
        // Attach the adapter to a ListView
        contactList.setAdapter(adapter);

        CheckUserPermission();
    }

    // Request code constant
    public final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    //CHECK PERMISSIONS
    //==============================================================
    void CheckUserPermission()
    {
        // Ask user for contact permissions
        if(Build.VERSION.SDK_INT >= 23)
        {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED )
            {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }

        ReadContacts();
    }

    //GET PERMISSION
    //==============================================================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            // Check permission
            case REQUEST_CODE_ASK_PERMISSIONS:
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                ReadContacts();
            }else
            {
                Toast.makeText(this, "Contact Access Denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //GET CONTACTS FROM PHONE
    //==============================================================
    void ReadContacts()
    {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        while(cursor.moveToNext())
        {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            adapter.add(new ContactItem(name, phoneNum));
        }
    }
}