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

    public Api(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
                .create();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String url = NetworkReachability.getInstance().isLocal() && !settings.getString("pref_local_url", "").isEmpty() ? settings.getString("pref_local_url", "") + ':' + settings.getString("pref_local_port", "") : settings.getString("pref_remote_url", "") + ':' + settings.getString("pref_remote_port", "");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("http://" + url)
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
}
