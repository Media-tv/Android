package com.vacuum.app.plex.Adapter;

import android.view.LayoutInflater;

import java.util.ArrayList;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vacuum.app.plex.R;

public class SlidingTextAdapter extends PagerAdapter {


    private ArrayList<String> textList,textList2;
    private LayoutInflater inflater;
    private Context context;


    public SlidingTextAdapter(Context context,ArrayList<String> textList2) {
        this.context = context;
        this.textList2=textList2;
        inflater = LayoutInflater.from(context);
        textList = new ArrayList<>();
        textList.add("Welcome");
        textList.add("Collections");
        textList.add("Sharing");

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return textList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingtext_layout, view, false);

        assert imageLayout != null;
        final TextView text_slide =  imageLayout
                .findViewById(R.id.text_slide);
        final TextView text_slide2 =  imageLayout
                .findViewById(R.id.text_slide2);

        text_slide.setText(textList.get(position));
        text_slide2.setText(textList2.get(position));

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
