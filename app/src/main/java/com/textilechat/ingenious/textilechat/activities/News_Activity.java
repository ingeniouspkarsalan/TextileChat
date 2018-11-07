package com.textilechat.ingenious.textilechat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.Adapters.New_Adapter;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.JSONParser;
import com.textilechat.ingenious.textilechat.classes.News_Class;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class News_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<News_Class> newsClassList;
    private New_Adapter new_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("News");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);


        if(Utils.isOnline(News_Activity.this))
        {
            try
            {
                requestData(Endpoints.ip_server);
            }
            catch (Exception ex) {
                new SweetAlertDialog(News_Activity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Some thing went wrong!")
                        .show();
            }
        } else
        {
            new SweetAlertDialog(News_Activity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Internet Not Found!")
                    .show();
        }
    }

    public void requestData(String uri) {
        final String id = Prefs.getString("user_id", "0");
        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {
                    new SweetAlertDialog(News_Activity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Alert...")
                            .setContentText("News not available..")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    finish();
                                }
                            })
                            .show();
                } else {
                    newsClassList = JSONParser.parse_news(response);
                    new_adapter = new New_Adapter(News_Activity.this, newsClassList);
                    recyclerView.setAdapter(new_adapter);
                    LinearLayoutManager llm = new LinearLayoutManager(News_Activity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(News_Activity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_key", "get_all_news");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public void finish() {
        super.finish();
        Animation.swipeLeft(this);
    }



}
