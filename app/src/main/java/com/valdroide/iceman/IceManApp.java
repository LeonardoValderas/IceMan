package com.valdroide.iceman;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.valdroide.iceman.lib.di.LibsModule;
import com.valdroide.iceman.main_activity.di.DaggerMainComponent;
import com.valdroide.iceman.main_activity.di.MainComponent;
import com.valdroide.iceman.main_activity.di.MainModule;
import com.valdroide.iceman.main_activity.ui.ActivityView;
import com.valdroide.iceman.reports.di.DaggerReportComponent;
import com.valdroide.iceman.reports.di.ReportComponent;
import com.valdroide.iceman.reports.di.ReportModule;
import com.valdroide.iceman.reports.iu.Report;
import com.valdroide.iceman.reports.iu.ReportView;

/**
 * Created by LEO on 1/12/2016.
 */
public class IceManApp extends Application {
    private LibsModule libsModule;
    private IceManAppModule iceManAppModule;
    @Override
    public void onCreate() {
        super.onCreate();
//        initDB();
        initModules();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        //      DBTearDown();
    }

    private void initModules() {
        libsModule = new LibsModule();
        iceManAppModule = new IceManAppModule(this);
    }

    public MainComponent getMainComponent(ActivityView view, Context context, Activity activity){
        return DaggerMainComponent
                .builder()
                .iceManAppModule(iceManAppModule)
                .libsModule(new LibsModule(activity))
                .mainModule(new MainModule(view,context, activity))
                .build();
    }

    public ReportComponent getReportComponent(ReportView view, Context context, Activity activity){
        return DaggerReportComponent
                .builder()
                .iceManAppModule(iceManAppModule)
                .libsModule(new LibsModule(activity))
                .reportModule(new ReportModule(view,context, activity))
                .build();
    }

}
