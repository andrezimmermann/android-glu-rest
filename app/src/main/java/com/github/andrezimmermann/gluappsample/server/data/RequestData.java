package com.github.andrezimmermann.gluappsample.server.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Marker interface for Request Data to be consumed by the RestClient
 */
public abstract class RequestData {

    Map<String, Object> params = new HashMap<>();

    protected Map<String, Object> getParams() {
        return params;
    }


}
