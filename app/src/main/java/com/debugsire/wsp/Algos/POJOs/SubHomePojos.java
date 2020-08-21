package com.debugsire.wsp.Algos.POJOs;

public class SubHomePojos {
    private final String title, tableName;
    private boolean repeat;

    public SubHomePojos(String title, String tableName, boolean repeat) {
        this.title = title;
        this.tableName = tableName;
        this.repeat = repeat;
    }

    public String getTitle() {
        return title;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isRepeat() {
        return repeat;
    }
}
