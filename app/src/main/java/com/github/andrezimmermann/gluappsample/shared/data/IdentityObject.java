package com.github.andrezimmermann.gluappsample.shared.data;

/**
 * TODO: add javadoc
 */
public abstract class IdentityObject {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdentityObject that = (IdentityObject) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}

