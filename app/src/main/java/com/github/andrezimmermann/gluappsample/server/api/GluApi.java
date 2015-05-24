package com.github.andrezimmermann.gluappsample.server.api;


import com.github.andrezimmermann.gluappsample.server.api.command.FindByNameCommand;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnavaiableException;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceUnkownError;
import com.github.andrezimmermann.gluappsample.server.converter.DataConverter;
import com.github.andrezimmermann.gluappsample.server.converter.JsonConverter;
import com.github.andrezimmermann.gluappsample.server.data.FindRouteByNameParameter;
import com.github.andrezimmermann.gluappsample.server.data.FindRouteByNameResponse;
import com.github.andrezimmermann.gluappsample.shared.data.LinhaOnibus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that maps the REST calls
 */
public class GluApi {

    private DataConverter converter = new JsonConverter();


    public List<LinhaOnibus> getRouteIdByName(String routeName) throws ServiceUnavaiableException, ServiceUnkownError {


        FindByNameCommand command = new FindByNameCommand(Endpoint.FIND_ROUTE_ID, converter);

        FindRouteByNameParameter parameter = new FindRouteByNameParameter(routeName);

        FindRouteByNameResponse responseData = command.sendData(parameter);


        List<FindRouteByNameResponse.FindRoundByNameRow> rows = responseData.getRows();

        if (rows == null) {
            return Collections.emptyList();
        } else {
            ArrayList<LinhaOnibus> retorno = new ArrayList<>(rows.size());
            for (FindRouteByNameResponse.FindRoundByNameRow row : rows) {
                LinhaOnibus linhaOnibus = new LinhaOnibus();
                linhaOnibus.setId(row.getId());
                linhaOnibus.setCodigo(row.getShortName());
                linhaOnibus.setDescrica(row.getLongName());
                retorno.add(linhaOnibus);
            }
            return retorno;
        }


    }
}
