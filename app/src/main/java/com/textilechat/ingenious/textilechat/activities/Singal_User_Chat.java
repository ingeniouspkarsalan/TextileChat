package com.textilechat.ingenious.textilechat.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.Adapters.Singlechat_adaptor;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.JSONParser;
import com.textilechat.ingenious.textilechat.classes.Single_user_msg_list;
import com.textilechat.ingenious.textilechat.classes.chat_messages;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Singal_User_Chat extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Single_user_msg_list> single_chat_message_list;
    private Singlechat_adaptor chat_adapters;
    String sending_msg;
    private EditText edit_message;
    private Button btn_send;
    final String id = Prefs.getString("user_id", "0");
    private String other_image,to_user_id;


    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singal__user__chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("user_name"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        edit_message=findViewById(R.id.edit_msg);
        btn_send=findViewById(R.id.btn_send);

        to_user_id=getIntent().getStringExtra("user_id");
        other_image=Prefs.getString("other_image","0");

        if(Utils.isOnline(Singal_User_Chat.this))
        {
            try
            {
                requestData(Endpoints.ip_server);
            }
            catch (Exception ex) {
                new SweetAlertDialog(Singal_User_Chat.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Some thing went wrong!")
                        .show();
            }
        } else
        {
            new SweetAlertDialog(Singal_User_Chat.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Internet Not Found!")
                    .show();
        }

        //sending msg
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sending_msg=edit_message.getText().toString();
                if(!sending_msg.isEmpty()){
                        sending_chat_to_server(sending_msg,id,to_user_id);
                        hideSoftKeyboard(Singal_User_Chat.this);
                }else{
                    Toast.makeText(Singal_User_Chat.this,"Insert text to send",Toast.LENGTH_SHORT).show();
                }
            }
        });


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Single_user_msg_list msg_class=(Single_user_msg_list) intent.getSerializableExtra("single_msg");

                single_chat_message_list.add(msg_class);
                chat_adapters.notifyDataSetChanged();

                if (chat_adapters.getItemCount() > 1) {
                    recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, chat_adapters.getItemCount() - 1);
                }

            }
        };

    }


    //getting chats from server one time
    public void requestData(String uri) {

        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {

                    single_chat_message_list = new ArrayList<>();
                    chat_adapters = new Singlechat_adaptor(Singal_User_Chat.this, single_chat_message_list,to_user_id,other_image,getIntent().getStringExtra("user_name"));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Singal_User_Chat.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(chat_adapters);
                } else {
                    try {
                        single_chat_message_list = JSONParser.parse_single_chatmessages(response);
                        chat_adapters = new Singlechat_adaptor(Singal_User_Chat.this, single_chat_message_list,to_user_id,other_image,getIntent().getStringExtra("user_name"));
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Singal_User_Chat.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(chat_adapters);
                        if (chat_adapters.getItemCount() > 1) {
                            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, chat_adapters.getItemCount() - 1);
                        }
                    }catch (Exception e){

                    }
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(Singal_User_Chat.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_key", "get_all_singal_chat");


                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    // Declear the Registration Function
    private void sending_chat_to_server(String message,String from_user_id, String to_user_id)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","add_singal_chat");
        params.put("to_id",to_user_id);
        params.put("from_id",from_user_id);
        params.put("message",message);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
//                pd = Utils.showProgress(Chat_Activity.this);
//                pd.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    //Toasty.warning(Chat_Activity.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            Toasty.success(Singal_User_Chat.this,object.getString("message"),Toast.LENGTH_LONG).show();
                        }else {
                            new SweetAlertDialog(Singal_User_Chat.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Alert Info...")
                                    .setContentText(object.getString("message"))
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String  response  = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Singal_User_Chat.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

                }else {

                    Log.d("response",response);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
//                pd.dismiss();
                edit_message.setText("");
            }
        });
    }



    //for keyboard hide
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("single_chat"));
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
