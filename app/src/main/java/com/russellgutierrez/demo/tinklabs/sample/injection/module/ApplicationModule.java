package com.russellgutierrez.demo.tinklabs.sample.injection.module;

import android.app.Application;
import android.content.Context;

import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.remote.ImageSearchEndpoint;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.remote.ImageSearchManager;
import com.russellgutierrez.demo.tinklabs.sample.data.remote.ImageSearch;
import com.russellgutierrez.demo.tinklabs.sample.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    ImageSearchEndpoint provideImageSearchEndpoint() {
        return ImageSearchEndpoint.Creator.newImageSearchEndpoint();
    }

    @Provides
    @Singleton
    ImageSearch provideImageSearch(ImageSearchEndpoint imageSearchEndpoint, @ApplicationContext Context context) {
        return new ImageSearchManager(imageSearchEndpoint, context);
    }

}
