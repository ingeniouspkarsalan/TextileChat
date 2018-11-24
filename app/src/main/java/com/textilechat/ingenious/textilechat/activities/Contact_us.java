package com.textilechat.ingenious.textilechat.activities;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.Adapters.Ads_adaptor;
import com.textilechat.ingenious.textilechat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.JSONParser;

public class Contact_us extends AppCompatActivity {
    private EditText nametxt,emailtxt,phonetxt,msgtxt;
    private Button btn_submit;
    private SweetAlertDialog pd;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String currentDateandTime = sdf.format(new Date());
    List<String> for_subjects;
    Spinner spinner_subject;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle("Contact us");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        gettingvalues();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isOnline(Contact_us.this))
                {
                    if(nametxt.getText().toString().isEmpty() || phonetxt.getText().toString().isEmpty() || msgtxt.getText().toString().isEmpty())
                    {
                        new SweetAlertDialog(Contact_us.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Fields are Required!")
                                .show();

                    }
                    else
                    {
                        //call the methode
                        requestData(Endpoints.ip_server);
                    }
                }
                else
                {
                    new SweetAlertDialog(Contact_us.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Internet Not Found!")
                            .show();
                }
            }
        });
    }




    private void gettingvalues(){
        for_subjects=new ArrayList<>();
        for_subjects.add("Choose the Subject");
        for_subjects.add("feedback");
        for_subjects.add("complains");
        for_subjects.add("call back request");
        for_subjects.add("Package detail");
        for_subjects.add("others");

        spinner_subject=findViewById(R.id.subject);
        spinner_subject.setAdapter(new ArrayAdapter<>(Contact_us.this, android.R.layout.simple_spinner_dropdown_item, for_subjects));
        spinner_subject.setSelection(0);


        nametxt=findViewById(R.id.nametxt);
        emailtxt=findViewById(R.id.emailtxt);
        phonetxt=findViewById(R.id.phonetxt);
        msgtxt=findViewById(R.id.msgtxt);
        btn_submit=findViewById(R.id.btn_submit);
    }

    public void requestData(String uri) {
        final String id = Prefs.getString("user_id", "0");
        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {
                    new SweetAlertDialog(Contact_us.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Alert...")
                            .setContentText("Try Again.")
                            .show();
                } else {

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(Contact_us.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_key","contact_us_email");
                params.put("u_email",emailtxt.getText().toString());
                params.put("subject",spinner_subject.getSelectedItem().toString());
                params.put("message",msgtxt.getText().toString());
                params.put("name",nametxt.getText().toString());
                params.put("phone",phonetxt.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
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
