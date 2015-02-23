package com.alexanza.domowear.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.alexanza.common.api.model.Switch;
import com.alexanza.common.utils.NetworkReachability;
import com.alexanza.domowear.App;
import com.alexanza.domowear.R;

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

        App.getApi().updateUrl(getActivity());

        App.getApi().getSwitchesService().listSwitches(new Callback<List<Switch>>() {
            @Override
            public void success(List list, Response response) {
                switchAdapter.refresh(list);
            }

            @Override
            public void failure(RetrofitError error) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(getString(R.string.error_list_message));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }
}
