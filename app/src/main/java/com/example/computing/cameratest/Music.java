package com.example.computing.cameratest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.AsyncTask;
import android.widget.*;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Music extends Activity {


    static String xml = "";

    static Bitmap bitmap;

    ArrayList<String> songs = new ArrayList<String>();

//    ListView list = (ListView)findViewById(R.id.songList);
//    ArrayAdapter<String> songArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//            }
//        });

        new AsyncTaskParseXml().execute();
    }

    public class AsyncTaskParseXml extends AsyncTask<String, String, String>
    {
        String yourXmlServiceUrl = "https://query.yahooapis.com/v1/public/yql?q=SELECT%20content%2C%20src%2C%20alt%20FROM%20html%20WHERE%0Aurl%3D%22http%3A%2F%2Fwww.bbc.co.uk%2Fradio1%2Fchart%2Fsingles%2F%22%20and%20xpath%3D%22%2F%2Fimg%5B%40class%3D'cht-entry-image'%5D%7C%2F%2Fdiv%5B%40class%3D'cht-entry-artist'%5D%2Fa%22%20LIMIT%2080";

        @Override
        // this method is used for
        protected void onPreExecute()
        {

        }

        @Override
        protected String doInBackground(String... arg0)
        {
            try {
                String text = null;
                // create new instance of the http connect class
                httpConnect xmlParser = new httpConnect();

                // get cml string from service url
                xml = xmlParser.getJSONFromUrl(yourXmlServiceUrl);

                // creatte new inteace ogf xml pull parser
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                // set input to xml parser as xml string from service
                xpp.setInput( new StringReader(xml));

                // variable for xml parse event
                int event = xpp.getEventType();

                // variable for setting the chart position of reach song
                int chartPosition = 1;

                while (event != XmlPullParser.END_DOCUMENT) {
                    String name = xpp.getName();

                    switch (event) {
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            text = xpp.getText();
                            break;

                        case XmlPullParser.END_TAG:

                            // when xml parser matches the ing tag, get the alt attribute value which contains the song name
                            if (name.equals("img")) {
                                songs.add(chartPosition + ". " + xpp.getAttributeValue(null, "alt"));
                                // increment the chart pos
                                chartPosition++;

                                // get the first img tag line number as it containts the current nu,mber 1
                                if (xpp.getLineNumber() == 2) {
                                    // get the image cover art
                                    String imageurl = xpp.getAttributeValue(null, "src");
                                    // parse the coveer art url to proper type
                                    URL u = new URL(imageurl);
                                    // download image cover art from url and save as a bitmap
                                    InputStream is = u.openConnection().getInputStream();
                                    bitmap = BitmapFactory.decodeStream(is);
                                }
                            } else if (name.equals("a")) {
                                System.out.println("Artist: " + text.trim());
                            } else {
                            }
                            break;
                    }
                    event = xpp.next();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        // below method will run when service htto request ius complete
        protected void onPostExecute(String strFromDoInBg){
            // bind the xml from service to the textview
            ImageView singleArt = (ImageView) findViewById(R.id.singleCoverArt);
            singleArt.setImageBitmap(bitmap);

            ListView list = (ListView)findViewById(R.id.songList);
            ArrayAdapter<String> songArrayAdapter = new ArrayAdapter<String>(Music.this, android.R.layout.simple_expandable_list_item_1, songs);
            list.setAdapter(songArrayAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
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
