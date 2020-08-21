package com.debugsire.wsp.Algos.POJOs;

public class MaterialPojos {
    private final String mat, dia, unit, len;

    public MaterialPojos(String mat, String dia, String unit, String len) {
        this.mat = mat;
        this.dia = dia;
        this.unit = unit;
        this.len = len;
    }


    public String getMat() {
        return mat;
    }

    public String getDia() {
        return dia;
    }

    public String getUnit() {
        return unit;
    }

    public String getLen() {
        return len;
    }
}
