package com.vacuum.app.cinema.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.halilibo.bettervideoplayer.BetterVideoCallback;
import com.halilibo.bettervideoplayer.BetterVideoPlayer;
import com.halilibo.bettervideoplayer.subtitle.CaptionsView;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.VideoEnabledWebChromeClient;
import com.vacuum.app.cinema.Utility.VideoEnabledWebView;
import android.media.MediaPlayer.TrackInfo;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jzvd.JZUtils;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;



public class WatchActivity extends AppCompatActivity implements BetterVideoCallback {


    private TextView txtDisplay;
    private static Handler handler = new Handler();

    Context mContext;
    String url,Stream,title,thumbnail;
    int position;

    BetterVideoPlayer player;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        setContentView(R.layout.watch_activity);


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Lato-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());

        mContext = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        String[] arrayInB = extras.getStringArray("url");


        //PlayVideo(arrayInB[0],arrayInB[1],arrayInB[2]);
        try{
            Stream =arrayInB[0] ;
            title = arrayInB[1];
            thumbnail = arrayInB[2];
        }catch (Exception e){
            Log.e("TAG","Hello world");
        }
        PlayVideo_bettervideoplayer();



    }

    private void PlayVideo_bettervideoplayer() {

        player =  findViewById(R.id.player);

        player.setAutoPlay(true);
        player.getToolbar().setTitle(title);
        player.setSource(Uri.parse(Stream));
        //player.setCaptions(Uri.parse("https://mohamedebrahim.000webhostapp.com/cimaclub/Subtitles/fightclub2.srt"), CaptionsView.CMime.SUBRIP);
        player.setHideControlsOnPlay(true);
        player.enableSwipeGestures(getWindow());
        player.setBottomProgressBarVisibility(true);
        player.setCallback(this);

        if (thumbnail != null) {
            Glide.with(mContext).load(thumbnail).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    player.setBackground(resource);

                }
            });
        }


        List<String> limits = new ArrayList<>();
        limits.add("En");
        limits.add("ES");
        limits.add("DE");
        limits.add("AR");

        for(int i = 0; i < limits.size(); i++) {
            player.getToolbar().getMenu().add(0,i,Menu.NONE,limits.get(i));
        }
        player.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case 0:
                        Toast.makeText(mContext, "EN"+player.getCurrentPosition(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Stream));
                        intent.setDataAndType(Uri.parse(Stream), "video/*");
                        startActivity(intent);
                        break;
                    case 1:
                        Toast.makeText(mContext, "ES", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(mContext, "DE", Toast.LENGTH_SHORT).show();
                        player.setCaptions(Uri.parse("https://mohamedebrahim.000webhostapp.com/cimaclub/Subtitles/Fight.Club.1999.BluRay.1080p.DTS.x264.dxva-EuReKA.ger.srt")
                                , CaptionsView.CMime.SUBRIP);
                        break;
                    case 3:
                        Toast.makeText(mContext, "AR", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //this.finish();
        if (player != null) {
            position = player.getCurrentPosition();
            player.pause();
            //player.stop();
            //player.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("savedInstanceState",player.getCurrentPosition());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int myInt = savedInstanceState.getInt("savedInstanceState");
        player.seekTo(myInt);

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



    @Override
    public void onStarted(BetterVideoPlayer player) {
        player.setBackgroundColor(Color.BLACK);
    }

    @Override
    public void onPaused(BetterVideoPlayer player) {
    }

    @Override
    public void onPreparing(BetterVideoPlayer player) {
    }

    @Override
    public void onPrepared(BetterVideoPlayer player) { }

    @Override
    public void onBuffering(int percent) {
        Log.e("TAG","onBuffering");
    }

    @Override
    public void onError(BetterVideoPlayer player, Exception e) {
    }

    @Override
    public void onCompletion(BetterVideoPlayer player) { }

    @Override
    public void onToggleControls(BetterVideoPlayer player, boolean isShowing) {

    }
}