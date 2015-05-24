package com.github.andrezimmermann.gluappsample.server.api.command;

import com.github.andrezimmermann.gluappsample.server.api.Endpoint;
import com.github.andrezimmermann.gluappsample.server.converter.DataConverter;
import com.github.andrezimmermann.gluappsample.server.data.FindStopsByRouteIdParameter;
import com.github.andrezimmermann.gluappsample.server.data.FindStopsByRouteIdResponse;

/**
 * TODO: add javadoc
 */
public class FindStopsByRouteId extends RestConnection<FindStopsByRouteIdParameter, FindStopsByRouteIdResponse> {

    public FindStopsByRouteId(Endpoint endpoint, DataConverter converter) {
        super(endpoint, converter);
    }

}
