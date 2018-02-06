package com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model;


import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class Image implements Parcelable {
    public abstract String contextLink();

    public abstract String thumbnailLink();

    public static Builder builder() {
        return new AutoValue_Image.Builder();
    }

    public static TypeAdapter<Image> typeAdapter(Gson gson) {
        return new AutoValue_Image.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder contextLink(String contextLink);

        public abstract Builder thumbnailLink(String thumbnailLink);

        public abstract Image build();
    }

}