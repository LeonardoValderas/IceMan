package com.valdroide.iceman.reports.di;

import com.valdroide.iceman.IceManAppModule;
import com.valdroide.iceman.lib.di.LibsModule;
import com.valdroide.iceman.reports.iu.Report;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LEO on 3/12/2016.
 */
@Singleton
@Component(modules = {ReportModule.class, LibsModule.class, IceManAppModule.class})
public interface ReportComponent {
    void inject(Report activity);
}
