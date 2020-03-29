package com.example.weatherappbyczirko.api.viewModells;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappbyczirko.api.AccuApi;
import com.example.weatherappbyczirko.api.model.geoModels.GeoLocationCity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeoViewModel extends ViewModel {
    private MutableLiveData<GeoLocationCity> data;

    public LiveData<GeoLocationCity> getCityByGeo(String apiKey, String geoCode){

        data = new MutableLiveData<>();
        loadDatas( apiKey,geoCode);

        return data;
    }

    private void loadDatas(String apiKey, String geoCode) {

        Call<GeoLocationCity>getByGeo= AccuApi.getInstance().location().getCityByGeo(apiKey,geoCode,"hu",false,false);
        getByGeo.enqueue(new Callback<GeoLocationCity>() {
            @Override
            public void onResponse(Call<GeoLocationCity> call, Response<GeoLocationCity> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GeoLocationCity> call, Throwable t) {

            }
        });
    }
}
