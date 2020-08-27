package com.debugsire.wsp.Algos.WebService;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.WebService.Model.WebService;
import com.debugsire.wsp.R;
import com.debugsire.wsp.SignIn;

import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;

/**
 * Created by $anju on 2018-03-21.
 */

public class AsyncWebService extends AsyncTask<String, String, String> {
    private static final String TAG = "======================";
    public ProgressDialog progressDialog;
    Context context;
    int face;
    String tableKey;

    //For normal call
    public AsyncWebService(Context context, int face) {
        this.context = context;
        this.face = face;
        //
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    //For multiple Spinners
    public AsyncWebService(Context context, int face, String tableKey) {
        this.context = context;
        this.face = face;
        this.tableKey = tableKey;
        //
        progressDialog = new ProgressDialog(context, R.style.MyAlertDialogStyle);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    //For keep using prev dialog
    public AsyncWebService(Context context, int face, ProgressDialog progressDialog) {
        this.context = context;
        this.face = face;
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (face == MyConstants.SIGN_IN) {
            new PreTasks(context, face);
        } else {
            new PreTasks(face, progressDialog);
        }
    }


    @Override
    protected String doInBackground(String... strings) {
        String response = "ErrorCalling";
        if (Methods.isNetworkAvailable(context)) {
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName("value");
            propertyInfo.setValue(strings[1]);

            try {
//                response = "";
                response = WebService.sendRequest(strings[0], propertyInfo);

                checkAsyncBack(response, strings[1]);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "doInBackground: " + response);
        return response;

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Methods methods = new Methods();
        if (s.equalsIgnoreCase("ErrorCalling")) {
            methods.showToast("Can't communicate with server", context, MyConstants.MESSAGE_ERROR);
            resetToCurrentState();
        } else {
            if (face == MyConstants.SIGN_IN) {
                try {
                    new PostTasks(context, face, s);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((SignIn) context).showProgress(false);
                    methods.showToast("Something went wrong", context, MyConstants.MESSAGE_ERROR);
                }
            } else {
                try {
                    new PostTasks(context, face, tableKey, progressDialog, s);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    methods.showToast("Something went wrong", context, MyConstants.MESSAGE_ERROR);
                }
            }

        }
    }


    /////
    /////
    /////
    /////
    /////
    /////
    /////
    /////
    /////
    /////
    /////
    /////
    /////
    /////


    private void checkAsyncBack(String response, String value) throws Exception {
        DoInBackTasks doInBackTasks = new DoInBackTasks(face, this);
        if (face == MyConstants.ACTION_GET_DROP_LIST
                || face == MyConstants.ACTION_RESYNCH_DROP_LIST
                || face == MyConstants.ACTION_RESYNCH_BEFORE_UPLOAD) {
            doInBackTasks.checkDropdownSynch(response, value, face);

        } else if (face == MyConstants.ACTION_GET_DSD_AND_CBO) {
            doInBackTasks.checkDSDandCBO(response);

        } else if (face == MyConstants.ACTION_GET_BY_CBO_NAME_DOWNLOADS) {
            doInBackTasks.checkDownloadsByCbo(response);

        } else if (face == MyConstants.ACTION_GET_LOCATION_DSD_GND) {
            doInBackTasks.saveLocationValues(response);

        } else if (face == MyConstants.ACTION_CONTACT_UPLOAD) {
            doInBackTasks.updateBasicInfoID(response);

        }
    }


    private void resetToCurrentState() {
        if (face == MyConstants.SIGN_IN) {
            ((SignIn) context).showProgress(false);
        } else {
            progressDialog.dismiss();
        }
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        progressDialog.setMessage(values[0]);
    }


    public void myPublishProgress(String value) {
        publishProgress(value);
    }
}
