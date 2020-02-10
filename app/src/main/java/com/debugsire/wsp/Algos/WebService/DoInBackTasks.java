package com.debugsire.wsp.Algos.WebService;

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

    public void checkDropdownSynch(String response, String value) {
        try {

            JSONArray array = new JSONArray(response.trim());
            Log.d(TAG, "checkAsyncBack: " + array.length());

            if (face == MyConstants.ACTION_RESYNCH_DROP_LIST) {
                MyDB.setData("DELETE FROM wsp_droplist WHERE ref_section = '" + value + "'");
            }
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                MyDB.setData("INSERT INTO wsp_droplist VALUES (" +
                        " '" + object.getInt("id") + "', " +
                        " '" + object.getString("ref_section").trim() + "', " +
                        " '" + object.getString("value").trim() + "', " +
                        " '" + object.getString("display_label").trim() + "', " +
                        " '0', " +
                        " '" + Methods.getNowDateTime() + "' " +
                        ")");

                asyncWebService.myPublishProgress("Caching " + i + " of " + array.length());
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
                            " '" + Methods.configNull(jsonObject, "userName", "-") + "', " +
                            " '" + Methods.configNull(jsonObject, "dateTime_", "-") + "' " +
                            ")");
                } else if (key.equalsIgnoreCase(MyConstants.ACTION_GET_CONNECTION_DOWNLOADS)) {
                    deleteRow("connectionD", "CBONum", jsonObject.getString("cboId").trim());
                    asyncWebService.myPublishProgress("Caching Connection Details...");
                    MyDB.setData("INSERT INTO connectionD VALUES (" +
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
}
