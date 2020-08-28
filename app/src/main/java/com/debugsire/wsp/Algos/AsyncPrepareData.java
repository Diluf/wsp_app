package com.debugsire.wsp.Algos;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.debugsire.wsp.AvailableCBO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Date;

public class AsyncPrepareData extends AsyncTask<String, String, String> {

    private static final String TAG = "AsyncPrepareData----";
    Context context;
    public ProgressDialog progressDialog;
    Methods methods;
    int face;

    public AsyncPrepareData(Context context, ProgressDialog progressDialog, Methods methods, int face) {
        this.context = context;
        this.progressDialog = progressDialog;
        this.methods = methods;
        this.face = face;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (face == MyConstants.ACTION_UPLOAD_1) {
            progressDialog.setMessage("Preparing to upload...");
        } else if (face == MyConstants.ACTION_UPLOAD_2) {
            progressDialog.setMessage("Almost done...");

        } else if (face == MyConstants.ACTION_REMOVE_OFFLINE_DATA) {
            progressDialog.setMessage("Finishing...");

        }
    }

    @Override
    protected String doInBackground(String... strings) {
        if (face == MyConstants.ACTION_UPLOAD_1 || face == MyConstants.ACTION_UPLOAD_2) {
            return getGeneratedQueries().toString();

        } else if (face == MyConstants.ACTION_REMOVE_OFFLINE_DATA) {
            finishUpload();

        }

        return "";
    }

