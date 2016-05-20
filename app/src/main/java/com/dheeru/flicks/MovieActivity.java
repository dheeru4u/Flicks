package com.dheeru.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.dheeru.flicks.adapters.MovieArrayAdapter;
import com.dheeru.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieArrayAdapter;
    ListView lvItems;
    public static String TAG = MovieActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        lvItems = (ListView)findViewById(R.id.lvmovie);
        movies= new ArrayList<Movie>();
       // movies.add(new Movie("AAAA", "sdse", "LLLL"));
        movieArrayAdapter=new MovieArrayAdapter(this, movies);
        lvItems.setAdapter(movieArrayAdapter);
        movieArrayAdapter.notifyDataSetChanged();
         String url = "http://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
