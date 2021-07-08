package com.example.partyplaner;

//  FILE HEADER COMMENT:
//  FILE            : CreateParty.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Main program entry point.  The User can either be a Host and
//                    create a party.  They are able to choose optional details and
//                    information to show to the potential guests, or be a Guest and
//                    view a list of existing parties.  They can select and join a party
//                    (with optional friends attending), using a unique code provided
//                    by the Host.

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import static com.example.partyplaner.GlobalState.userCreatedParty;

public class CreateParty extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    //Declaration
    DatePickerDialog pickDate;//Date widget picker
    TimePickerDialog pickTime; //Time widget picker
    EditText etxtName; //Name Entry
    EditText etxtAddress;//Address Entry
    EditText etxtDate; //Date line
    EditText etxtTime; //Time line
    Button btnDone;
    //Checkboxes for items
    CheckBox checkBox, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8;
    Button btnSubmit, btnClear; //Submit and Clear buttons
    TextView debugText;
    private TextView textViewPrice;
    private SeekBar seekBar;
    DatabaseHelper mDatabaseHelper;

    //Variables
    private String Name = "";
    private String Address = "";
    private Date PartyDate = null;
    private Time PartyTime = null;
    private int People = 1;
    private int EntryFee = 0;
    private ArrayList<String> AlcoholicBevarages;
    private ArrayList<String> NonAlcoholicBevarages;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_party);


        //Initialization of the information entry states
        etxtName = (EditText) findViewById(R.id.etxt_name);
        etxtAddress = (EditText) findViewById(R.id.etxt_address);
        etxtDate = (EditText) findViewById(R.id.etxt_date);
        etxtTime = (EditText) findViewById(R.id.etxt_time);
        debugText = (TextView) findViewById(R.id.txt_debug);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        //Initialize checkboxes
        etxtDate = (EditText) findViewById(R.id.etxt_date);
        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
        mDatabaseHelper = new DatabaseHelper(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                textViewPrice.setText("Entry Fee: $" + progress);
                EntryFee = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //DATE PICKER
        //==============================================================
        etxtDate.setInputType(InputType.TYPE_NULL);
        etxtDate.setOnClickListener(new View.OnClickListener()
        {
            //Sets the date using the date widget
            @Override
            public void onClick(View v)
            {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);


                pickDate = new DatePickerDialog(CreateParty.this,
                        new DatePickerDialog.OnDateSetListener()
                        {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day)
                            {
                                PartyDate = new Date(year, month, day);
                                month += 1;
                                etxtDate.setText(year + "-" + month + "-" + day);
                            }
                        }, year, month, day);
                pickDate.show();
            }
        });


        //TIME PICKER
        //==============================================================
        etxtTime.setInputType(InputType.TYPE_NULL);
        etxtTime.setOnClickListener(new View.OnClickListener()
        {
            //Sets the time using the time widget
            @Override
            public void onClick(View v)
            {
                Calendar cldr = Calendar.getInstance();
                int currH = cldr.getTime().getHours();
                int currM = cldr.getTime().getMinutes();

                //Calculates the time result and display options
                pickTime = new TimePickerDialog(CreateParty.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        PartyTime = new Time(hourOfDay, minute, 0);
                        String minuteString;

                        String AM_PM ;

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
                        etxtTime.setText(hourOfDay + ":" + minuteString + " " + AM_PM);
                    }
                }, currH,currM,false);
                pickTime.show();
            }
        });


        //SPINNER
        //==============================================================
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //FINAL BUTTON
        //==============================================================
        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            //Gets all the entries for the Host information entered
            @Override
            public void onClick(View v)
            {
                //Get name
                Name = etxtName.getText().toString();

                //Get Address
                Address = etxtAddress.getText().toString();

                if(Name.isEmpty() == false && Address.isEmpty() == false && PartyDate != null && PartyTime != null)
                {
                    StringBuilder result = new StringBuilder();

                    if (checkBox.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox.getText().toString());
                    }
                    if (checkBox2.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox2.getText().toString());
                    }
                    if (checkBox3.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox3.getText().toString());
                    }
                    if (checkBox4.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox4.getText().toString());
                    }
                    if (checkBox5.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox5.getText().toString());
                    }
                    if (checkBox6.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox6.getText().toString());
                    }
                    if (checkBox7.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox7.getText().toString());
                    }
                    if (checkBox8.isChecked())
                    {
                        result.append("\n\u2022 ").append(checkBox8.getText().toString());
                    }
                    if (!checkBox.isChecked() && !checkBox2.isChecked() && !checkBox3.isChecked() && !checkBox4.isChecked() && !checkBox5.isChecked() && !checkBox6.isChecked() && !checkBox7.isChecked() && !checkBox8.isChecked())
                        result.append("No Drinks");

                    //Create a Party OBJECT
                    Party firstParty = new Party(Name, Address, PartyDate, PartyTime, People, EntryFee, result.toString());
                    firstParty.setRandCode(generateRandomCode());

                    mDatabaseHelper = new DatabaseHelper(CreateParty.this);
                    AddData(firstParty);


                    userCreatedParty = true;


                    Intent intent2 = new Intent(CreateParty.this, HostFinal.class);
                    intent2.putExtra("PartyObj", firstParty);
                    startActivity(intent2);
                }
                //One of the entries is missing information, display missing field issue to user
                else
                {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //CLEAR BUTTON
        //==============================================================
        btnClear.setOnClickListener(new View.OnClickListener()
        {
            //Allows user to clear out all the current information entries
            @Override
            public void onClick(View v)
            {
                etxtAddress.setText("");
                etxtName.setText("");

                spinner.setSelection(0);

                etxtDate.setText("");
                etxtTime.setText("");

                seekBar.setProgress(0);

                if(checkBox.isChecked())
                    checkBox.setChecked(false);
                if(checkBox2.isChecked())
                    checkBox2.setChecked(false);
                if(checkBox3.isChecked())
                    checkBox3.setChecked(false);
                if(checkBox4.isChecked())
                    checkBox4.setChecked(false);
                if(checkBox5.isChecked())
                    checkBox5.setChecked(false);
                if(checkBox6.isChecked())
                    checkBox6.setChecked(false);
                if(checkBox7.isChecked())
                    checkBox7.setChecked(false);
                if(checkBox8.isChecked())
                    checkBox8.setChecked(false);

                //Displays result of clear to user
                Toast.makeText(getApplicationContext(), "All Fields have been cleared.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Max people selectionS
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String maxPeople = parent.getItemAtPosition(position).toString();
        People = Integer.parseInt(maxPeople);
        //Toast.makeText(parent.getContext(), "ppl:" + maxPeople, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
    }


    //A02 - methods =====================================================================================
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

    //Random number for ID assignment
    private int generateRandomCode()
    {
        int min = 1;
        int max = 99999;
        double random_dub = Math.random() * (max - min + 1) + min;
        int number = (int) random_dub;

        return number;
    }
}