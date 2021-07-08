package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : ContactItem.java
//  PROJECT         : A-03 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-04-21 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Stores the name and phone number in public struct.

public class ContactItem
{

    // initialize name and phone number
    String name;
    String phoneNumber;

    //STORE CONTACT INFO
    //==============================================================
    public ContactItem(String n, String pn)
    {
        name = n;
        phoneNumber = pn;
    }
}
