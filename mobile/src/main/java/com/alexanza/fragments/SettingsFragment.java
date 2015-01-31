package com.alexanza.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.alexanza.domowear.R;

import com.alexanza.utils.NetworkReachability;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (settings.getString("pref_local_wifi", "").isEmpty()
                && NetworkReachability.getInstance().isNetworkAvailable(getActivity())
                && NetworkReachability.getInstance().isWifiConnected()) {
            EditTextPreference localWifi = (EditTextPreference) super.findPreference("pref_local_wifi");
            localWifi.setText(NetworkReachability.getInstance().getWifiSSID());
        }
    }
}