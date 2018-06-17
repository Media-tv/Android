package com.vacuum.app.plex.Utility;

import com.vacuum.app.plex.Model.API_KEY;
import com.vacuum.app.plex.Model.Actor;
import com.vacuum.app.plex.Model.ActorMovies;
import com.vacuum.app.plex.Model.Credits;
import com.vacuum.app.plex.Model.DoneMovies;
import com.vacuum.app.plex.Model.Images_tmdb;
import com.vacuum.app.plex.Model.MovieDetails;
import com.vacuum.app.plex.Model.MoviesResponse;
import com.vacuum.app.plex.Model.OpenloadResult;
import com.vacuum.app.plex.Model.OpenloadThumbnail;
import com.vacuum.app.plex.Model.SeasonDetails;
import com.vacuum.app.plex.Model.Slider;
import com.vacuum.app.plex.Model.TVDetails;
import com.vacuum.app.plex.Model.TrailerResponse;
import com.vacuum.app.plex.Model.Update;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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


    @GET("person/{id}")
    Call<Actor> getActorDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("person/{id}/movie_credits")
    Call<ActorMovies> getActorMovies(@Path("id") int id, @Query("api_key") String apiKey);
    @GET
    Call<List<Slider>> getSlider(@Url String url);

    @GET
    Call<OpenloadResult> getOpenload(@Url String url);

    @GET
    Call<OpenloadThumbnail> getOpenloadThumbnail(@Url String url);


    @FormUrlEncoded
    @POST("cimaclub/request.php")
    Call<ResponseBody> requestMovie(@Field("movie_id") String movie_id,
                                   @Field("title") String title);

    @FormUrlEncoded
    @POST("cimaclub/addmovie.php")
    Call<ResponseBody> addMovie(@Field("id") String movie_id,
                                    @Field("title") String title,
                                @Field("file_id") String file_id
    );

    @GET
    Call<Update> getUpdateVersion(@Url String url);

    @GET
    Call<API_KEY> getApiKEY(@Url String url);

    @GET
    Call<String> getMovie_openload_id(@Url String url);


    @GET
    Call<DoneMovies> getDoneMovies(@Url String url);

    @GET
    Call<String> getSend(@Url String url);

    @GET
    Call<OpenloadResult> uploadOpenload(@Url String url);

    @GET
    Call<ResponseBody> uploadOpenload_id(@Url String url);



}
