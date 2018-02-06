package com.russellgutierrez.demo.tinklabs.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.russellgutierrez.demo.tinklabs.sample.ui.main.MainMvpView;
import com.russellgutierrez.demo.tinklabs.sample.ui.main.MainPresenter;
import com.russellgutierrez.demo.tinklabs.sample.util.RxSchedulersOverrideRule;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock
    MainMvpView mMainMvpView;

    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter();
        mMainPresenter.attachView(mMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

    @Test
    public void showPage() {
        final int page = 0;

        mMainPresenter.loadPage(page);

        verify(mMainMvpView).showTab(eq(page));

    }

}
