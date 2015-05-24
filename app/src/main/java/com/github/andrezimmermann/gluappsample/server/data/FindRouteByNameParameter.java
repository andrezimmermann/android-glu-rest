package com.github.andrezimmermann.gluappsample.server.data;


public class FindRouteByNameParameter implements RequestData {

    private String stopName;

    public FindRouteByNameParameter(String stopName) {
        this.stopName = stopName;
    }

    public String getStopName() {
        return stopName;
    }
}
