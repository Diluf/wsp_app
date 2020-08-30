package com.debugsire.wsp.Algos.POJOs;

public class MaterialPojos {
    private final String dia, len;
    private final int mat, unit;

    public MaterialPojos(int mat, String dia, int unit, String len) {
        this.mat = mat;
        this.dia = dia;
        this.unit = unit;
        this.len = len;
    }

    public String getDia() {
        return dia;
    }

    public String getLen() {
        return len;
    }

    public int getMat() {
        return mat;
    }

    public int getUnit() {
        return unit;
    }
}
