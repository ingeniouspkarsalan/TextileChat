package com.textilechat.ingenious.textilechat.classes;

import android.app.Application;
import android.content.ContextWrapper;

import com.google.firebase.messaging.FirebaseMessaging;
import com.pixplicity.easyprefs.library.Prefs;

public class Main_class extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }
}