    private void finishUpload() {
        ArrayList<String> tableArrayList = methods.getAllTableNames(true);
        tableArrayList.remove("distTypes");
        for (String tableName :
                tableArrayList) {
            if (tableName.equalsIgnoreCase("dist")) {
                Cursor cursor = methods.getCursorBySelectedCBONum(context, tableName);
                while (cursor.moveToNext()) {
                    MyDB.setData("DELETE FROM distTypes WHERE uniqueId = '" + cursor.getString(cursor.getColumnIndex("uniqueId")) + "'");
                }
            }
            MyDB.setData("DELETE FROM " + tableName + " WHERE CBONum = '" + Methods.getCBONum(context) + "'");


        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (face == MyConstants.ACTION_UPLOAD_1 || face == MyConstants.ACTION_UPLOAD_2) {
            new AsyncWebService(context, face, progressDialog)
                    .execute(WebRefferences.execQuery.methodName, s);
        } else if (face == MyConstants.ACTION_REMOVE_OFFLINE_DATA) {
            ((AvailableCBO) context).loadOfflineCBOs();
            progressDialog.dismiss();
            methods.showToast("Successfuly Uploaded", context, MyConstants.MESSAGE_SUCCESS);

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
    private JSONArray getGeneratedQueries() {
        JSONArray jsonArray = new JSONArray();
        String q;
        Cursor cursor;

        if (face == MyConstants.ACTION_UPLOAD_1) {
            cursor = methods.getCursorBySelectedCBONum(context, "cboBasicDetailsFilled");
            while (cursor.moveToNext()) {
                q = "UPDATE `cbodetails` SET " +
                        " `name`='" + cursor.getString(cursor.getColumnIndex("name")) + "'," +
                        " `street`='" + cursor.getString(cursor.getColumnIndex("assNum")) + "'," +
                        " `road`='" + cursor.getString(cursor.getColumnIndex("road")) + "'," +
                        " `village`='" + cursor.getString(cursor.getColumnIndex("village")) + "'," +
                        " `town`='" + cursor.getString(cursor.getColumnIndex("town")) + "'," +
                        " `lon`='" + cursor.getString(cursor.getColumnIndex("lon")) + "'," +
                        " `lat`='" + cursor.getString(cursor.getColumnIndex("lat")) + "'," +
                        " `height`='" + cursor.getString(cursor.getColumnIndex("height")) + "'," +
                        " `acc`='" + cursor.getString(cursor.getColumnIndex("acc")) + "'," +
                        " `dateTime_`='" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                        " `dateTimeUploaded_`='" + Methods.getNowDateTime() + "'," +
                        " `userName`='" + methods.getLoggedUserNameAsString() + "'," +
                        " `application`='" + MyConstants.APP_ID + "' " +
                        " WHERE " +
                        " `_id` = '" + cursor.getString(cursor.getColumnIndex("id")) + "'";
                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "connectionDFilled");
            while (cursor.moveToNext()) {
                q = "UPDATE `connection` SET " +
                        " `domestic`='" + cursor.getString(cursor.getColumnIndex("dom")) + "'," +
                        " `religious`='" + cursor.getString(cursor.getColumnIndex("rel")) + "'," +
                        " `commercial`='" + cursor.getString(cursor.getColumnIndex("com")) + "'," +
                        " `schools`='" + cursor.getString(cursor.getColumnIndex("sch")) + "'," +
                        " `healthcenter`='" + cursor.getString(cursor.getColumnIndex("health")) + "'," +
                        " `governement`='" + cursor.getString(cursor.getColumnIndex("gov")) + "'," +
                        " `other`='" + cursor.getString(cursor.getColumnIndex("other")) + "'," +
                        " `dateTime_`='" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                        " `dateTimeUploaded_`='" + Methods.getNowDateTime() + "'," +
                        " `userName`='" + methods.getLoggedUserNameAsString() + "'," +
                        " `application`='" + MyConstants.APP_ID + "' " +
                        " WHERE " +
                        " `_id` = '" + cursor.getString(cursor.getColumnIndex("id")) + "'";
                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "coverageInfoFilled");
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                if (id.trim().equalsIgnoreCase("-")) {
                    q = "DELETE FROM `coverageinfo` WHERE " +
                            " `idGND` = '" + cursor.getString(cursor.getColumnIndex("idGnd")) + "' AND" +
                            " `dateTime_` = '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "' AND" +
                            " `CBONum` = '" + Methods.getCBONum(context) + "'";
                    jsonArray.put(q);

                    q = "INSERT INTO `coverageinfo`" +
                            " (`CBONum`, " +
                            " `village`, " +
                            " `dsd`, " +
                            " `gnd`, " +
                            " `NoOfHouse`, " +
                            " `dateTime_`, " +
                            " `dateTimeUploaded_`, " +
                            " `userName`, " +
                            " `application`, " +
                            " `idGND`)" +
                            " VALUES " +
                            " (" +
                            " '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'," +
                            " '" + cursor.getString(cursor.getColumnIndex("village")) + "'," +
                            " '" + (methods.getSingleStringFromDB("dsd", "locations", "idGnd", cursor.getString(cursor.getColumnIndex("idGnd")))) + "'," +
                            " '" + (methods.getSingleStringFromDB("gnd", "locations", "idGnd", cursor.getString(cursor.getColumnIndex("idGnd")))) + "'," +
                            " '" + cursor.getString(cursor.getColumnIndex("noOfHHold")) + "'," +
                            " '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                            " '" + Methods.getNowDateTime() + "'," +
                            " '" + methods.getLoggedUserNameAsString() + "'," +
                            " '" + MyConstants.APP_ID + "'," +
                            " '" + cursor.getString(cursor.getColumnIndex("idGnd")) + "'" +
                            " )";
                } else {
                    q = "UPDATE `coverageinfo` SET " +
                            " `village`='" + cursor.getString(cursor.getColumnIndex("village")) + "'," +
                            " `dsd`='" + (methods.getSingleStringFromDB("dsd", "locations", "idGnd", cursor.getString(cursor.getColumnIndex("idGnd")))) + "'," +
                            " `gnd`='" + (methods.getSingleStringFromDB("gnd", "locations", "idGnd", cursor.getString(cursor.getColumnIndex("idGnd")))) + "'," +
                            " `NoOfHouse`='" + cursor.getString(cursor.getColumnIndex("noOfHHold")) + "'," +
                            " `dateTime_`='" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                            " `dateTimeUploaded_`='" + Methods.getNowDateTime() + "'," +
                            " `userName`='" + methods.getLoggedUserNameAsString() + "'," +
                            " `application`='" + MyConstants.APP_ID + "'," +
                            " `idGND`='" + cursor.getString(cursor.getColumnIndex("idGnd")) + "' " +
                            " WHERE " +
                            " `idCoverageInfo` = '" + cursor.getString(cursor.getColumnIndex("id")) + "'";

                }

                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "pop");
            while (cursor.moveToNext()) {
                q = "DELETE FROM `wsp_cbo_details` WHERE " +
                        " `data_collected_time` = '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "' AND" +
                        " `cboid` = '" + Methods.getCBONum(context) + "'";
                jsonArray.put(q);

                q = "INSERT INTO `wsp_cbo_details`" +
                        " (`cboid`," +
                        " `mgt_of_wss`," +
                        " `male`," +
                        " `female`," +
                        " `less_than_18`," +
                        " `age_8_25_years`," +
                        " `elder_than_25`," +
                        " `disability_peoples`," +
                        " `wsp_implementation`," +
                        " `wq_parameter_checked`," +
                        " `wq_tapping_point`," +
                        " `awareness_wsp`," +
                        " `mode_communication_health`," +
                        " `frequency_awareness`," +
                        " `wss_affected_climate`," +
                        " `wss_impact_climate`," +
                        " `climate_factors`," +
                        " `flood_impact`," +
                        " `drought_impact`," +
                        " `water_availability_through_year`," +
                        " `water_availability_reason`," +
                        " `energy_water_pumping`," +
                        " `water_recharge_point`," +
                        " `reduce_water_leakage`," +
                        " `mitigation_effect_drought`," +
                        " `mitigation_effect_flood`," +
                        " `land_ownership_wss`," +
                        " `water_distribution_issue_ethnic`," +
                        " `transboundary_water_issues`," +
                        " `conflict_among_users`," +
                        " `conflict_among_users_reason`," +
                        " `conflict_among_communities`," +
                        " `community_conflict_reason`," +
                        " `observation`," +
                        " `userid`," +
                        " `data_collected_time`," +
                        " `data_uploaded`," +
                        " `web_validated`)" +
                        " VALUES " +
                        " (" +
                        " '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'," +
                        " '" + methods.getSingleStringFromDB("manWss", "cboBasicDetailsFilled", "CBONum", Methods.getCBONum(context)) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("male")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("female")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("less")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("eighteen")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("elder")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("noOfDis")) + "'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " '" + methods.getLoggedUserNameAsString() + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                        " '" + Methods.getNowDateTime() + "'," +
                        " 'NULL'" +
                        " )";
                jsonArray.put(q);

            }


            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            //////////////////Water res/////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////

            cursor = methods.getCursorBySelectedCBONum(context, "existingQA");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_cbo_details` SET " +
                        " `wsp_implementation`='" + cursor.getString(cursor.getColumnIndex("wsp")) + "'," +
                        " `wq_parameter_checked`='" + cursor.getString(cursor.getColumnIndex("wqp")) + "'," +
                        " `wq_tapping_point`='" + cursor.getString(cursor.getColumnIndex("wqt")) + "'," +
                        " `awareness_wsp`='" + cursor.getString(cursor.getColumnIndex("aow")) + "'," +
                        " `mode_communication_health`='" + cursor.getString(cursor.getColumnIndex("moc")) + "'," +
                        " `frequency_awareness`='" + cursor.getString(cursor.getColumnIndex("foa")) + "'" +
                        " WHERE " +
                        " `data_collected_time` = '" + (methods.getSingleStringFromDBByCBONum(context, "dateTime_", "pop")) + "'" +
                        " AND " +
                        " cboid = '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'";
                jsonArray.put(q);


            }

            cursor = methods.getCursorBySelectedCBONum(context, "catchment");
            while (cursor.moveToNext()) {
                q = "DELETE FROM `wsp_catchment` WHERE " +
                        " `date_collected` = '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "' AND" +
                        " `cboid` = '" + Methods.getCBONum(context) + "'";
                jsonArray.put(q);

                q = "INSERT INTO `wsp_catchment`" +
                        " (`cboid`," +
                        " `name_catchment`," +
                        " `area_catchment`," +
                        " `location_catchment`," +
                        " `nature_catchment`," +
                        " `risk_water_contamination`," +
                        " `source_contamination`," +
                        " `issues_ownership_catchment`," +
                        " `risk_mitigation_source_contamination`," +
                        " `userid`," +
                        " `date_collected`," +
                        " `date_uploaded`," +
                        " `web_validated`)" +
                        " VALUES " +
                        " (" +
                        " '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("catchName")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("area")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("loca")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("nature")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("riskOf")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("riskFor")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("issues")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("riskMit")) + "'," +
                        " '" + methods.getLoggedUserNameAsString() + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                        " '" + Methods.getNowDateTime() + "'," +
                        " 'NULL'" +
                        " )";
                jsonArray.put(q);


            }


            cursor = methods.getCursorBySelectedCBONum(context, "treatment");
            while (cursor.moveToNext()) {
                q = "DELETE FROM `wsp_treatment` WHERE " +
                        " `date_collected` = '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "' AND" +
                        " `cboid` = '" + Methods.getCBONum(context) + "'";
                jsonArray.put(q);

                q = "INSERT INTO `wsp_treatment`" +
                        " (`cboid`," +
                        " `name_treatment`," +
                        " `source_type`," +
                        " `source_protection`," +
                        " `intake_protection`," +
                        " `availability_treatment`," +
                        " `conventional_modules_treatment`," +
                        " `special_modules_treatment`," +
                        " `other_treatment_technique`," +
                        " `water_quality_parameters`," +
                        " `current_risk_water_treatment`," +
                        " `risk_mitigation`," +
                        " `userid`," +
                        " `date_collected`," +
                        " `date_uploaded`," +
                        " `web_validated`)" +
                        " VALUES" +
                        " (" +
                        " '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'," +
                        " 'NULL'," +
                        " '" + cursor.getString(cursor.getColumnIndex("sType")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("sProt")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("intake")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("avail")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("indi")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("spec")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("other")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("wq")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("currentR")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("riskMit")) + "'," +
                        " '" + methods.getLoggedUserNameAsString() + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                        " '" + Methods.getNowDateTime() + "'," +
                        " 'NULL'" +
                        " )";
                jsonArray.put(q);


            }


            //
            //
            //
            //
            cursor = methods.getCursorBySelectedCBONum(context, "dist");
            while (cursor.moveToNext()) {
                q = "DELETE FROM `wsp_distribution` WHERE " +
                        " `date_collected` = '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "' AND" +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND" +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("uniqueId")) + "'";
                jsonArray.put(q);

                q = "INSERT INTO `wsp_distribution`" +
                        " (`cboid`," +
                        " `name_distribution`," +
                        " `metering_connection`," +
                        " `number_connection`," +
                        " `expandability`," +
                        " `intermittent_water_quality`," +
                        " `service_interuptions`," +
                        " `identified_risk_factors`," +
                        " `risk_mitigation`," +
                        " `userid`," +
                        " `date_collected`," +
                        " `date_uploaded`," +
                        " `web_validated`," +
                        " `uniqueId`," +
                        " `overall`)" +
                        " VALUES " +
                        "(" +
                        " '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("distName")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("meter")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("numCon")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("exp")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("inter")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("serv")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("ident")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("riskMit")) + "'," +
                        " '" + methods.getLoggedUserNameAsString() + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                        " '" + Methods.getNowDateTime() + "'," +
                        " 'NULL'," +
                        " '" + cursor.getString(cursor.getColumnIndex("uniqueId")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("overAll")) + "'" +
                        ")";
                jsonArray.put(q);
            }
            cursor = MyDB.getData("SELECT * FROM distTypes WHERE uniqueId IN (SELECT uniqueId FROM dist WHERE CBONum = '" + Methods.getCBONum(context) + "')");
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    q = "DELETE FROM `wsp_distribution_pipe` WHERE " +
                            " `cboid` = '" + Methods.getCBONum(context) + "' AND" +
                            " uniqueId = '" + cursor.getString(cursor.getColumnIndex("uniqueId")) + "'";
                    jsonArray.put(q);
                }

                q = "INSERT INTO `wsp_distribution_pipe`" +
                        " (`cboid`," +
                        " `uniqueId`," +
                        " `material`," +
                        " `diameter`," +
                        " `unit`," +
                        " `length`," +
                        " `userid`," +
                        " `date_collected`," +
                        " `date_uploaded`," +
                        " `web_validated`)" +
                        " VALUES" +
                        " (" +
                        " '" + Methods.getCBONum(context) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("uniqueId")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("mat")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("diam")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("un")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("len")) + "'," +
                        " '" + methods.getLoggedUserNameAsString() + "'," +
                        " '" + (methods.getSingleStringFromDB("dateTime_", "dist", "uniqueId", cursor.getString(cursor.getColumnIndex("uniqueId")))) + "'," +
                        " '" + Methods.getNowDateTime() + "'," +
                        " 'NULL'" +
                        " )";
                jsonArray.put(q);

            }
            //
            //
            //
            //

            cursor = methods.getCursorBySelectedCBONum(context, "clim");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_cbo_details` SET" +
                        " `wss_affected_climate`='" + cursor.getString(cursor.getColumnIndex("isTheW")) + "'," +
                        " `wss_impact_climate`='" + cursor.getString(cursor.getColumnIndex("how")) + "'," +
                        " `climate_factors`='" + cursor.getString(cursor.getColumnIndex("they")) + "'," +
                        " `flood_impact`='" + cursor.getString(cursor.getColumnIndex("effeF")) + "'," +
                        " `drought_impact`='" + cursor.getString(cursor.getColumnIndex("effeD")) + "'," +
                        " `water_availability_through_year`='" + cursor.getString(cursor.getColumnIndex("waterIsA")) + "'," +
                        " `water_availability_reason`='" + cursor.getString(cursor.getColumnIndex("reas")) + "'," +
                        " `energy_water_pumping`='" + cursor.getString(cursor.getColumnIndex("whatAreS")) + "'," +
                        " `water_recharge_point`='" + cursor.getString(cursor.getColumnIndex("whatAreT")) + "'," +
                        " `reduce_water_leakage`='" + cursor.getString(cursor.getColumnIndex("water")) + "'," +
                        " `mitigation_effect_drought`='" + cursor.getString(cursor.getColumnIndex("drought")) + "'," +
                        " `mitigation_effect_flood`='" + cursor.getString(cursor.getColumnIndex("flood")) + "' " +
                        " WHERE " +
                        " `data_collected_time` = '" + (methods.getSingleStringFromDBByCBONum(context, "dateTime_", "pop")) + "'" +
                        " AND " +
                        " cboid = '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'";
                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "gov");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_cbo_details` SET " +
                        " `land_ownership_wss`='" + cursor.getString(cursor.getColumnIndex("fair")) + "'," +
                        " `water_distribution_issue_ethnic`='" + cursor.getString(cursor.getColumnIndex("inc")) + "'," +
                        " `transboundary_water_issues`='" + cursor.getString(cursor.getColumnIndex("tra")) + "'," +
                        " `conflict_among_users`='" + cursor.getString(cursor.getColumnIndex("con")) + "'," +
                        " `conflict_among_users_reason`='" + cursor.getString(cursor.getColumnIndex("theReas")) + "'," +
                        " `conflict_among_communities`='" + cursor.getString(cursor.getColumnIndex("isThere")) + "'," +
                        " `community_conflict_reason`='" + cursor.getString(cursor.getColumnIndex("pot")) + "' " +
                        " WHERE " +
                        " `data_collected_time` = '" + (methods.getSingleStringFromDBByCBONum(context, "dateTime_", "pop")) + "'" +
                        " AND " +
                        " cboid = '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'";
                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "obsWS");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_cbo_details` SET " +
                        " `observation`='" + cursor.getString(cursor.getColumnIndex("obs")) + "' " +
                        " WHERE " +
                        " `data_collected_time` = '" + (methods.getSingleStringFromDBByCBONum(context, "dateTime_", "pop")) + "'" +
                        " AND " +
                        " cboid = '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'";
                jsonArray.put(q);
            }
        } else {
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            //////////////////End user//////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////
            ////////////////////////////////////////////////


            cursor = methods.getCursorBySelectedCBONum(context, "basicInfo");
            while (cursor.moveToNext()) {
                q = "DELETE FROM `wsp_hs_main` WHERE " +
                        " `date_collected` = '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "' AND" +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND" +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "'";
                jsonArray.put(q);

                q = "INSERT INTO `wsp_hs_main`" +
                        "(`cboid`," +
                        " `contact_id`," +
                        " `cbo_water_adequacy`," +
                        " `cbo_water_adequacy_reason`," +
                        " `per_capita_water_use`," +
                        " `secondary_water_source_availability`," +
                        " `secondary_water_source_type`," +
                        " `cbo_water_usage`," +
                        " `cbo_water_quality_satisfy`," +
                        " `cbo_water_quality_satisfy_reason`," +
                        " `water_treat_before_drink`," +
                        " `water_treat_before_drink_reason`," +
                        " `water_treat_before_drink_method`," +
                        " `household_water_storage_type`," +
                        " `drink_water_storage_closed`," +
                        " `household_water_storage_clean_interval`," +
                        " `household_water_storage_clean_method`," +
                        " `diarrhea_affect`," +
                        " `health_related_messages`," +
                        " `water_save_reuse`," +
                        " `water_reuse_method`," +
                        " `observation`," +
                        " `userid`," +
                        " `date_collected`," +
                        " `date_uploaded`," +
                        " `web_validated`," +
                        " `uniqueId`)" +
                        " VALUES" +
                        " (" +
                        " '" + cursor.getString(cursor.getColumnIndex("CBONum")) + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("id")) + "'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " 'NULL'," +
                        " '" + methods.getLoggedUserNameAsString() + "'," +
                        " '" + cursor.getString(cursor.getColumnIndex("dateTime_")) + "'," +
                        " '" + Methods.getNowDateTime() + "'," +
                        " 'NULL'," +
                        " '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "' " +
                        ")";
                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "waterAd");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_hs_main` SET " +
                        " `cbo_water_adequacy`='" + cursor.getString(cursor.getColumnIndex("doYouG")) + "'," +
                        " `cbo_water_adequacy_reason`='" + cursor.getString(cursor.getColumnIndex("ifNo")) + "'," +
                        " `per_capita_water_use`='" + cursor.getString(cursor.getColumnIndex("whatIs")) + "'," +
                        " `secondary_water_source_availability`='" + cursor.getString(cursor.getColumnIndex("doYouUse")) + "'," +
                        " `secondary_water_source_type`='" + cursor.getString(cursor.getColumnIndex("ifYes")) + "'" +
                        " WHERE " +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND " +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "'";
                jsonArray.put(q);
            }


            cursor = methods.getCursorBySelectedCBONum(context, "waterQ");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_hs_main` SET " +
                        " `cbo_water_usage`='" + cursor.getString(cursor.getColumnIndex("forW")) + "'," +
                        " `cbo_water_quality_satisfy`='" + cursor.getString(cursor.getColumnIndex("doYouS")) + "'," +
                        " `cbo_water_quality_satisfy_reason`='" + cursor.getString(cursor.getColumnIndex("pleaseP")) + "'," +
                        " `water_treat_before_drink`='" + cursor.getString(cursor.getColumnIndex("doYouT")) + "'," +
                        " `water_treat_before_drink_reason`='" + cursor.getString(cursor.getColumnIndex("whatAre")) + "'," +
                        " `water_treat_before_drink_method`='" + cursor.getString(cursor.getColumnIndex("ifWater")) + "' " +
                        " WHERE " +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND " +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "'";
                jsonArray.put(q);
            }


            cursor = methods.getCursorBySelectedCBONum(context, "houseHold");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_hs_main` SET " +
                        " `household_water_storage_type`='" + cursor.getString(cursor.getColumnIndex("howDoYouS")) + "'," +
                        " `drink_water_storage_closed`='" + cursor.getString(cursor.getColumnIndex("isThe")) + "'," +
                        " `household_water_storage_clean_interval`='" + cursor.getString(cursor.getColumnIndex("howOften")) + "'," +
                        " `household_water_storage_clean_method`='" + cursor.getString(cursor.getColumnIndex("howDoYouC")) + "' " +
                        " WHERE " +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND " +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "'";
                jsonArray.put(q);
            }


            cursor = methods.getCursorBySelectedCBONum(context, "WaterH");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_hs_main` SET " +
                        " `diarrhea_affect`='" + cursor.getString(cursor.getColumnIndex("didA")) + "'," +
                        " `health_related_messages`='" + cursor.getString(cursor.getColumnIndex("didY")) + "' " +
                        " WHERE " +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND " +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "'";
                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "waterS");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_hs_main` SET " +
                        " `water_save_reuse`='" + cursor.getString(cursor.getColumnIndex("doM")) + "'," +
                        " `water_reuse_method`='" + cursor.getString(cursor.getColumnIndex("ifYes")) + "' " +
                        " WHERE " +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND " +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "'";
                jsonArray.put(q);
            }

            cursor = methods.getCursorBySelectedCBONum(context, "obsEU");
            while (cursor.moveToNext()) {
                q = "UPDATE `wsp_hs_main` SET " +
                        " `observation`='" + cursor.getString(cursor.getColumnIndex("obs")) + "' " +
                        " WHERE " +
                        " `cboid` = '" + Methods.getCBONum(context) + "' AND " +
                        " uniqueId = '" + cursor.getString(cursor.getColumnIndex("generatedId")) + "'";
                jsonArray.put(q);
            }


        }

        return jsonArray;

    }
}
