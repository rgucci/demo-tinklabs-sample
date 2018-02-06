package com.russellgutierrez.demo.tinklabs.sample.data.model;

import com.google.auto.value.AutoValue;

import java.util.List;

@AutoValue
public abstract class SearchItems {
    public abstract List<Item> items();

    public abstract int count();

    public abstract int nextPageStart();

    public static Builder builder() {
        return new AutoValue_SearchItems.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder items(List<Item> items);

        public abstract Builder count(int count);

        public abstract Builder nextPageStart(int nextPageStart);

        public abstract SearchItems build();
    }
}
