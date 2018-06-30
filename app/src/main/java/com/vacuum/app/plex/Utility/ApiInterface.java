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
import com.vacuum.app.plex.Model.User;

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
    @POST("plex/request.php")
    Call<ResponseBody> requestMovie(@Field("movie_id") String movie_id,
                                   @Field("title") String title);

    @FormUrlEncoded
    @POST("plex/addmovie.php")
    Call<ResponseBody> addMovie(@Field("id") String movie_id,
                                    @Field("title") String title,
                                @Field("file_id") String file_id,

                                @Field("id_tvseries_tmdb") int id_tvseries_tmdb,
                                @Field("name_tv_series") String name_tv_series,
                                @Field("season_number") int season_number,
                                @Field("episode_name") String episode_name,
                                @Field("episode_number") int  episode_number,
                                @Field("episode_id_tmdb") int episode_id_tmdb
    );

    @FormUrlEncoded
    @POST("plex/registration.php")
    Call<User> registration(
                                @Field("full_name") String full_name,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("phone") String phone,
                                @Field("points") int points,
                                @Field("age") String age,
                                @Field("location") String loation,
                                @Field("address") String  address
    );


    @FormUrlEncoded
    @POST("plex/login.php")
    Call<User> loging_user(
            @Field("email") String email,
            @Field("password") String password
    );



    @GET
    Call<Update> getUpdateVersion(@Url String url);

    @GET
    Call<API_KEY> getApiKEY(@Url String url);

    @GET("plex/getfileid.php")
    Call<String> getMovie_openload_id(@Query("id") int id);


    @GET("plex/getDoneMovies.php")
    Call<DoneMovies> getDoneMovies();


    @GET
    Call<ResponseBody> profilePicture(@Url String url);

    @GET
    Call<OpenloadResult> uploadOpenload(@Url String url);

    @GET
    Call<ResponseBody> uploadOpenload_id(@Url String url);



}
