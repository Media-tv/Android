package com.vacuum.app.plex.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vacuum.app.plex.R;


/**
 * Created by Home on 10/19/2017.
 */

public class AboutUsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us_layout);


        TextView textView1 = findViewById(R.id.text1);
        TextView textView2 = findViewById(R.id.text2);
        Button button1 = findViewById(R.id.Button01);
        Button button2 = findViewById(R.id.Button02);
        Button button3 = findViewById(R.id.Button03);
        /*Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/oswaldlight.ttf");
        textView1.setTypeface(typeface);
        textView2.setTypeface(typeface);*/
        Html.fromHtml(getString(R.string.ss));


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/mohamedebrahim93");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://twitter.com/mohamedhima96");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.ebrahimm131@gmail.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}
