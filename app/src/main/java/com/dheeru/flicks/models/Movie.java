package com.dheeru.flicks.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by dkthaku on 5/18/16.
 */
public class Movie {
    String posterPath;
    String originalTitle;
    String overView;
    public static String TAG = Movie.class.getSimpleName();
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", this.posterPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    public Movie(String posterPath, String originalTitle, String overView) {
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overView = overView;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overView = jsonObject.getString("overview");
    }




    public static ArrayList<Movie> getMovieList(JSONArray jsonMovieArray){
                ArrayList<Movie> movieArrayList = new ArrayList<Movie>();
        try {
            if (jsonMovieArray != null) {
                Log.d(TAG, "getMovieList: jsonMovieArray.length =" + jsonMovieArray.length());
                for (int i = 0; i < jsonMovieArray.length(); i++) {
                    JSONObject jsonObj=jsonMovieArray.getJSONObject(i);
                 //   Log.d(TAG, "getMovieList: jsonObj =" + jsonObj);
                    if(jsonObj!=null) {
                      //  Log.d(TAG, "getMovieList: jsonObj =" + jsonObj.toString());
                        Movie movieObj=new Movie(jsonObj);
                      //  Log.d(TAG, "getMovieList: movieObj =" + jsonObj);
                        movieArrayList.add(movieObj);
                    }
                }
            }
            Log.d(TAG, "getMovieList: movieArrayList.lenght =" + movieArrayList.size());

        }catch (JSONException e){
            e.printStackTrace();
        }

        return movieArrayList;
    }
}
