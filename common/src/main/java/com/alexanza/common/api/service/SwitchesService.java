package com.alexanza.common.api.service;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by alex on 1/02/15.
 */
public interface SwitchesService {
    @GET("/json.htm?type=command&param=getlightswitches")
    public void listSwitches(Callback<List> callback);
}
