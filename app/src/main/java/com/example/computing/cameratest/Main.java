package com.example.computing.cameratest;

import android.app.Activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.support.v4.content.FileProvider;
import android.os.Bundle;


import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Main extends Activity {
    String currentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private File createImageFile() throws IOException{
        // create image filename
        String timeStamp = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        // save a file path for which to use action intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void dispatchTakePictureIntent(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // ensure there is a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) !=null){
            // create file where the photo will go
            File photoFile = null;
            try{
                photoFile = createImageFile();
            } catch (IOException ex){
                // we had an error or sorts
                Log.e("image file creation", ex.getMessage());
            }
            //continue if the shit works
            if (photoFile != null){
                Uri photoURI = FileProvider.getUriForFile(
                        this,
                        "com.example.computing.cameratest",
                        photoFile);
                Log.d("uri working", String.valueOf(photoURI));
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            File imgFile = new File(currentPhotoPath);
            if (imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                ImageView myImage = (ImageView) findViewById(R.id.imageView);

                myImage.setImageBitmap(myBitmap);
            }
        }
    }

    public void browserIntent(View view){
        String url = "http://www.imdb.com/title/";
        String movieID = "tt0816692";
        Uri webpage = Uri.parse(url + movieID);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }

    public void eventIntent(View view){
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, 12, 25, 00, 00);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, 12, 25, 00, 01);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis());
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Xmas!");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "North Pole");
        startActivity(calendarIntent);
    }

    public void startTimer(View view){
        Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                .putExtra(AlarmClock.EXTRA_MESSAGE, "timer started!")
                .putExtra(AlarmClock.EXTRA_LENGTH, 60)
                .putExtra(AlarmClock.EXTRA_SKIP_UI, false);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void getTweetData (View view){
        // create an intent to the tweets activity
        Intent intent = new Intent(this, Tweets.class);
        startActivity(intent);
    }

    public void getMusicData(View view)
    {
        // create intent to start music thingy
        Intent intent = new Intent(this, Music.class);
        startActivity(intent);
    }

}










































