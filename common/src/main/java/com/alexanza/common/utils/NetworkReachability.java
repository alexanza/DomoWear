package com.alexanza.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

public class NetworkReachability extends BroadcastReceiver{
    private static NetworkReachability instance;
    private boolean isWifiConnected;
    private boolean isMobileConnected;
    private String wifiSSID;
    private boolean isLocal;

    private NetworkReachability(){}

    public static NetworkReachability getInstance(){
        if(instance == null){
            instance = new NetworkReachability();
        }

        return instance;
    }

    public boolean isNetworkAvailable(Context context) {
        checkNetworkStatus(context);
        return isWifiConnected || isMobileConnected;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        checkNetworkStatus(context);
    }

    private void checkNetworkStatus(Context context){
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);

        if (networkInfo != null && networkInfo.isConnected()) {
            isWifiConnected = networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
            isMobileConnected = networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;

            if (isWifiConnected) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifiManager.getConnectionInfo ();
                wifiSSID = info.getSSID();

                if (!settings.getString("pref_local_wifi", "").isEmpty()
                    && wifiSSID == settings.getString("pref_local_wifi", "")) {
                    isLocal = true;
                }
            }
        }
        else{
            isWifiConnected = false;
            isMobileConnected = false;
            wifiSSID = "";
            isLocal = false;
        }
    }

    public boolean isMobileConnected() {
        return isMobileConnected;
    }

    public boolean isWifiConnected() {
        return isWifiConnected;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public String getWifiSSID() {
        return wifiSSID;
    }
}
