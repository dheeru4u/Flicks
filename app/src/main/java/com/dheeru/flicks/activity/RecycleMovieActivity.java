package com.dheeru.flicks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.dheeru.flicks.MovieActivity;
import com.dheeru.flicks.R;
import com.dheeru.flicks.adapters.MovieDynaItemAdapater;
import com.dheeru.flicks.events.EndlessScrollListener;
import com.dheeru.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dkthaku on 5/21/16.
 */
public class RecycleMovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieDynaItemAdapater movieArrayAdapter;
    ListView lvItems;
    String url = "http://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    int page =1;
    AsyncHttpClient client = new AsyncHttpClient();
    public static String TAG = RecycleMovieActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        toolbar.bringToFront();
       // lvItems = (ListView)findViewById(R.id.lvmovie);
        // Lookup the recyclerview in activity layout
        RecyclerView lvItems = (RecyclerView) findViewById(R.id.rmvItems);
        movies= new ArrayList<Movie>();
       // movies.add(new Movie("/rDT86hJCxnoOs4ARjrCiRej7pOi.jpg", "Captain America: Civil War", "Following the events of Age of Ultron, the collective governments of the world "));
        movies.addAll(movies);
        Log.d(this.getClass().getSimpleName(), "addAll:movies "+movies.size());
        movieArrayAdapter = new MovieDynaItemAdapater(movies);

       // movieArrayAdapter=new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvItems.setLayoutManager(linearLayoutManager);
        movieArrayAdapter.notifyDataSetChanged();
        movieArrayAdapter.notifyItemRangeInserted(0, movies.size()-1);
        // Intent actionviewIntent = new Intent(Intent.ACTION_VIEW);


        RequestHandle results = client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                {
                    //super.onSuccess(statusCode, headers, response);
                    JSONArray results = null;
                    try {
                        results = response.getJSONArray("results");
                        if (results != null) {
                            movies.addAll(Movie.getMovieList(results));
                            movieArrayAdapter.notifyDataSetChanged();
                            Log.d(TAG, "onSuccess: results  " + results.length());
                            Log.d(TAG, "onSuccess: movieArrayAdapter getItemCount " + movieArrayAdapter.getItemCount());
                        }
                        Log.d(TAG, "onSuccess: movies length ::: " + movies.size());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        lvItems.addOnScrollListener(new EndlessScrollListener(linearLayoutManager) {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Append more data into the adapter
    public void customLoadMoreDataFromApi(int offset) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        // Deserialize API response and then construct new objects to append to the adapter
        // Add the new objects to the data source for the adapter
      //  movies.addAll(moreItems);
        // For efficiency purposes, notify the adapter of only the elements that got changed
        // curSize will equal to the index of the first element inserted because the list is 0-indexed
        Log.d(TAG, "customLoadMoreDataFromApi: offset XXXXXXXXXXXXXXXXXXXXXXX "+offset);
        int curSize = movieArrayAdapter.getItemCount();
        Log.d(TAG, "customLoadMoreDataFromApi:  XXXXXXXXXXXXXXXXXXXXXXX curSize === "+curSize);
        Log.d(TAG, "customLoadMoreDataFromApi:  XXXXXXXXXXXXXXXXXXXXXXX movie size === "+movies.size());
        if(curSize==movies.size()) {
            String query =url.concat("&page=" + (++page));
            Log.d(TAG, "customLoadMoreDataFromApi: query XXXXXXXXXXXXXXXXXXXXXXX === "+query);
            RequestHandle results = client.get(query, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    {
                        //super.onSuccess(statusCode, headers, response);
                        JSONArray results = null;
                        try {
                            results = response.getJSONArray("results");
                            Log.d(TAG, "onSuccess: results  " + results);
                            if (results != null) {
                                movies.addAll(Movie.getMovieList(results));
                                movieArrayAdapter.notifyDataSetChanged();
                                Log.d(TAG, "onSuccess: results  " + results.length());
                                Log.d(TAG, "onSuccess: movieArrayAdapter getItemCount " + movieArrayAdapter.getItemCount());
                            }
                            Log.d(TAG, "onSuccess: movies length ::: " + movies.size());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
            movieArrayAdapter.notifyItemRangeInserted(curSize, movies.size() - 1);
        }

    }

    public  void sortByPapularity(){
        try {
            Intent intent = new Intent(this, MovieActivity.class);
            Log.d(TAG, "sortByPapularity: intent"+intent);
            // Collections.sort(movies, Movie.MovieSortByPopularity);
            // movieArrayAdapter.notifyDataSetChanged();
            startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //sortByReview
     public  void sortByReview(){
         Log.d(TAG, "sortByReview: ");
         Intent intent = new Intent(this, MovieRatingActivity.class);
         startActivity(intent);
       // Collections.sort(movies, Movie.MovieSortByPopularity);
       // movieArrayAdapter.notifyDataSetChanged();
    }
    public  void playingNow(){
        Log.d(TAG, "playingNow: ");
        Intent intent = new Intent(this, RecycleMovieActivity.class);
        startActivity(intent);
        // Collections.sort(movies, Movie.MovieSortByPopularity);
        // movieArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {
            case R.id.popularity:
                sortByPapularity();
               // Log.d(TAG, "onOptionsItemSelected: popularity movies(1)"+movies.get(1)); playingNow
              //  movieArrayAdapter.notifyItemRangeInserted(1, movies.size() - 1);
               // Log.d(TAG, "onOptionsItemSelected: popularity movies(1)"+movies.get(1));
                return true;
            case R.id.review:
                Log.d(TAG, "onOptionsItemSelected: popularity");
                sortByReview();
                //movieArrayAdapter.notifyItemRangeInserted(1, movies.size() - 1);
                return true;
            case R.id.playingNow:
                Log.d(TAG, "onOptionsItemSelected: playingNow");
                playingNow();

            default:
                Log.d(TAG, "onOptionsItemSelected: popularity");
                return super.onOptionsItemSelected(item);
        }
    }



}
