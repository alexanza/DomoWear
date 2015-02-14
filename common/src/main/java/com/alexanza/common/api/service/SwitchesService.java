package com.alexanza.common.api.service;

import com.alexanza.common.api.model.Switch;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by alex on 1/02/15.
 */
public interface SwitchesService {
    @GET("/json.htm?type=devices&used=true")
    public void listSwitches(Callback<List<Switch>> callback);
}
