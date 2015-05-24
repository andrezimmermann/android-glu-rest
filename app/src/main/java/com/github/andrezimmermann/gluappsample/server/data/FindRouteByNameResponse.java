package com.github.andrezimmermann.gluappsample.server.data;

import java.util.Date;
import java.util.List;

/**
 * Response of a {@link FindRouteByNameParameter}
 */
public class FindRouteByNameResponse implements ResponseData {

    private List<FindRoundByNameRow> rows;
    private int rowsAffected;

    public List<FindRoundByNameRow> getRows() {
        return rows;
    }

    public void setRows(List<FindRoundByNameRow> rows) {
        this.rows = rows;
    }

    public int getRowsAffected() {
        return rowsAffected;
    }

    public void setRowsAffected(int rowsAffected) {
        this.rowsAffected = rowsAffected;
    }

    public class FindRoundByNameRow {

        private int id;
        private String shortName;
        private String longName;
        private Date lastModifiedDate;
        private int agencyId;

        public int getAgencyId() {
            return agencyId;
        }

        public void setAgencyId(int agencyId) {
            this.agencyId = agencyId;
        }

        public Date getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(Date lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }

        public String getLongName() {
            return longName;
        }

        public void setLongName(String longName) {
            this.longName = longName;
        }

        public String getShortName() {

            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
