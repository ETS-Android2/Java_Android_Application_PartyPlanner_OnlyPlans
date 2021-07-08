package com.example.partyplaner;

//FILE HEADER COMMENT
//FILE            : Party.java
//PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)PROJECT         : A-06 : My Own Web Server (PROG2001)
//FIRST VERSION   : 2021-02-13 (Rev.01)
//AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//DESCRIPTION     : Gets the details entered in for the creation of a party event


import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedList;

public class Party implements Serializable
{
    //Declarations
    private String PartyName;
    private String Address;
    private Date PartyDate;
    private Time PartyTime;
    private int People;
    private int EntryFee;
    private String DrinksString;
    private int RandCode;

    public Party()
    {
        PartyName = "Test";
        Address = "Test add";
        PartyDate = new Date(1,1,1);
        PartyTime = new Time(1,1,1);
        People = 50;
        EntryFee = 25;
        DrinksString = "..";
    }

    public Party(String name, String address, Date partyDate, Time partyTime, int people, int entryFee, String drinksString)
    {
        PartyName = name;
        Address = address;
        PartyDate = partyDate;
        PartyTime = partyTime;
        People = people;
        EntryFee = entryFee;
        DrinksString = drinksString;
    }

    public Party(String name, String address, Date partyDate, Time partyTime, int people, int entryFee, String drinksString, int keyC)
    {
        PartyName = name;
        Address = address;
        PartyDate = partyDate;
        PartyTime = partyTime;
        People = people;
        EntryFee = entryFee;
        DrinksString = drinksString;
        RandCode = keyC;
    }

    //Getters & Setters
    public String getAddress()
    {
        return Address;
    }

    public Date getPartyDate()
    {
        return PartyDate;
    }

    public Time getPartyTime()
    {
        return PartyTime;
    }

    public int getPeople()
    {
        return People;
    }

    public int getEntryFee()
    {
        return EntryFee;
    }

    public String getPartyName()
    {
        return PartyName;
    }

    public String getDrinksString()
    {
        return DrinksString;
    }

    public int getRandCode()
    {
        return RandCode;
    }


    public void setAddress(String address)
    {
        Address = address;
    }

    public void setPartyDate(Date partyDate)
    {
        PartyDate = partyDate;
    }

    public void setPartyTime(Time partyTime)
    {
        PartyTime = partyTime;
    }

    public Boolean setPeople(int people)
    {
        if(people < 0)
        {
            return false;
        }
        else
        {
            People = people;
            return true;
        }
    }

    public void setEntryFee(int entryFee)
    {
        EntryFee = entryFee;
    }

    public void setPartyName(String partyName)
    {
        PartyName = partyName;
    }

    public void setDrinksString(String drinksString)
    {
        DrinksString = drinksString;
    }

    public void setRandCode(int randCode)
    {
        RandCode = randCode;
    }


}
