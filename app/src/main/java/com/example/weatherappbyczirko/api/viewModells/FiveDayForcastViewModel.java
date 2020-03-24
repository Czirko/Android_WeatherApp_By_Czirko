package com.example.weatherappbyczirko.api.viewModells;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherappbyczirko.api.AccuApi;
import com.example.weatherappbyczirko.api.model.fiveDayForecastModels.FiveDayForcastRoot;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiveDayForcastViewModel extends ViewModel {

    private MutableLiveData<FiveDayForcastRoot> datas;

    public LiveData<FiveDayForcastRoot> getFiveDayForecast(String cityKey, String apiKey){
        datas=new MutableLiveData<>();
        loadDatas(cityKey,apiKey);

        return datas;
    }

    private void loadDatas(String cityKey, String apiKey) {
        Call<FiveDayForcastRoot>getFiveDayDatas= AccuApi.getInstance().forecast().getFiveDayForecast(
                cityKey
                ,apiKey
                ,"hu"
                ,false
                ,true
        );
        getFiveDayDatas.enqueue(new Callback<FiveDayForcastRoot>() {
            @Override
            public void onResponse(Call<FiveDayForcastRoot> call, Response<FiveDayForcastRoot> response) {
                Log.d("currentTest","fiveday: ok ");
                datas.setValue(response.body());
            }

            @Override
            public void onFailure(Call<FiveDayForcastRoot> call, Throwable t) {
                Log.d("currentTest","fiveday: NOTok ");

            }
        });

    }
}
