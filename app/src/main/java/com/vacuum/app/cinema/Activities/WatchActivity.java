package com.vacuum.app.cinema.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class WatchActivity extends AppCompatActivity implements MediaPlayer.OnTimedTextListener  {


    private TextView txtDisplay;
    private static Handler handler = new Handler();

    Context mContext;
    String url,Stream;

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    RelativeLayout Layout_videoplayer, Layout_webview;
    String OPENLOAD_API_Login, OPENLOAD_API_KEY, file_id, ticket, captcha_text, openload_thumbnail_url;
    JZVideoPlayerStandard jzVideoPlayerStandard;
    AlertDialog.Builder alertadd;
    String SRT  = "https://dozeu380nojz8.cloudfront.net/uploads/video/subtitle_file/3533/srt_919a069ace_boss.srt";





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

        Layout_videoplayer = findViewById(R.id.Layout_videoplayer);
        Layout_webview = findViewById(R.id.Layout_webview);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        JZVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        JZVideoPlayer.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;



        if (arrayInB.length == 0) {
            WebViewMethod();
            Layout_webview.setVisibility(View.VISIBLE);
        } else {
            PlayVideo(arrayInB[0],arrayInB[1],arrayInB[2]);
            Layout_videoplayer.setVisibility(View.VISIBLE);
        }
    }





    private void PlayVideo(String Stream,String title,String thumbnail) {
        this.Stream = Stream;
        jzVideoPlayerStandard.fullscreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(mContext, String.valueOf(jzVideoPlayerStandard.getCurrentPositionWhenPlaying())+" : "+
                        String.valueOf(jzVideoPlayerStandard.getDuration()), Toast.LENGTH_SHORT).show();*/
                time(jzVideoPlayerStandard.getCurrentPositionWhenPlaying());
                time(jzVideoPlayerStandard.getDuration());


            }
        });
        jzVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Back!", Toast.LENGTH_SHORT).show();
                WatchActivity.this.finish();
            }
        });


        jzVideoPlayerStandard.setUp(Stream
                , JZVideoPlayerStandard.SCREEN_WINDOW_FULLSCREEN,title);
        Glide(thumbnail,jzVideoPlayerStandard.thumbImageView);
        jzVideoPlayerStandard.startButton.performClick();

        Subtitle_setup();
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

    private void time(long millis) {
        @SuppressLint("DefaultLocale")
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        Toast.makeText(mContext, hms, Toast.LENGTH_SHORT).show();

    }
    private void Subtitle_setup() {



        txtDisplay = (TextView) findViewById(R.id.txtDisplay);
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.video);
        //mediaPlayer = MediaPlayer.create(this, Uri.parse(Stream));

        try {
            mediaPlayer.addTimedTextSource(getSubtitleFile(R.raw.fightclub2),
                    MediaPlayer.MEDIA_MIMETYPE_TEXT_SUBRIP);
            int textTrackIndex = findTrackIndexFor(
                    MediaPlayer.TrackInfo.MEDIA_TRACK_TYPE_TIMEDTEXT, mediaPlayer.getTrackInfo() );
            if (textTrackIndex >= 0) {
                mediaPlayer.selectTrack(textTrackIndex);
            } else {
                Log.w("TAG", "Cannot find text track!");
            }
            mediaPlayer.setOnTimedTextListener(this);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private int findTrackIndexFor(int mediaTrackType, TrackInfo[] trackInfo) {
        int index = -1;
        for (int i = 0; i < trackInfo.length; i++) {
            if (trackInfo[i].getTrackType() == mediaTrackType) {
                Log.w("TAG lenght :", String.valueOf(trackInfo.length));
                return i;
            }
        }
        return index;
    }

    private String getSubtitleFile(int resId) {
        String fileName = getResources().getResourceEntryName(resId);
        File subtitleFile = getFileStreamPath(fileName);
        if (subtitleFile.exists()) {
            Log.d("TAG", "Subtitle already exists");
            return subtitleFile.getAbsolutePath();
        }
        Log.d("TAG", "Subtitle does not exists, copy it from res/raw");

        // Copy the file from the res/raw folder to your app folder on the
        // device
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = getResources().openRawResource(resId);
            outputStream = new FileOutputStream(subtitleFile, false);
            copyFile(inputStream, outputStream);
            return subtitleFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStreams(inputStream, outputStream);
        }
        return "";
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream)
            throws IOException {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int length = -1;
        while ((length = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, length);
        }
    }

    // A handy method I use to close all the streams
    private void closeStreams(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable stream : closeables) {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void onTimedText(final MediaPlayer mp, final TimedText text) {
        if (text != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    int seconds = mp.getCurrentPosition() / 1000;
                    txtDisplay.setText(text.getText());
                }
            });
        }
    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }



}