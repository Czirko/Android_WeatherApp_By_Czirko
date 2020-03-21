package com.example.weatherappbyczirko.api.apiInterfaces;

import com.example.weatherappbyczirko.api.model.Forecast12H;

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
}
