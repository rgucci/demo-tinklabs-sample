package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import com.russellgutierrez.demo.tinklabs.sample.data.model.Category;
import com.russellgutierrez.demo.tinklabs.sample.injection.ConfigPersistent;
import com.russellgutierrez.demo.tinklabs.sample.ui.base.BasePresenter;

import javax.inject.Inject;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private static Category[] mCategories = {Category.TRAVEL, Category.FOOD, Category.SHOPPING};

    @Inject
    public MainPresenter() {
    }

    public void loadPage(int position) {
        getMvpView().showTab(position);
    }

    public static Category[] getCategories() {
        return mCategories;
    }
}
