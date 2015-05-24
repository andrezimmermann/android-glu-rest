package com.github.andrezimmermann.gluappsample.server.data;

import java.util.List;

/**
 * TODO: add javadoc
 */
public class FindStopsByRouteIdResponse implements ResponseData {
    private List<FindStopsByRouteIdRow> rows;
    private int rowsAffected;

    public int getRowsAffected() {
        return rowsAffected;
    }

    public List<FindStopsByRouteIdRow> getRows() {
        return rows;
    }

    public class FindStopsByRouteIdRow {

        private String name;
        private int id;
        private int sequence;
        private int routeId;

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public int getSequence() {
            return sequence;
        }

        public int getRouteId() {
            return routeId;
        }
    }
}
