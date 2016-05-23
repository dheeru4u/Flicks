package com.dheeru.flicks;

import android.util.Log;

import com.dheeru.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * Created by dkthaku on 5/18/16.
 */
public class Utility {
   public static String url = "http://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";


    public static ArrayList<Movie> getMovies () {
        final ArrayList<Movie> movieArrayList=new ArrayList<Movie>();
        AsyncHttpClient client = new AsyncHttpClient();
        JSONArray movieJsn = null;
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               // super.onSuccess(statusCode, headers, response);
                JSONArray results = null;
                try {
                    results= response.getJSONArray("results");

                    if(results!=null){
                        if (results != null) {
                            Log.d("Utility", "getMovie: jsonMovieArray.length =" + results.length());
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject jsonObj=results.getJSONObject(i);
                                //   Log.d(TAG, "getMovieList: jsonObj =" + jsonObj);
                                if(jsonObj!=null) {
                                    //  Log.d(TAG, "getMovieList: jsonObj =" + jsonObj.toString());
                                    Movie movieObj=new Movie(jsonObj);
                                    //  Log.d(TAG, "getMovieList: movieObj =" + jsonObj);
                                    movieArrayList.add(movieObj);
                                }
                            }
                        }
                        Log.d("Utility", "getMovie: movieArrayList.lenght =" + movieArrayList.size());
                    }
                  Log.d("Movie Json obj", "onSuccess: movies length ::: "+movieArrayList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        Log.d(" Utility Movie Json obj", "onSuccess: movies return %%%%%% ::: "+movieArrayList);
        return movieArrayList;
    }


}
