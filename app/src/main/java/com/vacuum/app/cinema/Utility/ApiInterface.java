package com.vacuum.app.cinema.Utility;

import com.vacuum.app.cinema.Model.Credits;
import com.vacuum.app.cinema.Model.Images_tmdb;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MovieDetails;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.Model.OpenloadResult;
import com.vacuum.app.cinema.Model.OpenloadThumbnail;
import com.vacuum.app.cinema.Model.SeasonDetails;
import com.vacuum.app.cinema.Model.Slider;
import com.vacuum.app.cinema.Model.TVDetails;
import com.vacuum.app.cinema.Model.TrailerResponse;
import com.vacuum.app.cinema.Model.allucResults;
import com.vacuum.app.cinema.Model.allucThumbnail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Home on 2/20/2018.
 */
public interface ApiInterface {
    @GET("movie/upcoming")
    Call<MoviesResponse> getupcomingMovies(@Query("api_key") String apiKey,@Query("page") int pageIndex);

    @GET("movie/popular")
    Call<MoviesResponse> getpopularMovies(@Query("api_key") String apiKey,@Query("page") int pageIndex);


    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey,@Query("page") int pageIndex);


    @GET("tv/top_rated")
    Call<MoviesResponse> getTopRatedTV(@Query("api_key") String apiKey,@Query("page") int pageIndex);

    @GET("tv/popular")
    Call<MoviesResponse> getpopularTV(@Query("api_key") String apiKey,@Query("page") int pageIndex);






    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("{type}/{id}/videos")
    Call<TrailerResponse> gettrailers(@Path("type") String type,@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<Credits> getMovieCredits(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/multi")
    Call<MoviesResponse> getMovieSearch(@Query("query") String query, @Query("api_key") String apiKey);


    @GET("tv/{id}")
    Call<TVDetails> getTVDetails(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("tv/{id}/credits")
    Call<Credits> getTVCredits(@Path("id") int id, @Query("api_key") String apiKey);
    @GET("tv/{id}/season/{number}")
    Call<SeasonDetails> getepisodes(@Path("id") int id, @Path("number") int number, @Query("api_key") String apiKey);
    @GET("{type}/{id}/images")
    Call<Images_tmdb> getImages(@Path("type") String type,@Path("id") int id, @Query("api_key") String apiKey);



    @GET
    Call<allucResults> profilePicture(@Url String url);

    @GET
    Call<allucThumbnail> profilePicture2(@Url String url);

    @GET
    Call<List<Slider>> getSlider(@Url String url);

    @GET
    Call<OpenloadResult> getOpenload(@Url String url);

    @GET
    Call<OpenloadThumbnail> getOpenloadThumbnail(@Url String url);
}
