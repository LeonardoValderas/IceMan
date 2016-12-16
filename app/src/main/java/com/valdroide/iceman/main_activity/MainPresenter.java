package com.valdroide.iceman.main_activity;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;
import com.valdroide.iceman.main_activity.events.ActivityEvent;

import java.util.List;

/**
 * Created by LEO on 1/12/2016.
 */
public interface MainPresenter {
    void onCreate();
    void onDestroy();
    void saveSale(IceSale iceSale);
    void onEventMainThread(ActivityEvent event);
    void getClient();
    void saveClient(String client);
}
