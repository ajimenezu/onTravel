package com.tec_diseno.ontravel.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.core.Persister;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String SERVER_NAMESPACE = "https://on-travel-api.herokuapp.com/api/";

    private static RestClient sInstance;
    private RestInterface mRestInterface;
    private Retrofit retrofit;

    /**
     * It returns the singleton class instance
     * @return instance class
     */
    public static RestClient getsInstance() {
        if (sInstance == null) {
            sInstance = new RestClient();
        }
        return sInstance;
    }

    /**
     * Clear instance method
     */
    public static void clearInstance() {
        sInstance = null;
    }

    /**
     * Private default constructor
     * required for the singleton implementation
     */
    private RestClient() {
        try {
            setupRestClient();
        } catch (Exception e) {
            //TODO:
        }
    }

    /**
     * Set up RestInterface object
     */
    private void setupRestClient(){

        Strategy strategy = new AnnotationStrategy();
        Serializer serializer = new Persister(strategy);
        String url = SERVER_NAMESPACE;

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build();
        mRestInterface = retrofit.create(RestInterface.class);

    }

    public OkHttpClient getOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RequestInterceptor())
                /*.connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)*/
                .build();
        return okHttpClient;
    }

    /**
     * It returns the RestInterface object initialized
     * @return the RestInterface
     */
    public RestInterface getService() {
        return mRestInterface;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
