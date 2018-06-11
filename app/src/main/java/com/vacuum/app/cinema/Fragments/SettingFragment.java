package com.vacuum.app.cinema.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

import com.vacuum.app.cinema.R;

/**
 * Created by Home on 3/10/2018.
 */

public class SettingFragment extends Fragment {
    Context mContext;
    public static final String TAG_SETTING_FRAGMENT = "TAG_SETTING_FRAGMENT";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_settings, container, false);
        View view = inflater.inflate(R.layout.ticket, container, false);
        mContext = this.getContext();


        FrameLayout frame = (FrameLayout) view.findViewById(R.id.graphics_holder);
        PlayAreaView image = new PlayAreaView(mContext);
        frame.addView(image);
        return view;

    }

    private class PlayAreaView extends View {

        private GestureDetector gestures;
        private Matrix translate;
        private Bitmap droid;

        private Matrix animateStart;
        private Interpolator animateInterpolator;
        private long startTime;
        private long endTime;
        private float totalAnimDx;
        private float totalAnimDy;

        public void onAnimateMove(float dx, float dy, long duration) {
            animateStart = new Matrix(translate);
            animateInterpolator = new OvershootInterpolator();
            startTime = System.currentTimeMillis();
            endTime = startTime + duration;
            totalAnimDx = dx;
            totalAnimDy = dy;
            post(new Runnable() {
                @Override
                public void run() {
                    onAnimateStep();
                }
            });
        }

        private void onAnimateStep() {
            long curTime = System.currentTimeMillis();
            float percentTime = (float) (curTime - startTime)
                    / (float) (endTime - startTime);
            float percentDistance = animateInterpolator
                    .getInterpolation(percentTime);
            float curDx = percentDistance * totalAnimDx;
            float curDy = percentDistance * totalAnimDy;
            translate.set(animateStart);
            onMove(curDx, curDy);

            //Log.v(DEBUG_TAG, "We're " + percentDistance + " of the way there!");
            if (percentTime < 1.0f) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        onAnimateStep();
                    }
                });
            }
        }

        public void onMove(float dx, float dy) {
            translate.postTranslate(dx, dy);
            invalidate();
        }

        public void onResetLocation() {
            translate.reset();
            invalidate();
        }

        public void onSetLocation(float dx, float dy) {
            translate.postTranslate(dx, dy);
        }

        public PlayAreaView(Context context) {
            super(context);
            translate = new Matrix();
            gestures = new GestureDetector(mContext,
                    new GestureListener(this));
            droid = BitmapFactory.decodeResource(getResources(),
                    R.drawable.ticket3);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // Log.v(DEBUG_TAG, "onDraw");
            canvas.drawBitmap(droid, translate, null);
            Matrix m = canvas.getMatrix();
            //Log.d(DEBUG_TAG, "Matrix: " + translate.toShortString());
            //Log.d(DEBUG_TAG, "Canvas: " + m.toShortString());
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            return gestures.onTouchEvent(event);
        }

    }

    private class GestureListener implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener {

        PlayAreaView view;

        public GestureListener(PlayAreaView view) {
            this.view = view;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.v("TAG", "onDown");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               final float velocityX, final float velocityY) {
            //Log.v(DEBUG_TAG, "onFling");
            final float distanceTimeFactor = 0.4f;
            final float totalDx = (distanceTimeFactor * velocityX / 2);
            final float totalDy = (distanceTimeFactor * velocityY / 2);

            view.onAnimateMove(totalDx, totalDy,
                    (long) (1000 * distanceTimeFactor));
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.v("TAG", "onDoubleTap");
            view.onResetLocation();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.v("TAG", "onLongPress");
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            //Log.v(DEBUG_TAG, "onScroll");

            view.onMove(-distanceX, -distanceY);
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.v("TAG", "onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.v("TAG", "onSingleTapUp");
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.v("TAG", "onDoubleTapEvent");
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.v("TAG", "onSingleTapConfirmed");
            return false;
        }

    }

}