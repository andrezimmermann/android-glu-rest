package com.github.andrezimmermann.gluappsample.server.data;

import android.test.suitebuilder.annotation.Smoke;

import com.github.andrezimmermann.gluappsample.server.converter.JsonConverter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


@Smoke
public class JsonConverterTest {

    private JsonConverter converter;

    @Before
    public void setUp() {
        converter = new JsonConverter();
    }

    @Test
    public void shouldOutputValidJSONForByRoute() {
        FindRouteByNameParameter nameParameter = new FindRouteByNameParameter("TESTE");
        String asString = converter.fromData(nameParameter);
        FindRouteByNameParameter requestData = converter.toData(asString, FindRouteByNameParameter.class);
        assertEquals("Should be same!", nameParameter.getStopName(), requestData.getStopName());
    }


    @Test
    public void shouldOutputValidJSONForByDepartures() {
        FindDeparturesByRouteIdParameter parameter = new FindDeparturesByRouteIdParameter(1);
        String asString = converter.fromData(parameter);
        FindDeparturesByRouteIdParameter converted = converter.toData(asString, FindDeparturesByRouteIdParameter.class);
        assertEquals("Should be same!", parameter.getRouteId(), converted.getRouteId());
    }

    @Test
    public void shouldOutputValidJSONForByStop() {
        FindStopsByRouteIdParameter nameParameter = new FindStopsByRouteIdParameter(1);
        String asString = converter.fromData(nameParameter);
        FindStopsByRouteIdParameter requestData = converter.toData(asString, FindStopsByRouteIdParameter.class);
        assertEquals("Should be same!", nameParameter.getRouteId(), requestData.getRouteId());
    }

}