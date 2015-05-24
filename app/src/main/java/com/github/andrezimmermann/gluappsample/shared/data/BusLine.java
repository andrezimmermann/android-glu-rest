package com.github.andrezimmermann.gluappsample.shared.data;

/**
 * TODO: add javadoc
 */
public class BusLine {

    private int id;
    /**
     * Numerical Identification of the bus line
     */
    private String numberId;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumberId() {
        return numberId;
    }

    public void setNumberId(String numberId) {
        this.numberId = numberId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
