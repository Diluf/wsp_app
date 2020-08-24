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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class HouseholdStorage extends AppCompatActivity {

    Context context;
    Integer[] valuesHowStore, valuesIsThe, valuesHowOften, valuesHowClean;
    Methods methods;

    Spinner howStore, isThe, howOften, howClean;
    Button howStoreR, isTheR, howOftenR, howCleanR, save;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_storage);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
        setEvents();
        setSpinnerValues(MyConstants.ALL);
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
            methods.removeEntry(context, tableName, dateTime_);
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_);
        while (cursor.moveToNext()) {
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("howDoYouS")), valuesHowStore, howStore);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("isThe")), valuesIsThe, isThe);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("howOften")), valuesHowOften, howOften);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("howDoYouC")), valuesHowClean, howClean);
        }


    }


    private void setEvents() {
        howStoreR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE);
            }
        });

        isTheR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_IS_THE_D);
            }
        });

        howOftenR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_HOW_OFTEN);
            }
        });

        howCleanR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, howStore, isThe, howOften, howClean);
                if (!spinnerNull) {
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
                            ArrayList<String> strings = methods.getConfiguredStringForInsert(
                                    valuesHowStore[howStore.getSelectedItemPosition()] + "",
                                    valuesIsThe[isThe.getSelectedItemPosition()] + "",
                                    valuesHowOften[howOften.getSelectedItemPosition()] + "",
                                    valuesHowClean[howClean.getSelectedItemPosition()] + ""

                            );

                            methods.insertData(context, tableName, dateTime_, strings);
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
    }

    private void initCompos() {
        howStore = findViewById(R.id.spinner_howDoYouStore_HouseHoldStorage);
        isThe = findViewById(R.id.spinner_isTheDrinking_HouseHoldStorage);
        howOften = findViewById(R.id.spinner_howOften_HouseHoldStorage);
        howClean = findViewById(R.id.spinner_howDoYouClean_HouseHoldStorage);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        howStoreR = findViewById(R.id.btn_rHowDoYouStore_HouseHoldStorage);
        isTheR = findViewById(R.id.btn_rIsTheDrinking_HouseHoldStorage);
        howOftenR = findViewById(R.id.btn_rHowOften_HouseHoldStorage);
        howCleanR = findViewById(R.id.btn_rHowDoYouClean_HouseHoldStorage);
        save = findViewById(R.id.btn_saveActivityHousehold);
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE) {
            valuesHowStore = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE,
                    valuesHowStore, howStore, true);

        } else if (tableKey == MyConstants.DL_HOUSE_HOLD_IS_THE_D) {
            valuesIsThe = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_IS_THE_D,
                    valuesIsThe, isThe, false);

        } else if (tableKey == MyConstants.DL_HOUSE_HOLD_HOW_OFTEN) {
            valuesHowOften = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_OFTEN,
                    valuesHowOften, howOften, true);

        } else if (tableKey == MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN) {
            valuesHowClean = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN,
                    valuesHowClean, howClean, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesHowStore = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_STORE,
                    valuesHowStore, howStore, true);

            valuesIsThe = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_IS_THE_D,
                    valuesIsThe, isThe, false);

            valuesHowOften = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_OFTEN,
                    valuesHowOften, howOften, true);

            valuesHowClean = methods.setSpinnerThings(context, MyConstants.DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN,
                    valuesHowClean, howClean, true);


        }
    }
}