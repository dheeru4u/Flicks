package com.dheeru.flicks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dheeru.flicks.R;
import com.dheeru.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by dkthaku on 5/21/16.
 */
public  class MovieDynaItemAdapater extends
        RecyclerView.Adapter<MovieDynaItemAdapater.ViewHolder> {

    List<Movie> movieList =new ArrayList<Movie>();

    public MovieDynaItemAdapater(List<Movie> movieList) {
        Log.d("MovieDynaItemAdapater", "MovieDynaItemAdapater: "+movieList);
        this.movieList = movieList;

    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MovieDynaItemAdapater.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View view = inflater.inflate(R.layout.item_movie, parent, false);
     //   Log.d(this.getClass().getSimpleName(), "onCreateViewHolder: view"+view.getId());
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MovieDynaItemAdapater.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Movie movie = movieList.get(position);
        if(movie==null ){
            Log.d(this.getClass().getSimpleName(), "onBindViewHolder: in if "+movie);
            return;
        }
       // Log.d(this.getClass().getSimpleName(), "onBindViewHolder: "+movie.toString());
        // Set item views based on the data model

        ImageView mvImage = viewHolder.mvImage;
       // Picasso.with(mvImage.getContext()).load(movie.getPosterPath()).into(mvImage);
        // mvImage.setImageResource(0);
       // Log.d(this.getClass().getSimpleName(), "mvImage: "+mvImage);
        /**
         Picasso.with(mContext).load(R.drawable.demo)
         .transform(new RoundedCornersTransformation(10, 10)).into((ImageView) findViewById(R.id.image));
         * **/
        if(mvImage!=null) {
            /**
            Picasso.with(mvImage.getContext()).load(movie.getPosterPath()).fit().centerCrop()
                    .placeholder(R.drawable.myphoto)
                    .error(R.drawable.donaldtrump)
                    .into(mvImage);
             */
            Picasso.with(mvImage.getContext()).load(movie.getPosterPath())
                    .transform(new RoundedCornersTransformation(20, 20)).into(mvImage);
        }
        TextView textViewTitle = viewHolder.mvTitle;
      //  Log.d(this.getClass().getSimpleName(), "textViewTitle: XXXXXXX "+textViewTitle);
        if(textViewTitle!=null)
            textViewTitle.setText(movie.getOriginalTitle());


        TextView textOriginalView = viewHolder.mvOverview;
       // Log.d(this.getClass().getSimpleName(), "textOriginalView: XXXXXXX "+textOriginalView);
        if(textOriginalView!=null)
            textOriginalView.setText(movie.getOverView());


    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public ImageView mvImage ;
        public TextView mvTitle;
        public TextView mvOverview ;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

             mvImage = (ImageView)itemView.findViewById(R.id.movieImage);
           // Picasso.with(getContext()).load(movie.getPosterPath()).into(mvImage);
            // mvImage.setImageResource(0);
            mvTitle = (TextView)itemView.findViewById(R.id.mvTitle);
            mvOverview = (TextView)itemView.findViewById(R.id.mvOverview);
           // textOriginalView.setText(movie.getOverView());
        }
    }
}
