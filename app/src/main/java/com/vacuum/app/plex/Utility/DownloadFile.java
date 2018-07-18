package com.vacuum.app.plex.Utility;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class DownloadFile {
    private static final String TAG = "TAG";
    Context mContext;
    String url,title;
    public DownloadFile(Context mContext, String url,String title) {
        this.mContext = mContext;
        this.url = url;
        this.title = title;
        downloading();
    }
    private void downloading() {
        if(isStoragePermissionGranted()){
            DownloadManager mgr = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri =  Uri.parse(url);
            DownloadManager.Request req=new DownloadManager.Request(uri);
            req.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                    | DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(title)
                    .setDescription("downloading...")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,title);
            mgr.enqueue(req);
        }
    }
    private boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }


}
