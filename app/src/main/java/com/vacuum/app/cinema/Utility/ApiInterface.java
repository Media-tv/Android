package com.vacuum.app.cinema.Utility;

import com.vacuum.app.cinema.Model.Credits;
import com.vacuum.app.cinema.Model.MovieDetails;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.Model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Home on 2/20/2018.
 */
public interface ApiInterface {
    @GET("movie/upcoming")
    Call<MoviesResponse> getupcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<MoviesResponse> getpopularMovies(@Query("api_key") String apiKey);


    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);


    @GET("tv/top_rated")
    Call<MoviesResponse> getTopRatedTV(@Query("api_key") String apiKey);

    @GET("tv/popular")
    Call<MoviesResponse> getpopularTV(@Query("api_key") String apiKey);



    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<TrailerResponse> getMovietrailers(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/Credits")
    Call<Credits> getMovieCredits(@Path("id") int id, @Query("api_key") String apiKey);

}
