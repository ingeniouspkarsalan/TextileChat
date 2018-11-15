package com.textilechat.ingenious.textilechat.fcm_classes;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.pixplicity.easyprefs.library.Prefs;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.activities.Chat_Activity;
import com.textilechat.ingenious.textilechat.activities.Home;
import com.textilechat.ingenious.textilechat.activities.Singal_User_Chat;
import com.textilechat.ingenious.textilechat.activities.User_profile;
import com.textilechat.ingenious.textilechat.classes.Single_user_msg_list;
import com.textilechat.ingenious.textilechat.classes.Sqlite_for_markers;
import com.textilechat.ingenious.textilechat.classes.serialize_msg_class;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    final String id = Prefs.getString("user_id", "0");
    private Sqlite_for_markers sqlite_for_markers=new Sqlite_for_markers(this);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }


        if(remoteMessage.getData().get("title").equals("qwerty")) {

            String massege = remoteMessage.getData().get("massege"),
                    u_id = remoteMessage.getData().get("u_id"),
                    u_name = remoteMessage.getData().get("u_name"),
                    created_at = remoteMessage.getData().get("created_at"),
                    id_name = remoteMessage.getData().get("id_name"),
                    c_id = remoteMessage.getData().get("c_id"),
                    sc_id = remoteMessage.getData().get("sc_id");

            serialize_msg_class sendclass = new serialize_msg_class(massege, u_id, u_name, created_at, id_name, c_id, sc_id);
            Intent pushNotification = new Intent("chat");
            pushNotification.putExtra("msg", sendclass);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);


            if(!id.equals(u_id)){
            if (!isAppIsInBackground(this)) {

                if(sendclass.getSc_id().equals("0")){

                    showNotificationsilentforchat(massege, sendclass.getC_id(),"category",sendclass.getC_id(),"0");

                }else{

                    showNotificationsilentforchat(massege, sendclass.getC_id()+"->"+sendclass.getSc_id(),"sub_category",sendclass.getC_id(),sendclass.getSc_id());

                }

            } else {

                if(sendclass.getSc_id().equals("0")){

                    showNotificationforchat(massege, sendclass.getC_id(),"category",sendclass.getC_id(),"0");

                }else{

                    showNotificationforchat(massege, sendclass.getC_id()+"->"+sendclass.getSc_id(),"sub_category",sendclass.getC_id(),sendclass.getSc_id());

                }

            }
        }


        }
        //for single user chat
        else if(remoteMessage.getData().get("title").equals("single_chat")){

                    String message=remoteMessage.getData().get("massege"),
                            from_user_id=remoteMessage.getData().get("from_id"),
                            to_user_id=remoteMessage.getData().get("to_id"),
                            time_stamp=remoteMessage.getData().get("created_at");

            Single_user_msg_list s_u_m_l=new Single_user_msg_list(from_user_id,message,to_user_id,time_stamp);
            Intent pushNotification = new Intent("single_chat");
            pushNotification.putExtra("single_msg", s_u_m_l);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            if(to_user_id.equals(id)){
                if (!isAppIsInBackground(this)) {
                    showNotificationsilentforsinglechat(message,from_user_id);
                }else {
                    showNotificationforsinglechat(message,from_user_id);
                    try {
                        sqlite_for_markers.insert_per_marks(from_user_id);
                    }catch (Exception e){}
                }
            }


        }
        //for approve msg and sent to all users
        else if(remoteMessage.getData().get("title").equals("approve_from_admin")){
            String massege = remoteMessage.getData().get("massege"),
                    u_id = remoteMessage.getData().get("u_id"),
                    u_name = remoteMessage.getData().get("u_name"),
                    created_at = remoteMessage.getData().get("created_at"),
                    msg_id = remoteMessage.getData().get("msg_id"),
                    c_id = remoteMessage.getData().get("c_id"),
                    sc_id = remoteMessage.getData().get("sc_id"),
                    c_name = remoteMessage.getData().get("c_name"),
                    sc_name = remoteMessage.getData().get("sc_name");

            if(!id.equals(u_id)){
                try {
                    Log.e("yesyes",c_id+" "+sc_id);
                    sqlite_for_markers.insert_marks(Integer.parseInt(msg_id),c_id,sc_id);
                }catch (Exception e){}
            }

            if(!id.equals(u_id)){
                if (!isAppIsInBackground(this)) {

                    if(sc_id.equals("0")){

                        showNotificationsilentforchat(massege, c_name,"category",c_id,"0");

                    }else{

                        showNotificationsilentforchat(massege, c_name+"->"+sc_name,"sub_category",c_id,sc_id);

                    }

                } else {

                    if(sc_id.equals("0")){

                        showNotificationforchat(massege, c_name,"category",c_id,"0");

                    }else{

                        showNotificationforchat(massege, c_name+"->"+sc_name,"sub_category",c_id,sc_id);

                    }

                }
            }else{
                if (!isAppIsInBackground(this)) {

                    if(sc_id.equals("0")){

                        showNotificationsilentforchat(massege, "Your message is approve now in "+c_name,"category",c_id,"0");

                    }else{

                        showNotificationsilentforchat(massege, "Your message is approve now in "+c_name+"->"+sc_name,"sub_category",c_id,sc_id);

                    }

                } else {

                    if(sc_id.equals("0")){

                        showNotificationforchat(massege, "Your message is approve now in "+c_name,"category",c_id,"0");

                    }else{

                        showNotificationforchat(massege, "Your message is approve now in "+c_name+"->"+sc_name,"sub_category",c_id,sc_id);

                    }

                }



            }



        }

        else {
            if(remoteMessage.getData().get("image").isEmpty()){
                showNotificationwithoutimage(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
            }else {
                showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), remoteMessage.getData().get("image"));
            }
        }

    }



    @Override
    public void onNewToken(String token) {
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);
    }


    private void scheduleJob() {
        // [START dispatch_job]
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
        // [END dispatch_job]
    }


    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }


    //for silent chat notification
    private void showNotificationsilentforchat(String title,String message,String id_name,String c_id,String sc_id) {

    Intent i = new Intent(this,Chat_Activity.class);
    i.putExtra("id_name",id_name);
    i.putExtra("c_id",c_id);
    i.putExtra("s_id",sc_id);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
            .setAutoCancel(true)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentIntent(pendingIntent);

    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("");
            manager.createNotificationChannel(channel);
        }


    manager.notify(1,builder.build());
}


    //for  chat notification with sound
    private void showNotificationforchat(String title,String message,String id_name,String c_id,String sc_id) {

        Intent i = new Intent(this,Chat_Activity.class);
        i.putExtra("id_name",id_name);
        i.putExtra("c_id",c_id);
        i.putExtra("s_id",sc_id);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000, 1000})
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1,builder.build());
    }



        //this for normal notification withoutimage
    private void showNotificationwithoutimage(String title,String message) {

        Intent i = new Intent(this,Home.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000, 1000})
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1,builder.build());
    }


    //this for normal notification withimage
    private void showNotification(String title,String message,String url) {

        Intent i = new Intent(this,Home.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
                .setVibrate(new long[] { 1000, 1000})
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1,builder.build());
    }

    //for showing image in normal notification
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }




    //for silent chat notification single
    private void showNotificationsilentforsinglechat(String message,String id) {

        Intent i = new Intent(this,User_profile.class);
        i.putExtra("other_user_id",id);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("You have new message")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1,builder.build());
    }


    //for  chat notification with sound single
    private void showNotificationforsinglechat(String message,String id) {

        Intent i = new Intent(this,User_profile.class);
        i.putExtra("other_user_id",id);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("You have new message")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setSound(alarmSound)
                .setVibrate(new long[] { 1000, 1000})
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(1,builder.build());
    }



    //checking method app is alive or not
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }
}


