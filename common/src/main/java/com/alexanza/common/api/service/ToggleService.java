package com.alexanza.common.api.service;

import com.alexanza.common.api.model.Response;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alex on 1/02/15.
 */
public interface ToggleService {
    @GET("/json.htm?type=command&param=switchlight&level=0")
    public void toggle(@Query("idx") String idx, @Query("switchcmd") String switchcmd, Callback<Response> callback);
}
