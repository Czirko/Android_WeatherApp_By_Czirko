package com.example.weatherappbyczirko.api.repos;

import com.example.weatherappbyczirko.api.AccuApi;

public class LocationRepository {

        private static LocationRepository locationRepo;

        public static LocationRepository getInstance(){
            if(locationRepo == null)
                locationRepo=new LocationRepository();

            return locationRepo;
        }

        private AccuApi accuApi;

      //  public LocationRepository(){
      //      accuApi=Re
      //  }

}
