package com.valdroide.iceman;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 1/12/2016.
 */
@Module
public class IceManAppModule {
    Application application;
    public IceManAppModule(Application application) {
        this.application = application;
    }
    @Provides
    @Singleton
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }
}
