package com.example.computing.cameratest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.json.JSONArray;
import org.json.JSONObject;
import android.os.AsyncTask;
import java.util.ArrayList;

import android.view.View;
import android.widget.*;

public class Tweets extends Activity {

    // array list to store tweets items from web service
    ArrayList<String> items = new ArrayList<String>();
    // json test string
    String jsonTest;
    String webAccess = "http://derekfoster.cloudapp.net/tweet/tweetledee/userjson.php?user=";
    String webEnd = "&c=10";
    String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweets);

        // start the  AsyncTask for calling the REST service using httpConnect class

    }

    public void fetchTweets (View view)
    {
        items.clear();
        EditText name = (EditText) findViewById(R.id.twitterHandle);
        UserName = name.getText().toString();
        new AsyncTaskParseJson().execute();
    }
    // added asynctask class methods below -  you can make this class as a separate class file
    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        // set the url of the web service to call
        String yourServiceUrl = webAccess + UserName + webEnd;

        @Override
        // this method is used for......................
        protected void onPreExecute() {}

        @Override
        // this method is used for...................
        protected String doInBackground(String... arg0)  {

            try {
                // create new instance of the httpConnect class
                httpConnect jParser = new httpConnect();

                // get json string from service url
                String json = jParser.getJSONFromUrl(yourServiceUrl);

                // parse returned json into array
                JSONArray jsonArray = new JSONArray(json);

                //loop through json array and add each tweet to item in the list
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject json_message = jsonArray.getJSONObject(i);

                    if (json_message != null){
                        // add each tweet to array list as an item
                        items.add(json_message.getString("text"));
                    }
                }

                // save returned json to your test string
                jsonTest = json.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        // below method will run when service HTTP request is complete, will then bind tweet text in arrayList to ListView
        protected void onPostExecute(String strFromDoInBg) {
            ListView list = (ListView)findViewById(R.id.tweetList);
            ArrayAdapter<String> tweetArrayAdapter = new ArrayAdapter<String>(Tweets.this, android.R.layout.simple_expandable_list_item_1, items);
            list.setAdapter(tweetArrayAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweets, menu);
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
