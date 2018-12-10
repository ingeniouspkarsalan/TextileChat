package com.textilechat.ingenious.textilechat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.textilechat.ingenious.textilechat.R;

public class Ads_image_show extends AppCompatActivity {
    ImageView ads_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_image_show);
        ads_image=findViewById(R.id.ads_image);
        if(!getIntent().getStringExtra("image").isEmpty()){
            Glide.with(Ads_image_show.this).load(getIntent().getStringExtra("image")).into(ads_image);
        }
    }
}
