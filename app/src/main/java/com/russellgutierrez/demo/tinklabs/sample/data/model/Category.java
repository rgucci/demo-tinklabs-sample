package com.russellgutierrez.demo.tinklabs.sample.data.model;

public enum Category {
    TRAVEL("hong kong travel guide"),
    FOOD("hong kong food burger"),
    SHOPPING("hong kong shopping designer brand");

    private String mQuery;

    Category(String query) {
        mQuery = query;
    }

    public String getQuery() {
        return mQuery;
    }
}
