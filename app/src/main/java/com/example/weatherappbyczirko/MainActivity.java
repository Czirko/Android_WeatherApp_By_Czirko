package com.example.weatherappbyczirko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherappbyczirko.adapters.CitySuggestAdapter;
import com.example.weatherappbyczirko.adapters.MyFiveDayRVAdapter;
import com.example.weatherappbyczirko.adapters.MyHourlyRVAdapter;
import com.example.weatherappbyczirko.api.AccuApi;
import com.example.weatherappbyczirko.api.model.fiveDayForecastModels.FiveDayForcastRoot;
import com.example.weatherappbyczirko.api.model.geoModels.GeoLocationCity;
import com.example.weatherappbyczirko.api.model.locationModels.City;
import com.example.weatherappbyczirko.api.model.currentModels.CurrentDatas;
import com.example.weatherappbyczirko.api.model.houdlyModels.Forecast12H;
import com.example.weatherappbyczirko.api.viewModells.CurrentViewModell;
import com.example.weatherappbyczirko.api.viewModells.FiveDayForcastViewModel;
import com.example.weatherappbyczirko.api.viewModells.GeoViewModel;
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
    private static final String apiKey = "B3yHbxjfcICiwxmY9FptiBu9tH1K9rH0";
    private String acceptLanguage = "hu";

    private String locString;

    private TextView tvCurrentTemp;
    private TextView tvCurrentLocation;
    private TextView tvCurrentIconPhrase;



    private RecyclerView rvHourly;
    private MyHourlyRVAdapter hourlyAdapter;
    private List<Forecast12H> hourlyDataList;

    private RecyclerView rvDaily;
    private MyFiveDayRVAdapter dailyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initLocation();

        final AutoCompleteTextView autoCompleteTextView =

                findViewById(R.id.auto_complete_edit_text);

        rvHourly = findViewById(R.id.rvHourly);
        LinearLayoutManager horiLayManager = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        rvHourly.setLayoutManager(horiLayManager);
        rvHourly.setHasFixedSize(false);

        rvDaily = findViewById(R.id.rvDaily);
        rvDaily.setLayoutManager(new LinearLayoutManager(this));
        rvDaily.setHasFixedSize(true);


        autoSuggestAdapter = new CitySuggestAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                City selectedCity = autoSuggestAdapter.getItem(position);
                autoCompleteTextView.setText("");

                getForeCasts(selectedCity.getKey(),selectedCity.getLocalizedName());


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

        handlerCity = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeAutoTextApi(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });


    }

    private void getForeCasts(String cityKey,String cityName) {
        getHourlyCall(cityKey);
        testCurrent(cityKey,cityName);
        showFiveDayForecast(cityKey);

    }

    private void initLocation() {
        Log.d("locTest", "initloc");

        final LocationListener locListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                locString = location.getLatitude() + "," + location.getLongitude();
                Log.d("locTest", locString);
                GeoViewModel gvm =ViewModelProviders.of(MainActivity.this).get(GeoViewModel.class);
                gvm.getCityByGeo(apiKey,locString).observe(MainActivity.this, new Observer<GeoLocationCity>() {
                    @Override
                    public void onChanged(GeoLocationCity geoLocationCity) {
                        getForeCasts(geoLocationCity.getKey(),geoLocationCity.getLocalizedName());
                    }
                });


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }


        };
        Log.d("locTest", "criteria");

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Log.d("locTest", "locmng");

        final Looper looper = null;

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.
                PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.
                        permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            Log.d("locTest", "ifStm");

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;

            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.

        }
        Log.d("locTest", "elvileg itt kéri");

        locationManager.requestSingleUpdate(criteria, locListener, looper);


    }

    private void showFiveDayForecast(String cityKey) {
        FiveDayForcastViewModel fvm=ViewModelProviders.of(this).get(FiveDayForcastViewModel.class);
        fvm.getFiveDayForecast(cityKey,apiKey).observe(this, new Observer<FiveDayForcastRoot>() {
            @Override
            public void onChanged(FiveDayForcastRoot fiveDayForcastRoot) {
                Log.d("currentTest","fiveday: "+fiveDayForcastRoot.getDailyForecasts().size()+"");
                if(dailyAdapter==null){
                    dailyAdapter=new MyFiveDayRVAdapter(fiveDayForcastRoot);
                    rvDaily.setAdapter(dailyAdapter);
                }else{
                    dailyAdapter.setDatas(fiveDayForcastRoot);
                }


            }
        });
    }

    private void testCurrent(String cityKey, final String cityName) {

        tvCurrentTemp=findViewById(R.id.tvCurrentTemp);
        tvCurrentLocation=findViewById(R.id.tvCurrentLocation);
        tvCurrentIconPhrase=findViewById(R.id.tvCurrentIconPhrase);
        CurrentViewModell cvm= ViewModelProviders.of(this).get(CurrentViewModell.class);
        cvm.getCurrentForecast(cityKey,apiKey).observe(this, new Observer<ArrayList<CurrentDatas>>() {
            @Override
            public void onChanged(ArrayList<CurrentDatas> currentDatas) {
                Log.d("currentTest",currentDatas.size()+"");
                tvCurrentTemp.setText(currentDatas.get(0).getTemperature().getMetric().getValue()+" C");
                tvCurrentIconPhrase.setText(currentDatas.get(0).getWeatherText());
                tvCurrentLocation.setText(cityName);



            }
        });
    }

    private void getHourlyCall(String cityKey) {
        HourlyViewModel vm = ViewModelProviders.of(this).get(HourlyViewModel.class);
        vm.getHorulyForecast(cityKey,apiKey).observe(this, new Observer<ArrayList<Forecast12H>>() {
            @Override
            public void onChanged(ArrayList<Forecast12H> forecast12HS) {
                if(hourlyAdapter==null){
                    hourlyAdapter= new MyHourlyRVAdapter(forecast12HS);
                    rvHourly.setAdapter(hourlyAdapter);
                }else{
                    hourlyAdapter.setDatas(forecast12HS);
                    hourlyAdapter.notifyDataSetChanged();
                }

            }
        });
    }





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
