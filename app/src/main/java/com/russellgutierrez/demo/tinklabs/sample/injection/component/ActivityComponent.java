package com.russellgutierrez.demo.tinklabs.sample.injection.component;

import android.content.Context;

import com.russellgutierrez.demo.tinklabs.sample.injection.ActivityContext;
import com.russellgutierrez.demo.tinklabs.sample.injection.PerActivity;
import com.russellgutierrez.demo.tinklabs.sample.injection.module.ActivityModule;
import com.russellgutierrez.demo.tinklabs.sample.ui.main.ListFragment;
import com.russellgutierrez.demo.tinklabs.sample.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(ListFragment listFragment);

    @ActivityContext Context context();

}
