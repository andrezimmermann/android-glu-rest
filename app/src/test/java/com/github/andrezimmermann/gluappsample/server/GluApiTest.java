package com.github.andrezimmermann.gluappsample.server;


import com.github.andrezimmermann.gluappsample.server.api.GluApi;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnavaiableException;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnkownError;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;
import com.github.andrezimmermann.gluappsample.shared.data.BusStop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GluApiTest {

    GluApi gluApi;

    @Before
    public void setUp() {
        gluApi = new GluApi();
    }

    @Test
    public void shouldQueryStopsByRouteId() throws ServiceUnavaiableException, ServiceUnkownError {
        List<BusStop> list = gluApi.getStopsByRouteId(35);
        Assert.assertEquals("Should return some data", 12, list.size());
    }


    @Test
    public void shouldQueryRouteId() throws ServiceUnavaiableException, ServiceUnkownError {
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
