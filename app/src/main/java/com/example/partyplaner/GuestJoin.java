package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : GuestJoin.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Guest option to see details of a host party and join (with guests)


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class GuestJoin extends AppCompatActivity
{
    //Declarations
    TextView txtDescriptionLeft, txtDescriptionRight, hideTitle, partyNameTitle;
    EditText etxtJoin, etxtName1, etxtName2, etxtName3, etxtName4, mEditText;
    Button btnJoin, btnHide, btnSave, btnLoad, btnMap;
    LinearLayout hideLayout1, hideLayout2;
    TextView maxPeople;
    TextView drinks;
    TextView entryFee;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_join);

        //Initialization
        etxtJoin = (EditText) findViewById(R.id.etxt_join);
        btnJoin = (Button) findViewById(R.id.btn_join);
        hideTitle = (TextView) findViewById(R.id.hideTitle);
        hideLayout1 = (LinearLayout) findViewById(R.id.hideGuest1);
        hideLayout2 = (LinearLayout) findViewById(R.id.hideGuest2);
        btnHide = (Button) findViewById(R.id.btnHide);
        partyNameTitle = (TextView) findViewById(R.id.txt_partyNameTitle);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnLoad = (Button) findViewById(R.id.btn_load);

        etxtName1 = (EditText) findViewById(R.id.etxtName1);
        etxtName2 = (EditText) findViewById(R.id.etxtName2);
        etxtName3 = (EditText) findViewById(R.id.etxtName3);
        etxtName4 = (EditText) findViewById(R.id.etxtName4);
        mEditText = (EditText) findViewById(R.id.mEditText);

        maxPeople= (TextView) findViewById(R.id.maxPeople);
        drinks = (TextView) findViewById(R.id.drinks);
        entryFee = (TextView) findViewById(R.id.entryFee);

        hideTitle.setVisibility(View.INVISIBLE);
        hideLayout1.setVisibility(View.INVISIBLE);
        hideLayout2.setVisibility(View.INVISIBLE);
        btnHide.setVisibility(View.INVISIBLE);
        btnSave.setVisibility(View.INVISIBLE);
        btnLoad.setVisibility(View.INVISIBLE);
        mEditText.setVisibility(View.INVISIBLE);

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(GuestJoin.this);

        if(getIntent().getExtras() != null)
        {
            Party tempParty = (Party) getIntent().getSerializableExtra("listObj");
            partyNameTitle.setText(tempParty.getPartyName().toString());

            maxPeople.setText("" + tempParty.getPeople());
            drinks.setText("" + tempParty.getDrinksString().toString());
            entryFee.setText("" + tempParty.getEntryFee());

            btnJoin.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(etxtJoin.getText().toString().isEmpty() == false)
                    {
                        //Fix - do validation for string to int convertion
                        int enteredCode = Integer.parseInt(etxtJoin.getText().toString());

                        if (enteredCode == tempParty.getRandCode())
                        {
                            //Set visibility
                            hideTitle.setVisibility(View.VISIBLE);
                            hideLayout1.setVisibility(View.VISIBLE);
                            hideLayout2.setVisibility(View.VISIBLE);
                            btnHide.setVisibility(View.VISIBLE);
                            btnSave.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Incorrect Code.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Incorrect Code.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //CONFIRM INVITE BUTTON
            //==============================================================
            btnHide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name1 = etxtName1.getText().toString();
                    String name2 = etxtName2.getText().toString();
                    String name3 = etxtName3.getText().toString();
                    String name4 = etxtName4.getText().toString();

                    int numOfGuests = 0;

                    if(name1.length() != 0)
                        numOfGuests++;
                    if(name2.length() != 0)
                        numOfGuests++;
                    if(name3.length() != 0)
                        numOfGuests++;
                    if(name4.length() != 0)
                        numOfGuests++;


                    int capacity = tempParty.getPeople() - numOfGuests;
                    Boolean capacityNotFull = tempParty.setPeople(capacity);
                    if(capacityNotFull == false)
                    {
                        Toast.makeText(GuestJoin.this, "Sorry, no spots left.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String AM_PM ;
                    int hourOfDay = tempParty.getPartyTime().getHours();
                    int minute = tempParty.getPartyTime().getMinutes();
                    String minuteString;


                    //Set time to AMPM
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


                    String msg = "Address: " + tempParty.getAddress() +
                            "\nDate: " +  tempParty.getPartyDate().getYear() + "-" + (tempParty.getPartyDate().getMonth() + 1) +  "-" +  tempParty.getPartyDate().getDate() +
                            "\nTime: " + hourOfDay + ":" + minuteString + " " + AM_PM +
                            "\nRegistered for " + numOfGuests + " people" +
                            "\nYour CONFIRMATION code: AH03F"; // WIP

                    builder.setMessage(msg)

                    .setCancelable(false).setPositiveButton("HOME", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Go to main page
                            Intent intent = new Intent(GuestJoin.this, MainActivity.class);
                            startActivity(intent);
                            Toast.makeText(GuestJoin.this, "You are in.", Toast.LENGTH_LONG).show();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Final Party Info");
                    alert.show();
                }
            });

            //SAVE INVITE TO TEXT FILE BUTTON
            //==============================================================
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    String file_name = tempParty.getPartyName() + ".txt";


                    // test - get real info here
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

                    //Invite file info
                    String invite = "Your party invite!\n" + tempParty.getPartyName() + "\n" +
                            "Date: " + (tempParty.getPartyDate().getYear()) + "-" + tempParty.getPartyDate().getDay() + "-" + tempParty.getPartyDate().getMonth() + "\n" +
                            "Time: " + hourOfDay + ":" + minuteString + " " + AM_PM + " \n" +
                            "Entry Fee: " + tempParty.getEntryFee() + "\n" +
                            "Drinks: " + tempParty.getDrinksString() + "\n";


                    FileOutputStream fos = null;

                    try
                    {
                        //File output writer
                        fos = openFileOutput(file_name, MODE_PRIVATE);
                        fos.write(invite.getBytes());

                        Toast.makeText(GuestJoin.this, "Saved to " + getFilesDir() + "/" + file_name, Toast.LENGTH_LONG).show();

                        mEditText.setVisibility(View.VISIBLE);
                        btnLoad.setVisibility(View.VISIBLE);
                        btnSave.setEnabled(false);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if(fos != null)
                        {
                            try
                            {
                                fos.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });

            //LOAD TEXT FILE INVITE BUTTON
            //==============================================================
            btnLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    //Set file name
                    String file_name = tempParty.getPartyName() + ".txt";
                    FileInputStream fis = null;

                    try
                    {
                        //File input reader
                        fis = openFileInput(file_name);
                        InputStreamReader isr = new InputStreamReader(fis);
                        BufferedReader br = new BufferedReader(isr);
                        StringBuilder sb = new StringBuilder();
                        String text;

                        //Read file and display
                        while ((text = br.readLine()) != null)
                        {
                            sb.append(text).append("\n");
                        }
                        mEditText.setText(sb.toString());
                        btnLoad.setEnabled(false);
                    }
                    catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        if (fis != null)
                        {
                            try
                            {
                                fis.close();
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }
}


