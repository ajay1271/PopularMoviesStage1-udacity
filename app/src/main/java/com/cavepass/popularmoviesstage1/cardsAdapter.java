package com.cavepass.popularmoviesstage1;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;



import java.util.ArrayList;

public class cardsAdapter extends ArrayAdapter<cards> {


  Context context;

    cardsAdapter(Activity context, ArrayList<cards> cards) {


        super(context, 0, cards);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View listItemView  = convertView;

        if(listItemView==null){

            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);



        }


        cards currentCard = getItem(position);

        ImageView icon = (ImageView) listItemView.findViewById(R.id.image);





        Glide.with(context).load("https://image.tmdb.org/t/p/w185/"+ (currentCard != null ? currentCard.getmImageID() : null)).into(icon);





        return listItemView;

    }

}
