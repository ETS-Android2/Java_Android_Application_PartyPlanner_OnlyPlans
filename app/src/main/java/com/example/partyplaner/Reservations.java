package com.example.partyplaner;

//  FILE HEADER COMMENT:
//  FILE            : Reservations.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : This class models the reservations page where users can view
//                    different party location's websites as well as call to book
//                    their parties. It also allows navigation to the holiday specials
//                    event page.

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class  Reservations extends AppCompatActivity {

    //Initialization
    Button buttonMcD;
    Button buttonPartyCity;
    Button buttonBurgerKing;
    Button buttonRuralRoots;
    Button buttonSpecials;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);


        //BUTTON TO NAVIGATE TO SPECIALS PAGE
        //==============================================================
        buttonSpecials = findViewById(R.id.buttonSpecial);
        buttonSpecials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reservations.this, Specials.class);
                startActivity(intent);
            }
        });

        //BUTTON TO NAVIGATE TO MCDONALD'S WEBSITE
        //==============================================================
        buttonMcD = findViewById(R.id.buttonMcD);
        buttonMcD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("https://www.mcdonalds.com/ca/en-ca.html"));
                startActivity(i2);
            }
        });

        //BUTTON TO NAVIGATE TO PARTY CITY'S WEBSITE
        //==============================================================
        buttonPartyCity = findViewById(R.id.buttonPartyCity);
        buttonPartyCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("https://www.partycity.ca/en.html"));
                startActivity(i2);
            }
        });


        //BUTTON TO NAVIGATE TO BURGER KING'S WEBSITE
        //==============================================================
        buttonBurgerKing = findViewById(R.id.buttonBurgerKing);
        buttonBurgerKing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("https://www.burgerking.ca/"));
                startActivity(i2);
            }
        });

        //BUTTON TO NAVIGATE TO RURAL ROOTS WEBSITE
        //==============================================================
        buttonRuralRoots = findViewById(R.id.buttonRuralRoots);
        buttonRuralRoots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Intent.ACTION_VIEW);
                i2.setData(Uri.parse("https://ruralrootsbrewery.ca/"));
                startActivity(i2);
            }
        });
    }
}