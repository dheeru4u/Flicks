package com.dheeru.flicks.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by dkthaku on 5/18/16.
 */
public class Movie {
    String posterPath;
    String originalTitle;
    String overView;
    long popularity=0;
    long vote_average=0;
    boolean video;
    Date release_date;

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

    public long getPopularity() {
        return popularity;
    }

    public long getVote_average() {
        return vote_average;
    }

    public boolean isVideo() {
        return video;
    }

    public Date getRelease_date() {
        return release_date;
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
        this.popularity= jsonObject.getLong("popularity");
        this.vote_average= jsonObject.getLong("vote_average");
        this.video=jsonObject.getBoolean("video");
       // this.release_date=jsonObject.getString();

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


    public static Comparator<Movie> MovieSortByPopularity = new Comparator<Movie>(){

    public int compare(Movie papularity1, Movie papularity2) {

        long temp1 = papularity1.getPopularity();
        long temp2 = papularity2.getPopularity();
        long diff;
        diff = (temp2-temp1);
        Log.d(TAG, "compare:MovieSortByVote diff"+diff);
        int result = ((Long) diff).intValue();
        // temp2-temp1;

	   /*For ascending order*/
        return result;

	   /*For descending order*/
        //rollno2-rollno1;
    }};

    public static Comparator<Movie> MovieSortByVote = new Comparator<Movie>(){

        public int compare(Movie papularity1, Movie papularity2) {

            long temp1 = papularity1.getVote_average();
            long temp2 = papularity2.getVote_average();
            long diff;
            diff = (temp2-temp1);
            Log.d(TAG, "compare:MovieSortByVote diff"+diff);
            int result = ((Long) diff).intValue();
            // temp2-temp1;

	   /*For ascending order*/
            return result;

	   /*For descending order*/
            //rollno2-rollno1;
        }};

    @Override
    public String toString() {
        return "Movie{" +
                "posterPath='" + posterPath + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overView='" + overView + '\'' +
                ", popularity=" + popularity +
                ", vote_average=" + vote_average +
                ", video=" + video +
                ", release_date=" + release_date +
                '}';
    }
}
