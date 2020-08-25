package com.debugsire.wsp.Algos.WebService;

import android.app.ProgressDialog;
import android.content.Context;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.debugsire.wsp.AvailableCBO;
import com.debugsire.wsp.CoverageByTheScheme;
import com.debugsire.wsp.SignIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostTasks {

    Methods methods = new Methods();

    //With Dialog Things
    //With Dialog Things
    //With Dialog Things
    //With Dialog Things
    //With Dialog Things
    public PostTasks(Context context, int face, String tableKey, ProgressDialog progressDialog, String response) throws JSONException {
        if (face == MyConstants.ACTION_GET_LOCATION_DSD_GND) {
            new JSONObject(response);
        } else {
            new JSONArray(response);
        }

        if (face == MyConstants.ACTION_RESYNCH_DROP_LIST) {
            new PostTasksHandler().checkAndWorkOnSpinner(context, tableKey);
            methods.showToast("Successfully resynchronized", context, MyConstants.MESSAGE_SUCCESS);
            progressDialog.dismiss();

        } else if (face == MyConstants.ACTION_GET_DROP_LIST) {
            if (MyDB.getData("SELECT * FROM locations").getCount() == 0) {
                new AsyncWebService(context, MyConstants.ACTION_GET_DSD_AND_CBO, progressDialog)
                        .execute(WebRefferences.getDSDandCBO.methodName,
                                new Methods().getLoggedUserName().toString());
            } else {
                progressDialog.dismiss();
            }

        } else if (face == MyConstants.ACTION_GET_DSD_AND_CBO) {
            progressDialog.dismiss();
            methods.showToast("Successfully resynchronized", context, MyConstants.MESSAGE_SUCCESS);
            ((AvailableCBO) context).loadDSDSpinner();

        } else if (face == MyConstants.ACTION_GET_BY_CBO_NAME_DOWNLOADS) {
            progressDialog.dismiss();
            AvailableCBO availableCBO = (AvailableCBO) context;
            availableCBO.loadOfflineCBOs();
            availableCBO.loadCbo();
            methods.showToast("Successfully resynchronized", context, MyConstants.MESSAGE_SUCCESS);


        } else if (face == MyConstants.ACTION_GET_LOCATION_DSD_GND) {
            progressDialog.dismiss();
            ((CoverageByTheScheme) context).loadDSDSpinner();
            methods.showToast("Successfully resynchronized", context, MyConstants.MESSAGE_SUCCESS);


        } else if (face == MyConstants.ACTION_RESYNCH_BEFORE_UPLOAD) {
            progressDialog.dismiss();
            methods.showToast("Successfully resynchronized", context, MyConstants.MESSAGE_SUCCESS);


        }
    }

    //Without Dialog Things
    //Without Dialog Things
    //Without Dialog Things
    //Without Dialog Things
    //Without Dialog Things
    public PostTasks(Context context, int face, String response) throws JSONException {
        if (face == MyConstants.SIGN_IN) {
            new PostTasksHandler().checkLogin(context, response, true);
            SignIn signIn = (SignIn) context;
            signIn.showProgress(false);
        }
    }


}
