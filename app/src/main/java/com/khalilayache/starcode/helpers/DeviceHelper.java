package com.khalilayache.starcode.helpers;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author USUARIO
 * @since 22/01/2017.
 */

public class DeviceHelper {

    public static boolean checkInternetConnection(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();

    }

    public static boolean checkDeviceHasCamera(Context context){
        PackageManager pm = context.getPackageManager();
        if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return  false;
    }
}
