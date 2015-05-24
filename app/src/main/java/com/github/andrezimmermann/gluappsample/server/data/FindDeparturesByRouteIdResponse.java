package com.github.andrezimmermann.gluappsample.server.data;

import java.util.List;

/**
 * TODO: add javadoc
 */
public class FindDeparturesByRouteIdResponse implements ResponseData {

    private List<FindDeparturesByRouteIdRow> rows;
    private int rowsAffected;

    public int getRowsAffected() {
        return rowsAffected;
    }

    public List<FindDeparturesByRouteIdRow> getRows() {
        return rows;
    }

    public class FindDeparturesByRouteIdRow {

        private String calendar;
        private int id;
        private String time;


        public String getCalendar() {
            return calendar;
        }

        public void setCalendar(String calendar) {
            this.calendar = calendar;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

}
