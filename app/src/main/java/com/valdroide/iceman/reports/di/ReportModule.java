package com.valdroide.iceman.reports.di;

import android.app.Activity;
import android.content.Context;

import com.valdroide.iceman.entities.IceSale;
import com.valdroide.iceman.lib.base.EventBus;
import com.valdroide.iceman.reports.ReportInteractor;
import com.valdroide.iceman.reports.ReportInteractorImpl;
import com.valdroide.iceman.reports.ReportPresenter;
import com.valdroide.iceman.reports.ReportPresenterImpl;
import com.valdroide.iceman.reports.ReportRepository;
import com.valdroide.iceman.reports.ReportRepositoryImpl;
import com.valdroide.iceman.reports.adapters.AdapterReports;
import com.valdroide.iceman.reports.iu.Report;
import com.valdroide.iceman.reports.iu.ReportView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 3/12/2016.
 */
@Module
public class ReportModule {
    ReportView view;
    Context context;
    Activity activity;

    public ReportModule(ReportView view, Context context, Activity activity) {
        this.view = view;
        this.context = context;
        this.activity = activity;
    }

    @Provides
    @Singleton
    ReportView providesReportView(){
        return  view;
    }

    @Provides
    @Singleton
    ReportPresenter providesReportPresenter(ReportInteractor interactor, ReportView view, EventBus eventBus){
        return new ReportPresenterImpl(interactor, view, eventBus);
    }
    @Provides
    @Singleton
    ReportInteractor providesReportInteractor(ReportRepository repository){
        return new ReportInteractorImpl(repository);
    }
    @Provides
    @Singleton
    ReportRepository providesReportRepository(EventBus eventBus,Context context){
        return new ReportRepositoryImpl(eventBus, context);
    }

    @Provides
    @Singleton
    AdapterReports providesAdapterReports(List<IceSale> checkList, Context context) {
        return new AdapterReports(checkList, context);
    }
    @Provides
    @Singleton
    List<IceSale> providesCheckList() {
        return new ArrayList<IceSale>();
    }
}
