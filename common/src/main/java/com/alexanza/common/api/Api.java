package com.alexanza.common.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alexanza.common.api.service.SwitchesService;
import com.alexanza.common.api.service.ToggleService;
import com.alexanza.common.utils.NetworkReachability;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by alex on 1/02/15.
 */
public class Api {
    private SwitchesService switchesService;
    private ToggleService toggleService;
    private DynamicEndPoint endPoint;

    public Api(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        updateUrl(context);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.NONE)
                .setEndpoint(endPoint)
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RemoteRequestInterceptor(context))
                .build();

        switchesService = restAdapter.create(SwitchesService.class);
        toggleService = restAdapter.create(ToggleService.class);
    }

    public SwitchesService getSwitchesService() {
        return switchesService;
    }

    public ToggleService getToggleService() {
        return toggleService;
    }

    private void updateUrl(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String url = NetworkReachability.getInstance().isLocal(context) && !settings.getString("pref_local_url", "").isEmpty() ? settings.getString("pref_local_url", "") + ':' + settings.getString("pref_local_port", "") : settings.getString("pref_remote_url", "") + ':' + settings.getString("pref_remote_port", "");
        String protocol = "http";

        if ((url.equals(settings.getString("pref_local_url", "")) && settings.getBoolean("pref_local_secure", false))
                || (url.equals(settings.getString("pref_remote_url", "")) && settings.getBoolean("pref_remote_secure", false))) {
            protocol = "https";
        }

        endPoint = new DynamicEndPoint();
        endPoint.setUrl(protocol + "://" + url);
    }
}
