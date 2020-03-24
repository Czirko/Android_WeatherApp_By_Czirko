package com.example.weatherappbyczirko.api.viewModells;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappbyczirko.api.AccuApi;
import com.example.weatherappbyczirko.api.model.currentModels.CurrentDatas;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentViewModell extends ViewModel {

    private MutableLiveData<ArrayList<CurrentDatas>> datas;

    public LiveData<ArrayList<CurrentDatas>> getCurrentForecast(String cityKey, String apiKey) {
        datas = new MutableLiveData<>();
        loadDatas(cityKey, apiKey);

        return datas;
    }

    private void loadDatas(String cityKey, String apiKey) {
        Call<ArrayList<CurrentDatas>> getCurrent = AccuApi
                .getInstance()
                .forecast().
                        getCurrentWeather(
                                cityKey
                                , apiKey
                                , "hu"
                                , false);
        getCurrent.enqueue(new Callback<ArrayList<CurrentDatas>>() {
            @Override
            public void onResponse(Call<ArrayList<CurrentDatas>> call, Response<ArrayList<CurrentDatas>> response) {
                datas.setValue(response.body());
                Log.d("currentTest", "OK ");

            }

            @Override
            public void onFailure(Call<ArrayList<CurrentDatas>> call, Throwable t) {
                Log.d("currentTest", "not ok "+t.toString()
                );

            }
        });
    }
}
