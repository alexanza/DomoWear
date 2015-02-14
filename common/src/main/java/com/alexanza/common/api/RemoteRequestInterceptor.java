package com.alexanza.common.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.alexanza.common.utils.NetworkReachability;

import retrofit.RequestInterceptor;

/**
 * Created by alex on 1/02/15.
 */
public class RemoteRequestInterceptor implements RequestInterceptor {
    private Context context;

    public RemoteRequestInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public void intercept(RequestFacade request)
    {
        String username =  "";
        String password = "";
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        if (NetworkReachability.getInstance().isLocal(context)) {
            username = settings.getString("pref_local_username", "");
            password = settings.getString("pref_local_password", "");
        } else {
            username = settings.getString("pref_remote_username", "");
            password = settings.getString("pref_remote_password", "");
        }

        if (!username.isEmpty()) {
            String credentials = username + ":" + password;
            request.addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP));
        }
    }
}
