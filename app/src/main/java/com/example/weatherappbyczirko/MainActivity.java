package com.example.weatherappbyczirko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.example.weatherappbyczirko.adapters.CitySuggestAdapter;
import com.example.weatherappbyczirko.adapters.MyHourlyRVAdapter;
import com.example.weatherappbyczirko.api.AccuApi;
import com.example.weatherappbyczirko.api.model.City;
import com.example.weatherappbyczirko.api.model.Forecast12H;
import com.example.weatherappbyczirko.api.viewModells.HourlyViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private CitySuggestAdapter autoSuggestAdapter;
    private Handler handlerCity;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private static final String apiKey= "B3yHbxjfcICiwxmY9FptiBu9tH1K9rH0";
    private String acceptLanguage="hu";

    private RecyclerView rvMain;
    private MyHourlyRVAdapter hourlyAdapter;
    private List<Forecast12H> hourlyDataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AutoCompleteTextView autoCompleteTextView =
                findViewById(R.id.auto_complete_edit_text);

        rvMain = findViewById(R.id.rvMain);
        rvMain.setLayoutManager(new LinearLayoutManager(this));

        rvMain.setHasFixedSize(false);


        autoSuggestAdapter = new CitySuggestAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City selectedCity=autoSuggestAdapter.getItem(position);
                autoCompleteTextView.setText("");
                getHourlyCall(selectedCity);

            }
        });

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handlerCity.removeMessages(TRIGGER_AUTO_COMPLETE);
                handlerCity.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handlerCity=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if(msg.what==TRIGGER_AUTO_COMPLETE){
                    if(!TextUtils.isEmpty(autoCompleteTextView.getText())){
                        makeAutoTextApi(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });


    }

    private void getHourlyCall(City selectedCity) {
        HourlyViewModel vm = ViewModelProviders.of(this).get(HourlyViewModel.class);
        vm.getHorulyForecast(selectedCity.getKey(),apiKey).observe(this, new Observer<ArrayList<Forecast12H>>() {
            @Override
            public void onChanged(ArrayList<Forecast12H> forecast12HS) {
                if(hourlyAdapter==null){
                    hourlyAdapter= new MyHourlyRVAdapter(forecast12HS);
                    rvMain.setAdapter(hourlyAdapter);
                }else{
                    hourlyAdapter.setDatas(forecast12HS);
                    hourlyAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    /*private List<Forecast12H> getHourlyDatas(String cityKey) {
        Log.d("hourlyTest", "hourlyCallFun");

        Call<ArrayList<Forecast12H>>hourlyCall=AccuApi.getInstance().hourly().call12hourly(cityKey,apiKey,"hu",false,true);
        hourlyCall.enqueue(new Callback<ArrayList<Forecast12H>>() {
            @Override
            public void onResponse(Call<ArrayList<Forecast12H>> call, Response<ArrayList<Forecast12H>> response) {
                hourlyDataList= response.body();
                Log.d("hourlyTest", "OK "+hourlyDataList.size());
            }

            @Override
            public void onFailure(Call<ArrayList<Forecast12H>> call, Throwable t) {
                Log.d("hourlyTest", "FALSE "+ t.toString());

            }
        });

        return hourlyDataList;

    }*/

    private void makeAutoTextApi(String txt) {
        Call<ArrayList<City>> autoCity = AccuApi.getInstance().location().autoCompleteCity(apiKey,txt,acceptLanguage);
        autoCity.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                ArrayList<City> cityList = response.body();

                autoSuggestAdapter.setData(cityList);
                autoSuggestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                Log.d("testTag", "auto food complete error: " + t.toString());
            }
        });
        }
}
