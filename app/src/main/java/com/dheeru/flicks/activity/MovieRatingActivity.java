package com.dheeru.flicks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.dheeru.flicks.MovieActivity;
import com.dheeru.flicks.R;
import com.dheeru.flicks.adapters.MovieArrayAdapter;
import com.dheeru.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieRatingActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieArrayAdapter;
    ListView lvItems;
    String query ="http://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static String TAG = MovieRatingActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_rating);
        Log.d(TAG, "onCreate: SSSSSSSSSSSSSSSSSSSSSSSSS ");
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //  getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        lvItems = (ListView)findViewById(R.id.lvmovie);
        movies= new ArrayList<Movie>();
        // movies.add(new Movie("AAAA", "sdse", "LLLL"));
        movieArrayAdapter=new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);
        movieArrayAdapter.notifyDataSetChanged();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getQuery(), new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                JSONArray results=null;
                try {
                    results= response.getJSONArray("results");
                    if(results!=null){
                        movies.addAll(Movie.getMovieList(results));
                        movieArrayAdapter.notifyDataSetChanged();
                        Log.d(TAG, "onSuccess: results  "+results.length());
                        Log.d(TAG, "onSuccess: movieArrayAdapter  "+movieArrayAdapter.getCount());
                    }
                    Log.d(TAG, "onSuccess: movies length ::: "+movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

        // Intent actionviewIntent = new Intent(Intent.ACTION_VIEW);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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


    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

}
