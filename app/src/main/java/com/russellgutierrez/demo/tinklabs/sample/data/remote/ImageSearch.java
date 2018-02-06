package com.russellgutierrez.demo.tinklabs.sample.data.remote;

import io.reactivex.Observable;
import com.russellgutierrez.demo.tinklabs.sample.data.model.SearchItems;

public interface ImageSearch {
    Observable<SearchItems> search(String query, int startIndex);
}
