package com.debugsire.wsp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.debugsire.wsp.Algos.Adapters.DropdownAdapter;
import com.debugsire.wsp.Algos.Adapters.DropdownAdapterForGND;
import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CoverageByTheScheme extends AppCompatActivity {

    Context context;
    Methods methods;

    ArrayList<String> dsdList;
    ArrayList<String> gndList;
    ArrayList<String> gndIds;
    String idGnd;

    RelativeLayout topBack;
    Spinner dsd, gnd;
    TextInputLayout village, num;
    TextView dsdTop, gndTop;
    Button res, save;

    private static final String TAG = "-----";
    boolean firstTime, isUpdate;
    String id, dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverage_by_the_scheme);

        context = this;
        methods = new Methods();
        firstTime = true;
        idGnd = getIntent().getExtras().getString("idGnd");
        tableName = "coverageInfoFilled";
        dateTime_ = methods.getSingleStringFromDBByCBONum(context, "dateTime_", tableName, "idGnd", idGnd);

        initCompos();
        loadDSDSpinner();
        loadFields();
        addEventListeners();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        methods.setOptionsMenuRemove(menu, dateTime_);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            methods.removeEntry(context, tableName, dateTime_, false);
        }
        return true;
    }

    private void loadFields() {
        if (idGnd.trim().equalsIgnoreCase("-")) {
            if (dsdList.size() != 0) {
                firstTime = false;
                dsd.setSelection(0);
            }
            gndTop.setVisibility(View.GONE);
            dsdTop.setText("New Entry");
            topBack.setBackgroundColor(getResources().getColor(R.color.greenDark, null));
            return;
        }
        Cursor data;
        if (methods.isAvailOnDB("coverageInfoFilled", "idGnd", idGnd, "CBONum", Methods.getCBONum(context))) {
            data = methods.getCursor("coverageInfoFilled", "idGnd", idGnd, "CBONum", Methods.getCBONum(context));
            if (methods.getCursor("coverageInfo", "idGnd", idGnd, "CBONum", Methods.getCBONum(context)).getCount() != 0) {
                disableSpinner();
            }

        } else {
            data = methods.getCursor("coverageInfo", "idGnd", idGnd, "CBONum", Methods.getCBONum(context));
            disableSpinner();
        }

        while (data.moveToNext()) {
            village.getEditText().setText(data.getString(data.getColumnIndex("village")));
            num.getEditText().setText(data.getString(data.getColumnIndex("noOfHHold")));
            id = data.getString(data.getColumnIndex("id"));
        }
        //
        data = methods.getCursor("locations", "idGnd", idGnd);
        while (data.moveToNext()) {
            String selectedDsd = data.getString(data.getColumnIndex("dsd"));
            String selectedGnd = data.getString(data.getColumnIndex("gnd"));
            dsdTop.setText(selectedDsd);
            gndTop.setText(selectedGnd);
            for (int i = 0; i < dsdList.size(); i++) {
                if (dsdList.get(i).equalsIgnoreCase(selectedDsd)) {
                    this.dsd.setSelection(i);
                    loadGndSpinner(selectedDsd);

                    break;
                }
            }

            for (int i = 0; i < gndList.size(); i++) {
                Log.d(TAG, "loadFields: " + selectedGnd);
                if (gndList.get(i).equalsIgnoreCase(selectedGnd)) {
                    Log.d(TAG, "loadFields: " + i);
                    this.gnd.setSelection(i);
                    break;
                }
            }
        }
        isUpdate = true;

    }

    private void disableSpinner() {
        dsd.setEnabled(false);
        gnd.setEnabled(false);
        dsd.setAlpha(.5f);
        gnd.setAlpha(.5f);
        res.setEnabled(false);
        res.setAlpha(.5f);
    }

    private void addEventListeners() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean tilFieldsNull = methods.isTILFieldsNull(context,
                        village, num);
                boolean dsdSpinnerNull = methods.isSpinnerNull(context, dsd, dsdList);
                boolean gndSpinnerNull = methods.isSpinnerNull(context, gnd, gndList);

                if (!tilFieldsNull && !dsdSpinnerNull && !gndSpinnerNull) {
                    final AlertDialog dialog = methods.getSaveConfirmationDialog(context, isUpdate);

                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String selectedGndId = gndIds.get(gnd.getSelectedItemPosition());
                            if (dsd.isEnabled()) {
                                if (methods.getCursor("coverageInfo", "idGnd", selectedGndId, "CBONum", Methods.getCBONum(context)).getCount() != 0) {
                                    methods.showToast("'" + gndList.get(gnd.getSelectedItemPosition()) + "' is already downloaded. You can't add a entry for already added earlier.", context, MyConstants.MESSAGE_ERROR);
                                    return;
                                }
                            }
                            MyDB.setData("DELETE FROM coverageInfoFilled WHERE CBONum = '" + Methods.getCBONum(context) + "' AND idGnd = '" + idGnd + "'");
                            MyDB.setData("INSERT INTO coverageInfoFilled VALUES (" +
                                    " '" + (id == null ? "-" : id) + "', " +
                                    " '" + Methods.getCBONum(context) + "', " +
                                    " '" + Methods.configNull(village.getEditText().getText().toString(), "") + "', " +
                                    " '" + selectedGndId + "', " +
                                    " '" + Methods.configNull(num.getEditText().getText().toString(), "") + "', " +
                                    " '" + Methods.getNowDateTime() + "' " +
                                    ")");
                            methods.showToast(getString(R.string.saved), context, MyConstants.MESSAGE_SUCCESS);
                            onBackPressed();
                        }
                    });
                    dialog.show();


                } else {
                    methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
                }

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
                final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setIcon(R.drawable.ic_info_outline);
                dialog.setTitle("WSP Information");
                dialog.setMessage("Resynchronize this may change 'DS Division' and 'GN Division values'");
                dialog.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                        dialogInterface.dismiss();
                    }
                });

                dialog.create().show();


            }
        });
    }


    private void loadGndSpinner(String dsd) {
        gndList = new ArrayList<>();
        gndIds = new ArrayList<>();
        Cursor cursor = MyDB.getData("SELECT * FROM locations WHERE dsd = '" + dsd + "' ORDER BY gnd ASC");
        while (cursor.moveToNext()) {
            gndList.add(cursor.getString(cursor.getColumnIndex("gnd")));
            gndIds.add(cursor.getString(cursor.getColumnIndex("idGnd")));
        }
        gnd.setAdapter(null);
        DropdownAdapterForGND adapter = new DropdownAdapterForGND(context, gndList, idGnd, getFilledGnds(), methods);
        gnd.setAdapter(adapter);
    }

    private ArrayList<String> getFilledGnds() {
        ArrayList<String> strings = new ArrayList<>();


        Cursor cursor = methods.getCursorBySelectedCBONum(context, "coverageInfo");
        ;
        while (cursor.moveToNext()) {
            strings.add(methods.getSingleStringFromDB("gnd", "locations", "idGnd", cursor.getString(cursor.getColumnIndex("idGnd"))));
        }
        cursor = methods.getCursorBySelectedCBONum(context, "coverageInfoFilled");
        ;
        while (cursor.moveToNext()) {
            String idGnd = cursor.getString(cursor.getColumnIndex("idGnd"));
            if (!strings.contains(idGnd)) {
                strings.add(methods.getSingleStringFromDB("gnd", "locations", "idGnd", idGnd));
            }
        }
        return strings;
    }

    private void initCompos() {
        dsd = findViewById(R.id.spinner_dsdCoverageByS);
        gnd = findViewById(R.id.spinner_GnCoverageByS);
        topBack = findViewById(R.id.rl_toBackCoverageByTheScheme);
        dsdTop = findViewById(R.id.tv_dsdNameCoverageByTheScheme);
        gndTop = findViewById(R.id.tv_gndNameCoverageByTheScheme);
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
