package com.textilechat.ingenious.textilechat.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.Calendar;

public class Daily_service_class extends Service {
    final String id = Prefs.getString("user_id", "0");
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                Toast.makeText(Daily_service_class.this, "time changed", Toast.LENGTH_LONG).show();
//                handler.postDelayed(this, 86400000); //now is every 2 minutes  86400000 48 hours
//            }
//        }, 86400000); //Every 120000 ms (2 minutes)


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 25);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);




        return START_STICKY;
    }



    @Override
    public void onDestroy() {
        //super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


}
