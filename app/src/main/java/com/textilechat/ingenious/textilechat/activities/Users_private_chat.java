package com.textilechat.ingenious.textilechat.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.Adapters.pc_adaptor;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.JSONParser;
import com.textilechat.ingenious.textilechat.classes.pc_class;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class Users_private_chat extends AppCompatActivity {

    /*
    *
    * SELECT sc.from_user_id,sc.to_user_id,u.u_name as to_user ,u.u_image as to_user_image,uf.u_name as from_user ,uf.u_image as from_user_image from single_chat sc
     * INNER JOIN users u ON sc.to_user_id=u.u_id INNER JOIN users uf ON sc.from_user_id= uf.u_id where from_user_id='20' OR to_user_id='20' GROUP by to_user_id
    *
    *
    * */




   private RecyclerView recyclerView;
   private pc_adaptor pc_adaptor;
   private List<pc_class> pc_classList;

    final String id = Prefs.getString("user_id", "0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_private_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Private Chats");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        if(Utils.isOnline(Users_private_chat.this))
        {
            try
            {
                requestData(Endpoints.ip_server);
            }
            catch (Exception ex) {
                new SweetAlertDialog(Users_private_chat.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Some thing went wrong!")
                        .show();
            }
        } else
        {
            new SweetAlertDialog(Users_private_chat.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Internet Not Found!")
                    .show();
        }

    }


    //getting ads from server one time
    public void requestData(String uri) {

        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {

                } else {
                    pc_classList=JSONParser.parse_pc(response);
                    pc_adaptor=new pc_adaptor(Users_private_chat.this,pc_classList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Users_private_chat.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(pc_adaptor);
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(Users_private_chat.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_key", "private_chat");
                params.put("usr_id", id);
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
