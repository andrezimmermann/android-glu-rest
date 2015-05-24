package com.github.andrezimmermann.gluappsample.server.data;


public class FindRouteByNameParameter extends RequestData {



    public FindRouteByNameParameter(String stopName) {
        getParams().put("stopName", stopName);
    }

    public String getStopName() {
        if (getParams().containsKey("stopName")) {
            return (String) getParams().get("stopName");
        } else {
            return null;
        }
    }
}
