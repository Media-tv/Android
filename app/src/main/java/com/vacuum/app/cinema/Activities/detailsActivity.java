package com.vacuum.app.cinema.Activities;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.bluzwong.swipeback.SwipeBackActivityHelper;
import com.vacuum.app.cinema.Adapter.CreditsAdapter;
import com.vacuum.app.cinema.Adapter.CrewAdapter;
import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.Adapter.TrailerAdapter;
import com.vacuum.app.cinema.Fragments.DetailsFragment;
import com.vacuum.app.cinema.Model.Cast;
import com.vacuum.app.cinema.Model.Credits;
import com.vacuum.app.cinema.Model.Crew;
import com.vacuum.app.cinema.Model.Genre;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MovieDetails;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.Model.Trailer;
import com.vacuum.app.cinema.Model.TrailerResponse;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 2/22/2018.
 */



public class detailsActivity  extends AppCompatActivity {


    public  static  SwipeBackActivityHelper helper = new SwipeBackActivityHelper();
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity_layout);
        mContext = this.getApplicationContext();
        helper.setEdgeMode(false)
                .setParallaxMode(true)
                .setParallaxRatio(3)
                .setNeedBackgroundShadow(true)
                .init(this);

        DetailsFragment detailsFragment = new DetailsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame_detailsFragment, detailsFragment, "detailsFragment");
        fragmentTransaction.commitAllowingStateLoss();
    }






    @Override
    public void onBackPressed() {
        helper.finish();
    }

}