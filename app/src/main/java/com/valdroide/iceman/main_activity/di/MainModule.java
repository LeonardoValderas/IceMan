package com.valdroide.iceman.main_activity.di;

import android.app.Activity;
import android.content.Context;

import com.valdroide.iceman.entities.Client;
import com.valdroide.iceman.lib.base.EventBus;
import com.valdroide.iceman.main_activity.MainInteractor;
import com.valdroide.iceman.main_activity.MainInteractorImpl;
import com.valdroide.iceman.main_activity.MainPresenter;
import com.valdroide.iceman.main_activity.MainPresenterImpl;
import com.valdroide.iceman.main_activity.MainRepository;
import com.valdroide.iceman.main_activity.MainRepositoryImpl;
import com.valdroide.iceman.main_activity.adapters.AdapterSpinnerClient;
import com.valdroide.iceman.main_activity.ui.ActivityView;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by LEO on 2/12/2016.
 */
@Module
public class MainModule {
    ActivityView view;
    Context context;
    Activity activity;

    public MainModule(ActivityView view, Context context, Activity activity) {
        this.view = view;
        this.context = context;
        this.activity = activity;
    }
    @Provides
    @Singleton
    ActivityView providesActivityView(){
        return  view;
    }
    @Provides
    @Singleton
    MainPresenter providesMainPresenter(EventBus eventBus, ActivityView view, MainInteractor interactor){
        return new MainPresenterImpl(eventBus, view, interactor);
    }
    @Provides
    @Singleton
    MainInteractor providesMainInteractor(MainRepository repository){
        return new MainInteractorImpl(repository);
    }
    @Provides
    @Singleton
    MainRepository providesMainRepository(EventBus eventBus,Context context){
        return new MainRepositoryImpl(eventBus, context);
    }
//    @Provides
//    @Singleton
//    AdapterSpinnerClient providesAdapterSpinnerClient(List<Client>, Context context){
//        return new MainRepositoryImpl(eventBus, context);
//    }


}
