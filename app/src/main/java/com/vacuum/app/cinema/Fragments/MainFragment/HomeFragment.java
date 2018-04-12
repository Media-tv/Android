package com.vacuum.app.cinema.Fragments.MainFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.vacuum.app.cinema.Activities.WatchActivity;
import com.vacuum.app.cinema.Adapter.MoviesAdapter;
import com.vacuum.app.cinema.Fragments.MoreFragment;
import com.vacuum.app.cinema.MainActivity;
import com.vacuum.app.cinema.Model.Movie;
import com.vacuum.app.cinema.Model.MoviesResponse;
import com.vacuum.app.cinema.Model.OpenloadResult;
import com.vacuum.app.cinema.Model.OpenloadResult2;
import com.vacuum.app.cinema.Model.OpenloadThumbnail;
import com.vacuum.app.cinema.Model.Slider;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.util.HashMap;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Home on 2/19/2018.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    private static String API_KEY;
    RecyclerView movies_recycler1_UpComing,movies_recycler2_popular,movies_recycler3_top_rated;
    private Context mContext;
    AlertDialog.Builder alertadd;
    SliderLayout mDemoSlider;
    public static  ProgressBar progressBar;
    public static  ApiInterface apiService;

    TextView more_upcoming,more_Popular,more_top_rated;
    public  static  LinearLayout layout;
    EditText captcha_edit_text;
    String OPENLOAD_API_Login,OPENLOAD_API_KEY,ticket,file_id,openload_thumbnail_url,title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mContext = this.getActivity();

        API_KEY = getResources().getString(R.string.TMBDB_API_KEY);
        movies_recycler1_UpComing=  view.findViewById(R.id.movies_recycler1_UpComing);
        movies_recycler2_popular=  view.findViewById(R.id.movies_recycler2_popular);
        movies_recycler3_top_rated=  view.findViewById(R.id.movies_recycler3_top_rated);

        progressBar =  view.findViewById(R.id.progressBar);
        layout =  view.findViewById(R.id.layout);


        more_upcoming= view.findViewById(R.id.more_upcoming);
        more_Popular= view.findViewById(R.id.more_Popular);
        more_top_rated= view.findViewById(R.id.more_top_rated);

        more_upcoming.setOnClickListener(this);
        more_Popular.setOnClickListener(this);
        more_top_rated.setOnClickListener(this);



        mDemoSlider = view.findViewById(R.id.slider);


        //layoutManager= new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        movies_recycler1_UpComing.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        movies_recycler2_popular.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        movies_recycler3_top_rated.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));




        retrofit();
        return view;
    }



    private void setupslider(final List<Slider> sliders) {

        final HashMap<String,String> url_maps = new HashMap<>();
        for(int i=0;i<sliders.size();i++){
            url_maps.put(sliders.get(i).getTitle(),sliders.get(i).getPosterPath());
        }



        for(final Slider s : sliders){
            TextSliderView textSliderView = new TextSliderView(mContext);
            // initialize a SliderLayout
            textSliderView
                    .description(s.getTitle())
                    .image(s.getPosterPath())
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            //add your extra information
            /*textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);*/

            textSliderView.image(s.getPosterPath())
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    //watchFragment(s);
                    title = s.getTitle();
                    file_id = s.getFile_id();
                    retrofit_1();
                }
            });
            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

    }




    private void retrofit(){

        apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);

        Call<MoviesResponse> call_UpComing = apiService.getupcomingMovies(API_KEY,1);
        call_UpComing.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler1_UpComing.setAdapter(new MoviesAdapter(movies, mContext));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });
        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call_popular = apiService.getpopularMovies(API_KEY,1);
        call_popular.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler2_popular.setAdapter(new MoviesAdapter(movies, mContext));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });
        //====================================================================================
        //====================================================================================
        //====================================================================================
        Call<MoviesResponse> call_top_rated = apiService.getTopRatedMovies(API_KEY,1);
        call_top_rated.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                movies_recycler3_top_rated.setAdapter(new MoviesAdapter(movies, mContext));
            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });

        //====================================================================================
        //====================================================================================
        //====================================================================================

        Call<List<Slider>> call_slider = apiService.getSlider("https://mohamedebrahim.000webhostapp.com/cimaclub/slider.php");
        call_slider.enqueue(new Callback<List<Slider>>() {
            @Override
            public void onResponse(Call<List<Slider>>call, Response<List<Slider>> response) {
                try {
                    List<Slider> slider = response.body();
                    Log.i("slider", slider.get(0).getTitle());
                    setupslider(slider);
                    progressBar.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                }catch (Exception e ){
                    Log.e("Exception::",e.toString());
                }

            }

            @Override
            public void onFailure(Call<List<Slider>>call, Throwable t) {
                // Log error here since request failed
                Log.e("slider", t.toString());
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_upcoming:
                moreFragment("more_upcoming");
                break;
            case R.id.more_top_rated:
                moreFragment("more_top_rated");
                break;
            case R.id.more_Popular:
                moreFragment("more_Popular");
                break;
        }
    }

    private void moreFragment(String value) {
        Fragment fragment = new MoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("value", value);
        fragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, MoreFragment.TAG_MORE_FRAGMENT);
        fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
        fragmentTransaction.commit();
    }



    private void watchActivity(String url ) {
        String intArray[] = {url,title,openload_thumbnail_url};
        Intent inent = new Intent(mContext, WatchActivity.class);
        inent.putExtra("url", intArray);
        mContext.startActivity(inent);
    }

    private void retrofit_1() {

        OPENLOAD_API_Login = getResources().getString(R.string.OPENLOAD_API_Login);
        OPENLOAD_API_KEY = getResources().getString(R.string.OPENLOAD_API_KEY);
        String full_url = "https://api.openload.co/1/file/dlticket?file=" + file_id + "&login=" + OPENLOAD_API_Login + "&key=" + OPENLOAD_API_KEY;

        Log.e("TAG","retrofit_1");
        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);
        Call<OpenloadResult> call_slider = apiService.getOpenload(full_url);
        call_slider.enqueue(new Callback<OpenloadResult>() {
            @Override
            public void onResponse(Call<OpenloadResult> call, Response<OpenloadResult> response) {
                OpenloadResult open = response.body();
                ticket = open.getOpenload().getTicket();
                Boolean x = open.getOpenload().getcaptchaUrlboolen();
                Log.e("TAG: Boolen ==  ", x.toString());

                String url = "https://api.openload.co/1/file/dl?file=" + file_id + "&ticket=" + ticket;
                retrofit_2(url);
            }

            @Override
            public void onFailure(Call<OpenloadResult> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG: error ", t.toString());

            }
        });

        //=================================================
        String thumbnail_url = "https://api.openload.co/1/file/getsplash?login=" + OPENLOAD_API_Login + "&key=" + OPENLOAD_API_KEY + "&file=" + file_id;
        Call<OpenloadThumbnail> call_openload_thumbnail = apiService.getOpenloadThumbnail(thumbnail_url);
        call_openload_thumbnail.enqueue(new Callback<OpenloadThumbnail>() {
            @Override
            public void onResponse(Call<OpenloadThumbnail> call, Response<OpenloadThumbnail> response) {
                OpenloadThumbnail open = response.body();
                openload_thumbnail_url = open.getResult();
            }
            @Override
            public void onFailure(Call<OpenloadThumbnail> call, Throwable t) {
                // Log error here since request failed
                Log.e("openloadResult", t.toString());
            }
        });
    }
    private void AlertDialog(String url) {
        alertadd = new AlertDialog.Builder(this.getContext());
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View view2 = factory.inflate(R.layout.alertdialog, null);
        ImageView captcha = view2.findViewById(R.id.captcha);
        captcha_edit_text= view2.findViewById(R.id.captcha_edit_text);
        captcha_edit_text.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(mContext.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        Glide.with(mContext)
                .load(url).into(captcha);
        alertadd.setView(view2)
                .setCancelable(true);
        alertadd.setNeutralButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                String captcha_text = captcha_edit_text.getText().toString();
                String full_url = "https://api.openload.co/1/file/dl?file=" + file_id + "&ticket=" + ticket + "&captcha_response=" + captcha_text;
                retrofit_2(full_url);
            }
        });
        alertadd.show();
    }

    private void retrofit_2(String full_url) {


        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);
        Call<OpenloadResult> call_slider = apiService.getOpenload(full_url);
        call_slider.enqueue(new Callback<OpenloadResult>() {
            @Override
            public void onResponse(Call<OpenloadResult> call, Response<OpenloadResult> response) {
                OpenloadResult open = response.body();
                if (open.getOpenload() != null) {
                    //Log.i("openload :: url = ", open.getOpenload().getUrl());
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    watchActivity(open.getOpenload().getUrl());

                } else {
                    retrofit_3();
                    //Log.e("TAG","open load = NULL");
                }
            }
            @Override
            public void onFailure(Call<OpenloadResult> call, Throwable t) {
                Log.e("openloadResult", t.toString());
            }
        });

    }

    private void retrofit_3() {
        String full_url = "https://api.openload.co/1/file/dlticket?file=" + file_id + "&login=" + OPENLOAD_API_Login + "&key=" + OPENLOAD_API_KEY;

        //Log.e("TAG","retrofit_3");
        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);
        Call<OpenloadResult2> call_slider = apiService.getOpenload2(full_url);
        call_slider.enqueue(new Callback<OpenloadResult2>() {
            @Override
            public void onResponse(Call<OpenloadResult2> call, Response<OpenloadResult2> response) {
                OpenloadResult2 open = response.body();
                ticket = open.getOpenload().getTicket();
                AlertDialog(open.getOpenload().getCaptchaUrl());
                //Log.i("TAG: ", open.getOpenload().getCaptchaUrl());
                String url = "https://api.openload.co/1/file/dl?file=" + file_id + "&ticket=" + ticket + "&captcha_response="+open.getOpenload().getCaptchaUrl();
            }

            @Override
            public void onFailure(Call<OpenloadResult2> call, Throwable t) {
                // Log error here since request failed
                Log.e("TAG: error ", t.toString());

            }
        });
    }


}
