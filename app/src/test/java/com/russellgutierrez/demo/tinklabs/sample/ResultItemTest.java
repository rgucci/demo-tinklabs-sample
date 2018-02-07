package com.russellgutierrez.demo.tinklabs.sample;

import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.Image;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.ResultItem;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.data.model.ItemType;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class ResultItemTest {

    @Test
    public void testNormalItemConversion() {
        final ItemType itemType = ItemType.NORMAL;
        final String title = "title";
        final String snippet = "snippet";
        final String displayLink = "displayLink";
        final String link = "link";
        final String contextLink = "contextLink";
        final String thumbnailLink = "thumbnailLink";

        ResultItem resultItem = ResultItem.builder()
                .title(title)
                .snippet(snippet)
                .displayLink(displayLink)
                .link(link)
                .image(Image.builder()
                        .contextLink(contextLink)
                        .thumbnailLink(thumbnailLink)
                        .build())
                .build();

        Item item = resultItem.convert(itemType);
        assertEquals(itemType, item.itemType());
        assertEquals(resultItem.displayLink(), item.title());
        assertEquals(resultItem.snippet(), item.description());
        assertEquals(resultItem.image().thumbnailLink(), item.imageUrl());
        assertEquals(resultItem.image().contextLink(), item.linkUrl());
    }

    @Test
    public void testImageOnlyItemConversion() {
        final ItemType itemType = ItemType.IMAGE_ONLY;
        final String title = "title";
        final String snippet = "snippet";
        final String displayLink = "displayLink";
        final String link = "link";
        final String contextLink = "contextLink";
        final String thumbnailLink = "thumbnailLink";

        ResultItem resultItem = ResultItem.builder()
                .title(title)
                .snippet(snippet)
                .displayLink(displayLink)
                .link(link)
                .image(Image.builder()
                        .contextLink(contextLink)
                        .thumbnailLink(thumbnailLink)
                        .build())
                .build();

        Item item = resultItem.convert(itemType);
        assertEquals(itemType, item.itemType());
        assertEquals("", item.title());
        assertEquals("", item.description());
        assertEquals(resultItem.link(), item.imageUrl());
        assertEquals(resultItem.image().contextLink(), item.linkUrl());
    }
}
