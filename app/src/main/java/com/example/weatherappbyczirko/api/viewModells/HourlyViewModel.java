package com.example.weatherappbyczirko.api.viewModells;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappbyczirko.api.AccuApi;
import com.example.weatherappbyczirko.api.model.houdlyModels.Forecast12H;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HourlyViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Forecast12H>> datas;

    public LiveData<ArrayList<Forecast12H>> getHorulyForecast(String cityKey, String apiKey){
        datas=new MutableLiveData<>();
        loadDatas(cityKey,apiKey);

        return datas;
    }

    private void loadDatas(String cityKey, String apiKey) {

        Call<ArrayList<Forecast12H>> hourlyCall= AccuApi.getInstance().forecast().call12hourly(cityKey,apiKey,"hu",false,true);
        hourlyCall.enqueue(new Callback<ArrayList<Forecast12H>>() {
            @Override
            public void onResponse(Call<ArrayList<Forecast12H>> call, Response<ArrayList<Forecast12H>> response) {
                datas.setValue( response.body());
                Log.d("hourlyTest", "OK ");
            }

            @Override
            public void onFailure(Call<ArrayList<Forecast12H>> call, Throwable t) {
                Log.d("hourlyTest", "FALSE "+ t.toString());

            }
        });

    }

    ;



}
