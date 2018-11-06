package com.textilechat.ingenious.textilechat.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootBroadcastReceiver  extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "yes i did it....", Toast.LENGTH_LONG).show();
    }
}