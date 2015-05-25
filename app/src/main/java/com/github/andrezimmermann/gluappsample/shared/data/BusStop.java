package com.github.andrezimmermann.gluappsample.shared.data;

/**
 * TODO: add javadoc
 */
public class BusStop extends IdentityObject {

    private int sequence;
    private String name;


    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
