package com.example.weatherappbyczirko.adapters;

import android.icu.text.SimpleDateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappbyczirko.R;
import com.example.weatherappbyczirko.api.model.fiveDayForecastModels.DailyForecast;
import com.example.weatherappbyczirko.api.model.fiveDayForecastModels.FiveDayForcastRoot;

import java.text.ParseException;

import java.util.Date;
import java.util.List;

public class MyFiveDayRVAdapter extends RecyclerView.Adapter<MyFiveDayRVAdapter.ViewHolder> {
    public FiveDayForcastRoot datas;

    public MyFiveDayRVAdapter(FiveDayForcastRoot datas) {
        this.datas = datas;
    }

    public void setDatas(FiveDayForcastRoot datas){
        this.datas=datas;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.five_day_forecast_list_item,parent,false);

        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyForecast todayForecast = datas.getDailyForecasts().get(position);

        holder.tvDate.setText(getDate(todayForecast.getDate()));
        holder.tvWeatherDay.setText(todayForecast.getDay().getIconPhrase());
        holder.tvWeatherNight.setText(todayForecast.getNight().getIconPhrase());
        String todayTemp=(todayForecast.getTemperature().getMaximum().getValue())+" / "+todayForecast.getTemperature().getMinimum().getValue();
        holder.tvTemp.setText(todayTemp);

    }

    private String getDate(String dateInString) {
        dateInString=dateInString.substring(0,10);
        //2020-03-25
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");

        try {
             Date date = sdf.parse(dateInString);

             return makeStringDate(date);
        } catch (ParseException e) {
            Log.d("DateTest", e.toString());
            return "unknow Date";
        }

    }

    private String makeStringDate(Date date) {

        String back="";

        switch(date.getDay()){
            case 0: back ="Hétf.";
            break;
            case 1: back ="Kedd";
                break;
            case 2: back ="Szerda";
                break;
            case 3: back ="Csüt.";
                break;
            case 4: back ="Pént.";
                break;
            case 5: back ="Szomb.";
                break;
            case 6: back ="Vas.";
                break;
        }
        return back;
    }

    @Override
    public int getItemCount() {
        return datas.getDailyForecasts().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDate;
        public TextView tvWeatherDay;
        public TextView tvWeatherNight;

        public TextView tvTemp;

        public ViewHolder(@NonNull View v) {
            super(v);
            tvDate = v.findViewById(R.id.tvFiveDayDate);
            tvWeatherDay = v.findViewById(R.id.tvFiveDayWeather);
            tvWeatherNight = v.findViewById(R.id.tvFiveNightWeather);

            tvTemp = v.findViewById(R.id.tvFiveDayTemp);


        }
    }
}
