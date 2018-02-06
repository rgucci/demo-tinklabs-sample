package com.russellgutierrez.demo.tinklabs.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import com.russellgutierrez.demo.tinklabs.sample.data.DataManager;
import com.russellgutierrez.demo.tinklabs.sample.data.local.BrowserHelper;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;
import com.russellgutierrez.demo.tinklabs.sample.ui.main.ListMvpView;
import com.russellgutierrez.demo.tinklabs.sample.ui.main.ListPresenter;
import com.russellgutierrez.demo.tinklabs.sample.util.RxSchedulersOverrideRule;
import com.russellgutierrez.demo.tinklabs.sample.util.SearchResultGenerator;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ListPresenterTest {

    @Mock
    ListMvpView mMockListMvpView;
    @Mock
    DataManager mMockDataManager;
    @Mock
    BrowserHelper mBrowserHelper;

    private ListPresenter mListPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mListPresenter = new ListPresenter(mMockDataManager, mBrowserHelper);
        mListPresenter.attachView(mMockListMvpView);
    }

    @After
    public void tearDown() {
        mListPresenter.detachView();
    }

    @Test
    public void loadImageResults() {
        final String query = "dummy";
        final int count = 10;
        final int startIndex = 1;

        SearchItems searchItems = SearchResultGenerator.generateSearchItems(count, startIndex);
        when(mMockDataManager.search(eq(query), eq(startIndex)))
                .thenReturn(Observable.just(searchItems));

        mListPresenter.setQuery(query);
        mListPresenter.loadItems();
        verify(mMockDataManager).search(eq(query), eq(startIndex));
        verify(mMockListMvpView).showItems(eq(searchItems.items()), eq(count));
        verify(mMockListMvpView, never()).showItemsEmpty();
        verify(mMockListMvpView, never()).showError();
        assertTrue(mListPresenter.isSearchStarted());

    }

    @Test
    public void loadImageEmpty() {
        final String query = "dummy";
        final int startIndex = 1;

        SearchItems searchItems = SearchResultGenerator.generateEmptySearchItems();
        when(mMockDataManager.search(eq(query), eq(startIndex)))
                .thenReturn(Observable.just(searchItems));

        mListPresenter.setQuery(query);
        mListPresenter.loadItems();
        verify(mMockDataManager).search(eq(query), eq(startIndex));
        verify(mMockListMvpView, never()).showItems(Mockito.<Item>anyList(), Mockito.anyInt());
        verify(mMockListMvpView).showItemsEmpty();
        verify(mMockListMvpView, never()).showError();
        assertTrue(mListPresenter.isSearchStarted());

    }

    @Test
    public void loadImageFails() {
        final String query = "dummy";
        final int startIndex = 1;

        when(mMockDataManager.search(eq(query), eq(startIndex)))
                .thenReturn(Observable.<SearchItems>error(new RuntimeException()));

        mListPresenter.setQuery(query);
        mListPresenter.loadItems();

        verify(mMockDataManager).search(eq(query), eq(startIndex));
        verify(mMockListMvpView, never()).showItems(Mockito.<Item>anyList(), Mockito.anyInt());
        verify(mMockListMvpView, never()).showItemsEmpty();
        verify(mMockListMvpView).showError();
        assertTrue(mListPresenter.isSearchStarted());

    }

    @Test
    public void openInBrowser() {
        final String query = "dummy";
        final int startIndex = 1;
        final int count = 10;
        final int indexToBrowse = 5;

        SearchItems searchItems = SearchResultGenerator.generateSearchItems(count, startIndex);
        when(mMockDataManager.search(eq(query), eq(startIndex)))
                .thenReturn(Observable.just(searchItems));

        mListPresenter.setQuery(query);
        mListPresenter.loadItems();
        mListPresenter.browseItem(indexToBrowse);

        String url = searchItems.items().get(indexToBrowse).linkUrl();
        verify(mBrowserHelper).openInBrowser(url);
    }

}
