package com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

@AutoValue
public abstract class Queries implements Parcelable {
    public abstract List<NextPage> nextPage();

    public static Builder builder() {
        return new AutoValue_Queries.Builder();
    }

    public static TypeAdapter<Queries> typeAdapter(Gson gson) {
        return new AutoValue_Queries.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder nextPage(List<NextPage> nextPage);

        public abstract Queries build();
    }
}
