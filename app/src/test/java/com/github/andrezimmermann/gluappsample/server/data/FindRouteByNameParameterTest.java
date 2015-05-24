package com.github.andrezimmermann.gluappsample.server.data;

import com.github.andrezimmermann.gluappsample.server.converter.JsonConverter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FindRouteByNameParameterTest {

    private JsonConverter converter;

    @Before
    public void setUpOnce() {
        converter = new JsonConverter();
    }

    @Test
    public void shouldOutputValidJSON() {
        FindRouteByNameParameter nameParameter = new FindRouteByNameParameter("TESTE");
        String asString = converter.fromData(nameParameter);
        FindRouteByNameParameter requestData = converter.toData(asString, FindRouteByNameParameter.class);
        assertEquals("Should be same!", nameParameter.getStopName(), requestData.getStopName());
    }

}