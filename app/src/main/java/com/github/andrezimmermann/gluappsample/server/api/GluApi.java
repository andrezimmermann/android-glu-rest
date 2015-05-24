package com.github.andrezimmermann.gluappsample.server.api;


import com.github.andrezimmermann.gluappsample.server.api.command.FindByNameCommand;
import com.github.andrezimmermann.gluappsample.server.api.command.FindDeparturesByRouteId;
import com.github.andrezimmermann.gluappsample.server.api.command.FindStopsByRouteId;
import com.github.andrezimmermann.gluappsample.server.api.error.ServiceException;
import com.github.andrezimmermann.gluappsample.server.converter.DataConverter;
import com.github.andrezimmermann.gluappsample.server.converter.JsonConverter;
import com.github.andrezimmermann.gluappsample.server.data.FindDeparturesByRouteIdParameter;
import com.github.andrezimmermann.gluappsample.server.data.FindDeparturesByRouteIdResponse;
import com.github.andrezimmermann.gluappsample.server.data.FindRouteByNameParameter;
import com.github.andrezimmermann.gluappsample.server.data.FindRouteByNameResponse;
import com.github.andrezimmermann.gluappsample.server.data.FindStopsByRouteIdParameter;
import com.github.andrezimmermann.gluappsample.server.data.FindStopsByRouteIdResponse;
import com.github.andrezimmermann.gluappsample.shared.data.BusDeparture;
import com.github.andrezimmermann.gluappsample.shared.data.BusLine;
import com.github.andrezimmermann.gluappsample.shared.data.BusStop;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that maps the REST calls
 */
public class GluApi {

    private DataConverter converter = new JsonConverter();


    public List<BusLine> getRouteIdByName(String routeName) throws ServiceException {
        FindByNameCommand command = new FindByNameCommand(Endpoint.FIND_ROUTE_ID, converter);
        FindRouteByNameParameter parameter = new FindRouteByNameParameter(routeName);
        FindRouteByNameResponse responseData = command.sendData(parameter);
        List<FindRouteByNameResponse.FindRouteByNameRow> rows = responseData.getRows();

        return generateReturnForId(rows);
    }

    private List<BusLine> generateReturnForId(List<FindRouteByNameResponse.FindRouteByNameRow> rows) {
        if (rows == null) {
            return Collections.emptyList();
        } else {
            ArrayList<BusLine> list = new ArrayList<>(rows.size());
            for (FindRouteByNameResponse.FindRouteByNameRow row : rows) {
                BusLine busLine = new BusLine();
                busLine.setId(row.getId());
                busLine.setNumberId(row.getShortName());
                busLine.setDescription(row.getLongName());
                list.add(busLine);
            }
            return list;
        }
    }

    private List<BusStop> generateReturnForStop(List<FindStopsByRouteIdResponse.FindStopsByRouteIdRow> rows) {
        if (rows == null) {
            return Collections.emptyList();
        } else {
            ArrayList<BusStop> list = new ArrayList<>(rows.size());
            for (FindStopsByRouteIdResponse.FindStopsByRouteIdRow row : rows) {
                BusStop busStop = new BusStop();
                busStop.setId(row.getId());
                busStop.setName(row.getName());
                busStop.setSequence(row.getSequence());
                list.add(busStop);
            }
            return list;
        }
    }

    public List<BusStop> getStopsByRouteId(int routeId) throws ServiceException {
        FindStopsByRouteId command = new FindStopsByRouteId(Endpoint.FIND_STOP, converter);
        FindStopsByRouteIdParameter parameter = new FindStopsByRouteIdParameter(routeId);
        FindStopsByRouteIdResponse responseData = command.sendData(parameter);
        List<FindStopsByRouteIdResponse.FindStopsByRouteIdRow> rows = responseData.getRows();

        return generateReturnForStop(rows);

    }

    public List<BusDeparture> getDeparturesByRouteId(int routeId) throws ServiceException {
        FindDeparturesByRouteId command = new FindDeparturesByRouteId(Endpoint.FIND_DEPARTURE, converter);
        FindDeparturesByRouteIdParameter parameter = new FindDeparturesByRouteIdParameter(routeId);
        FindDeparturesByRouteIdResponse responseData = command.sendData(parameter);

        List<FindDeparturesByRouteIdResponse.FindDeparturesByRouteIdRow> rows = responseData.getRows();
        return generateReturnForDepartures(rows);
    }

    private List<BusDeparture> generateReturnForDepartures(List<FindDeparturesByRouteIdResponse.FindDeparturesByRouteIdRow> rows) {
        if (rows == null) {
            return Collections.emptyList();
        } else {
            ArrayList<BusDeparture> list = new ArrayList<>(rows.size());
            for (FindDeparturesByRouteIdResponse.FindDeparturesByRouteIdRow row : rows) {
                BusDeparture busStop = new BusDeparture();
                busStop.setId(row.getId());
                busStop.setCalendar(row.getCalendar());
                busStop.setTime(row.getTime());
                list.add(busStop);
            }
            return list;
        }
    }
}
