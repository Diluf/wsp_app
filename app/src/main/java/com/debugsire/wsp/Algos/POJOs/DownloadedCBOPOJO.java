package com.debugsire.wsp.Algos.POJOs;

public class DownloadedCBOPOJO {
    private String cboNum, cboName, street, road, village, town, dateTime;


    public DownloadedCBOPOJO(String cboNum, String cboName, String street, String road, String village, String town, String dateTime) {
        this.cboNum = cboNum;
        this.cboName = cboName;
        this.street = street;
        this.road = road;
        this.village = village;
        this.town = town;
        this.dateTime = dateTime;
    }

    public String getCboNum() {
        return cboNum;
    }

    public String getCboName() {
        return cboName;
    }

    public String getStreet() {
        return street;
    }

    public String getRoad() {
        return road;
    }

    public String getVillage() {
        return village;
    }

    public String getTown() {
        return town;
    }

    public String getDateTime() {
        return dateTime;
    }
}
