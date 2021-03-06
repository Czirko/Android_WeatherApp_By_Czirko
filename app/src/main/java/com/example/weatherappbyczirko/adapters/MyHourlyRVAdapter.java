package com.example.weatherappbyczirko.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherappbyczirko.R;
import com.example.weatherappbyczirko.api.model.houdlyModels.Forecast12H;

import java.util.List;

public class MyHourlyRVAdapter extends RecyclerView.Adapter<MyHourlyRVAdapter.ViewHolder> {
    public List<Forecast12H>hourlyDatalist;

    public MyHourlyRVAdapter(List<Forecast12H> hourlyDatalist) {
        this.hourlyDatalist = hourlyDatalist;
        Log.d("hourlyTest", "adapterConst "+hourlyDatalist.size());

    }

    public void setDatas(List<Forecast12H> datas){
        this.hourlyDatalist=datas;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.hourly12_list_item,parent,false);

        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Forecast12H f12=hourlyDatalist.get(position);

        holder.tvTemp.setText(f12.getHourlyTemperature()+"C");
        holder.tvPhrae.setText(f12.getIconPhrase());
        holder.tvTime.setText(getTime(f12));
        //holder.ivHourly.set

    }

    private String getTime(Forecast12H f12) {
        String time = f12.getDateTime().substring(11,16);
        return time;

    }

    @Override
    public int getItemCount() {
        return hourlyDatalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivHourly;
        public TextView tvTemp;
        public TextView tvTime;
        public TextView tvPhrae;

        public ViewHolder(@NonNull View iv) {
            super(iv);
            ivHourly= iv.findViewById(R.id.ivListItemHourly);
            tvTemp= iv.findViewById(R.id.tvTemp);
            tvTime= iv.findViewById(R.id.tvHourlyTime);
            tvPhrae= iv.findViewById(R.id.tvHourlyIconPhrase);

        }
    }
}
