package com.russellgutierrez.demo.tinklabs.sample.data.model;

public enum ItemType {
    NORMAL(1),
    IMAGE_ONLY(2);


    private int mCode;

    ItemType(int code) {
        mCode = code;
    }

    public int getCode() {
        return mCode;
    }

    public static ItemType fromCode(int code) {
        switch (code) {
            case 2:
                return IMAGE_ONLY;
            case 1:
            default:
                return NORMAL;
        }
    }

}
