package com.example.partyplaner;

//FILE HEADER COMMENT
//FILE            : UsersAdapter.java
//PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//FIRST VERSION   : 2021-02-13 (Rev.01)
//AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//DESCRIPTION     : Displays party hat images and other fetch details for the listview


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

public class UsersAdapter extends ArrayAdapter<Party>
{
    //Partyhat iamge array
    public int[] imagesPartHat = {R.drawable.partyhat01,
            R.drawable.partyhat02, R.drawable.partyhat03,
            R.drawable.partyhat04, R.drawable.partyhat05,
            R.drawable.partyhat06, R.drawable.partyhat07,
            R.drawable.partyhat08, R.drawable.partyhat09};

    public UsersAdapter(Context context, ArrayList<Party> partyObjects)
    {
        super(context, 0, partyObjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        Party p = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvEntryFee = (TextView) convertView.findViewById(R.id.tvEntryFee);
        //ImageView imgView = (ImageView) convertView.findViewById(R.id.imgList);

        // Partyhat random image insert into list
        ImageView tvImage = (ImageView) convertView.findViewById(R.id.imgList);
        double random_dub = Math.random() * (8 - 0 + 1) + 0;
        int rand = (int)random_dub;
        tvImage.setImageResource(imagesPartHat[rand]);

        // Populate the data into the template view using the data object
        tvName.setText(p.getPartyName());
        tvEntryFee.setText("Entry fee: $" + p.getEntryFee() + "\nMax people: " + p.getPeople());
        // Return the completed view to render on screen
        return convertView;
    }
}
