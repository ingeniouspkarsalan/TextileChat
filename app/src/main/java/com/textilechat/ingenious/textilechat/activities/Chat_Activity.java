package com.textilechat.ingenious.textilechat.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.Adapters.chat_adapter;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Ads_class;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.CategoryClass;
import com.textilechat.ingenious.textilechat.classes.Daily_service_class;
import com.textilechat.ingenious.textilechat.classes.JSONParser;
import com.textilechat.ingenious.textilechat.classes.Sqlite_for_markers;
import com.textilechat.ingenious.textilechat.classes.chat_messages;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.textilechat.ingenious.textilechat.classes.serialize_msg_class;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Chat_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<chat_messages> chat_message_list;
    private List<chat_messages> searchlist;
    private chat_adapter chat_adapters;
    String sending_msg;
    private EditText edit_message,searchbox;
    private Button btn_send;
    final String id = Prefs.getString("user_id", "0");

    private ImageView attachment,ads_banners;
    private List<Ads_class> ads_classList;

    Sqlite_for_markers sqlite_for_markers;

    TextView toolbar_title;
    Switch aSwitch;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private int iterator;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_title=toolbar.findViewById(R.id.title);
        toolbar_title.setText(getIntent().getStringExtra("name"));
        aSwitch=toolbar.findViewById(R.id.on_off_switch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        edit_message=findViewById(R.id.edit_msg);
        btn_send=findViewById(R.id.btn_send);

        //init database
        sqlite_for_markers=new Sqlite_for_markers(Chat_Activity.this);


        //for notification mute unmute
        try {
            if (getIntent().getStringExtra("id_name").equals("category")) {

                if (sqlite_for_markers.getnotifCount(getIntent().getStringExtra("c_id"), "0") > 0) {
                    aSwitch.setChecked(true);
                }

            } else if (getIntent().getStringExtra("id_name").equals("sub_category")) {

                if (sqlite_for_markers.getnotifCount(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id")) > 0) {
                    aSwitch.setChecked(true);
                }

            }
        }catch (Exception e){}


        //user doing mute unmute
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked==true){
                   if (getIntent().getStringExtra("id_name").equals("category")) {

                       if (sqlite_for_markers.insert_notif(getIntent().getStringExtra("c_id"), "0") == 1) {
                           aSwitch.setChecked(true);
                       }

                   } else if (getIntent().getStringExtra("id_name").equals("sub_category")) {

                       if (sqlite_for_markers.insert_notif(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id")) ==1) {
                           aSwitch.setChecked(true);
                       }

                   }
               }else if(isChecked==false){
                   if (getIntent().getStringExtra("id_name").equals("category")) {

                       if (sqlite_for_markers.getnotifCount(getIntent().getStringExtra("c_id"), "0") > 0) {
                           sqlite_for_markers.delete_notif_ids(getIntent().getStringExtra("c_id"), "0");
                           aSwitch.setChecked(false);
                       }

                   } else if (getIntent().getStringExtra("id_name").equals("sub_category")) {

                       if (sqlite_for_markers.getnotifCount(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id")) > 0) {
                           sqlite_for_markers.delete_notif_ids(getIntent().getStringExtra("c_id"),  getIntent().getStringExtra("s_id"));
                           aSwitch.setChecked(false);
                       }

                   }
               }
            }

        });


        //paid user get visible on attachment
        attachment=findViewById(R.id.attachment);
        if(Prefs.getString("User_paid_status","0").equals("1")){
            attachment.setVisibility(View.VISIBLE);
            attachment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toasty.success(Chat_Activity.this,"yes u r paid user.",Toast.LENGTH_SHORT).show();
                    for_attachment();
                }
            });
        }

        if(Utils.isOnline(Chat_Activity.this))
        {
            try
            {
                requestData(Endpoints.ip_server);
            }
            catch (Exception ex) {
                new SweetAlertDialog(Chat_Activity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Some thing went wrong!")
                        .show();
            }
        } else
        {
            new SweetAlertDialog(Chat_Activity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Internet Not Found!")
                    .show();
        }

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                serialize_msg_class msg_class=(serialize_msg_class) intent.getSerializableExtra("msg");
                chat_messages coming=new chat_messages();

                final String image_owner = Prefs.getString("owner_image", "");
                coming.setIds(msg_class.getU_id().toString());
                coming.setUser_name(msg_class.getU_name().toString());
                coming.setMessages(msg_class.getMassege().toString());
                coming.setTimestamp(msg_class.getCreated_at().toString());
                coming.setU_status("0");
                coming.setU_image(image_owner);

                if(getIntent().getStringExtra("id_name").equals("category")) {
                    if(getIntent().getStringExtra("c_id").equals(msg_class.getC_id())){
                        chat_message_list.add(chat_message_list.size(),coming);
                        chat_adapters.notifyDataSetChanged();

                        if (chat_adapters.getItemCount() > 1) {
                            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, chat_adapters.getItemCount() - 1);
                        }
                    }

                }else if(getIntent().getStringExtra("id_name").equals("sub_category")) {
                    if(getIntent().getStringExtra("c_id").equals(msg_class.getC_id()) & getIntent().getStringExtra("s_id").equals(msg_class.getSc_id())){
                        chat_message_list.add(chat_message_list.size(),coming);
                        chat_adapters.notifyDataSetChanged();

                        if (chat_adapters.getItemCount() > 1) {
                            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, chat_adapters.getItemCount() - 1);
                        }
                    }

                }
            }
        };





        //sending msg
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sending_msg=edit_message.getText().toString();
                if(!sending_msg.isEmpty()){
                    if(getIntent().getStringExtra("id_name").equals("category")) {
                        btn_send.setEnabled(false);
                        sending_chat_to_server(sending_msg,getIntent().getStringExtra("c_id")+"","0");

                        hideSoftKeyboard(Chat_Activity.this);

                    }else if(getIntent().getStringExtra("id_name").equals("sub_category")) {
                        btn_send.setEnabled(false);
                        sending_chat_to_server(sending_msg,getIntent().getStringExtra("c_id")+"",getIntent().getStringExtra("s_id")+"");
                        hideSoftKeyboard(Chat_Activity.this);
                    }
                }else{
                    Toast.makeText(Chat_Activity.this,"Insert text to send",Toast.LENGTH_SHORT).show();
                }
            }
        });
        initsearch();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter("chat"));
    }

    //getting chats from server one time
    public void requestData(String uri) {

        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {

                    chat_message_list = new ArrayList<>();
                    chat_adapters = new chat_adapter(Chat_Activity.this, chat_message_list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(Chat_Activity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(chat_adapters);
                    requestDataforads(Endpoints.ip_server);
                } else {
                    try {
                        chat_message_list = JSONParser.parse_chatmessages(response);
                        chat_adapters = new chat_adapter(Chat_Activity.this, chat_message_list);
                        LinearLayoutManager layoutManager = new LinearLayoutManager(Chat_Activity.this);
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(chat_adapters);

                        if (chat_adapters.getItemCount() > 1 || chat_adapters.getItemCount() == 1) {

                            if(getIntent().getStringExtra("id_name").equals("category")) {

                                if(sqlite_for_markers.getCategoryCount(getIntent().getStringExtra("c_id")) > 0 )
                                {
                                  int msg_id =  sqlite_for_markers.get_position(getIntent().getStringExtra("c_id"),"0");
                                    sqlite_for_markers.delete_cat(getIntent().getStringExtra("c_id"));
                                    recyclerView.scrollToPosition(chat_adapters.getItemPosition(msg_id));


                                }else{
                                    //recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, chat_adapters.getItemCount() - 1);
                                    recyclerView.getLayoutManager().scrollToPosition(chat_adapters.getItemCount() - 1);
                                }
                            }
                            else if(getIntent().getStringExtra("id_name").equals("sub_category")) {

                                if(sqlite_for_markers.getSubCount(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id")) > 0)
                                {
                                    int msg_id =  sqlite_for_markers.get_position(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id"));
                                    sqlite_for_markers.delete_sub_cat(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id"));
                                    recyclerView.scrollToPosition(chat_adapters.getItemPosition(msg_id));

                                }else{
                                    //recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, chat_adapters.getItemCount() - 1);
                                    recyclerView.getLayoutManager().scrollToPosition(chat_adapters.getItemCount() - 1);
                                }

                            }
                        }

                        requestDataforads(Endpoints.ip_server);
                    }catch (Exception e){

                    }
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(Chat_Activity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                if(getIntent().getStringExtra("id_name").equals("category")) {

                    params.put("req_key", "retrive_category_masseges");
                    params.put("c_id", getIntent().getStringExtra("c_id")+"");

                    //deleting category marks
//                    if(sqlite_for_markers.getCategoryCount(getIntent().getStringExtra("c_id")) > 0 )
//                    {
//                        sqlite_for_markers.delete_cat(getIntent().getStringExtra("c_id"));
//
//                    }
                }else if(getIntent().getStringExtra("id_name").equals("sub_category")) {

                    params.put("req_key", "retrive_sub_category_masseges");
                    params.put("c_id", getIntent().getStringExtra("c_id")+"");
                    params.put("sc_id", getIntent().getStringExtra("s_id")+"");

                    //deleting sub category marks
//                    if(sqlite_for_markers.getSubCount(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id")) > 0)
//                    {
//                        sqlite_for_markers.delete_sub_cat(getIntent().getStringExtra("c_id"), getIntent().getStringExtra("s_id"));
//
//                    }

                }
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }


    // Declear the Registration Function
    private void sending_chat_to_server(String message,String cat_id, String sc_id)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","add_messges");
        params.put("cat_id",cat_id);
        params.put("sub_cat_id",sc_id);
        params.put("user_id",id);
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
                   // Toasty.warning(Chat_Activity.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success")) {
                            btn_send.setEnabled(true);
                            Toasty.success(Chat_Activity.this,object.getString("message"),Toast.LENGTH_LONG).show();
                        }else {
                            new SweetAlertDialog(Chat_Activity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Alert Info...")
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
                    Toasty.warning(Chat_Activity.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

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

    //getting ads from server one time
    public void requestDataforads(String uri) {

        StringRequest request = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {

                } else {
                    try {
                        ads_classList = JSONParser.parse_ads(response);
                        iterator=ads_classList.size();
                        final Handler handler = new Handler();

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if(iterator>0){
                                    try {
                                        iterator--;

                                        if(getIntent().getStringExtra("id_name").equals("category")) {

                                            if(ads_classList.get(iterator).getC_id().equals(getIntent().getStringExtra("c_id")) && ads_classList.get(iterator).getSc_id().equals("0")) {

                                                ads_banners = findViewById(R.id.ads_banners);
                                                ads_banners.setVisibility(View.VISIBLE);
                                                Glide.with(Chat_Activity.this).load(ads_classList.get(iterator).getAd_image()).into(ads_banners);
                                                ads_banners.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(Chat_Activity.this,Ads_image_show.class).putExtra("image",ads_classList.get(iterator).getAd_image()));
                                                    }
                                                });

                                            }
                                        }else if(getIntent().getStringExtra("id_name").equals("sub_category")) {

                                            if(ads_classList.get(iterator).getC_id().equals(getIntent().getStringExtra("c_id")) && ads_classList.get(iterator).getSc_id().equals(getIntent().getStringExtra("s_id"))){

                                                ads_banners = findViewById(R.id.ads_banners);
                                                ads_banners.setVisibility(View.VISIBLE);
                                                Glide.with(Chat_Activity.this).load(ads_classList.get(iterator).getAd_image()).into(ads_banners);
                                                ads_banners.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(Chat_Activity.this,Ads_image_show.class).putExtra("image",ads_classList.get(iterator).getAd_image()));
                                                    }
                                                });

                                            }

                                        }

                                    }catch (Exception e){}
                                }else{
                                    try {
                                        ads_banners.setVisibility(View.GONE);
                                    }catch (Exception e){}
                                }

                                handler.postDelayed(this, ads_classList.get(iterator).getAd_display_time()); //now is every 2 minutes  86400000 48 hours
                            }
                        }, 10000); //Every 120000 ms (2 minutes)

                    }catch (Exception e){

                    }
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(Chat_Activity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                    params.put("req_key", "all_advertisments");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void searching(String words){
        searchlist = new ArrayList<>();
        if(searchlist != null)
            for (chat_messages list : chat_message_list)
            {
                if(list != null)
                    if(list.getMessages().toLowerCase().contains(words))
                    {
                        searchlist.add(list);
                    }
            }
        chat_adapters.searchedList(searchlist);
    }

    //init searching
    private void initsearch(){

        searchbox = (EditText) findViewById(R.id.searchbox);
        try{
            searchbox.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searching(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }catch (Exception e){}

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        this.menu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        MenuItem search = menu.findItem(R.id.search);
        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            if(search.getTitle().equals("Search")){
                search.setTitle("cancel search");
                searchbox.setVisibility(View.VISIBLE);
            }else{
                search.setTitle("Search");
                searchbox.setVisibility(View.GONE);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public void for_attachment(){
        String[] mimeTypes =
                {"image/*","application/pdf","application/msword","application/vnd.ms-powerpoint","application/vnd.ms-excel","text/plain"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), 0);
    }
}
