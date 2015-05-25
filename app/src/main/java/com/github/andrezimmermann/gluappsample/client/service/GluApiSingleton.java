package com.github.andrezimmermann.gluappsample.client.service;

import com.github.andrezimmermann.gluappsample.server.api.GluApi;

/**
 * TODO: add javadoc
 */
public class GluApiSingleton {

    private static GluApi gluApi;

    public static GluApi getInstance() {
        if (gluApi == null) {
            gluApi = new GluApi();
        }
        return gluApi;
    }

}
