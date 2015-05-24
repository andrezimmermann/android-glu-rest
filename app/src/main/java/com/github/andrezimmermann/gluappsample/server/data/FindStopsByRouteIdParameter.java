package com.github.andrezimmermann.gluappsample.server.data;

/**
 * TODO: add javadoc
 */
public class FindStopsByRouteIdParameter extends RequestData<FindStopsByRouteIdResponse> {

    public FindStopsByRouteIdParameter(int routeId) {
        setRouteId(routeId);
    }

    public int getRouteId() {
        return getFromParameter("routeId");
    }

    public void setRouteId(int routeId) {
        getParams().put("routeId", routeId);
    }


}
