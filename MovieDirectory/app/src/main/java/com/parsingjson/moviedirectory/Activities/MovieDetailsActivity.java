package com.parsingjson.moviedirectory.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.parsingjson.moviedirectory.Model.Movie;
import com.parsingjson.moviedirectory.R;
import com.parsingjson.moviedirectory.Util.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsActivity extends AppCompatActivity {
    private Movie movie;
    private TextView title;
    private TextView year;
    private TextView type;
    private TextView directors;
    private TextView plot;
    private TextView actors;
    private TextView writers;
    private TextView boxOffice;
    private ImageView poster;

    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movie = (Movie) getIntent().getSerializableExtra("movie");

        String imdbID = movie.getImdbId();

        setUpUI();
        getMovieDetails(imdbID);





    }

    private void getMovieDetails(String imdbID) {

        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Constants.imdbID_URL + imdbID,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String posterLink = null;
                        try {
                            posterLink = response.getString("Poster");
                            Picasso.with(getApplicationContext()).load(posterLink).into(poster);

                            title.setText(response.getString("Title"));
                            year.setText("Released: "+ response.getString("Year"));
                            type.setText("Type: "+ response.getString("Type"));
                            directors.setText("Directors: "+response.getString("Director"));
                            plot.setText("Plot: "+response.getString("Plot"));
                            actors.setText("Actors: "+response.getString("Actors"));
                            writers.setText("Writers: "+ response.getString("Writer"));
                            boxOffice.setText("Box Office: "+response.getString("BoxOffice"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    private void setUpUI() {
        title = (TextView) findViewById(R.id.dtlMovieTitleID);
        year = (TextView) findViewById(R.id.dtlMovieReleaseID);
        type = (TextView) findViewById(R.id.dtlMovieCategoryID);
        directors = (TextView) findViewById(R.id.dtlDirectorID);
        plot = (TextView) findViewById(R.id.dtlPlot);
        actors = (TextView) findViewById(R.id.dtlActorsID);
        writers= (TextView) findViewById(R.id.dtlWriters);
        boxOffice = (TextView) findViewById(R.id.dtlBoxOffice);
        poster = (ImageView) findViewById(R.id.dtlMovieImageID);
    }
}
