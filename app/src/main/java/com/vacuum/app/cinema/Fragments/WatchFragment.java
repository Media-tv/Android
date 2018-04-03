package com.vacuum.app.cinema.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.jzvd.JZVideoPlayerStandard;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vacuum.app.cinema.Model.OpenloadResult;
import com.vacuum.app.cinema.Model.OpenloadThumbnail;
import com.vacuum.app.cinema.Model.Slider;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;
import com.vacuum.app.cinema.Utility.VideoEnabledWebChromeClient;
import com.vacuum.app.cinema.Utility.VideoEnabledWebView;

import java.util.List;


/**
 * Created by Home on 3/8/2018.
 */

public class WatchFragment extends Fragment {

    Context mContext;
    String url;
    public static final String TAG_WATCH_FRAGMENT = "TAG_WATCH_FRAGMENT";

    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;
    RelativeLayout Layout_videoplayer,Layout_webview;
    Slider slider;
    String OPENLOAD_API_Login,OPENLOAD_API_KEY,file_id,ticket,captcha_text,openload_thumbnail_url;
    View view;
    JZVideoPlayerStandard jzVideoPlayerStandard;


    String videoPath = "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4";
    String videoPath3 = "https://s27.vidbom.com/6jmm5l6isuazsalriuzqdjfqbky53h4tpigjl6ivsitzctq77cwueyvjxglq/v.mp4";
    String videoPath5 = "https://s28.vidbom.com/6jmmcjr7sqazsalriuwqd45wbjcnnvd5d5ydm6kpaoh4wgdfm7k5iny2j7hq/v.mp4";
    String videoPath6 = "http://212.47.226.206/videos/fjpe9ypxi3b7dfe9u4u4gt567a.mp4";
    String m3u8 = "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
    String videoPath7 = "https://vd63.mycdn.me/?expires=1521555557693&srcIp=156.216.204.81&srcAg=CHROME&type=1&sig=f01ce89500c05cc2c10a4d2a2e3da25120f621e1&ct=4&urls=5.61.21.112&clientType=0&id=363537762879&bytes=0-8540";
    String raw = "home.MP4";
    String openload = "https://1fgqfv7.oloadcdn.net/dl/l/DtNztu8dxWPs1dd8/Gsm_Sc7uLQY/VfE_html5.mp4?mime=true";
    String openload2 = "https://oql1j8.oloadcdn.net/dl/l/hvV1BIB3-abCvS6E/US-rfOsPjLY/%D8%A7%D8%B1%D8%B7%D8%BA%D8%B1%D9%84+%D8%A7%D9%84%D8%AD%D9%84%D9%82%D8%A9+111+%D9%85%D8%AA%D8%B1%D8%AC%D9%85+541%D9%85%D8%A8.MP4.mp4?mime=true";
    String okRu = "https://1fjiw5h.oloadcdn.net/dl/l/g2pRH9OANyvlQ__g/NqbDSkWc634/%5BCimaClub.Com%5D-La.casa.de.papel.Episode.13.mkv.mp4?mime=true";
    String okRu2 = "https://pgli8p.oloadcdn.net/dl/l/dNGo-_s8qKDdqMCg/9d2GZimdDjA/Ferdinand.2017.720p.BluRay.Cima4Up.tv.mp4?mime=true";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.watch_fragment_layout, container, false);
        mContext = this.getActivity();
        slider = (Slider)getArguments().getSerializable("url");
        Layout_videoplayer = view.findViewById(R.id.Layout_videoplayer);
        Layout_webview = view.findViewById(R.id.Layout_webview);
        jzVideoPlayerStandard= (JZVideoPlayerStandard) view.findViewById(R.id.videoplayer);



        if(slider.getFile_id().isEmpty()){
            WebViewMethod(view);
            Layout_webview.setVisibility(View.VISIBLE);
        }else {
            retrofit();
            Layout_videoplayer.setVisibility(View.VISIBLE);
        }
        onbackpressed(view);
        return view;
    }








    private void retrofit() {

        OPENLOAD_API_Login = getResources().getString(R.string.OPENLOAD_API_Login);
        OPENLOAD_API_KEY = getResources().getString(R.string.OPENLOAD_API_KEY);
        file_id = slider.getFile_id();
        String full_url = "https://api.openload.co/1/file/dlticket?file="+file_id+"&login="+OPENLOAD_API_Login+"&key="+OPENLOAD_API_KEY;

        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);
        Call<OpenloadResult> call_slider = apiService.getOpenload(full_url);
        call_slider.enqueue(new Callback<OpenloadResult>() {
            @Override
            public void onResponse(Call<OpenloadResult>call, Response<OpenloadResult> response) {
                OpenloadResult open = response.body();
                AlertDialog(open.getOpenload().getCaptchaUrl());
                ticket = open.getOpenload().getTicket();
                Log.i("openloadgetCaptchaUrl: ", open.getOpenload().getCaptchaUrl());
            }

            @Override
            public void onFailure(Call<OpenloadResult>call, Throwable t) {
                // Log error here since request failed
                Log.e("openloadResult", t.toString());
            }
        });

        //=================================================
        String thumbnail_url = "https://api.openload.co/1/file/getsplash?login="+OPENLOAD_API_Login+"&key="+OPENLOAD_API_KEY+"&file="+file_id;
        Call<OpenloadThumbnail> call_openload_thumbnail = apiService.getOpenloadThumbnail(thumbnail_url);
        call_openload_thumbnail.enqueue(new Callback<OpenloadThumbnail>() {
            @Override
            public void onResponse(Call<OpenloadThumbnail>call, Response<OpenloadThumbnail> response) {
                OpenloadThumbnail open = response.body();
                openload_thumbnail_url =  open.getResult();
                Glide(openload_thumbnail_url,jzVideoPlayerStandard.thumbImageView);
            }

            @Override
            public void onFailure(Call<OpenloadThumbnail>call, Throwable t) {
                // Log error here since request failed
                Log.e("openloadResult", t.toString());
            }
        });
    }

    private void AlertDialog(String url) {
        AlertDialog.Builder alertadd = new AlertDialog.Builder(mContext);
        LayoutInflater factory = LayoutInflater.from(mContext);
        final View view2 = factory.inflate(R.layout.alertdialog, null);
        ImageView captcha  = view2.findViewById(R.id.captcha);
        final EditText captcha_edit_text  = view2.findViewById(R.id.captcha_edit_text);
        Glide(url,captcha);
        alertadd.setView(view2);
        alertadd.setNeutralButton( "Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {
                captcha_text =  captcha_edit_text.getText().toString();
                retrofit2();
            }
        });

        alertadd.show();
    }

    private void retrofit2() {

        String full_url = "https://api.openload.co/1/file/dl?file="+file_id+"&ticket="+ticket+"&captcha_response="+captcha_text;

        ApiInterface apiService =
                ApiClient.getClient(mContext).create(ApiInterface.class);
        Call<OpenloadResult> call_slider = apiService.getOpenload(full_url);
        call_slider.enqueue(new Callback<OpenloadResult>() {
            @Override
            public void onResponse(Call<OpenloadResult>call, Response<OpenloadResult> response) {
                OpenloadResult open = response.body();

                if (open.getOpenload() != null)
                {
                    Log.i("openload :: url = ", open.getOpenload().getUrl());
                    PlayVideo(open.getOpenload().getUrl());
                }else {
                    retrofit();
                }

            }

            @Override
            public void onFailure(Call<OpenloadResult>call, Throwable t) {
                Log.e("openloadResult", t.toString());
            }
        });

    }

    private void WebViewMethod(View view) {

        webView = (VideoEnabledWebView) view.findViewById(R.id.webView);
        View nonVideoLayout = view.findViewById(R.id.nonVideoLayout); // Your own view, read class comments
        ViewGroup videoLayout = (ViewGroup) view.findViewById(R.id.videoLayout); // Your own view, read class comments
        webChromeClient = new VideoEnabledWebChromeClient(nonVideoLayout, videoLayout, view, webView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress)
            {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback()
        {
            @Override
            public void toggledFullscreen(boolean fullscreen)
            {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                if (fullscreen)
                {
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    }
                }
                else
                {
                    WindowManager.LayoutParams attrs = getActivity().getWindow().getAttributes();
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    getActivity().getWindow().setAttributes(attrs);
                    if (android.os.Build.VERSION.SDK_INT >= 14)
                    {
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }

            }
        });
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl(url);

    }


    private void PlayVideo(String Stream)
    {

        jzVideoPlayerStandard.setUp(Stream
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, slider.getTitle());

        /*VideoView videoView =  view.findViewById(R.id.videoplayer);
        videoView.setVideoPath(slider.getStreamlink()).getPlayer().start();*/

    }

    private void Glide(String url,ImageView imageView){
        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide.with(mContext)
                .load(url)
                .apply(options).into(imageView);
    }


    private void onbackpressed(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                    return true;
                }
                return false;
            }
        } );
    }


}
