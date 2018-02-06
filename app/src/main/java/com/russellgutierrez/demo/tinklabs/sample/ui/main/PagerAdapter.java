package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.russellgutierrez.demo.tinklabs.sample.data.model.Category;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private final Category[] mCategories;

    public PagerAdapter(FragmentManager fm, Category[] categories) {
        super(fm);
        mCategories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(mCategories[position]);
    }

    @Override
    public int getCount() {
        return mCategories.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategories[position].name();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

}
