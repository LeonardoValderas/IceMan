package com.valdroide.iceman.lib.di;

import android.app.Activity;

import com.valdroide.iceman.lib.base.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 1/12/2016.
 */
@Module
public class LibsModule {
    private Activity activity;

    public LibsModule() {
    }
    public LibsModule(Activity activity) {
        this.activity = activity;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    @Provides
    @Singleton
    EventBus providesEventBus() {
        return new GreenRobotEventBus();
    }
    @Provides
    @Singleton
    Activity providesActivity() {
        return this.activity;
    }
}
