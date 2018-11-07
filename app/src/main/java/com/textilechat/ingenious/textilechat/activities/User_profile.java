package com.textilechat.ingenious.textilechat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Animation;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class User_profile extends AppCompatActivity {

    private ImageView user_image, is_verified;
    private TextView u_name, u_conatct, u_city, u_email, u_company, u_nature_bsns, u_comp_address;
    private Button single_chat_btn;
    private SweetAlertDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("User Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get User id With Intent Extra
        final String user_id = getIntent().getStringExtra("other_user_id");

        //Init All Views
        single_chat_btn = findViewById(R.id.btn_open_single_Chat);
        user_image = findViewById(R.id.user_image);
        is_verified = findViewById(R.id.is_verified);
        u_name = findViewById(R.id.user_name);
        u_conatct = findViewById(R.id.conatct_no);
        u_email = findViewById(R.id.u_email);
        u_city = findViewById(R.id.u_city);
        u_company = findViewById(R.id.u_company);
        u_nature_bsns = findViewById(R.id.u_nature_busns);
        u_comp_address = findViewById(R.id.u_company_address);

        // Set Click Listner on Single Chat Button To Open Single Chat Activity
        single_chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(User_profile.this, user_id,Toast.LENGTH_LONG).show();
            }
        });

        // Set Click Listner on Verified icon
        is_verified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toasty.success(User_profile.this,"Paid User",Toast.LENGTH_LONG).show();
            }
        });

        //Call the function of User Profile Detail
        get_user_profile_detail(user_id);
    }

    // Declear the User Profile Detail Function
    private void get_user_profile_detail(String u_id)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","get_user_profile_detail_by_id");
        params.put("u_id",u_id);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                pd = Utils.showProgress(User_profile.this);
                pd.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(User_profile.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            u_name.setText(object.getString("u_name"));
                            u_conatct.setText(object.getString("u_contact"));
                            u_city.setText(object.getString("u_city"));
                            u_email.setText(object.getString("u_email"));
                            u_company.setText(object.getString("u_company"));
                            u_comp_address.setText(object.getString("u_comp_address"));
                            u_nature_bsns.setText(object.getString("u_nature_bussniess"));
                            Glide.with(User_profile.this)
                                    .load(object.getString("u_image"))
                                    .into(user_image);
                            if (object.getString("u_is_paid").equals("1"))
                            {
                                is_verified.setVisibility(View.VISIBLE);
                            }

                        }else {
                            new SweetAlertDialog(User_profile.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText(object.getString("message"))
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("response",response);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String  response  = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(User_profile.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

                }else {

                    Log.d("response",response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                pd.dismiss();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        Animation.slideLeft(User_profile.this);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        Animation.slideLeft(User_profile.this);
    }
}
