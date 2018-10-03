package com.textilechat.ingenious.textilechat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.Animation;

public class Splash extends AppCompatActivity {

    ImageView imageView;
    Button sing_in_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splash_banner);
        Picasso.get().load(R.drawable.splash).into(imageView);

        sing_in_btn = findViewById(R.id.btn_sign_in);
        sing_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, Sign_in.class));
                Animation.slideLeft(Splash.this);
            }
        });
    }
}
