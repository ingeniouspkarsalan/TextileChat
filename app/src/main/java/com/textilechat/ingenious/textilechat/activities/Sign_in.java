package com.textilechat.ingenious.textilechat.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.fcm_classes.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Sign_in extends AppCompatActivity {
    private EditText tv_email,tv_pass;
    private SweetAlertDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    private void init(){
        tv_email=findViewById(R.id.tv_email);
        tv_pass=findViewById(R.id.tv_pass);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isOnline(Sign_in.this))
                {
                    scaningallfields();
                }
                else
                {
                    new SweetAlertDialog(Sign_in.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Internet Not Found!")
                            .show();
                }
            }
        });

    }

    private void scaningallfields(){
        if(!TextUtils.isEmpty(tv_email.getText().toString()) & !TextUtils.isEmpty(tv_pass.getText().toString())){

                sign_in(tv_email.getText().toString(),tv_pass.getText().toString());




        }else{
            new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Fill all Fields")
                    .show();
        }
    }

    // Declear the Registration Function
    private void sign_in(String email, String password)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","user_login");
        params.put("email",email);
        params.put("pass",password);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                pd = Utils.showProgress(Sign_in.this);
                pd.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Sign_in.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            Toasty.success(Sign_in.this,object.getString("message"),Toast.LENGTH_LONG).show();
                            Prefs.putString("user_id",object.getString("id"));
                            Prefs.putString("user_name",object.getString("name"));
                            Prefs.putString("user_email",object.getString("email"));
                            Prefs.putBoolean("loginSuccess",true); // change this value on logout
                            startActivity(new Intent(Sign_in.this, Home.class));
                            Animation.slideUp(Sign_in.this);
                        }else {
                            new SweetAlertDialog(Sign_in.this, SweetAlertDialog.ERROR_TYPE)
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
                    Toasty.warning(Sign_in.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

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
        Animation.slideRight(Sign_in.this);
    }
}
