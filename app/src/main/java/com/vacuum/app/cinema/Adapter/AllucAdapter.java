package com.vacuum.app.cinema.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.vacuum.app.cinema.Fragments.AllucFragment;
import com.vacuum.app.cinema.Fragments.WatchFragment;
import com.vacuum.app.cinema.Model.Alluc;
import com.vacuum.app.cinema.Model.allucThumbnail;
import com.vacuum.app.cinema.R;
import com.vacuum.app.cinema.Utility.ApiClient;
import com.vacuum.app.cinema.Utility.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Home on 3/4/2018.
 */

public class AllucAdapter extends RecyclerView.Adapter<AllucAdapter.AllucViewHolder> {

    private List<Alluc> alluc;
    private Context mContext;


    public static class AllucViewHolder extends RecyclerView.ViewHolder {
        TextView overview,Title,year;
        ImageView thumbnail;
        public AllucViewHolder(View v) {
            super(v);
            overview = (TextView) v.findViewById(R.id.overview);
            Title = (TextView) v.findViewById(R.id.Title);
            year = (TextView) v.findViewById(R.id.year);

            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        }
    }

    public AllucAdapter(List<Alluc> alluc, Context mContext) {
        this.alluc = alluc;
        this.mContext = mContext;
    }

    @Override
    public AllucAdapter.AllucViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search, parent, false);
        return new AllucAdapter.AllucViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AllucAdapter.AllucViewHolder holder, final int position) {

        final String url = alluc.get(position).getHosterurls().get(0).getUrl();
        holder.overview.setText(url);
        holder.Title.setText(alluc.get(position).getHostername());
        holder.year.setText(alluc.get(position).getSourcetitle());
        //holder.year.setText(alluc.get(position).getImageid());

        getData(alluc.get(position).getImageid(),holder.thumbnail);
        //onClick
        //==================================================================
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    // Chrome browser presumably not installed so allow user to choose instead
                    intent.setPackage(null);
                    mContext.startActivity(intent);
                }*/

                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                WatchFragment watchFragment = new WatchFragment();
                watchFragment.setArguments(bundle);
                FragmentTransaction manager = ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                manager.replace(R.id.frame_detailsFragment, watchFragment);
                manager.addToBackStack(null).commit();
            }
        });
    }

    public void getData(String id, final ImageView thumbnail2) {
        String Alluc_API_KEY = mContext.getString(R.string.Alluc_API_KEY);

        String url = "https://www.alluc.ee/api/thumbnail/"+id+"&apikey="+Alluc_API_KEY;
        ApiInterface apiService2 =
                ApiClient.getClient().create(ApiInterface.class);
        Call<allucThumbnail> call_Image_Alluc = apiService2.profilePicture2(url);
        call_Image_Alluc.enqueue(new Callback<allucThumbnail>() {
            @Override
            public void onResponse(Call<allucThumbnail> call, Response<allucThumbnail> response) {
                String imageBytes  = response.body().getResult().getThumbnailBase64();
                byte[] imageByteArray= Base64.decode(imageBytes, Base64.DEFAULT);
                Glide.with(mContext)
                        .load(imageByteArray)
                        .into(thumbnail2);

            }

            @Override
            public void onFailure(Call<allucThumbnail> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return alluc.size();
    }

}
