package com.github.andrezimmermann.gluappsample.server.data;


public class FindRouteByNameParameter extends RequestData<FindRouteByNameResponse> {

    public FindRouteByNameParameter(String stopName) {
        getParams().put("stopName", new StringBuilder().append("%").append(stopName).append("%").toString());
    }

    public String getStopName() {
        if (getParams().containsKey("stopName")) {
            return (String) getParams().get("stopName");
        } else {
            return null;
        }
    }
}
