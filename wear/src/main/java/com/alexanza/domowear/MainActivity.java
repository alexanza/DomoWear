package com.alexanza.domowear;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import android.widget.Toast;

import com.alexanza.common.api.model.Switch;
import com.google.android.gms.wearable.DataMap;
import com.mariux.teleport.lib.TeleportClient;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TextView mTextView;
    private TeleportClient mTeleportClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        mTeleportClient = new TeleportClient(this);
        mTeleportClient.setOnSyncDataItemTask(new onSyncDataItemTask());
        mTeleportClient.sendMessage("getSwitchesList", null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTeleportClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTeleportClient.disconnect();

    }

    public class onSyncDataItemTask extends TeleportClient.OnSyncDataItemTask {
        @Override
        protected void onPostExecute(DataMap dataMap) {
            ArrayList<Switch> switches = new ArrayList<Switch>();
            for(int i=0; i < dataMap.getDataMapArrayList("switchesList").size(); i++) {
                switches.add(new Switch(dataMap.getDataMapArrayList("switchesList").get(i)));
            }

            Toast.makeText(getApplicationContext(), "Number of switches: " + switches.size(), Toast.LENGTH_SHORT).show();
        }
    }
}
