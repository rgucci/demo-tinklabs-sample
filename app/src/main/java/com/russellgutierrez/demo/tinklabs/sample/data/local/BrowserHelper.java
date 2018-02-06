package com.russellgutierrez.demo.tinklabs.sample.data.local;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.russellgutierrez.demo.tinklabs.sample.injection.ActivityContext;

import javax.inject.Inject;

public class BrowserHelper {

    private final Context mContext;

    @Inject
    public BrowserHelper(@ActivityContext Context context) {
        mContext = context;
    }

    public void openInBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        mContext.startActivity(intent);
    }
}
