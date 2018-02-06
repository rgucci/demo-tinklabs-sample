package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import com.russellgutierrez.demo.tinklabs.sample.data.DataManager;
import com.russellgutierrez.demo.tinklabs.sample.data.local.BrowserHelper;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;
import com.russellgutierrez.demo.tinklabs.sample.ui.base.BasePresenter;
import com.russellgutierrez.demo.tinklabs.sample.util.RxUtil;

public class ListPresenter extends BasePresenter<ListMvpView> {

    private final DataManager mDataManager;
    private BrowserHelper mBrowserHelper;
    private Disposable mDisposable;
    private List<Item> mItems = new ArrayList<>();
    private String mQuery;
    private boolean mSearchStarted = false;

    @Inject
    public ListPresenter(DataManager dataManager, BrowserHelper browserHelper) {
        mDataManager = dataManager;
        mBrowserHelper = browserHelper;
    }

    @Override
    public void attachView(ListMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public void loadItems() {
        checkViewAttached();
        RxUtil.dispose(mDisposable);

        mItems.clear();
        loadItems(mQuery, 1);
        mSearchStarted = true;
    }

    public void loadMoreItems() {
        RxUtil.dispose(mDisposable);
        loadItems(mQuery, mItems.size() + 1);
    }

    private void loadItems(String query, int startIndex) {
        mDataManager.search(query, startIndex)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SearchItems>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(SearchItems searchItems) {
                        final List<Item> items = searchItems.items();
                        if (items.isEmpty() && mItems.isEmpty()) {
                            getMvpView().showItemsEmpty();
                        }
                        if (!items.isEmpty()) {
                            mItems.addAll(items);
                            getMvpView().showItems(mItems, items.size());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "There was an error loading the results.");
                        getMvpView().showError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public boolean isSearchStarted() {
        return mSearchStarted;
    }

    public void browseItem(int position) {
        browseItem(mItems.get(position));
    }

    private void browseItem(Item item) {
        mBrowserHelper.openInBrowser(item.linkUrl());
    }
}
