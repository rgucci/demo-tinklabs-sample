package com.russellgutierrez.demo.tinklabs.sample;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.russellgutierrez.demo.tinklabs.sample.data.model.Category;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.data.model.ItemType;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;
import com.russellgutierrez.demo.tinklabs.sample.test.common.SearchResultGenerator;
import com.russellgutierrez.demo.tinklabs.sample.test.common.TestComponentRule;
import com.russellgutierrez.demo.tinklabs.sample.ui.main.MainActivity;
import com.russellgutierrez.demo.tinklabs.sample.util.RecyclerViewMatcher;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.text.MessageFormat;

import io.reactivex.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    public final TestComponentRule component =
            new TestComponentRule(InstrumentationRegistry.getTargetContext());
    public final ActivityTestRule<MainActivity> main =
            new ActivityTestRule<>(MainActivity.class, false, false);

    public static final String TITLE_PATTERN = "#{0} {1}";

    private String[] tabNames;

    // TestComponentRule needs to go first to make sure the Dagger ApplicationTestComponent is set
    // in the Application before any Activity is launched.
    @Rule
    public final TestRule chain = RuleChain.outerRule(component).around(main);

    @Before
    public void setup() {
        tabNames = new String[]{"TRAVEL", "FOOD", "SHOPPING"};
    }

    @Test
    public void displayAllTabs() {
        SearchItems searchItems = SearchResultGenerator.generateSearchItems(10, 1);
        when(component.getMockDataManager().search(anyString(), anyInt()))
                .thenReturn(Observable.just(searchItems));

        main.launchActivity(null);

        onView(withId(R.id.pager))
                .check(matches(isDisplayed()));
        onView(withId(R.id.pager_tab_strip))
                .check(matches(isDisplayed()));

        for (String tab : tabNames) {
            Matcher<View> matcher = allOf(withText(tab),
                    isDescendantOfA(withId(R.id.pager)));
            onView(matcher)
                    .check(matches(isDisplayed()));
            onView(withId(R.id.pager))
                    .perform(swipeLeft());
        }
    }

    @Test
    public void checkTitleAndDescriptionAndImage() {
        final Category category = Category.TRAVEL;
        final int count = 4;
        SearchItems searchItems = SearchResultGenerator.generateSearchItems(count, 1);
        when(component.getMockDataManager().search(anyString(), anyInt()))
                .thenReturn(Observable.just(searchItems));

        main.launchActivity(null);

        Matcher<View> recyclerViewMatcher = allOf(withId(R.id.recycler_view), withTagValue(is((Object) category)));
        onView(recyclerViewMatcher).check(matches(isDisplayed()));
        onView(recyclerViewMatcher).perform(RecyclerViewActions.scrollToPosition(0));

        for (int i = 0; i < count; i++) {
            Item item = searchItems.items().get(i);
            if (item.itemType() == ItemType.NORMAL) {
                onView(new RecyclerViewMatcher(R.id.recycler_view).atPosition(i))
                        .check(matches(hasDescendant(allOf(withId(R.id.text_title),
                                withText(MessageFormat.format(TITLE_PATTERN, i + 1, item.title()))))));
                onView(new RecyclerViewMatcher(R.id.recycler_view).atPosition(i))
                        .check(matches(hasDescendant(allOf(withId(R.id.text_description),
                                withText(item.description())))));
            }
            onView(new RecyclerViewMatcher(R.id.recycler_view).atPosition(i))
                    .check(matches(hasDescendant(withId(R.id.image))))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void displayError() {
        when(component.getMockDataManager().search(anyString(), anyInt()))
                .thenReturn(Observable.<SearchItems>error(new RuntimeException("dummy exception")));

        main.launchActivity(null);

        onView(withText(R.string.error_loading_results)).check(matches(isDisplayed()));
    }
}
