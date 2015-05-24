package com.github.andrezimmermann.gluappsample.server.data;

/**
 * TODO: add javadoc
 */
public class FindStopsByRouteIdParameter implements RequestData<FindStopsByRouteIdResponse> {

    private RouteIdValue params = new RouteIdValue();


    public FindStopsByRouteIdParameter(int routeId) {
        setRouteId(routeId);
    }

    public int getRouteId() {
        return params.getRouteId();
    }

    public void setRouteId(int routeId) {
        this.params.setRouteId(routeId);
    }


}
