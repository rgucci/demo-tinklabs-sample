package com.russellgutierrez.demo.tinklabs.sample.data.googleimage.remote;


import android.content.Context;

import com.russellgutierrez.demo.tinklabs.sample.R;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.NextPage;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.ResultItem;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.SearchResult;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.data.model.ItemType;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;
import com.russellgutierrez.demo.tinklabs.sample.data.remote.ImageSearch;
import com.russellgutierrez.demo.tinklabs.sample.injection.ApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

@Singleton
public class ImageSearchManager implements ImageSearch {
    //Using a free version of google image search, so try to behave and not use up the free quota
    //Note: some things are hard-coded by the api: page size = 10, max results = 100
    //Also, if the daily quota is used up, the API will return and HTTP error
    private String mApiKey;
    private String mContainerId;
    private String SEARCH_TYPE = "image";
    private int MAX_RESULT_COUNT = 100;

    private final ImageSearchEndpoint mImageSearchEndpoint;

    @Inject
    public ImageSearchManager(ImageSearchEndpoint imageSearchEndpoint, @ApplicationContext Context context) {
        mImageSearchEndpoint = imageSearchEndpoint;
        mApiKey = context.getString(R.string.api_key);
        mContainerId = context.getString(R.string.container_id);
    }

    @Override
    public Observable<SearchItems> search(final String query, final int start) {
        if (start > MAX_RESULT_COUNT) {
            return Observable.just(SearchItems.builder()
                    .items(Collections.<Item>emptyList())
                    .count(0)
                    .nextPageStart(1)
                    .build());
        }


        return mImageSearchEndpoint.search(mApiKey, mContainerId, SEARCH_TYPE, query, start)
                .map(new Function<SearchResult, SearchItems>() {
                    @Override
                    public SearchItems apply(SearchResult searchResult) throws Exception {
                        List<Item> list = new ArrayList<>();
                        for (ResultItem resultItem : searchResult.items()) {
                            //for demo, make it easier to see the item number by putting it in the title
                            int index = start + list.size();

                            //create a very simple way to return different item types
                            //this is ok for demo only
                            ItemType itemType = index % 10 == 3 ? ItemType.IMAGE_ONLY : ItemType.NORMAL;
                            list.add(resultItem.convert(itemType));
                        }
                        NextPage nextPage = searchResult.queries().nextPage().get(0);
                        return SearchItems.builder()
                                .items(list)
                                .count(nextPage.count())
                                .nextPageStart(nextPage.startIndex())
                                .build();
                    }
                });
    }

}
