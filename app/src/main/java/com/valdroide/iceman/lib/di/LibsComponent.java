package com.valdroide.iceman.lib.di;

import com.valdroide.iceman.IceManAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by LEO on 1/12/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, IceManAppModule.class})
public interface LibsComponent {
}
