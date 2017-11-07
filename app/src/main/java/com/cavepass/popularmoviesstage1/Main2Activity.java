package com.cavepass.popularmoviesstage1;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import org.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ArrayList<cards> movies = new ArrayList<>();


    private GridView gridview;

    boolean popular=false;
    boolean toprated=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(CheckNetwork.isInternetAvailable(this))
        {



            gridview = (GridView) findViewById(R.id.grid);
            gridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

            MovieAsyncTask task = new MovieAsyncTask();
            task.execute();
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position,
                                        long id) {


                    Intent i = new Intent(MainActivity.this,DetailsActivity.class);

                    i.putExtra("overview",movies.get(position).getmOverview());
                    i.putExtra("Release_date",movies.get(position).getmYear());
                    i.putExtra("rating",movies.get(position).getmVote());
                    i.putExtra("imageID",movies.get(position).getmImageID());
                    i.putExtra("Title",movies.get(position).getmTitle());

                    startActivity(i);



                }
            });

        }
        else
        {
            Toast.makeText(this,getString(R.string.Error),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinner);
       final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            String items = spinner.getSelectedItem().toString();


            if(items.equals(getString(R.string.toprated))){

                Intent j = new Intent(MainActivity.this,Main2Activity.class);

                    startActivity(j);



            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {

        }

    });




        return true;
    }



    void rating(View view){



        Intent i = new Intent(MainActivity.this,DetailsActivity.class);

        popular=true;

        startActivity(i);



    }







    private class MovieAsyncTask extends AsyncTask<String, Void, String> {

        // ArrayList<cards> movies = new ArrayList<>();





        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }





        @Override
        protected String doInBackground(String... params) {
            URL url = null;





            try {

                url = createUrl(getString(R.string.MovieDB_URL)+getString(R.string.API));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) (url != null ? url.openConnection() : null);

                InputStream inputStream = urlConnection != null ? urlConnection.getInputStream() : null;

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s = bufferedReader.readLine();
                bufferedReader.close();

                return s;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage(), e);
            }
            return null;
        }






        private URL createUrl(String stringUrl) {
            URL url;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {

                return null;
            }
            return url;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{

                JSONObject baseJsonResponce = new JSONObject(s);
                JSONArray featureArray = baseJsonResponce.getJSONArray("results");

                if(featureArray.length()>0){


                    for(int i=0;i<featureArray.length();i++){

                        JSONObject Feature = featureArray.getJSONObject(i);
                        movies.add(new cards(Feature.getString("original_title"),Feature.getString("release_date"),Feature.getString("poster_path"),Feature.getString("overview"),Feature.getString("vote_average")));



                    }
                }




                cardsAdapter movieArrayAdapter = new cardsAdapter(MainActivity.this,movies);



                gridview.setAdapter(movieArrayAdapter);




            }

            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
