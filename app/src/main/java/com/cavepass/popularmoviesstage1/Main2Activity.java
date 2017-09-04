package com.cavepass.popularmoviesstage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class Main2Activity extends AppCompatActivity {

    ArrayList<cards> movies = new ArrayList<>();


    private GridView gridview;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        gridview = (GridView) findViewById(R.id.grid);
        gridview.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);

        MovieAsyncTask task = new MovieAsyncTask();
        task.execute();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {


                Intent i = new Intent(Main2Activity.this,DetailsActivity.class);

                i.putExtra("overview",movies.get(position).getmOverview());
                i.putExtra("Release_date",movies.get(position).getmYear());
                i.putExtra("rating",movies.get(position).getmVote());
                i.putExtra("imageID",movies.get(position).getmImageID());
                i.putExtra("Title",movies.get(position).getmTitle());

                startActivity(i);



            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(Main2Activity.this,sort.class);
        startActivity(i);




        return super.onOptionsItemSelected(item);
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
                
                url = createUrl(getString(R.string.MovieDB_URL2)+getString(R.string.API));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) (url != null ? url.openConnection() : null);
              
                InputStream inputStream = urlConnection != null ? urlConnection.getInputStream() : null;
            
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream != null ? inputStream : null));
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




                cardsAdapter movieArrayAdapter = new cardsAdapter(Main2Activity.this,movies);




                //Setting adapter to gricview




                gridview.setAdapter(movieArrayAdapter);











            }

            catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
