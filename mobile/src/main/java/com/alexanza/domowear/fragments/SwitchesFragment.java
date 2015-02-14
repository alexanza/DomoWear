package com.alexanza.domowear.fragments;

import android.app.ListFragment;
import android.os.Bundle;

import com.alexanza.common.api.model.Switch;
import com.alexanza.common.utils.NetworkReachability;
import com.alexanza.domowear.App;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alex on 1/02/15.
 */
public class SwitchesFragment extends ListFragment {
    public List<Switch> switches;
    private SwitchAdapter switchAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        switchAdapter = new SwitchAdapter(getActivity(), switches);
        setListAdapter(switchAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        if (!NetworkReachability.getInstance().isNetworkAvailable(getActivity()))
            return;

        App.getApi().getSwitchesService().listSwitches(new Callback<List<Switch>>() {
            @Override
            public void success(List list, Response response) {
                switchAdapter.refresh(list);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
