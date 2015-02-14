package com.alexanza.domowear;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alexanza.common.api.Api;
import com.alexanza.common.utils.NetworkReachability;

/**
 * Created by alex on 1/02/15.
 */
public class App extends Application {
    private static Api api;

    @Override
    public void onCreate()
    {
        super.onCreate();

        App.setApi(this);
    }

    public static Api getApi()
    {
        return api;
    }

    public static void setApi(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String url = NetworkReachability.getInstance().isLocal(context) ? settings.getString("pref_local_url", "") : settings.getString("pref_remote_url", "");
        if (!url.isEmpty()) {
            api = new Api(context);
        }
    }
}
