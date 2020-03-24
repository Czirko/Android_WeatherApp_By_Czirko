package com.example.weatherappbyczirko.api;

import com.example.weatherappbyczirko.api.apiInterfaces.ForecastsInterface;
import com.example.weatherappbyczirko.api.apiInterfaces.LocationInterface;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccuApi {
    public static final String API_URL="http://dataservice.accuweather.com";
    private static AccuApi mIstance;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private static final String apiKey= "B3yHbxjfcICiwxmY9FptiBu9tH1K9rH0";

    private AccuApi(){

        retrofit= new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .build();
    }

    public static synchronized AccuApi getInstance(){
        if(mIstance == null)
            mIstance=new AccuApi();

        return mIstance;
    }

    public LocationInterface location(){
        return retrofit.create(LocationInterface.class);
    }
    public ForecastsInterface forecast(){
        return retrofit.create(ForecastsInterface.class);
    }

}
