package com.example.weatherappbyczirko.api.model.fiveDayForecastModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FiveDayForcastRoot {
    @SerializedName("Headline")
    @Expose
    private Headline headline;
    @SerializedName("DailyForecasts")
    @Expose
    private List<DailyForecast> dailyForecasts = null;

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

    private class Headline {
        @SerializedName("EffectiveDate")
        @Expose
        private String effectiveDate;
        @SerializedName("EffectiveEpochDate")
        @Expose
        private Integer effectiveEpochDate;
        @SerializedName("Severity")
        @Expose
        private Integer severity;
        @SerializedName("Text")
        @Expose
        private String text;
        @SerializedName("Category")
        @Expose
        private String category;
        @SerializedName("EndDate")
        @Expose
        private Object endDate;
        @SerializedName("EndEpochDate")
        @Expose
        private Object endEpochDate;
        @SerializedName("MobileLink")
        @Expose
        private String mobileLink;
        @SerializedName("Link")
        @Expose
        private String link;

        public String getEffectiveDate() {
            return effectiveDate;
        }

        public void setEffectiveDate(String effectiveDate) {
            this.effectiveDate = effectiveDate;
        }

        public Integer getEffectiveEpochDate() {
            return effectiveEpochDate;
        }

        public void setEffectiveEpochDate(Integer effectiveEpochDate) {
            this.effectiveEpochDate = effectiveEpochDate;
        }

        public Integer getSeverity() {
            return severity;
        }

        public void setSeverity(Integer severity) {
            this.severity = severity;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public Object getEndEpochDate() {
            return endEpochDate;
        }

        public void setEndEpochDate(Object endEpochDate) {
            this.endEpochDate = endEpochDate;
        }

        public String getMobileLink() {
            return mobileLink;
        }

        public void setMobileLink(String mobileLink) {
            this.mobileLink = mobileLink;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
