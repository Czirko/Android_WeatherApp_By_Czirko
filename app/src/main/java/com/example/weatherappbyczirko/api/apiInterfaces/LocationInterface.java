package com.example.weatherappbyczirko.api.apiInterfaces;

import com.example.weatherappbyczirko.api.model.geoModels.GeoLocationCity;
import com.example.weatherappbyczirko.api.model.locationModels.City;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LocationInterface {

    @GET("/locations/v1/cities/autocomplete?")
    Call<ArrayList<City>>autoCompleteCity(
            @Query("apikey")String apiKey,
            @Query("q")String searchText,
            @Query("language")String language);

    @GET("/locations/v1/cities/geoposition/search?")
    Call<GeoLocationCity>getCityByGeo(
            @Query("apikey")String apiKey,
            @Query("q")String geoLocation,
            @Query("language")String language,
            @Query("details")Boolean isDetails,
            @Query("toplevel")Boolean isTopLevel
    );



}
