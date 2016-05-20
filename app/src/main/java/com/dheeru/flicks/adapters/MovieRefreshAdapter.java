package com.dheeru.flicks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dheeru.flicks.R;
import com.dheeru.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dkthaku on 5/20/16.
 */
public class MovieRefreshAdapter extends ArrayAdapter<Movie> {

    public MovieRefreshAdapter(Context context, ArrayList<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        Movie movie = getItem(position);
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.swiperefreshlayout, parent, false);
        }

        ImageView mvImage = (ImageView)convertView.findViewById(R.id.rmovieImage);
        Picasso.with(getContext()).load(movie.getPosterPath()).into(mvImage);
        // mvImage.setImageResource(0);

        TextView textViewTitle = (TextView)convertView.findViewById(R.id.rmvTitle);
        textViewTitle.setText(movie.getOriginalTitle());

        TextView textOriginalView = (TextView)convertView.findViewById(R.id.rmvOverview);
        textOriginalView.setText(movie.getOverView());

        return convertView;
    }
}
