package com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class NextPage implements Parcelable {
    public abstract int count();

    public abstract int startIndex();

    public static Builder builder() {
        return new AutoValue_NextPage.Builder();
    }

    public static TypeAdapter<NextPage> typeAdapter(Gson gson) {
        return new AutoValue_NextPage.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder count(int count);

        public abstract Builder startIndex(int startIndex);

        public abstract NextPage build();
    }

}
