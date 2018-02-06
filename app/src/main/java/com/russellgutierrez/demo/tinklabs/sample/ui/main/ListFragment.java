package com.russellgutierrez.demo.tinklabs.sample.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.russellgutierrez.demo.tinklabs.sample.R;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Category;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.ui.base.BaseActivity;
import com.russellgutierrez.demo.tinklabs.sample.util.DialogFactory;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ListFragment extends Fragment implements ListMvpView {

    public static final String ARG_CATEGORY = "category";

    @Inject
    ListPresenter mListPresenter;

    ItemsAdapter mItemsAdapter;
    MyInfiniteScrollListener infiniteScrollListener;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ((BaseActivity) getActivity()).activityComponent().inject(this);

        final View view = inflater.inflate(R.layout.view_list, container, false);
        ButterKnife.bind(this, view);

        setupRecyclerView();

        mListPresenter.attachView(this);
        final Category category = (Category) getArguments().getSerializable(ListFragment.ARG_CATEGORY);
        mListPresenter.setQuery(category.getQuery());

        if (getUserVisibleHint()) {
            mListPresenter.loadItems();
        }

        return view;
    }

    private void setupRecyclerView() {
        mItemsAdapter = new ItemsAdapter(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mListPresenter.browseItem(position);
            }
        });
        mRecyclerView.setAdapter(mItemsAdapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        infiniteScrollListener = createInfiniteScrollListener(10, layoutManager);
        mRecyclerView.addOnScrollListener(infiniteScrollListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListPresenter.detachView();
    }

    private MyInfiniteScrollListener createInfiniteScrollListener(int maxItemsPerRequest, LinearLayoutManager layoutManager) {
        return new MyInfiniteScrollListener(maxItemsPerRequest, layoutManager) {
            @Override
            public void onScrolledToEnd(int firstVisibleItemPosition) {
                mListPresenter.loadMoreItems();
            }

        };
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showItems(List<Item> items, int newItemCount) {
        mItemsAdapter.setItems(items);
        infiniteScrollListener.refreshView(mRecyclerView, mItemsAdapter, items.size() - newItemCount);
    }

    @Override
    public void showItemsEmpty() {
        mItemsAdapter.setItems(Collections.<Item>emptyList());
        mItemsAdapter.notifyDataSetChanged();
        Toast.makeText(getActivity(), R.string.empty_results, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(getActivity(), getString(R.string.error_loading_results))
                .show();
    }

    public ListPresenter getPresenter() {
        return mListPresenter;
    }

    public static ListFragment newInstance(Category category) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ListFragment.ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;

    }
}
