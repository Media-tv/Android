package com.vacuum.app.cinema.Activities;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.VideoEnabledWebChromeClient;
import com.vacuum.app.cinema.Utility.VideoEnabledWebView;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class WatchActivity extends AppCompatActivity {


    Context mContext;
    String url;
    public static final String TAG_WATCH_FRAGMENT = "TAG_WATCH_FRAGMENT";

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    RelativeLayout Layout_videoplayer, Layout_webview;
    String OPENLOAD_API_Login, OPENLOAD_API_KEY, file_id, ticket, captcha_text, openload_thumbnail_url;
    JZVideoPlayerStandard jzVideoPlayerStandard;
    AlertDialog.Builder alertadd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.watch_activity);


        mContext = getApplicationContext();
        Bundle extras = getIntent().getExtras();
        String[] arrayInB = extras.getStringArray("url");

        Layout_videoplayer = findViewById(R.id.Layout_videoplayer);
        Layout_webview = findViewById(R.id.Layout_webview);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        jzVideoPlayerStandard.fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Full screen!", Toast.LENGTH_SHORT).show();
            }
        });
        jzVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Back!", Toast.LENGTH_SHORT).show();
                WatchActivity.this.finish();
            }
        });

        if (arrayInB.length == 0) {
            WebViewMethod();
            Layout_webview.setVisibility(View.VISIBLE);
        } else {
            PlayVideo(arrayInB[0],arrayInB[1],arrayInB[2]);
            Layout_videoplayer.setVisibility(View.VISIBLE);
        }
    }



    private void PlayVideo(String Stream,String title,String thumbnail) {
        jzVideoPlayerStandard.setUp(Stream
                , JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,title);
        Glide(thumbnail,jzVideoPlayerStandard.thumbImageView);
        jzVideoPlayerStandard.startButton.performClick();

    }

    private void Glide(String url, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(mContext)
                .load(url)
                .apply(options).into(imageView);
    }


    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
        JZVideoPlayer . releaseAllVideos();
    }

    private void WebViewMethod() {

        webView = (VideoEnabledWebView) findViewById(R.id.webView);
        View nonVideoLayout = findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) findViewById(R.id.videoLayout); // Your own view, read class comments
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen) {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                } else {
                    WindowManager.LayoutParams attrs = getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14) {
                        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl(url);

    }

}