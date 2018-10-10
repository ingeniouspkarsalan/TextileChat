package com.textilechat.ingenious.textilechat.fcm_classes;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.textilechat.ingenious.textilechat.R;
import com.textilechat.ingenious.textilechat.activities.Home;
import com.textilechat.ingenious.textilechat.classes.serialize_msg_class;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if(remoteMessage.getData().get("title").equals("qwerty")){
            String massege=remoteMessage.getData().get("massege"),
                    u_id=remoteMessage.getData().get("u_id"),
                    u_name=remoteMessage.getData().get("u_name"),
                    created_at=remoteMessage.getData().get("created_at"),
                    id_name=remoteMessage.getData().get("id_name"),
                    c_id=remoteMessage.getData().get("c_id"),
                    sc_id=remoteMessage.getData().get("sc_id");

            serialize_msg_class sendclass=new serialize_msg_class(massege,u_id,u_name,created_at,id_name,c_id,sc_id);
            Intent pushNotification = new Intent("chat");
            pushNotification.putExtra("msg", sendclass);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

             showNotificationwithoutimage(massege," "+u_id+" "+u_name+" "+created_at+" "+id_name+" "+c_id+" "+sc_id+" ");

        }else {
            if(remoteMessage.getData().get("image").isEmpty()){
                showNotificationwithoutimage(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
            }else {
                showNotification(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"), remoteMessage.getData().get("image"));
            }
        }

    }



//    @Override
//    protected void onHandleIntent(Intent intent) {
//        String key = intent.getStringExtra("");
//        switch (key) {
//            case SUBSCRIBE:
//                // subscribe to a topic
//                String topic = intent.getStringExtra(TOPIC);
//                subscribeToTopic(topic);
//                break;
//            case UNSUBSCRIBE:
//                break;
//            default:
//                // if key is specified, register with GCM
//                registerGCM();
//        }
//
//    }


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

        manager.notify(0,builder.build());
    }

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

        manager.notify(0,builder.build());
    }

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

}


