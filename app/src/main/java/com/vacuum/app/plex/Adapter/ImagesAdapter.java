package com.vacuum.app.plex.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.vacuum.app.plex.Model.Backdrop;
import com.vacuum.app.plex.R;
import com.vacuum.app.plex.Utility.DownloadImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import static cn.jzvd.JZVideoPlayer.TAG;
import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Home on 2/24/2018.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.TrailerViewHolder> {

    //private List<Poster> posters;
    private List<Backdrop> posters;

    private Context mContext;


    public static class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        public TrailerViewHolder(View v) {
            super(v);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        }
    }

    public ImagesAdapter(List<Backdrop> posters, Context mContext) {
        this.posters = posters;
        this.mContext = mContext;
    }

    @Override
    public ImagesAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_images, parent, false);
        return new ImagesAdapter.TrailerViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ImagesAdapter.TrailerViewHolder holder, final int position) {

        String url = "http://image.tmdb.org/t/p/w500"+posters.get(position).getFilePath();
        Glide.with(mContext).load(url)
                .transition(withCrossFade())
                .into(holder.thumbnail);

        //onClick
        //==================================================================

        holder.thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                vibe.vibrate(150);
                Toast.makeText(mContext, "downloading...", Toast.LENGTH_SHORT).show();
                new DownloadImage(mContext,posters.get(position).getFilePath());

                return true;
            }
        });
    }




    @Override
    public int getItemCount() {
        return posters.size();
    }
}
