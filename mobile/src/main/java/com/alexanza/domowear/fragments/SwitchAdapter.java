package com.alexanza.domowear.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alexanza.common.api.model.Response;
import com.alexanza.common.api.model.Switch;
import com.alexanza.domowear.App;
import com.alexanza.domowear.R;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by alex on 4/02/15.
 */
public class SwitchAdapter extends BaseAdapter {
    private final Context context;
    private List<Switch> switches;

    public SwitchAdapter(Context context, List<Switch> switches) {
        this.context = context;
        Collections.sort(switches);
        this.switches = switches;
    }

    @Override
    public int getCount() {
        return switches.size();
    }

    @Override
    public Switch getItem(int position) {
        return switches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View switchView = inflater.inflate(R.layout.fragment_switches_list_item, parent, false);
        TextView textView = (TextView) switchView.findViewById(R.id.label);
        ToggleButton toggleButton = (ToggleButton) switchView.findViewById(R.id.toggle);

        textView.setText(getItem(position).getName());
        toggleButton.setTag(getItem(position).getIdx());
        toggleButton.setChecked(getItem(position).getStatus().equals("On") ? true : false);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked) {
                String idx = (String) buttonView.getTag();
                String switchcmd = isChecked ? "On" : "Off";

                App.getApi().getToggleService().toggle(idx, switchcmd, new Callback<Response>() {
                    @Override
                    public void success(Response response, retrofit.client.Response response2) {
                        if (!response.getStatus().equals("OK"))
                            buttonView.setChecked(!isChecked);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        buttonView.setChecked(!isChecked);
                    }
                });
            }
        });


        return switchView;
    }

    public void refresh(List<Switch> switches) {
        Collections.sort(switches);
        this.switches = switches;
        notifyDataSetChanged();
    }
}
