package com.russellgutierrez.demo.tinklabs.sample.data.googleimage.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.russellgutierrez.demo.tinklabs.sample.data.googleimage.model.SearchResult;
import com.russellgutierrez.demo.tinklabs.sample.util.MyGsonTypeAdapterFactory;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This REST API uses the free tier of google custom image search.
 * The page size = 10 is mandatory, maximum result count = 100
 * If the daily quota is exceeded, the api will return HTTP 400
 * This is ok for just demonstration purposes
 */
public interface ImageSearchEndpoint {

    String ENDPOINT = "https://www.googleapis.com/";

    @GET("customsearch/v1")
    Observable<SearchResult> search(@Query("key") String apiKey, @Query("cx") String cx,
                                    @Query("searchType") String searchType, @Query("q") String query,
                                    @Query("start") int start);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

        public static ImageSearchEndpoint newImageSearchEndpoint() {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                    .build();
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat(DATETIME_FORMAT)
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ImageSearchEndpoint.ENDPOINT)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(ImageSearchEndpoint.class);
        }
    }
}
