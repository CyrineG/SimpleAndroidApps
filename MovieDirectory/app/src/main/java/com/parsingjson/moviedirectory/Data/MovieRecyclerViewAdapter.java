package com.parsingjson.moviedirectory.Data;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.parsingjson.moviedirectory.Activities.MovieDetailsActivity;
import com.parsingjson.moviedirectory.Model.Movie;
import com.parsingjson.moviedirectory.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movieList;
    public MovieRecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movieList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        String posterLink = movie.getPoster();

        holder.movieTitle.setText(movie.getTitle());
        holder.movieType.setText(movie.getMovieType());
        holder.movieYear.setText(movie.getYear());

        Picasso.with(context)
                .load(posterLink)
                .placeholder(android.R.drawable.ic_btn_speak_now)
                .into(holder.moviePoster);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView moviePoster;
        TextView movieTitle;
        TextView movieYear;
        TextView movieType;
        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;

            movieTitle = (TextView) itemView.findViewById(R.id.movieTitleID);
            moviePoster = (ImageView) itemView.findViewById(R.id.movieImageID);
            movieYear = (TextView) itemView.findViewById(R.id.movieReleaseID);
            movieType = (TextView) itemView.findViewById(R.id.movieCategoryID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Movie movie = movieList.get(getAdapterPosition());
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra("movie", movie);
            context.startActivity(intent);
        }
    }
}
