package com.example.computing.cameratest;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;
import android.net.Uri;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import android.widget.TextView;


public class fileStorage extends Activity {

    public double lat;
    public double longi;

    String filename = "locationdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create the TestActivity activity
        super.onCreate(savedInstanceState);

// get location code
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
// Use network provider to get last known location
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

// check if a last known location exists
        if (lastKnownLocation == null) {
            // if no last location is available set lat/long to zero
            lat = 0;  // lat of Lincoln is 53.228029;
            longi = 0; // longi of Lincoln is -0.546055;
        } else {
            // if last location exists then get/set the lat/long
            lat = lastKnownLocation.getLatitude();
            longi = lastKnownLocation.getLongitude();
        }

// Create a new TextView widget programmatically and bind the current lat/long to it
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Location:\n" + lat + "\n" + longi);

// Set the text view as the activity layout and display coordinates
        setContentView(textView);

// write to file containing location coordinates
        String locationVal = lat + "," + longi;
        FileOutputStream outputStream;
        File file = getFileStreamPath(filename);

// first check if file exists, if not create it
        if (file == null || !file.exists()) {
            try {
                outputStream = openFileOutput(filename, MODE_PRIVATE);
                outputStream.write(locationVal.getBytes());
                outputStream.write("\r\n".getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

// if file already exists then append location data to it
        else if (file.exists()) {
            try {
                outputStream = openFileOutput(filename, Context.MODE_APPEND);
                outputStream.write(locationVal.getBytes());
                outputStream.write("\r\n".getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_storage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
