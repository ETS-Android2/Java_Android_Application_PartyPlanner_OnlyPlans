package com.example.partyplaner;

//  FILE HEADER COMMENT:
//  FILE            : DatabaseHelper.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : This is the database class that works with SQLite.
//                    This class creates the database and has functions to add to the database
//                    and read from the database.

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;

public class DatabaseHelper extends SQLiteOpenHelper
{

    //DB structure
    private static final String Tag = "DatabaseHelper";

    //Create table/columns
    private static final String TABLE_NAME = "party_table";
    private static final String COL0 = "ID";
    private static final String COL1 = "name";
    private static final String COL2 = "address";
    private static final String COL3 = "maxGuests";
    private static final String COL4 = "date";
    private static final String COL5 = "time";
    private static final String COL6 = "entryFee";
    private static final String COL7 = "drinks";
    private static final String COL8 = "keyCode";

//    public static final String TASK_MODIFIED =
//            "com.murach.tasklist.TASK_MODIFIED";


    public DatabaseHelper(Context context)
    {
        super(context, TABLE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTableQ = "CREATE TABLE " + TABLE_NAME + " ("+COL0+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL1 + " TEXT, " +
                COL2 + " TEXT, " +
                COL3 + " INTEGER, " +
                COL4 + " TEXT, " +
                COL5 + " TEXT, " +
                COL6 + " REAL, " +
                COL7 + " TEXT, " +
                COL8 + " INTEGER )";

        db.execSQL(createTableQ);
    }

    //UPDATE
    //==============================================================
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //CLEAR
    //==============================================================
    public void clearTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE * FROM "+ TABLE_NAME);
    }


    //ADD DATA
    //==============================================================
    public boolean addData(Party partyObj)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, partyObj.getPartyName());
        contentValues.put(COL2, partyObj.getAddress());
        contentValues.put(COL3, partyObj.getPeople());
        contentValues.put(COL4, partyObj.getPartyDate().toString());
        contentValues.put(COL5, partyObj.getPartyTime().toString());
        contentValues.put(COL6, partyObj.getEntryFee());
        contentValues.put(COL7, partyObj.getDrinksString());
        contentValues.put(COL8, partyObj.getRandCode());

        long res = db.insert(TABLE_NAME, null, contentValues);

        //broadcastTaskModified();

        if(res == -1)
        {
            return false;
        }else
        {
            return true;
        }
    }


    //GET PARTY INFO
    //==============================================================
    public ArrayList<Party> getPartyArray()
    {
        ArrayList<Party> retParties = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        while(data.moveToNext())
        {
            Party tmpParty = new Party(data.getString(1), data.getString(2), Date.valueOf(data.getString(4)), Time.valueOf(data.getString(5)), data.getInt(3), data.getInt(6), data.getString(7), data.getInt(8));
            retParties.add(tmpParty);
        }

        return retParties;
    }

    //GET DATA
    //==============================================================
    public Cursor getData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return  data;
    }

    //GET ITEM ID
    //==============================================================
    public Cursor getItemId(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String idQuery = "SELECT " + COL0 + " FROM " + TABLE_NAME + " WHERE" + COL1 + " = '" + name + "'";
        Cursor data = db.rawQuery(idQuery, null);
        return data;
    }

    //UPDATE PARTY NAME
    //==============================================================
    public void updateName(String newName, int id, String oldName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 + " = '" +newName+ "' WHERE " + COL0 + " = '"+id+"' AND " + COL1 + " = '" + oldName + "'";
    }

    //CHECK TO SEE IF PARTY EXISTS
    //==============================================================
    public boolean check(String partyName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "Select * from " + TABLE_NAME + " where " + COL1 + " = '" + partyName + "'";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        else
        {
            cursor.close();
            return true;
        }
    }

    //BROADCAST DATABASE MODIFIED
    //==============================================================
//    private void broadcastTaskModified() {
//        Intent intent = new Intent(TASK_MODIFIED);
//        Context context = null;
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            context.sendBroadcast(intent);
//            return;
//        }
//
//        Intent broadcastIntent = new Intent(intent);
//        PackageManager pm = context.getPackageManager();
//
//        java.util.List<ResolveInfo> broadcastReceivers  = pm.queryBroadcastReceivers(broadcastIntent, 0);
//        for(ResolveInfo info : broadcastReceivers) {
//            broadcastIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
//            context.sendBroadcast(broadcastIntent);
//        }
//    }
}

