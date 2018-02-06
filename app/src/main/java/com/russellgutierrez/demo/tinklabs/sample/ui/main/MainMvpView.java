package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import com.russellgutierrez.demo.tinklabs.sample.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showTab(int position);

    void showError();
}
