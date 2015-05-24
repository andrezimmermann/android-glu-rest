package com.github.andrezimmermann.gluappsample.server.api;

public enum Endpoint {

    FIND_ROUTE_ID("findRoutesByStopName/run"),
    FIND_STOP("findStopsByRouteId/run"),
    FIND_DEPARTURE("findDeparturesByRouteId/run");

    private String location;

    Endpoint(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
}