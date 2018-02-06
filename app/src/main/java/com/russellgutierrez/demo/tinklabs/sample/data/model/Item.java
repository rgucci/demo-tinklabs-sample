package com.russellgutierrez.demo.tinklabs.sample.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Item implements Parcelable {
    public abstract ItemType itemType();

    public abstract String title();

    public abstract String description();

    public abstract String imageUrl();

    public abstract String linkUrl();

    public static Builder builder() {
        return new AutoValue_Item.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder itemType(ItemType itemType);

        public abstract Builder title(String title);

        public abstract Builder description(String description);

        public abstract Builder imageUrl(String imageUrl);

        public abstract Builder linkUrl(String linkUrl);

        public abstract Item build();

    }
}

