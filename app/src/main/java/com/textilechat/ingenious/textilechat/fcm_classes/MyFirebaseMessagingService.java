package com.textilechat.ingenious.textilechat.fcm_classes;

import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import es.dmoral.toasty.Toasty;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Toasty.info(getApplicationContext(),"Test", Toast.LENGTH_LONG).show();
    }
}
