package com.example.weatherappbyczirko.api.model.currentModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CurrentTemperature {

    @SerializedName("Metric")
    @Expose
    private CurrentTempType metric;
    @SerializedName("Imperial")
    @Expose
    private CurrentTempType imperial;

    public CurrentTempType getMetric() {
        return metric;
    }

    public void setMetric(CurrentTempType metric) {
        this.metric = metric;
    }

    public CurrentTempType getImperial() {
        return imperial;
    }

    public void setImperial(CurrentTempType imperial) {
        this.imperial = imperial;
    }

}
