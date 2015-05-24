package com.github.andrezimmermann.gluappsample.server.data;

/**
 * TODO: add javadoc
 */
public class FindDeparturesByRouteIdParameter implements RequestData<FindDeparturesByRouteIdResponse> {

    private RouteIdValue params = new RouteIdValue();


    public FindDeparturesByRouteIdParameter(int routeId) {
        setRouteId(routeId);
    }

    public int getRouteId() {
        return params.getRouteId();
    }

    public void setRouteId(int routeId) {
        this.params.setRouteId(routeId);
    }


}
