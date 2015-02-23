package com.alexanza.domowear;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.alexanza.common.api.model.Switch;
import com.alexanza.common.utils.NetworkReachability;
import com.alexanza.domowear.fragments.NavigationDrawerFragment;
import com.alexanza.domowear.fragments.SettingsFragment;
import com.alexanza.domowear.fragments.SwitchesFragment;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private SwitchesFragment switchesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(NetworkReachability.getInstance(), intentFilter);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                mDrawerLayout);

        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        switchesFragment = new SwitchesFragment();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplication());
        if (settings.getString("pref_remote_url", "").isEmpty() && settings.getString("pref_local_url", "").isEmpty()) {
            goToSettings();
        } else if(settings.getString("pref_remote_url", "").isEmpty() && !NetworkReachability.getInstance().isLocal()) {
            goToSettings();
            Toast.makeText(this, R.string.toast_remote_url, Toast.LENGTH_LONG).show();
        } else if (NetworkReachability.getInstance().isNetworkAvailable(this)) {
            App.getApi().getSwitchesService().listSwitches(new Callback<List<Switch>>() {
                @Override
                public void success(List list, Response response) {
                    switchesFragment.switches = list;

                    getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, switchesFragment, "switches_fragment")
                            .addToBackStack("Switches")
                            .commit();
                }

                @Override
                public void failure(RetrofitError error) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(NetworkReachability.getInstance());
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    }

    public void onSectionAttached(int number) {
        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager().findFragmentByTag("settings_fragment");
        if (settingsFragment != null && settingsFragment.isVisible()) {
            mTitle = getString(R.string.action_settings);
            return;
        }
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_switches);
                break;
            case 2:
                mTitle = getString(R.string.title_scenes);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettings();
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToSettings() {
        // Display the fragment as the main content.
        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager().findFragmentByTag("settings_fragment");
        if (settingsFragment == null || !settingsFragment.isVisible()) {
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment(), "settings_fragment")
                    .addToBackStack("Settings")
                    .commit();

            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle(R.string.action_settings);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        SettingsFragment settingsFragment = (SettingsFragment) getFragmentManager().findFragmentByTag("settings_fragment");
        if (settingsFragment == null || !settingsFragment.isVisible()) {
            ActionBar actionBar = getActionBar();
            if (actionBar != null) {
                actionBar.setTitle(R.string.title_switches);
            }
        }
    }
}
