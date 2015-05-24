package com.github.andrezimmermann.gluappsample.server;


import com.github.andrezimmermann.gluappsample.server.api.GluApi;
import com.github.andrezimmermann.gluappsample.shared.data.BusDeparture;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;
import com.github.andrezimmermann.gluappsample.shared.data.BusStop;
import com.github.andrezimmermann.gluappsample.shared.error.ServiceException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GluApiTest {

    public static final int ROUTE_ID_INVALID = -1;
    public static final int ROUTE_ID_VALID = 35;
    private GluApi gluApi;

    @Before
    public void setUp() {
        gluApi = new GluApi();
    }


    public void shouldQueryStopsByRouteId() throws ServiceException {
        List<BusStop> list = gluApi.getStopsByRouteId(ROUTE_ID_VALID);
        Assert.assertEquals("Should return some data", 12, list.size());


    }

    @Test
    public void shouldQueryInvalidStopsByRouteId() throws ServiceException {
        //Given Invalid RouteID
        List<BusStop> list = gluApi.getStopsByRouteId(ROUTE_ID_INVALID);
        //Then
        Assert.assertEquals("Should return no data", ROUTE_ID_INVALID, list.size());
    }

    @Test
    public void shouldQueryDeparturesByRouteId() throws ServiceException {
        List<BusDeparture> list = gluApi.getDeparturesByRouteId(ROUTE_ID_VALID);
        Assert.assertEquals("Should return some data", 104, list.size());


        list = gluApi.getDeparturesByRouteId(ROUTE_ID_INVALID);
        Assert.assertEquals("Should return no data", 0, list.size());
    }
    @Test
    public void shouldQueryRouteId() throws ServiceException {
        List<BusLine> list = gluApi.getRouteIdByName("Delminda Silveira");
        Assert.assertEquals("Should return some data", 2, list.size());

        list = gluApi.getRouteIdByName("Mauro Ramos");
        Assert.assertEquals("Should return some data", 1, list.size());

        list = gluApi.getRouteIdByName("Governador Irineu Bornhausen");
        Assert.assertEquals("Should return some data", 3, list.size());

        list = gluApi.getRouteIdByName("Deputado Antônio Edu Vieira");
        Assert.assertEquals("Should return some data", 1, list.size());

        list = gluApi.getRouteIdByName("@@@@");
        Assert.assertEquals("Should return some data", 0, list.size());
    }

}
