package com.textilechat.ingenious.textilechat.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
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

public class Edit_profile extends AppCompatActivity {
    String user_id;
    public static final int PICK_IMAGE = 1;
    private SweetAlertDialog pd;
    private String photoPath = "";
    private ImageView profile_image;
    private EditText name,contact,city,company,c_nature,c_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user_id = getIntent().getStringExtra("user_id");
        get_user_profile_detail();
        init();
    }

    private void init(){
    profile_image=findViewById(R.id.profile_image);
        name=findViewById(R.id.tv_name);
        contact=findViewById(R.id.tv_contact);
        city=findViewById(R.id.tv_city);
        company=findViewById(R.id.tv_company);
        c_nature=findViewById(R.id.tv_c_nature);
        c_address=findViewById(R.id.tv_c_address);
    profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
    public void onClick(View v) {
        ImagePicker.create(Edit_profile.this) // Activity or Fragment
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Select Image") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .single() // single mode
                .start();
    }
    });

        findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utils.isOnline(Edit_profile.this))
                {
                   // scaningallfields();
                }
                else
                {
                    new SweetAlertDialog(Edit_profile.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText("Internet Not Found!")
                            .show();
                }
            }
        });
}



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (ImagePicker.shouldHandle(requestCode, resultCode, data))
            {
                Image image = ImagePicker.getFirstImageOrNull(data);
                photoPath = image.getPath();
                Bitmap bmImg = BitmapFactory.decodeFile(photoPath);
                profile_image.setImageBitmap(bmImg);
            }

        }catch (Exception e){

        }
    }



    // Declear the User Profile Detail Function
    private void get_user_profile_detail()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","get_user_profile_detail_by_id");
        params.put("u_id",user_id);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                pd = Utils.showProgress(Edit_profile.this);
                pd.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Edit_profile.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            if(!object.getString("u_image").isEmpty()){
                                Glide.with(Edit_profile.this)
                                        .load(object.getString("u_image"))
                                        .into(profile_image);
                            }

                            name.setText(object.getString("u_name"));
                            contact.setText(object.getString("u_contact"));
                            city.setText(object.getString("u_city"));
                            company.setText(object.getString("u_company"));
                            c_address.setText(object.getString("u_comp_address"));
                            c_nature.setText(object.getString("u_nature_bussniess"));

                        }else {
                            new SweetAlertDialog(Edit_profile.this, SweetAlertDialog.ERROR_TYPE)
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
                    Toasty.warning(Edit_profile.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

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
        Animation.slideLeft(Edit_profile.this);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
        Animation.slideLeft(Edit_profile.this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
