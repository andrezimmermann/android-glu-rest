package com.github.andrezimmermann.gluappsample.server.data;

/**
 * TODO: add javadoc
 */
public class FindDeparturesByRouteIdParameter extends RequestData<FindDeparturesByRouteIdResponse> {

    public FindDeparturesByRouteIdParameter(int routeId) {
        setRouteId(routeId);
    }

    public int getRouteId() {
        return getFromParameter("routeId");
    }

    public void setRouteId(int routeId) {
        getParams().put("routeId", routeId);
    }


}
