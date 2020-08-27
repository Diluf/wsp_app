package com.debugsire.wsp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debugsire.wsp.Algos.Adapters.DownloadedCBOAdapter;
import com.debugsire.wsp.Algos.Adapters.DropdownAdapter;
import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.POJOs.DownloadedCBOPOJO;
import com.debugsire.wsp.Algos.WebService.*;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AvailableCBO extends AppCompatActivity {

    Button makeOff, resynch;
    ListView offlineList;
    Context context;

    Spinner dsd, cbo;
    TextView placeHolder;
    ArrayList<String> alDSDs, alCBOs, alCBOsId;
    ArrayList<DownloadedCBOPOJO> offlineCbopojoArrayList;

    View dialogView;

    FloatingActionButton floatingActionButton;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cbo);

        context = AvailableCBO.this;
        methods = new Methods();

        offlineList = findViewById(R.id.lv_availCBO_AvailableCBO);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        placeHolder = findViewById(R.id.tvPlaceHolder);

        assignAlertView();
        addEventListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOfflineCBOs();

    }

    private void assignAlertView() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        dialogView = inflater.inflate(R.layout.dialog_download_cbo, null);

        resynch = dialogView.findViewById(R.id.btn_rAvailCbo_AvailableCBO);
        dsd = dialogView.findViewById(R.id.sp_DSD_AvailableCBO);
        cbo = dialogView.findViewById(R.id.sp_CBOName_AvailableCBO);
        makeOff = dialogView.findViewById(R.id.btn_MakeOffline_AvailableCBO);

        makeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (cbo.getSelectedItemPosition() != 0) {
                        JSONObject object = methods.getDownloadConfigs(MyConstants.ALL);
                        object.put("cboId", alCBOsId.get(cbo.getSelectedItemPosition()));
                        JSONObject userThings = methods.getLoggedUserName();
                        object.put("userName", userThings.getString("userName"));
                        object.put("password", userThings.getString("password"));
                        object.put("columnName", MyConstants.MY_APPLICATION_ACCESS_COLUMN);
                        new AsyncWebService(context, MyConstants.ACTION_GET_BY_CBO_NAME_DOWNLOADS)
                                .execute(WebRefferences.downloadByCbo.methodName, object.toString());
                    } else {
                        methods.showToast("Select a valid CBO Name to continue", context, MyConstants.MESSAGE_ERROR);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                startActivity(new Intent(context, Home.class));

            }
        });

        resynch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncWebService(context, MyConstants.ACTION_GET_DSD_AND_CBO)
                        .execute(WebRefferences.getDSDandCBO.methodName, methods.getLoggedUserName().toString());

            }
        });

        loadDSDSpinner();

        dsd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadCbo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void addEventListeners() {
        offlineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DownloadedCBOPOJO downloadedCBOPOJO = offlineCbopojoArrayList.get(i);
                Intent intent = new Intent(context, Home.class);
                intent.putExtra("cboName", downloadedCBOPOJO.getCboName());
                methods.setSharedPref(context, MyConstants.SHARED_CBO_NUM, downloadedCBOPOJO.getCboNum());
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCBODialog();
            }
        });
    }

    private void showCBODialog() {
        assignAlertView();
        final AlertDialog builder = new AlertDialog.Builder(context)
                .setView(dialogView)
                .create();

        builder.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        builder.show();
    }

    public void loadCbo() {
        String selectedDsd = alDSDs.get(dsd.getSelectedItemPosition());
        alCBOs = new ArrayList<>();
        alCBOsId = new ArrayList<>();
        alCBOs.add("-");
        alCBOsId.add("-");
        Cursor data = MyDB.getData("SELECT * FROM cboB WHERE idDSD = " +
                " (SELECT idDsd FROM locations WHERE dsd = '" + selectedDsd + "') ");
        while (data.moveToNext()) {
            alCBOs.add(data.getString(data.getColumnIndex("name")));
            alCBOsId.add(data.getString(data.getColumnIndex("CBONum")));
        }

        cbo.setAdapter(null);
        DropdownAdapter adapter = new DropdownAdapter(context, alCBOs);
        cbo.setAdapter(adapter);
    }

    public void loadOfflineCBOs() {
        offlineList.setAdapter(null);
        offlineCbopojoArrayList = new ArrayList<>();

        Cursor data = MyDB.getData("SELECT * FROM cboBasicDetails");
        while (data.moveToNext()) {
            offlineCbopojoArrayList.add(new DownloadedCBOPOJO(
                    data.getString(data.getColumnIndex("CBONum")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("street")),
                    data.getString(data.getColumnIndex("road")),
                    data.getString(data.getColumnIndex("village")),
                    data.getString(data.getColumnIndex("town")),
                    data.getString(data.getColumnIndex("dateTime_")),
                    data.getInt(data.getColumnIndex("uploadStarted")) == 0 ? false : true
            ));

        }


        offlineList.setAdapter(new DownloadedCBOAdapter(context, offlineCbopojoArrayList, methods));

        if (offlineCbopojoArrayList.size() == 0) {
            placeHolder.setVisibility(View.VISIBLE);
        } else {
            placeHolder.setVisibility(View.GONE);

        }

    }

    public void loadDSDSpinner() {
        dsd.setAdapter(null);
        Cursor data = MyDB.getData("SELECT * FROM locations GROUP BY dsd ORDER BY dsd ASC");
        alDSDs = new ArrayList<>();
        alDSDs.add("-");
        while (data.moveToNext()) {
            alDSDs.add(data.getString(data.getColumnIndex("dsd")));
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, alDSDs); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dsd.setAdapter(spinnerArrayAdapter);
        dsd.setSelection(0);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (MyDB.getData("SELECT * FROM wsp_droplist").getCount() == 0) {
            methods.doResynchDropDownValues(context, MyConstants.ALL, MyConstants.ACTION_GET_DROP_LIST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.on_available, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signOut_OnAvailable:

                AlertDialog dialog = methods.getRequiredAlertDialog(context, MyConstants.SIGNOUT_DIALOG);
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sign out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<String> allTableNames = methods.getAllTableNames(false);
                        for (String tableName :
                                allTableNames) {
                            MyDB.setData("DELETE FROM " + tableName);
                        }

                        startActivity(new Intent(AvailableCBO.this, SignIn.class));
                        finish();
                    }
                });


                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                return true;
            case R.id.menu_about_OnAvailable:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startUpload() {

        methods.doResynchDropDownValues(context, MyConstants.ALL, MyConstants.ACTION_RESYNCH_BEFORE_UPLOAD);


//        String q = "INSERT INTO `contacts`(" +
//                "`cboid`, " +
//                "`person_name`, " +
//                "`calling_name`, " +
//                "`designation_cbo`, " +
//                "`mobile1`, " +
//                "`mobile`, " +
//                "`gender`," +
//                "`preffered_language`, " +
//                "`dateTime_`, " +
//                "`dateTimeUploaded_`, " +
//                "`userName`, " +
//                "`application`, " +
//                "`prev_Id`, " +
//                "`web_validated`) VALUES (";
//        Cursor cursor = methods.getCursorBySelectedCBONum(context, "basicInfo");
//        while (cursor.moveToNext()) {
//
//            q += "'" + Methods.getCBONum(context) + "', '" + cursor.getString(cursor.getColumnIndex("name")) + "'";
//
////                q += "'" + Methods.getCBONum(context) + "'," +
////                        "person_name = '" + cursor.getString(cursor.getColumnIndex("name")) + "'," +
////                        " calling_name = '" + cursor.getString(cursor.getColumnIndex("name")) + "'," +
////                        " designation_cbo = '" + cursor.getString(cursor.getColumnIndex("desi")) + "'," +
////                        " " + columnName + " = '" + cursor.getString(cursor.getColumnIndex("mob")) + "'," +
////                        " gender = '" + cursor.getString(cursor.getColumnIndex("gen")) + "'," +
////                        " preffered_language = '" + cursor.getString(cursor.getColumnIndex("pref")) + "'" +
////                        " WHERE _id = '" + contactId + "'";
//        }
//
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.put(q);
//        new AsyncWebService(context, MyConstants.ACTION_UPLOAD)
//                .execute(
//                        WebRefferences.execQuery.methodName,
//                        jsonArray.toString()
//                );

    }
}
