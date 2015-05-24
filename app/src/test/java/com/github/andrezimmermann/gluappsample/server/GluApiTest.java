package com.github.andrezimmermann.gluappsample.server;


import com.github.andrezimmermann.gluappsample.server.api.GluApi;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnavaiableException;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnkownError;
import com.github.andrezimmermann.gluappsample.shared.data.LinhaOnibus;

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
    public void shouldQueryRouteId() throws ServiceUnavaiableException, ServiceUnkownError {
        List<LinhaOnibus> retorno = gluApi.getRouteIdByName("Delminda Silveira");
        Assert.assertEquals("Should return some data", 2, retorno.size());

        retorno = gluApi.getRouteIdByName("Mauro Ramos");
        Assert.assertEquals("Should return some data", 1, retorno.size());

        retorno = gluApi.getRouteIdByName("Governador Irineu Bornhausen");
        Assert.assertEquals("Should return some data", 3, retorno.size());

        retorno = gluApi.getRouteIdByName("Deputado Antônio Edu Vieira");
        Assert.assertEquals("Should return some data", 1, retorno.size());

        retorno = gluApi.getRouteIdByName("@@@@");
        Assert.assertEquals("Should return some data", 0, retorno.size());
    }

}
