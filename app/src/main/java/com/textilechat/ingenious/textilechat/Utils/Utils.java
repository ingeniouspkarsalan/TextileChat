package com.textilechat.ingenious.textilechat.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import java.io.UnsupportedEncodingException;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Utils
{
    //Will return instance on ProgressDialog to show and hide
    public static SweetAlertDialog showProgress(Context ctx)
    {
        SweetAlertDialog pDialog = new SweetAlertDialog(ctx, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Connecting to Server...");
        pDialog.setCancelable(false);
        return pDialog;
    }

    public static boolean isOnline(Context ctx) {

        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static String getResponse(byte[] responseBody) {

        String str;
        String jresponse = null;
        try {
            if(responseBody == null) {

                return "null";
            }

            str = new String(responseBody,"UTF-8");
            jresponse = str.replaceAll("\\\\", "");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (NullPointerException ex) {

            // Toast.makeText(context, "Internet connection is required", Toast.LENGTH_SHORT).show();
        }

        return jresponse;

    }
}
