package com.github.andrezimmermann.gluappsample.client.service;

import com.github.andrezimmermann.gluappsample.shared.data.BusDeparture;
import com.github.andrezimmermann.gluappsample.shared.data.BusStop;

import java.util.List;

/**
 * TODO: add javadoc
 */
public class BusDetail {

    private List<BusStop> stops;
    private List<BusDeparture> departures;

    public List<BusStop> getStops() {
        return stops;
    }

    public void setStops(List<BusStop> stops) {
        this.stops = stops;
    }

    public List<BusDeparture> getDepartures() {
        return departures;
    }

    public void setDepartures(List<BusDeparture> departures) {
        this.departures = departures;
    }
}
