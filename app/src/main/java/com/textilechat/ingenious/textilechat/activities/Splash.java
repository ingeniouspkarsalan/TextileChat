package com.textilechat.ingenious.textilechat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.classes.Animation;

public class Splash extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.splash_banner);
        Picasso.get().load(R.drawable.splash).into(imageView);

        findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, Sign_in.class));
                Animation.slideLeft(Splash.this);
            }
        });

        findViewById(R.id.btn_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Splash.this, Sign_up.class));
                Animation.slideLeft(Splash.this);
            }
        });

        //To check if user logged in
        Boolean isLoginSucces = Prefs.getBoolean("loginSuccess",false);
        if(isLoginSucces) {
            //start activity..
            Intent intent = new Intent(Splash.this,Home.class);
            startActivity(intent);
            finish();
        }
    }
}
