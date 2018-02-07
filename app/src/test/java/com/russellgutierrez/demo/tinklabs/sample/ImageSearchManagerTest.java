package com.russellgutierrez.demo.tinklabs.sample;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.SearchResult;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.remote.ImageSearchEndpoint;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.remote.ImageSearchManager;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;
import com.russellgutierrez.demo.tinklabs.sample.util.RxSchedulersOverrideRule;
import com.russellgutierrez.demo.tinklabs.sample.test.common.SearchResultGenerator;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static com.russellgutierrez.demo.tinklabs.sample.test.common.SearchResultGenerator.generateSearchItems;

@RunWith(MockitoJUnitRunner.class)
public class ImageSearchManagerTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Mock
    ImageSearchEndpoint mImageSearchEndpoint;
    @Mock
    Context mContext;

    private ImageSearchManager mImageSearchManager;

    @Before
    public void setUp() {
        when(mContext.getString(Mockito.anyInt())).thenReturn("");
        mImageSearchManager = new ImageSearchManager(mImageSearchEndpoint, mContext);
    }

    @Test
    public void resultIsOneSearchItem() {
        final String query = "dummy query, can be anything";
        final int start = 1;

        SearchResult searchResult = SearchResultGenerator.generateSearchResults(1, 1);
        when(mImageSearchEndpoint.search(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                eq(query), eq(start)))
                .thenReturn(Observable.just(searchResult));

        SearchItems searchItems = SearchResultGenerator.generateSearchItems(searchResult);
        TestObserver<SearchItems> result = new TestObserver<>();
        mImageSearchManager.search(query, start).subscribe(result);
        result.assertNoErrors();
        result.assertValue(searchItems);

    }

    @Test
    public void resultIsTenSearchItems() {
        final String query = "dummy query, can be anything";
        final int start = 1;

        SearchResult searchResult = SearchResultGenerator.generateSearchResults(10, 1);
        when(mImageSearchEndpoint.search(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                eq(query), eq(start)))
                .thenReturn(Observable.just(searchResult));

        SearchItems searchItems = SearchResultGenerator.generateSearchItems(searchResult);
        TestObserver<SearchItems> result = new TestObserver<>();
        mImageSearchManager.search(query, start).subscribe(result);
        result.assertNoErrors();
        result.assertValue(searchItems);

    }

    @Test
    public void resultIsZeroSearchTerms() {
        final String query = "dummy query, can be anything";
        final int start = 1;

        SearchResult searchResult = SearchResultGenerator.generateSearchResults(0, 1);
        when(mImageSearchEndpoint.search(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                eq(query), eq(start)))
                .thenReturn(Observable.just(searchResult));

        SearchItems searchItems = SearchResultGenerator.generateSearchItems(searchResult);
        TestObserver<SearchItems> result = new TestObserver<>();
        mImageSearchManager.search(query, start).subscribe(result);
        result.assertNoErrors();
        result.assertValue(searchItems);


    }

    @Test
    public void startAtNextPage11() {
        final String query = "dummy query, can be anything";
        final int start = 1;

        SearchResult searchResult = SearchResultGenerator.generateSearchResults(10, 11);
        when(mImageSearchEndpoint.search(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                eq(query), eq(start)))
                .thenReturn(Observable.just(searchResult));

        SearchItems searchItems = generateSearchItems(searchResult);
        TestObserver<SearchItems> result = new TestObserver<>();
        mImageSearchManager.search(query, start).subscribe(result);
        result.assertNoErrors();
        result.assertValue(searchItems);


    }

    @Test
    public void startIndexIsGreaterThan100ReturnsEmptyResult() {
        final String query = "dummy query, can be anything";
        final int start = 101;


        SearchItems searchItems = SearchResultGenerator.generateEmptySearchItems();
        TestObserver<SearchItems> result = new TestObserver<>();
        mImageSearchManager.search(query, start).subscribe(result);
        result.assertNoErrors();
        result.assertValue(searchItems);

    }


}
