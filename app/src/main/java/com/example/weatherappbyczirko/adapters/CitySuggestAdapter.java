package com.example.weatherappbyczirko.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.weatherappbyczirko.api.model.locationModels.City;

import java.util.ArrayList;
import java.util.List;

public class CitySuggestAdapter extends ArrayAdapter<City> implements Filterable {
    private List<City> mlistData;

    public CitySuggestAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mlistData=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mlistData.size();
    }

    @Nullable
    @Override
    public City getItem(int position) {
        return mlistData.get(position);
    }
    public void setData(List<City> dataList){
        mlistData.clear();
        mlistData.addAll(dataList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter dataFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    filterResults.values = mlistData;
                    filterResults.count = mlistData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && (results.count > 0)) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return dataFilter;    }
}
