package com.example.dhktpm15a_nhom06_todoapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.example.dhktpm15a_nhom06_todoapp.activity.HomeActivity;

public class NetWorkUtil {

    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if( (wifiConn != null && wifiConn.isConnected() )  || (mobileConn != null && mobileConn.isConnected()) ){
            return true;
        }

        return false;
    }


}
