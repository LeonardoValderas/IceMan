package com.valdroide.iceman.main_activity;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.entities.IceSale;
import com.valdroide.iceman.lib.base.EventBus;
import com.valdroide.iceman.main_activity.events.ActivityEvent;
import com.valdroide.iceman.main_activity.ui.ActivityView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by LEO on 1/12/2016.
 */
public class MainPresenterImpl implements MainPresenter {
    EventBus eventBus;
    ActivityView view;
    MainInteractor interactor;

    public MainPresenterImpl(EventBus eventBus, ActivityView view, MainInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.view = null;
        eventBus.unregister(this);
    }

    @Override
    public void saveSale(IceSale iceSale) {
        interactor.saveSale(iceSale);
    }


    @Override
    @Subscribe
    public void onEventMainThread(ActivityEvent event) {
        if (this.view != null) {
            if (event.getType() == 0) {//getClients
                view.setClients(event.getClientList());
            } else if (event.getType() == 1 || event.getType() == 3) {//Complete
                view.onAddComplete(event.getComplete());
            } else {//error
                String error = event.getError();
                String empty = event.getComplete();
                if (empty != null) {
                    view.onAddError(empty);
                } else {
                    view.onAddError(error);
                }
            }
        }
    }

    @Override
    public void getClient() {
        interactor.getClients();
    }

    @Override
    public void saveClient(String client) {
        interactor.saveClient(client);
    }
}
