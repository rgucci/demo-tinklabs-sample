package com.russellgutierrez.demo.tinklabs.sample.ui.main;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

/**
 * Need to make refreshView public to make it usable in Rx Subscriptions
 */
public abstract class MyInfiniteScrollListener extends InfiniteScrollListener {
    /**
     * Initializes InfiniteScrollListener, which can be added
     * to RecyclerView with addOnScrollListener method
     *
     * @param maxItemsPerRequest Max items to be loaded in a single request.
     * @param layoutManager      LinearLayoutManager created in the Activity.
     */
    public MyInfiniteScrollListener(int maxItemsPerRequest, LinearLayoutManager layoutManager) {
        super(maxItemsPerRequest, layoutManager);
    }

    @Override
    public void refreshView(RecyclerView view, RecyclerView.Adapter adapter, int position) {
        super.refreshView(view, adapter, position);
    }
}
