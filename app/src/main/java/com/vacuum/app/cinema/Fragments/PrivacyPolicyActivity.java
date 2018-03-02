package com.vacuum.app.cinema.Fragments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.vacuum.app.cinema.R;


/**
 * Created by Home on 10/19/2017.
 */

public class PrivacyPolicyActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacypolicy_layout);

        TextView tv =  findViewById(R.id.textView1);
        TextView tv2 =  findViewById(R.id.textView2);
        Toolbar mToolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Privacy Policy");


        /*Typeface face = Typeface.createFromAsset(getAssets(),
                "fonts/brownregular.ttf");
        Typeface face2 = Typeface.createFromAsset(getAssets(),
                "fonts/airbnb.ttf");
        tv.setTypeface(face);
        tv2.setTypeface(face2);*/


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                finish_Activity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        finish_Activity();
    }
    private void finish_Activity() {
        //Intent intent = new Intent(PrivacyPolicyActivity.this, MainActivity.class);
        //startActivity(intent);
        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }
}