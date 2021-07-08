package com.example.partyplaner;

//  FILE HEADER COMMENT
//  FILE            : ContactAdapter.java
//  PROJECT         : A-03 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-04-21 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : Gets access to the phones contacts and displays the name and number.

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<ContactItem>
{

    //CONTACT ADAPTER
    //==============================================================
    public ContactAdapter(Context context, ArrayList<ContactItem> contactList)
    {
        super(context, 0, contactList);
    }

    //GET THE CONTACTS AND DISPLAY THEM TO THE SCREEN
    //==============================================================
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ContactItem contact = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_list_item, parent, false);
        }
        // Lookup view for data population
        TextView contactName = (TextView) convertView.findViewById(R.id.contactName);
        TextView contactNumber = (TextView) convertView.findViewById(R.id.contactNumber);
        // Populate the data into the template view using the data object
        contactName.setText(contact.name);
        contactNumber.setText(contact.phoneNumber);
        // Return the completed view to render on screen
        return convertView;
    }
}