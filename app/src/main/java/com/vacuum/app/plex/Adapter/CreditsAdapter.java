package com.vacuum.app.plex.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.vacuum.app.plex.Fragments.ActorFragment;
import com.vacuum.app.plex.Fragments.DetailsTV_Fragment;
import com.vacuum.app.plex.MainActivity;
import com.vacuum.app.plex.Model.Cast;
import com.vacuum.app.plex.Model.Trailer;
import com.vacuum.app.plex.R;

import java.util.List;

import static com.vacuum.app.plex.Fragments.ActorFragment.TAG_ACTOR_FRAGMENT;

/**
 * Created by Home on 3/1/2018.
 */

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder> {

    private List<Cast> casts;
    private Context mContext;


    public static class CreditsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView character;
        ImageView thumbnail;
        public CreditsViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            character = (TextView) v.findViewById(R.id.character);
            thumbnail = (ImageView) v.findViewById(R.id.thumbnail);

        }
    }

    public CreditsAdapter(List<Cast> casts, Context mContext) {
        this.casts = casts;
        this.mContext = mContext;
    }

    @Override
    public CreditsAdapter.CreditsViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cast, parent, false);
        return new CreditsAdapter.CreditsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(CreditsAdapter.CreditsViewHolder holder, final int position) {

        holder.name.setText(casts.get(position).getName());
        holder.character.setText(casts.get(position).getCharacter());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.if_person);
        requestOptions.error(R.drawable.if_person);
        Glide.with(mContext).setDefaultRequestOptions(requestOptions).load("http://image.tmdb.org/t/p/w185"+casts.get(position).getProfilePath()).apply(RequestOptions.circleCropTransform()).into(holder.thumbnail);


        //onClick
        //==================================================================

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new ActorFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("person_id", casts.get(position).getId());
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment,TAG_ACTOR_FRAGMENT);
                fragmentTransaction.addToBackStack(MainActivity.CURRENT_TAG);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

}
