package com.debugsire.wsp.Algos.WebService;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.AvailableCBO;
import com.debugsire.wsp.CboBasicDetails;
import com.debugsire.wsp.EndUserAssessment.BasicInfoOfHousehold;
import com.debugsire.wsp.EndUserAssessment.HouseholdStorage;
import com.debugsire.wsp.EndUserAssessment.WaterAdequacy;
import com.debugsire.wsp.EndUserAssessment.WaterHealth;
import com.debugsire.wsp.EndUserAssessment.WaterQuality;
import com.debugsire.wsp.EndUserAssessment.WaterSaving;
import com.debugsire.wsp.SignIn;
import com.debugsire.wsp.WaterSafetyAndClimate.Catchment;
import com.debugsire.wsp.WaterSafetyAndClimate.ClimateAndDdr;
import com.debugsire.wsp.WaterSafetyAndClimate.Distribution;
import com.debugsire.wsp.WaterSafetyAndClimate.ExistingQa;
import com.debugsire.wsp.WaterSafetyAndClimate.Governance;
import com.debugsire.wsp.WaterSafetyAndClimate.Treatment;

import org.json.JSONException;
import org.json.JSONObject;

public class PostTasksHandler {

    Methods methods = new Methods();

    public void checkLogin(Context context, String response, boolean saveOnDB) throws JSONException {
        JSONObject jsonObject = new JSONObject(response);
        int message = jsonObject.getInt("message");
        if (message == MyConstants.ERROR_INVALID_USER) {
            methods.showToast("Invalid User Name or Password", context, MyConstants.MESSAGE_ERROR);

        } else if (message == MyConstants.ERROR_DENIED_PERMISSION_USER) {
            methods.showToast("You are not allowed access this App", context, MyConstants.MESSAGE_ERROR);

        } else if (message == MyConstants.VALID_USER || message == MyConstants.EXPANDED_USER) {
            if (jsonObject.getString("status").trim().equalsIgnoreCase("1")) {
                if (saveOnDB) {
                    MyDB.setData("DELETE FROM user");
                    MyDB.setData("INSERT INTO user VALUES (" +
                            " '" + jsonObject.getString("userName").trim() + "', " +
                            " '" + jsonObject.getString("password").trim() + "', " +
                            " '" + jsonObject.getString("name_").trim() + "', " +
                            " '" + jsonObject.getString("desig").trim() + "', " +
                            " '" + jsonObject.getString("status").trim() + "', " +
                            " '" + jsonObject.getString("dateTime_").trim() + "'" +
                            ")");

                    context.startActivity(new Intent(context, AvailableCBO.class));
                    ((SignIn) context).finish();


                    if (message == MyConstants.EXPANDED_USER) {
                        methods.showToast("Logged as an expanded user", context, MyConstants.MESSAGE_INFO);

                    }
                }
            } else {
                methods.showToast("Constricted User.!", context, MyConstants.MESSAGE_ERROR);
            }
        }

    }

    public void checkAndWorkOnSpinner(Context context, String tableKey) {
        if (context instanceof CboBasicDetails) {
            ((CboBasicDetails) context).setSpinnerValues();
        } else if (context instanceof ExistingQa) {
            ((ExistingQa) context).setSpinnerValues(tableKey);
        } else if (context instanceof Catchment) {
            ((Catchment) context).setSpinnerValues(tableKey);
        } else if (context instanceof Treatment) {
            ((Treatment) context).setSpinnerValues(tableKey);
        } else if (context instanceof Distribution) {
            ((Distribution) context).setSpinnerValues(tableKey);
        } else if (context instanceof ClimateAndDdr) {
            ((ClimateAndDdr) context).setSpinnerValues(tableKey);
        } else if (context instanceof Governance) {
            ((Governance) context).setSpinnerValues(tableKey);
        } else if (context instanceof BasicInfoOfHousehold) {
            ((BasicInfoOfHousehold) context).setSpinnerValues(tableKey);
        } else if (context instanceof WaterAdequacy) {
            ((WaterAdequacy) context).setSpinnerValues(tableKey);
        } else if (context instanceof WaterQuality) {
            ((WaterQuality) context).setSpinnerValues(tableKey);
        } else if (context instanceof HouseholdStorage) {
            ((HouseholdStorage) context).setSpinnerValues(tableKey);
        } else if (context instanceof WaterHealth) {
            ((WaterHealth) context).setSpinnerValues(tableKey);
        } else if (context instanceof WaterSaving) {
            ((WaterSaving) context).setSpinnerValues(tableKey);
        }
    }
}
