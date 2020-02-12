package com.debugsire.wsp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.debugsire.wsp.Algos.Adapters.DropdownAdapter;
import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class CoverageByTheScheme extends AppCompatActivity {

    Context context;
    Methods methods;

    ArrayList<String> dsdList;
    ArrayList<String> gndList;
    ArrayList<String> gndIds;
    String idGnd;

    Spinner dsd, gnd;
    TextInputLayout village, num;
    Button res, save;

    private static final String TAG = "-----";
    boolean firstTime, isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverage_by_the_scheme);

        context = this;
        methods = new Methods();
        firstTime = true;
        idGnd = getIntent().getExtras().getString("idGnd");

        initCompos();
        loadDSDSpinner();
        loadFields();
        addEventListeners();

    }

    private void loadFields() {
        Cursor data;
        if (methods.isAvailOnDB("coverageInfoFilled", "idGnd", idGnd, "CBONum", Methods.getCBONum(context))) {
            data = methods.getCursor("coverageInfoFilled", "idGnd", idGnd, "CBONum", Methods.getCBONum(context));
            Toast.makeText(context, data.getCount() + "===", Toast.LENGTH_SHORT).show();
            isUpdate = true;
        } else {
            data = MyDB.getData("SELECT * FROM coverageInfo WHERE idGnd = '" + idGnd + "' AND CBONum = '" + Methods.getCBONum(context) + "'");
        }

        while (data.moveToNext()) {
            village.getEditText().setText(data.getString(data.getColumnIndex("village")));
            num.getEditText().setText(data.getString(data.getColumnIndex("noOfHHold")));
        }
        //
        data = MyDB.getData("SELECT * FROM locations WHERE idGnd = '" + idGnd + "'");
        while (data.moveToNext()) {
            for (int i = 0; i < dsdList.size(); i++) {
                String dsd = data.getString(data.getColumnIndex("dsd"));
                Log.d(TAG, "loadFields: " + dsd);
                if (dsdList.get(i).equalsIgnoreCase(dsd)) {
                    this.dsd.setSelection(i);
                    loadGndSpinner(dsd);

                    break;
                }
            }

            for (int i = 0; i < gndList.size(); i++) {
                String gnd = data.getString(data.getColumnIndex("gnd"));
                Log.d(TAG, "loadFields: " + gnd);
                if (gndList.get(i).equalsIgnoreCase(gnd)) {
                    Log.d(TAG, "loadFields: " + i);
                    this.gnd.setSelection(i);
                    break;
                }
            }
        }
    }

    private void addEventListeners() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final AlertDialog dialog;
//                if (isUpdate) {
//                    dialog = methods.getSaveConfirmationDialog(context, true);
//
//                } else {
//                    dialog = methods.getSaveConfirmationDialog(context, false);
//
//                }
//                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialog.dismiss();
//                    }
//                });
//
//                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        boolean tilFieldsNull = methods.isTILFieldsNull(context,
//                                village, num);
//                        boolean spinnerNull = methods.isSpinnerNull(context, dsd, gnd);
//
//                        if (!tilFieldsNull && !spinnerNull) {
//                            if (!isUpdate) {
//                                MyDB.setData("INSERT INTO coverageInfoFilled VALUES (" +
//                                        " '" + Methods.getCBONum(context) + "', " +
//                                        " '" + Methods.configNull(village.getEditText().getText().toString(), "") + "', " +
//                                        " '" + gndList.get(gnd.getSelectedItemPosition()) + "', " +
//                                        " '" + Methods.configNull(num.getEditText().getText().toString(), "") + "', " +
//                                        " '" + Methods.getNowDateTime() + "' " +
//                                        ")");
//
//                                methods.showToast(getString(R.string.saved), context, MyConstants.MESSAGE_SUCCESS);
//                                onBackPressed();
//                            } else {
//                                MyDB.setData("UPDATE coverageInfoFilled SET " +
//                                        " village = '" + Methods.configNull(village.getEditText().getText().toString(), "") + "', " +
//                                        " idGnd = '" + gndIds.get(gnd.getSelectedItemPosition()) + "', " +
//                                        " noOfHHold = '" + num.getEditText().getText().toString().trim() + "', " +
//                                        " WHERE CBONum = '" + Methods.getCBONum(context) + "'" +
//                                        " ");
//                                methods.showToast(getString(R.string.updated), context, MyConstants.MESSAGE_SUCCESS);
//                                onBackPressed();
//                            }
//
//                        } else {
//                            methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
//                        }
//                    }
//                });
//                dialog.show();

            }
        });


        dsd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!firstTime) {
                    loadGndSpinner(dsdList.get(i));
                }
                firstTime = false;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to resynchronize values from server?");
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AsyncWebService(context, MyConstants.ACTION_GET_LOCATION_DSD_GND)
                                .execute(WebRefferences.getLocation.methodName,
                                        new Methods().getLoggedUserName().toString());
                    }
                });

                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.create().show();


            }
        });
    }

    private void loadGndSpinner(String dsd) {
        gndList = new ArrayList<>();
        gndIds = new ArrayList<>();
        Cursor cursor = MyDB.getData("SELECT * FROM locations WHERE dsd" +
                " = '" + dsd + "' ORDER BY gnd ASC");
        while (cursor.moveToNext()) {
            gndList.add(cursor.getString(cursor.getColumnIndex("gnd")));
            gndIds.add(cursor.getString(cursor.getColumnIndex("idGnd")));
        }
        gnd.setAdapter(null);
        DropdownAdapter adapter = new DropdownAdapter(context, gndList, idGnd);
        gnd.setAdapter(adapter);
    }

    private void initCompos() {
        dsd = findViewById(R.id.spinner_dsdCoverageByS);
        gnd = findViewById(R.id.spinner_GnCoverageByS);
        village = findViewById(R.id.til_villageCoverageByS);
        num = findViewById(R.id.til_numberCoverageByS);
        res = findViewById(R.id.btn_resynchDsdCoverageByS);
        save = findViewById(R.id.btn_saveCoverageByS);
    }

    public void loadDSDSpinner() {
        gnd.setAdapter(null);
        dsdList = new ArrayList<>();
        String lastUp = "-";

        Cursor cursor = MyDB.getData("SELECT * FROM locations GROUP BY dsd ORDER BY dsd ASC");
        while (cursor.moveToNext()) {
            dsdList.add(cursor.getString(cursor.getColumnIndex("dsd")));
//            gndList.add(cursor.getString(cursor.getColumnIndex("GND")));
            if (lastUp.equalsIgnoreCase("-")) {
                lastUp = cursor.getString(cursor.getColumnIndex("dateTime_"));
            }
        }

        DropdownAdapter adapter = new DropdownAdapter(context, dsdList);
        dsd.setAdapter(adapter);
    }
}
