package com.cavepass.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetailsActivity extends AppCompatActivity {


    String Overview;
    String Year;


    public void setOverview(String overview){
        Overview = overview;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(CheckNetwork.isInternetAvailable(this)) //returns true if internet available
        {

        TextView rating = (TextView) findViewById(R.id.rating);
        TextView Title = (TextView) findViewById(R.id.Title);
        TextView release = (TextView) findViewById(R.id.release_date);
       TextView overview = (TextView) findViewById(R.id.overview);
        ImageView icon = (ImageView)  findViewById(R.id.full_image_view) ;


        Bundle bundle = getIntent().getExtras();


        String title_ = String.valueOf(bundle.get("Title"));
        String overview_ = String.valueOf(bundle.get("overview"));
        String image1 = String.valueOf(bundle.get("imageID"));
        String release_ = String.valueOf(bundle.get("Release_date"));
        String rating_ = String.valueOf(bundle.get("rating"));



        Glide.with(this).load("https://image.tmdb.org/t/p/w185/"+image1).into(icon);
        Title.setText(title_);
      release.setText(release_);
       overview.setText(overview_);
        rating.setText("IMDB: "+rating_+"/10");



    }
        else
        {
            Toast.makeText(this,"No Internet Connection",1000).show();
        }
}}
