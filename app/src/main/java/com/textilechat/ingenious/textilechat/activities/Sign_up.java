package com.textilechat.ingenious.textilechat.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.fcm_classes.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Sign_up extends AppCompatActivity {
    private EditText name,contact,email,password,city,company,c_nature,c_address;
    private SweetAlertDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //initializing all getting texts and also submit button
        init();
    }


    private void init(){
        name=findViewById(R.id.tv_name);
        contact=findViewById(R.id.tv_contact);
        email=findViewById(R.id.tv_email);
        password=findViewById(R.id.tv_password);
        city=findViewById(R.id.tv_city);
        company=findViewById(R.id.tv_company);
        c_nature=findViewById(R.id.tv_c_nature);
        c_address=findViewById(R.id.tv_c_address);

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isOnline(Sign_up.this))
                {
                    scaningallfields();
                }
                else
                {
                    new SweetAlertDialog(Sign_up.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Internet Not Found!")
                            .show();
                }
            }
        });

    }

    private void scaningallfields(){
        if(!TextUtils.isEmpty(name.getText().toString()) & !TextUtils.isEmpty(contact.getText().toString()) & !TextUtils.isEmpty(email.getText().toString()) & !TextUtils.isEmpty(password.getText().toString())
                & !TextUtils.isEmpty(city.getText().toString()) & !TextUtils.isEmpty(company.getText().toString()) & !TextUtils.isEmpty(c_nature.getText().toString()) & !TextUtils.isEmpty(c_address.getText().toString())){

            String token = SharedPrefManager.getInstance(this).getDeviceToken();

            //if token is not null
            if (token != null) {
                //calling funtion
                signup(name.getText().toString(),contact.getText().toString(),email.getText().toString(),password.getText().toString(),
                        city.getText().toString(),company.getText().toString(),c_nature.getText().toString(),c_address.getText().toString(),token);
            } else {
                signup(name.getText().toString(),contact.getText().toString(),email.getText().toString(),password.getText().toString(),
                        city.getText().toString(),company.getText().toString(),c_nature.getText().toString(),c_address.getText().toString(),"");
            }



        }else{
            new SweetAlertDialog(Sign_up.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Fill all Fields")
                    .show();
        }
    }

    // Declear the Registration Function
    private void signup(String name,String contact, String email, String password,String city,String company,String c_nature,String c_address,String token)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","user_registration");
        params.put("name",name);
        params.put("email",email);
        params.put("contact",contact);
        params.put("pass",password);
        params.put("city",city);
        params.put("company",company);
        params.put("company_address",c_address);
        params.put("bussniess",c_nature);
        params.put("fcm_id",token);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                pd = Utils.showProgress(Sign_up.this);
                pd.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Sign_up.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            Toasty.success(Sign_up.this,object.getString("message"),Toast.LENGTH_LONG).show();
                            finish();
                        }else {
                            new SweetAlertDialog(Sign_up.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Oops...")
                                    .setContentText("Try Again")
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
                    Toasty.warning(Sign_up.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

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
        Animation.slideLeft(Sign_up.this);
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
