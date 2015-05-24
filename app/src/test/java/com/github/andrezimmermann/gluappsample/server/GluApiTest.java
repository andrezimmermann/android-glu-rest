package com.github.andrezimmermann.gluappsample.server;


import org.junit.Before;
import org.junit.Test;

public class GluApiTest {

    GluApi gluApi;

    @Before
    public void setUp() {
        gluApi = new GluApi();

    }

    @Test
    public void shouldQueryRouteId() {
        Integer routeId = gluApi.getRouteIdByName("Delminda Silveira");

    }

}
