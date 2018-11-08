package com.textilechat.ingenious.textilechat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.textilechat.ingenious.textilechat.R;

public class Packge_Page extends AppCompatActivity {
    TextView abc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packge__page);
        abc=findViewById(R.id.show_msg);
        abc.setText(getIntent().getStringExtra("msg"));
    }
}
