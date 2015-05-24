package com.github.andrezimmermann.gluappsample.server;


import com.github.andrezimmermann.gluappsample.server.converter.JsonConverter;
import com.github.andrezimmermann.gluappsample.server.converter.RequestDataConverter;
import com.github.andrezimmermann.gluappsample.server.data.FindRouteByNameParameter;

/**
 * Class that maps the REST calls
 */
public class GluApi {

    private RequestDataConverter converter = new JsonConverter();


    public Integer getRouteIdByName(String routeName) {

        try {
            RestConnection connection = new RestConnection<FindRouteByNameParameter>(Endpoint.FIND_ROUTE_ID, converter);

            FindRouteByNameParameter parameter = new FindRouteByNameParameter(routeName);

            connection.sendData(parameter);
        } catch (ServiceUnavaiableException e) {
            e.printStackTrace();
        } catch (ServiceUnkownError serviceUnkownError) {
            serviceUnkownError.printStackTrace();
        }


        return null;
    }
}
