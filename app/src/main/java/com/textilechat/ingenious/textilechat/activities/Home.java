package com.textilechat.ingenious.textilechat.activities;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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
import com.pkmmte.view.CircularImageView;
import com.textilechat.ingenious.textilechat.Adapters.Catergory_adapter;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.Utils.Endpoints;
import com.textilechat.ingenious.textilechat.Utils.Utils;
import com.textilechat.ingenious.textilechat.classes.Animation;
import com.textilechat.ingenious.textilechat.classes.CategoryClass;
import com.textilechat.ingenious.textilechat.classes.Daily_service_class;
import com.textilechat.ingenious.textilechat.classes.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private SweetAlertDialog pd;
    private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.news_icon,
            R.drawable.blog_icon,
            R.drawable.contact_jcon,
            R.drawable.about_icon,
    };

    String username,useremail;
    private TextView usernameview,useremailview;
    CircularImageView imageView;

    private RecyclerView recyclerView;
    private List<CategoryClass> categoryClassList;
     Catergory_adapter catergory_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        username= Prefs.getString("user_name","");
        useremail=Prefs.getString("user_email","");

        //checking service class
        if(!isMyServiceRunning(Daily_service_class.class)){
            startService(new Intent(getBaseContext(), Daily_service_class.class));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        usernameview=(TextView) headerView.findViewById(R.id.tv_name);
        useremailview=(TextView) headerView.findViewById(R.id.tv_email);
        imageView = headerView.findViewById(R.id.avatar);


        if (username!=null&&useremail!=null){
            usernameview.setText(username);
            useremailview.setText(useremail);
        }


        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);


        if(Utils.isOnline(Home.this))
        {
            try
            {
                check_user_expiry();
            }
            catch (Exception ex) {
                new SweetAlertDialog(Home.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("Some thing went wrong!")
                        .show();
            }
        } else
        {
            new SweetAlertDialog(Home.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Internet Not Found!")
                    .show();
        }
        fortablayout();

    }

        //fetching all categories of subcategory
    public void requestData() {
        final String id = Prefs.getString("user_id", "0");
        StringRequest request = new StringRequest(Request.Method.POST, Endpoints.ip_server, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("null")) {
                    Toasty.warning(Home.this, "Categories not available.", Toast.LENGTH_LONG).show();
                } else {
                    categoryClassList = JSONParser.parse_category(response);
                    catergory_adapter = new Catergory_adapter(Home.this, categoryClassList);
                    recyclerView.setAdapter(catergory_adapter);
//                    recyclerView.setLayoutManager(new GridLayoutManager(Home.this,1));
                    LinearLayoutManager llm = new LinearLayoutManager(Home.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    recyclerView.setLayoutManager(llm);
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new SweetAlertDialog(Home.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Some thing went wrong!")
                                .show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_key", "all_category");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    // Declear the User package expiration Function
    private void check_user_expiry()
    {
        final String id = Prefs.getString("user_id", "0");
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("req_key","check_user_expiration");
        params.put("user_id",id);
        client.post(Endpoints.ip_server, params, new AsyncHttpResponseHandler()
        {
            @Override
            public void onStart()
            {
                super.onStart();
                pd = Utils.showProgress(Home.this);
                pd.show();
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = Utils.getResponse(responseBody);
                if(response.equals("null")) {
                    Toasty.warning(Home.this, "Response is null", Toast.LENGTH_SHORT).show();
                }else {

                    try {
                        JSONObject object  = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                        if(object.getBoolean("success"))
                        {
                            Prefs.putString("User_paid_status",object.getString("message"));
                            requestData();
                        }else {
                            Intent pac_in=new Intent(Home.this,Packge_Page.class);
                            pac_in.putExtra("msg",object.getString("message"));
                            startActivity(pac_in);
                            finish();
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
                    Toasty.warning(Home.this, "Unable to Connect Server", Toast.LENGTH_SHORT).show();

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


    //initializing tablayout
    public void fortablayout(){

        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[0]).setText("News"));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[1]).setText("Blog"));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[2]).setText("Contact"));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[3]).setText("About"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    startActivity(new Intent(Home.this,News_Activity.class));
                    Animation.slideUp(Home.this);
                }else if(tabLayout.getSelectedTabPosition() == 1){
                    Toast.makeText(Home.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }else if(tabLayout.getSelectedTabPosition() == 2){
                    Toast.makeText(Home.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }else if(tabLayout.getSelectedTabPosition() == 3){
                    Toast.makeText(Home.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tabLayout.getSelectedTabPosition() == 0){
                    startActivity(new Intent(Home.this,News_Activity.class));
                    Animation.slideUp(Home.this);
                }else if(tabLayout.getSelectedTabPosition() == 1){
                    Toast.makeText(Home.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }else if(tabLayout.getSelectedTabPosition() == 2){
                    Toast.makeText(Home.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }else if(tabLayout.getSelectedTabPosition() == 3){
                    Toast.makeText(Home.this, "Tab " + tabLayout.getSelectedTabPosition(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Search) {
            // Handle the camera action
        } else if (id == R.id.nav_News) {

        } else if (id == R.id.nav_Contact) {

        } else if (id == R.id.nav_About) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //this method checking background class is running or not
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}
