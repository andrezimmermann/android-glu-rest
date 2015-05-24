package com.github.andrezimmermann.gluappsample.server.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Marker interface for Request Data to be consumed by the RestClient
 */
public abstract class RequestData<R extends ResponseData> implements ConvertableData {

    Map<String, Object> params = new HashMap<>();

    protected Map<String, Object> getParams() {
        return params;
    }

    protected <T> T getFromParameter(String parameterName) {
        if (getParams().containsKey("parameterName")) {
            return (T) getParams().get("parameterName");
        } else {
            return null;
        }
    }


}
