package com.example.partyplaner;

//  FILE HEADER COMMENT:
//  FILE            : Specials.java
//  PROJECT         : A-02 : Mobile App Party Planner - OnlyPlans (PROG3150)
//  FIRST VERSION   : 2021-02-13 (Rev.01)
//  AUTHORS         : Group 9 - Dusan Sasic, Devin Caron, Cole Spehar, Kevin Downer
//  DESCRIPTION     : This is the class for the special events page to display our
//                    current events with chained-async tasks and images downloaded from the
//                    internet.

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

public class  Specials extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CALL = 1;

    ImageView showImage;
    Button btnDownload;
    ProgressBar progressBar;
    TextView tvDetails;
    int imageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specials);
        progressBar = findViewById(R.id.progress_bar);

        ImageView imageCall = findViewById(R.id.image_call);
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });


        showImage = findViewById(R.id.showImage);
        btnDownload = findViewById(R.id.btnDownload);
        tvDetails = findViewById(R.id.tvDetails);

        btnDownload.setOnClickListener(this);
    }

    //MAKE PHONE CALL
    //==============================================================
    private void makePhoneCall()
    {
        String number = "613-745-1576";

        if (ContextCompat.checkSelfPermission(Specials.this ,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Specials.this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        }
        else
        {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    //PHONE PERMISSIONS
    //==============================================================
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall();
            }
            else
            {
                Toast.makeText(this, "Permission DENIED.", Toast.LENGTH_SHORT);
            }
        }
    }

    //BUTTON TO DISPLAY EVENTS AND START ASYNC TASK
    //==============================================================
    @Override
    public void onClick(View v)
    {
        if(imageNum != 0)
        {
            tvDetails.setText("Come join us for the greatest St. Patrick's Day Party ever!\n" +
                    "Don't miss out on the festivities!");
        }
        //Start async image download task
        new ImageDownload().execute("https://i.ibb.co/hY87zK1/pp-rs.png");

        imageNum = 0;
    }

    //ASYNC TASK - IMAGE DOWNLOAD
    //==============================================================
    private class ImageDownload extends AsyncTask<String, Void, Bitmap>
    {
        HttpURLConnection httpURLConnection;

        @Override
        protected Bitmap doInBackground(String... strings)
        {
            try
            {
                //URL connection
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                //Get image
                Bitmap temp = BitmapFactory.decodeStream(inputStream);

                return temp;
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally
            {
                //Disconnect URL connection
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null)
            {
                showImage.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Download Successful!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Download Failed.", Toast.LENGTH_SHORT).show();
            }

            if(imageNum == 0)
            {
                //Start chained-async task (ProgressTask)
                ProgressTask task = new ProgressTask(Specials.this);
                task.execute(10);
            }

            imageNum = 1;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    //ASYNC TASK - LOADING PROGRESS BAR
    //==============================================================
    private class ProgressTask extends AsyncTask<Integer, Integer, String>
    {
        private WeakReference<Specials> activityWeakReference;
        ProgressTask(Specials activity)
        {
            activityWeakReference = new WeakReference<Specials>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Specials activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing())
            {
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(Integer... integers)
        {
            for (int i = 0; i < integers[0]; i++)
            {
                publishProgress((i * 100) / integers[0]);
                try
                {
                    Thread.sleep(500);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return "Finished!";
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
            Specials activity = activityWeakReference.get();

            if (activity == null || activity.isFinishing())
            {
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            Specials activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing())
            {
                return;
            }

            activity.progressBar.setProgress(100);

            new ImageDownload().execute("https://i.ibb.co/fk7TQhW/of-rs.png");
            tvDetails.setText("We are also a proud sponsor of the annual K/W Oktoberfest, bringing joy for all lovers of beer and schnitzel!");
        }
    }
}

