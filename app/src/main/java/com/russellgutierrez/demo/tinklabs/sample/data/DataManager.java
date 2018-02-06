package com.russellgutierrez.demo.tinklabs.sample.data;

import com.russellgutierrez.demo.tinklabs.sample.data.local.PreferencesHelper;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;
import com.russellgutierrez.demo.tinklabs.sample.data.remote.ImageSearch;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DataManager {

    private final PreferencesHelper mPreferencesHelper;
    private final ImageSearch mImageSearch;

    @Inject
    public DataManager(ImageSearch imageSearch,
                       PreferencesHelper preferencesHelper) {
        mImageSearch = imageSearch;
        mPreferencesHelper = preferencesHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<SearchItems> search(String query, int startIndex) {
        return mImageSearch.search(query, startIndex);
    }

}
