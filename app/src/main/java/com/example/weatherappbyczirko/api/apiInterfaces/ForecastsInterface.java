package com.example.weatherappbyczirko.api.apiInterfaces;


import com.example.weatherappbyczirko.api.model.currentModels.CurrentDatas;
import com.example.weatherappbyczirko.api.model.fiveDayForecastModels.FiveDayForcastRoot;
import com.example.weatherappbyczirko.api.model.houdlyModels.Forecast12H;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ForecastsInterface {

    @GET("/forecasts/v1/hourly/12hour/{locationKey}?")
    Call<ArrayList<Forecast12H>>call12hourly(
            @Path("locationKey")String locationKey,
            @Query("apikey")String apiKey,
            @Query("language")String acceptLanguage,
            @Query("details")Boolean isDetails,
            @Query("metric")Boolean isMetric
    );

    @GET("/forecasts/v1/daily/5day/{locationKey}?")
    Call<FiveDayForcastRoot>getFiveDayForecast(
            @Path("locationKey")String locationKey,
            @Query("apikey")String apiKey,
            @Query("language")String acceptLanguage,
            @Query("details")Boolean isDetails,
            @Query("metric")Boolean isMetric
    );


    @GET("/currentconditions/v1/{locationKey}?")
    Call<ArrayList<CurrentDatas>>getCurrentWeather(
            @Path("locationKey")String locationKey,
            @Query("apikey")String apiKey,
            @Query("language")String acceptLanguage,
            @Query("details")Boolean isDetails
    );



}
