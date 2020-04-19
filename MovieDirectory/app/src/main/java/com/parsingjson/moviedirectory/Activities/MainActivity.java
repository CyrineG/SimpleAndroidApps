package com.parsingjson.moviedirectory.Activities;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.parsingjson.moviedirectory.Data.MovieRecyclerViewAdapter;
import com.parsingjson.moviedirectory.Model.Movie;
import com.parsingjson.moviedirectory.R;
import com.parsingjson.moviedirectory.Util.Constants;
import com.parsingjson.moviedirectory.Util.Prefs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieRecyclerViewAdapter adapter;
    private List<Movie> movieList;
    private RequestQueue requestQueue;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        requestQueue = Volley.newRequestQueue(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewID);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieList = new ArrayList<>();

        Prefs prefs = new Prefs(MainActivity.this);
        String search = prefs.getSearch();
        adapter = new MovieRecyclerViewAdapter(this, getMovie(search));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.new_search) {
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    public void showInputDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_view, null);
        final EditText newSearchEdit = (EditText) view.findViewById(R.id.searchEdit);
        Button btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs prefs = new Prefs(MainActivity.this);
                if (!newSearchEdit.getText().toString().isEmpty()){
                    String search = newSearchEdit.getText().toString();
                    prefs.setSearch(search);

                    getMovie(search);
                }
                dialog.dismiss();
            }
        });
    }


    //get movies
    public List<Movie> getMovie(String searchTerm){
        movieList.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.URL_LEFT + searchTerm + Constants.URL_RIGHT,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Search");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject movieObj = jsonArray.getJSONObject(i);
                                Movie movie = new Movie();
                                movie.setTitle(movieObj.getString("Title"));
                                movie.setPoster(movieObj.getString("Poster"));
                                movie.setYear("Released: "+ movieObj.getString("Year"));
                                movie.setMovieType("Type: "+ movieObj.getString("Type"));
                                movie.setImdbId(movieObj.getString("imdbID"));

                                //Log.d("Movie name : ", movie.getTitle());

                                movieList.add(movie);
                            }

                        adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);

        return movieList;
    }

}
