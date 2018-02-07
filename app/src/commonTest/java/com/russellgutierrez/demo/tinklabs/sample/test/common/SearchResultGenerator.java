package com.russellgutierrez.demo.tinklabs.sample.test.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.Image;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.NextPage;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.Queries;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.ResultItem;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.SearchResult;
import com.russellgutierrez.demo.tinklabs.sample.data.model.Item;
import com.russellgutierrez.demo.tinklabs.sample.data.model.ItemType;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;

public class SearchResultGenerator {
    public static SearchResult generateSearchResults(int count, int startIndex) {
        List<ResultItem> items = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            items.add(ResultItem.builder()
                    .title("title " + i)
                    .displayLink("display link" + i)
                    .link("link " + i)
                    .snippet("snippet " + i)
                    .image(Image.builder()
                            .contextLink("image contextual link " + i)
                            .thumbnailLink("image thumbnail link " + i)
                            .build())
                    .build());
        }

        SearchResult searchResult = SearchResult.builder()
                .items(items)
                .queries(Queries.builder()
                        .nextPage(Collections.singletonList(NextPage.builder()
                                .count(items.size())
                                .startIndex(startIndex)
                                .build()))
                        .build())
                .build();

        return searchResult;

    }

    public static SearchItems generateSearchItems(int count, int startIndex) {
        return generateSearchItems(generateSearchResults(count, startIndex));
    }

    public static SearchItems generateSearchItems(SearchResult searchResult) {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < searchResult.items().size(); i++) {
            int num = i + 1;
            ItemType itemType = num % 10 == 3 ? ItemType.IMAGE_ONLY : ItemType.NORMAL;
            Item item = searchResult.items().get(i).convert(itemType);
            items.add(item);
        }
        SearchItems searchItems = SearchItems.builder()
                .items(items)
                .count(searchResult.queries().nextPage().get(0).count())
                .nextPageStart(searchResult.queries().nextPage().get(0).startIndex())
                .build();

        return searchItems;
    }

    public static SearchItems generateEmptySearchItems() {
        SearchItems searchItems = SearchItems.builder()
                .items(Collections.<Item>emptyList())
                .count(0)
                .nextPageStart(1)
                .build();

        return searchItems;
    }
}
