package com.cavepass.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class sort extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_menu);




    }
void toprated(View view){

    Intent i= new Intent(this,Main2Activity.class);
    startActivity(i);

}

void popular(View view){


    Intent i= new Intent(this,MainActivity.class);
    startActivity(i);


}


}
