package com.debugsire.wsp.Algos.WebService;

import android.app.ProgressDialog;
import android.content.Context;

import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.SignIn;

public class PreTasks {
    //With Dialog Things
    //With Dialog Things
    //With Dialog Things
    //With Dialog Things
    //With Dialog Things
    public PreTasks(int face, ProgressDialog progressDialog) {
        if (face == MyConstants.ACTION_GET_DROP_LIST) {
            progressDialog.setMessage("Fetching required values...");

        } else if (face == MyConstants.ACTION_RESYNCH_DROP_LIST
                || face == MyConstants.ACTION_GET_LOCATION_DSD_GND) {
            progressDialog.setMessage("Resynchronizing required values...");

        } else if (face == MyConstants.ACTION_GET_DSD_AND_CBO) {
            progressDialog.setMessage("Resynchronizing DS Division and CBO Name values...");

        } else if (face == MyConstants.ACTION_GET_BY_CBO_NAME_DOWNLOADS) {
            progressDialog.setMessage("Fetching data for selected CBO...");

        }
        progressDialog.show();
    }


    // Without dialog things
    // Without dialog things
    // Without dialog things
    // Without dialog things
    // Without dialog things
    public PreTasks(Context context, int face) {
        if (face == MyConstants.SIGN_IN) {
            ((SignIn) context).showProgress(true);

        }

    }
}
