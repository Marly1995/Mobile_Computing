package com.example.computing.cameratest;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class storage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_storage, menu);
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

    public void saveUserData(View view){

        // get string values name email from widgets
        EditText name = (EditText)findViewById(R.id.nameText);
        EditText email = (EditText)findViewById(R.id.emailText);

        // create a new shared pref file by name
        // if it already s there use existing
        try{
            SharedPreferences userInfo = getSharedPreferences("userData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = userInfo.edit();
            //put key values into shared pref file
            editor.putString("user", name.getText().toString());
            editor.putString("email", email.getText().toString());
            editor.commit();

            // show toast message for success
            Context context = getApplicationContext();
            CharSequence text = "User data saved!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        catch( Exception ex){
            //error
        }
    }

    public void showUserData(View view){

        // create references to the two text widgets
        TextView name = (TextView)findViewById(R.id.showName);
        TextView email = (TextView)findViewById(R.id.showEmail);

        // get the saved values of the username and emak from prefs
        SharedPreferences userInfo = getSharedPreferences("userData", Context.MODE_PRIVATE);

        // set the new fields
        name.setText(userInfo.getString("user", ""));
        email.setText(userInfo.getString("email", ""));

    }
}
