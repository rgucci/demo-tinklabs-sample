package com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class SearchResult implements Parcelable {
    public abstract List<ResultItem> items();

    public abstract Queries queries();

    public static Builder builder() {
        return new AutoValue_SearchResult.Builder();
    }


    public static TypeAdapter<SearchResult> typeAdapter(Gson gson) {
        return new AutoValue_SearchResult.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder items(List<ResultItem> items);

        public abstract Builder queries(Queries queries);

        public abstract SearchResult build();
    }
}
