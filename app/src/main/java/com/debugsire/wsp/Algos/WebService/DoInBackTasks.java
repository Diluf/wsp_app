package com.debugsire.wsp.Algos.WebService;

import android.database.Cursor;
import android.util.Log;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DoInBackTasks {
    private static final String TAG = "DoInBackTasks";
    int face;
    AsyncWebService asyncWebService;

    public DoInBackTasks(int face, AsyncWebService asyncWebService) {
        this.face = face;
        this.asyncWebService = asyncWebService;

    }

    public void checkDropdownSynch(String response, String value, int face) {
        try {

            JSONArray array = new JSONArray(response.trim());
            Log.d(TAG, "checkAsyncBack: " + array.length());

            if (this.face == MyConstants.ACTION_RESYNCH_DROP_LIST) {
                JSONObject jsonObject = new JSONObject(value);
                MyDB.setData("DELETE FROM wsp_droplist WHERE ref_section = '" + jsonObject.getString("ref_section").trim() + "'");
            } else {
                MyDB.setData("DELETE FROM wsp_droplist");
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                checkAndUpdateColumn(object);
                MyDB.setData("INSERT INTO wsp_droplist VALUES (" +
                        " '" + object.getInt("id") + "', " +
                        " '" + object.getString("ref_section").trim() + "', " +
                        " '" + object.getString("value").trim() + "', " +
                        " '" + object.getString("display_label").trim() + "', " +
                        " '0', " +
                        " '" + Methods.getNowDateTime() + "' " +
                        ")");

                if (face == MyConstants.ACTION_RESYNCH_BEFORE_UPLOAD) {
                    asyncWebService.myPublishProgress("Resynchronizing " + i + " of " + array.length());

                } else {
                    asyncWebService.myPublishProgress("Caching " + i + " of " + array.length());

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void checkDSDandCBO(String response) throws Exception {
        asyncWebService.myPublishProgress("Fetching DS Division values...");
        Methods methods = new Methods();
        JSONObject jsonObject = new JSONArray(response.trim()).getJSONObject(0);
        JSONObject locas = new JSONObject(jsonObject.getString("locas"));

        JSONArray array = locas.getJSONArray("locations");
        MyDB.setData("DELETE FROM locations");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            asyncWebService.myPublishProgress("Caching " + i + " of " + array.length());
//            if (!methods.isAvailOnDB("locations", "dsd", object.getString("dsd").trim())) {
            MyDB.setData("INSERT INTO locations VALUES (" +
                    " '" + Methods.configNull(object, "idGnd", "-") + "', " +
                    " '" + Methods.configNull(object, "idDsd", "-") + "', " +
                    " '" + Methods.configNull(object, "dsd", "-") + "', " +
                    " '" + Methods.configNull(object, "gnd", "-") + "', " +
                    " '" + Methods.getNowDateTime() + "' " +
                    ")");
            Log.d(TAG, "checkDSDandCBO: " + object);
//            }
        }
//
        array = jsonObject.getJSONArray("other");
        MyDB.setData("DELETE FROM cboB");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            JSONArray cboJsonArray = object.getJSONArray("cboDetails");
            for (int j = 0; j < cboJsonArray.length(); j++) {
                JSONObject cboJsonObject = cboJsonArray.getJSONObject(j);
                asyncWebService.myPublishProgress("Caching CBO Details for " + object.getString("dsd").trim() + i + " of " + array.length());
                if (!methods.isAvailOnDB("cboB", "CBONum", cboJsonObject.getString("cboId").trim())) {
                    MyDB.setData("INSERT INTO cboB VALUES (" +
                            " '" + Methods.configNull(cboJsonObject, "id", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "cboId", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "name", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "street", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "road", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "village", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "town", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "lon", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "lat", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "height", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "acc", "-") + "', " +
                            " '" + Methods.configNull(cboJsonObject, "userName", "-") + "', " +
                            " '" + Methods.configNull(object, "idDsd", "-") + "', " +
                            " '0', " +
                            " '" + Methods.getNowDateTime() + "' " +
                            ")");
                }
            }


//            }
        }

    }

    public void checkDownloadsByCbo(String response) throws JSONException {
        JSONArray jsonArray = new JSONArray(response);
        boolean alreadyPerformed = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int message = jsonObject.getJSONObject("user").getInt("message");
            if (message == MyConstants.VALID_USER || message == MyConstants.EXPANDED_USER) {
                String key = jsonObject.getString("key");
                if (key.equalsIgnoreCase(MyConstants.ACTION_GET_CBO_DETAILS_DOWNLOADS)) {
                    deleteRow("cboBasicDetails", "CBONum", jsonObject.getString("cboId").trim());
                    asyncWebService.myPublishProgress("Caching CBO Details...");
                    MyDB.setData("INSERT INTO cboBasicDetails VALUES (" +
                            " '" + Methods.configNull(jsonObject, "id", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "cboId", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "name", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "street", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "road", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "village", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "town", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "lon", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "lat", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "height", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "acc", "-") + "', " +
                            " '0', " +
                            " '" + Methods.configNull(jsonObject, "userName", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "dateTime_", "-") + "' " +
                            ")");
                } else if (key.equalsIgnoreCase(MyConstants.ACTION_GET_CONNECTION_DOWNLOADS)) {
                    deleteRow("connectionD", "CBONum", jsonObject.getString("cboId").trim());
                    asyncWebService.myPublishProgress("Caching Connection Details...");
                    MyDB.setData("INSERT INTO connectionD VALUES (" +
                            " '" + Methods.configNull(jsonObject, "id", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "cboId", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "domestic", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "religious", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "commercial", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "schools", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "healthcenter", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "governement", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "other", "-").trim() + "', " +
                            " '" + Methods.getNowDateTime() + "' " +
                            ")");
                } else if (key.equalsIgnoreCase(MyConstants.ACTION_GET_COVERAGE_DOWNLOADS)) {
                    asyncWebService.myPublishProgress("Caching Coverage Information...");
                    if (!alreadyPerformed) {
                        deleteRow("coverageInfo", "CBONum", jsonObject.getString("cboId").trim());
                        alreadyPerformed = true;
                    }
                    MyDB.setData("INSERT INTO coverageInfo VALUES (" +
                            " '" + Methods.configNull(jsonObject, "id", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "cboId", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "village", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "idGnd", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "noOfHouse", "-") + "', " +
                            " '" + Methods.getNowDateTime() + "' " +
                            ")");
                }

            } else {
                break;
            }
        }

    }

    private void deleteRow(String tableName, String where, String value) {
        MyDB.setData("DELETE FROM " + tableName + " WHERE " + where + " = '"
                + value + "'");
    }

    public void saveLocationValues(String response) throws JSONException {
        JSONArray array = new JSONObject(response).getJSONArray("locations");
        MyDB.setData("DELETE FROM locations");
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            asyncWebService.myPublishProgress("Caching " + i + " of " + array.length());
//            if (!methods.isAvailOnDB("locations", "dsd", object.getString("dsd").trim())) {
            MyDB.setData("INSERT INTO locations VALUES (" +
                    " '" + Methods.configNull(object, "idGnd", "-") + "', " +
                    " '" + Methods.configNull(object, "idDsd", "-") + "', " +
                    " '" + Methods.configNull(object, "dsd", "-") + "', " +
                    " '" + Methods.configNull(object, "gnd", "-") + "', " +
                    " '" + Methods.getNowDateTime() + "' " +
                    ")");
            Log.d(TAG, "checkDSDandCBO: " + object);
//            }
        }
    }

    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //
    //


    private void checkAndUpdateColumn(JSONObject object) {
        try {
            int oldId = object.getInt("oldId");
            if (oldId != -1) {
                String tableName = null, columnName = null;
                boolean isSingle = true;
                String ref_section = object.getString("ref_section");
                if (ref_section.equalsIgnoreCase(MyConstants.DL_CBOBASICDETAILS_MANAGEMENT_OF_WSS)) {
                    tableName = "cboBasicDetailsFilled";
                    columnName = "manWss";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_EXISTINGQA_AWARENESS)) {
                    tableName = "existingQA";
                    columnName = "aow";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CATCHMENT_NATURE)) {
                    tableName = "catchment";
                    columnName = "nature";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CATCHMENT_RISKS_OF_WATER)) {
                    tableName = "catchment";
                    columnName = "riskOf";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CATCHMENT_RISKS_FOR_SOURCE)) {
                    tableName = "catchment";
                    columnName = "riskFor";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CATCHMENT_ISSUES)) {
                    tableName = "catchment";
                    columnName = "issues";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CATCHMENT_RISK_MITIGATION)) {
                    tableName = "catchment";
                    columnName = "riskMit";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_TREATMENTSYSTEM_SPECIAL)) {
                    tableName = "treatment";
                    columnName = "spec";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_TREATMENTSYSTEM_WATER_Q)) {
                    tableName = "treatment";
                    columnName = "wq";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_TREATMENTSYSTEM_CURRENT)) {
                    tableName = "treatment";
                    columnName = "currentR";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_TREATMENTSYSTEM_RISK_MITIGATION)) {
                    tableName = "treatment";
                    columnName = "riskMit";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_DISTRIBUTION_IDENTIFIED)) {
                    tableName = "dist";
                    columnName = "ident";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_DISTRIBUTION_RISK_MITIGATION)) {
                    tableName = "dist";
                    columnName = "riskMit";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_DISTRIBUTION_OVERALL)) {
                    tableName = "dist";
                    columnName = "overAll";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS)) {
                    tableName = "clim";
                    columnName = "how";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY)) {
                    tableName = "clim";
                    columnName = "they";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_DROUGHT)) {
                    tableName = "clim";
                    columnName = "effeD";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_FLOOD)) {
                    tableName = "clim";
                    columnName = "effeF";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS)) {
                    tableName = "clim";
                    columnName = "reas";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES)) {
                    tableName = "clim";
                    columnName = "whatAreS";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER)) {
                    tableName = "clim";
                    columnName = "whatAreT";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_OPTIONS_TO_RED)) {
                    tableName = "clim";
                    columnName = "water";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT)) {
                    tableName = "clim";
                    columnName = "drought";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_GOVERNANCE_FAIR_LAND)) {
                    tableName = "gov";
                    columnName = "fair";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_GOVERNANCE_INCLUSIVE)) {
                    tableName = "gov";
                    columnName = "inc";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_GOVERNANCE_TRANS)) {
                    tableName = "gov";
                    columnName = "tra";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS)) {
                    tableName = "gov";
                    columnName = "theReas";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_GOVERNANCE_ONYES_IS_THERE)) {
                    tableName = "gov";
                    columnName = "pot";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_WATER_AD_WHAT_ARE)) {
                    tableName = "waterAd";
                    columnName = "ifNo";
                    isSingle = false;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_WATER_AD_PLEASE_MENTION)) {
                    tableName = "waterAd";
                    columnName = "ifYes";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_WATER_QU_FOR_WHAT)) {
                    tableName = "waterQ";
                    columnName = "forW";
                    isSingle = true;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_WATER_QU_PLEASE_PRO)) {
                    tableName = "waterQ";
                    columnName = "pleaseP";
                    isSingle = true;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_WATER_QU_WHAT_ARE)) {
                    tableName = "waterQ";
                    columnName = "whatAre";
                    isSingle = true;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_WATER_QU_IF_WATER)) {
                    tableName = "waterQ";
                    columnName = "ifWater";
                    isSingle = true;

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE)) {
                    tableName = "houseHold";
                    columnName = "howDoYouS";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_HOUSE_HOLD_HOW_OFTEN)) {
                    tableName = "houseHold";
                    columnName = "howOften";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN)) {
                    tableName = "houseHold";
                    columnName = "howDoYouC";

                } else if (ref_section.equalsIgnoreCase(MyConstants.DL_WATER_SAVING_ON_YES)) {
                    tableName = "waterS";
                    columnName = "ifYes";
                    isSingle = false;

                }

                if (tableName != null && columnName != null) {
                    Cursor cursor = MyDB.getData("SELECT * FROM " + tableName);
                    while (cursor.moveToNext()) {
                        String newValue = null;
                        if (isSingle) {
                            if (oldId == cursor.getInt(cursor.getColumnIndex(columnName))) {
                                newValue = object.getString("value").trim();
                            }
                        } else {
                            if (cursor.getString(cursor.getColumnIndex(columnName)) != null && !cursor.getString(cursor.getColumnIndex(columnName)).trim().isEmpty()) {
                                JSONArray jsonArray = new JSONArray(cursor.getString(cursor.getColumnIndex(columnName)));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    if (oldId == jsonArray.getInt(i)) {
                                        jsonArray.put(i, object.getInt("value"));
                                        newValue = jsonArray.toString();
                                        break;
                                    }
                                }
                            }
                        }
                        if (newValue != null) {
                            MyDB.setData("UPDATE " + tableName + " SET " + columnName + " = '" + newValue + "' WHERE CBONum = '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "' AND dateTime_ = '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'");
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
