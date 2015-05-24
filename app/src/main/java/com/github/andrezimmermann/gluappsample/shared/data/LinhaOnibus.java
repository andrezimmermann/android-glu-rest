package com.github.andrezimmermann.gluappsample.shared.data;

/**
 * TODO: add javadoc
 */
public class LinhaOnibus {

    private int id;
    /**
     * Identificação númerica do Onibus
     */
    private String codigo;
    private String descrica;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescrica() {
        return descrica;
    }

    public void setDescrica(String descrica) {
        this.descrica = descrica;
    }
}
