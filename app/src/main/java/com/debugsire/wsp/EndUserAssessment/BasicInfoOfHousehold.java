package com.debugsire.wsp.EndUserAssessment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.MyStringRandomGen;
import com.debugsire.wsp.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;

public class BasicInfoOfHousehold extends AppCompatActivity {

    Context context;
    Integer[] valuesDesig, valuesGender, valuesPreLan;
    Methods methods;

    TextInputLayout name, comm, mob;
    Spinner desig, gender, preLan;
    Button desigR, genderR, preLanR, save;

    RelativeLayout headerWrapper;
    String dateTime_, tableName, generatedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_info_of_household);


        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");
        generatedId = getIntent().getExtras().getString("generatedId");

        initCompos();
        setSpinnerValues(MyConstants.ALL);
        setEvents();
        loadFields();
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
            methods.removeEntry(context, tableName, dateTime_, true);
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_, true);
        while (cursor.moveToNext()) {
            name.getEditText().setText(cursor.getString(cursor.getColumnIndex("name")));
            comm.getEditText().setText(cursor.getString(cursor.getColumnIndex("com")));
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("desi")), valuesDesig, desig);
            mob.getEditText().setText(cursor.getString(cursor.getColumnIndex("mob")));
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("gen")), valuesGender, gender);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("pref")), valuesPreLan, preLan);
        }
    }


    private void setEvents() {
        desigR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_BASIC_INFO_DESIG);
            }
        });


        genderR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_BASIC_INFO_GENDER);
            }
        });


        preLanR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_BASIC_INFO_PRE_LAN);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean tilMobileFieldsHasError = methods.isTILMobileFieldsHasError(context, mob);
                boolean tilFieldsNull = methods.isTILFieldsNull(context, name, comm);
                boolean spinnerNull = methods.isSpinnerNull(context, desig, gender, preLan);
                if (!spinnerNull && !tilFieldsNull && !tilMobileFieldsHasError) {
                    final AlertDialog dialog = methods.getSaveConfirmationDialog(context, dateTime_ != null);
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (generatedId == null) {
                                generatedId = new MyStringRandomGen().generateRandomString();
                            }
                            ArrayList<String> strings = methods.getConfiguredStringForInsert(
                                    "-",
                                    name.getEditText().getText().toString().trim(),
                                    comm.getEditText().getText().toString().trim(),
                                    valuesDesig[desig.getSelectedItemPosition()] + "",
                                    mob.getEditText().getText().toString().trim(),
                                    valuesGender[gender.getSelectedItemPosition()] + "",
                                    valuesPreLan[preLan.getSelectedItemPosition()] + "",
                                    generatedId

                            );

                            methods.insertData(context, tableName, dateTime_, strings, true);
                            methods.showToast(getString(R.string.saved), context, MyConstants.MESSAGE_SUCCESS);
                            methods.setSharedPref(context, MyConstants.ACTION_SELECTED_GENERATED_ID, generatedId);
                            onBackPressed();

                        }
                    });
                    dialog.show();
                } else {
                    methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
                }
            }
        });
    }

    private void initCompos() {
        name = findViewById(R.id.til_nameActivityBasicInfo);
        comm = findViewById(R.id.til_commActivityBasicInfo);
        mob = findViewById(R.id.til_mobActivityBasicInfo);

        desig = findViewById(R.id.spinner_desig_BasicInfo);
        gender = findViewById(R.id.spinner_gender_BasicInfo);
        preLan = findViewById(R.id.spinner_pre_BasicInfo);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        desigR = findViewById(R.id.btn_rDesig_BasicInfo);
        genderR = findViewById(R.id.btn_rGender_BasicInfo);
        preLanR = findViewById(R.id.btn_rPre_BasicInfo);
        save = findViewById(R.id.btn_saveBasicInfo);
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_BASIC_INFO_DESIG) {
            valuesDesig = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_DESIG,
                    valuesDesig, desig, false);

        } else if (tableKey == MyConstants.DL_BASIC_INFO_GENDER) {
            valuesGender = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_GENDER,
                    valuesGender, gender, false);

        } else if (tableKey == MyConstants.DL_BASIC_INFO_PRE_LAN) {
            valuesPreLan = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_PRE_LAN,
                    valuesPreLan, preLan, false);

        } else if (tableKey == MyConstants.ALL) {
            valuesDesig = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_DESIG,
                    valuesDesig, desig, false);

            valuesGender = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_GENDER,
                    valuesGender, gender, false);

            valuesPreLan = methods.setSpinnerThings(context, MyConstants.DL_BASIC_INFO_PRE_LAN,
                    valuesPreLan, preLan, false);

        }
    }
}