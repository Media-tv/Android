package com.vacuum.app.cinema.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.VideoEnabledWebChromeClient;
import com.vacuum.app.cinema.Utility.VideoEnabledWebView;

/**
 * Created by Home on 3/8/2018.
 */

public class WatchFragment extends Fragment {

    Context mContext;
    String url;
    WebView mWebView;
    private VideoEnabledWebView webView;
    private VideoEnabledWebChromeClient webChromeClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.watch_fragment_layout, container, false);
        mContext = this.getActivity();
        Bundle bundle = this.getArguments();
        url = bundle.getString("url");




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

        onbackpressed(view);
        return view;
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
