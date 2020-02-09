package com.debugsire.wsp;

import androidx.appcompat.app.AppCompatActivity;

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

import com.debugsire.wsp.Algos.Adapters.DropdownAdapter;
import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CoverageByTheScheme extends AppCompatActivity {

    Context context;
    Methods methods;

    ArrayList<String> dsdList;
    ArrayList<String> gndList;
    String idGnd;

    Spinner dsd, gnd;
    TextInputLayout village, num;
    Button res, save;

    private static final String TAG = "-----";
    boolean firstTime = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coverage_by_the_scheme);

        context = this;
        methods = new Methods();
        idGnd = getIntent().getExtras().getString("idGnd");

        initCompos();
        loadDSDSpinner();
        loadFields();
        addEventListeners();

    }

    private void loadFields() {
        Cursor data = MyDB.getData("SELECT * FROM coverageInfo WHERE idGnd = '" + idGnd + "'");
        while (data.moveToNext()) {
            village.getEditText().setText(data.getString(data.getColumnIndex("village")));
            num.getEditText().setText(data.getString(data.getColumnIndex("noOfHHold")));
        }
        data = MyDB.getData("SELECT * FROM locations WHERE idGnd = '" + idGnd + "'");
        Log.d(TAG, "loadFields: " + idGnd);
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
        Cursor cursor = MyDB.getData("SELECT gnd FROM locations WHERE dsd" +
                " = '" + dsd + "' ORDER BY gnd ASC");
        while (cursor.moveToNext()) {
            gndList.add(cursor.getString(cursor.getColumnIndex("gnd")));
        }
        gnd.setAdapter(null);
        DropdownAdapter adapter = new DropdownAdapter(context, gndList);
        gnd.setAdapter(adapter);
    }

    private void initCompos() {
        dsd = findViewById(R.id.spinner_dsdCoverageByS);
        gnd = findViewById(R.id.spinner_GnCoverageByS);
        village = findViewById(R.id.til_villageCoverageByS);
        num = findViewById(R.id.til_numberCoverageByS);
        res = findViewById(R.id.btn_resynchDsdCoverageByS);
        save = findViewById(R.id.btn_saveCboConnectionDetails);
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
