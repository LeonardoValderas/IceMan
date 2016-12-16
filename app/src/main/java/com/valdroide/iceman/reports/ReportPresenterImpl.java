package com.valdroide.iceman.reports;

import com.valdroide.iceman.lib.base.EventBus;
import com.valdroide.iceman.reports.events.ReportEvent;
import com.valdroide.iceman.reports.iu.ReportView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by LEO on 3/12/2016.
 */
public class ReportPresenterImpl implements ReportPresenter {
    ReportInteractor interactor;
    ReportView view;
    EventBus eventBus;

    public ReportPresenterImpl(ReportInteractor interactor, ReportView view, EventBus eventBus) {
        this.interactor = interactor;
        this.view = view;
        this.eventBus = eventBus;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
    }

    @Override
    public void getListSale(String since, String until, int id, boolean isAll) {
       interactor.getListSale(since, until, id, isAll);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ReportEvent event) {
        if (this.view != null) {
            if (event.getType() == 0) {//getList
                view.setListSale(event.getSaleList());
            } else if (event.getType() == 1) {//Error
                String error = event.getError();
                view.error(error);
            } else if (event.getType() == 2) {//empty
                String empty = event.getEmpty();
                view.listEmpty(empty);
            } else {
                view.setClients(event.getClientList());
            }
        }
    }
    @Override
    public void getClient() {
        interactor.getClients();
    }

}
