package com.dheeru.flicks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;

import com.dheeru.flicks.adapters.MovieRefreshAdapter;
import com.dheeru.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by dkthaku on 5/19/16.
 */
public class MovieSwipeRefreshLayout extends Activity {
    private SwipeRefreshLayout swipeContainer;
    ArrayList<Movie> movies;
    MovieRefreshAdapter adapter;
    ListView lvItems;
    public static String TAG = MovieSwipeRefreshLayout.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Only ever call `setContentView` once right at the top
        setContentView(R.layout.swiperefreshlayout);
        movies= new ArrayList<Movie>();
        adapter=new MovieRefreshAdapter(this, movies);

        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        adapter.notifyDataSetChanged();
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }

        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        String url = "http://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();


        client.get(url, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                // Remember to CLEAR OUT old items before appending in the new ones
                adapter.clear();
                // ...the data has come back, add new items to your adapter...
                JSONArray results=null;
                try {
                    results= response.getJSONArray("results");
                    if(results!=null){
                        movies.addAll(Movie.getMovieList(results));
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "onSuccess: results  "+results.length());
                        Log.d(TAG, "onSuccess: movieArrayAdapter  "+adapter.getCount());
                    }
                    Log.d(TAG, "onSuccess: movies length ::: "+movies);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter.addAll(movies);
                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            public void onFailure(Throwable e) {
                Log.d("DEBUG", "Fetch timeline error: " + e.toString());
            }
        });
    }

}
