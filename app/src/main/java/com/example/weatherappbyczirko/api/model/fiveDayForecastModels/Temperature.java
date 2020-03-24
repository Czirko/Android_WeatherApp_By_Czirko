package com.example.weatherappbyczirko.api.model.fiveDayForecastModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("Minimum")
    @Expose
    private FiveDayTempType minimum;
    @SerializedName("Maximum")
    @Expose
    private FiveDayTempType maximum;

    public FiveDayTempType getMinimum() {
        return minimum;
    }

    public void setMinimum(FiveDayTempType minimum) {
        this.minimum = minimum;
    }

    public FiveDayTempType getMaximum() {
        return maximum;
    }

    public void setMaximum(FiveDayTempType maximum) {
        this.maximum = maximum;
    }
}
