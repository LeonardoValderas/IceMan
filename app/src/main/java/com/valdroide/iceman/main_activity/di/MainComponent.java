package com.valdroide.iceman.main_activity.di;

import com.valdroide.iceman.IceManAppModule;
import com.valdroide.iceman.lib.di.LibsModule;
import com.valdroide.iceman.main_activity.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LEO on 2/12/2016.
 */
@Singleton
@Component(modules = {MainModule.class, LibsModule.class, IceManAppModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
