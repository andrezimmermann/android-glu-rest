package com.github.andrezimmermann.gluappsample.server.api.command;

import com.github.andrezimmermann.gluappsample.server.api.Endpoint;
import com.github.andrezimmermann.gluappsample.server.converter.DataConverter;
import com.github.andrezimmermann.gluappsample.server.data.FindRouteByNameParameter;
import com.github.andrezimmermann.gluappsample.server.data.FindRouteByNameResponse;

/**
 * TODO: add javadoc
 */
public class FindByNameCommand extends RestConnection<FindRouteByNameParameter, FindRouteByNameResponse> {

    public FindByNameCommand(Endpoint endpoint, DataConverter converter) {
        super(endpoint, converter);
    }
}
