package com.debugsire.wsp.Algos.POJOs;

public class HomePojos {
    private final String title, dbName;

    public HomePojos(String title, String dbName) {
        this.title = title;
        this.dbName = dbName;
    }

    public String getTitle() {
        return title;
    }

    public String getDbName() {
        return dbName;
    }
}
