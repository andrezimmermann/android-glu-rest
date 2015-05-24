package com.github.andrezimmermann.gluappsample.server.api.command;

import com.github.andrezimmermann.gluappsample.server.api.Endpoint;
import com.github.andrezimmermann.gluappsample.server.converter.DataConverter;
import com.github.andrezimmermann.gluappsample.server.data.FindDeparturesByRouteIdParameter;
import com.github.andrezimmermann.gluappsample.server.data.FindDeparturesByRouteIdResponse;

/**
 * TODO: add javadoc
 */
public class FindDeparturesByRouteId extends RestConnection<FindDeparturesByRouteIdParameter, FindDeparturesByRouteIdResponse> {

    public FindDeparturesByRouteId(Endpoint endpoint, DataConverter converter) {
        super(endpoint, converter);
    }

}