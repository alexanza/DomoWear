package com.alexanza.domowear;

import com.alexanza.common.api.model.Switch;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.mariux.teleport.lib.TeleportService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by alex on 18/05/15.
 */
public class WearService extends TeleportService {
    @Override
    public void onCreate() {
        super.onCreate();
        setOnGetMessageTask(new DispatchTask());
    }

    //Task that shows the path of a received message
    public class DispatchTask extends TeleportService.OnGetMessageTask {
        @Override
        protected void onPostExecute(String  path) {
            if (path.equals("getSwitchesList")){
                App.getApi().getSwitchesService().listSwitches(new Callback<List<Switch>>() {
                    @Override
                    public void success(List<Switch> list, Response response) {
                        PutDataMapRequest dataMapRequest = PutDataMapRequest.create("getSwitchesList");

                        ArrayList<DataMap> switchesList = new ArrayList<DataMap>();
                        for(int i=0; i < list.size(); i++) {
                            switchesList.add(list.get(i).putToDataMap(new DataMap()));
                        }
                        dataMapRequest.getDataMap().putDataMapArrayList("switchesList", switchesList);

                        syncDataItem(dataMapRequest);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        syncString("failure", getString(R.string.error_list_message));
                    }
                });
            }

            //let`s reset the task (otherwise it will be executed only once)
            setOnGetMessageTask(new DispatchTask());
        }
    }
}
