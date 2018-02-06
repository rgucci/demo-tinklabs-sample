package com.russellgutierrez.demo.tinklabs.sample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import com.russellgutierrez.demo.tinklabs.sample.data.DataManager;
import com.russellgutierrez.demo.tinklabs.sample.data.local.PreferencesHelper;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;
import com.russellgutierrez.demo.tinklabs.sample.data.remote.ImageSearch;
import com.russellgutierrez.demo.tinklabs.sample.util.SearchResultGenerator;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock
    ImageSearch mImageSearch;
    @Mock
    PreferencesHelper mMockPreferencesHelper;

    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mImageSearch, mMockPreferencesHelper);
    }

    @Test
    public void searchCallsImageSearch() {
        final String query = "dummy query";
        final int startIndex = 1;

        SearchItems searchItems = SearchResultGenerator.generateSearchItems(1, 1);
        when(mImageSearch.search(Mockito.anyString(), Mockito.anyInt())).thenReturn(Observable.just(searchItems));

        TestObserver<SearchItems> result = new TestObserver<>();
        mDataManager.search(query, startIndex).subscribe(result);
        verify(mImageSearch).search(eq(query), eq(startIndex));
        result.assertNoErrors();

    }

    @Test
    public void getCorrectPreferencesHelper() {
        PreferencesHelper result = mDataManager.getPreferencesHelper();
        assertEquals(result, mMockPreferencesHelper);
    }

}
