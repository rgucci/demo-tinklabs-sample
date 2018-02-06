package com.russellgutierrez.demo.tinklabs.sample.injection.component;

import android.app.Application;
import android.content.Context;

import com.russellgutierrez.demo.tinklabs.sample.data.DataManager;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.remote.ImageSearchEndpoint;
import com.russellgutierrez.demo.tinklabs.sample.data.local.PreferencesHelper;
import com.russellgutierrez.demo.tinklabs.sample.injection.ApplicationContext;
import com.russellgutierrez.demo.tinklabs.sample.injection.module.ApplicationModule;
import com.russellgutierrez.demo.tinklabs.sample.util.RxEventBus;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    @ApplicationContext
    Context context();

    Application application();

    ImageSearchEndpoint imageSearchEndpoint();

    PreferencesHelper preferencesHelper();

    DataManager dataManager();

    RxEventBus eventBus();

}
