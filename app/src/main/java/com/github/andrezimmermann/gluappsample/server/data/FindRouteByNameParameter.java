package com.github.andrezimmermann.gluappsample.server.data;


public class FindRouteByNameParameter implements RequestData<FindRouteByNameResponse> {

    private StopNameValue params = new StopNameValue();


    public FindRouteByNameParameter(String routeId) {
        setStopName(routeId);
    }

    public String getStopName() {
        return params.getStopName();
    }

    public void setStopName(String stopName) {
        this.params.setStopName("%" + stopName + "%");
    }

}
