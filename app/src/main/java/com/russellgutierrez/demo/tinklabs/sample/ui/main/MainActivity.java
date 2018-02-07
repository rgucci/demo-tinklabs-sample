package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.russellgutierrez.demo.tinklabs.sample.R;
import com.russellgutierrez.demo.tinklabs.sample.ui.base.BaseActivity;
import com.russellgutierrez.demo.tinklabs.sample.util.DialogFactory;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Inject
    MainPresenter mMainPresenter;

    @BindView(R.id.pager)
    ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupViewPager();

        mMainPresenter.attachView(this);
        mViewPager.setCurrentItem(0);
    }

    private void setupViewPager() {
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), MainPresenter.getCategories());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mMainPresenter.loadPage(position);
            }
        });
    }

    @Override
    public void showTab(int position) {
        ListFragment page = ((ListFragment) mViewPager.getAdapter().instantiateItem(mViewPager, mViewPager.getCurrentItem()));
        ListPresenter presenter = page.getPresenter();
        if (!presenter.isSearchStarted()) {
            presenter.loadItems();
        }
    }

    public void showError() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_results))
                .show();
    }

}
