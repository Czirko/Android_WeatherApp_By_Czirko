package com.example.weatherappbyczirko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.weatherappbyczirko.api.AccuApi;
import com.example.weatherappbyczirko.api.model.City;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AutoCompleteTextView autoCompleteTextView =
                findViewById(R.id.auto_complete_edit_text);
        final TextView selectedText = findViewById(R.id.selected_item);

        autoSuggestAdapter = new CitySuggestAdapter(this,
                android.R.layout.simple_dropdown_item_1line);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedText.setText(autoSuggestAdapter.getItem(position).getLocalizedName());

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
        /*autoCity.enqueue(new Callback<JSONArray>() {
            @Override
            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
                JSONArray c = response.body();
                Log.d("testTag" , c.toString());
            }

            @Override
            public void onFailure(Call<JSONArray> call, Throwable t) {
                Log.d("testTag", "auto food complete error: " + t.toString());

            }
        });
*/
        //java.lang.IllegalStateException: Expected BEGIN_OBJECT but was BEGIN_ARRAY at line 1 column 2 path $
    }
}
