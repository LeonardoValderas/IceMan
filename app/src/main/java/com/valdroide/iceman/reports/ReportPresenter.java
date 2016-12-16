package com.valdroide.iceman.reports;

import com.valdroide.iceman.reports.events.ReportEvent;
import com.valdroide.iceman.reports.iu.Report;

/**
 * Created by LEO on 3/12/2016.
 */
public interface ReportPresenter {
    void onCreate();
    void onDestroy();
    void getListSale(String since, String until, int id, boolean isAll);
    void onEventMainThread(ReportEvent event);
    void getClient();

}
