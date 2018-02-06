package com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ResultItem implements Parcelable {
    public abstract String title();

    public abstract String link();

    public abstract String snippet();

    public abstract String displayLink();

    public abstract Image image();

    public static Builder builder() {
        return new AutoValue_ResultItem.Builder();
    }


    public static TypeAdapter<ResultItem> typeAdapter(Gson gson) {
        return new AutoValue_ResultItem.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder title(String title);

        public abstract Builder link(String link);

        public abstract Builder snippet(String snippet);

        public abstract Builder displayLink(String displayLink);

        public abstract Builder image(Image image);

        public abstract ResultItem build();
    }

}