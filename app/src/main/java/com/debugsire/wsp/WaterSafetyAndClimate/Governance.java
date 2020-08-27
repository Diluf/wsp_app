package com.debugsire.wsp.WaterSafetyAndClimate;

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

public class Governance extends AppCompatActivity {

    Context context;
    Integer[] valuesFairLand, valuesConflicts, valuesIsThere,
            valuesInclusive, valuesTrans, valuesOnYesConflicts, valuesOnYesIsThere;
    Methods methods;

    Spinner fairLand, conflicts, isThere;
    LinearLayout inclusive, trans, onYesConflicts, onYesIsThere;
    Button fairLandR, conflictsR, isThereR,
            inclusiveR, transR, onYesConflictsR, onYesIsThereR, save;

    LinearLayout optionalOnYesConflicts, optionalOnYesIsThere;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_governance);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
        setSpinnerValues(MyConstants.ALL);
        setEvents();
        loadFields();;


//
//
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
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_, false);
        while (cursor.moveToNext()) {
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("fair")), valuesFairLand, fairLand);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("inc")), valuesInclusive, inclusive);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("tra")), valuesTrans, trans);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("con")), valuesConflicts, conflicts);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("theReas")), valuesOnYesConflicts, onYesConflicts);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("isThere")), valuesIsThere, isThere);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("pot")), valuesOnYesIsThere, onYesIsThere);
        }

    }

    private void setEvents() {
        fairLandR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_FAIR_LAND);
            }
        });

        conflictsR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_CONFLICTS);
            }
        });

        isThereR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_IS_THERE);
            }
        });

        inclusiveR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_INCLUSIVE);
            }
        });

        transR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_TRANS);
            }
        });

        onYesConflictsR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS);
            }
        });

        onYesIsThereR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_GOVERNANCE_ONYES_IS_THERE);
            }
        });

        conflicts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesConflicts[i] == 1) {
                    optionalOnYesConflicts.setVisibility(View.VISIBLE);
                } else {
                    optionalOnYesConflicts.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        isThere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesIsThere[i] == 1) {
                    optionalOnYesIsThere.setVisibility(View.VISIBLE);
                } else {
                    optionalOnYesIsThere.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, fairLand, conflicts, isThere);
                boolean multiCheckNull = methods.isMultiSelectorNull(context, inclusive, trans);
                if (!spinnerNull && !multiCheckNull) {
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
                                    valuesFairLand[fairLand.getSelectedItemPosition()] + "",
                                    methods.getCheckedValues(valuesInclusive, inclusive),
                                    methods.getCheckedValues(valuesTrans, trans),
                                    valuesConflicts[conflicts.getSelectedItemPosition()] + "",
                                    conflicts.getSelectedItemPosition() == 1 ? methods.getCheckedValues(valuesOnYesConflicts, onYesConflicts) : "",
                                    valuesIsThere[isThere.getSelectedItemPosition()] + "",
                                    isThere.getSelectedItemPosition() == 1 ? methods.getCheckedValues(valuesOnYesIsThere, onYesIsThere) : ""

                            );

                            methods.insertData(context, tableName, dateTime_, strings, false);
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
        fairLand = findViewById(R.id.spinner_fairLand_Governance);
        conflicts = findViewById(R.id.spinner_conflicts_Governance);
        isThere = findViewById(R.id.spinner_isThere_Governance);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        inclusive = findViewById(R.id.ll_inclusive_Governance);
        trans = findViewById(R.id.ll_trans_Governance);
        onYesConflicts = findViewById(R.id.ll_conflicts_Governance);
        onYesIsThere = findViewById(R.id.ll_isThere_Governance);

        fairLandR = findViewById(R.id.btn_rFairLand_Governance);
        conflictsR = findViewById(R.id.btn_rConflicts_Governance);
        isThereR = findViewById(R.id.btn_rIsThere_Governance);
        inclusiveR = findViewById(R.id.btn_rInclusive_Governance);
        transR = findViewById(R.id.btn_rTrans_Governance);
        onYesConflictsR = findViewById(R.id.btn_rOptionalOnYesConflicts_Governance);
        onYesIsThereR = findViewById(R.id.btn_rOptionalOnYesIsThere_Governance);
        save = findViewById(R.id.btn_saveActivityGovernance);

        optionalOnYesConflicts = findViewById(R.id.ll_optionalOnYesConflicts_Governance);
        optionalOnYesIsThere = findViewById(R.id.ll_optionalOnYesIsThere_Governance);
    }

    //
//
    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_GOVERNANCE_FAIR_LAND) {
            valuesFairLand = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_FAIR_LAND,
                    valuesFairLand, fairLand, true);


        } else if (tableKey == MyConstants.DL_GOVERNANCE_INCLUSIVE) {
            valuesInclusive = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_INCLUSIVE,
                    valuesInclusive, inclusive, true);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_TRANS) {
            valuesTrans = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_TRANS,
                    valuesTrans, trans, true);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_CONFLICTS) {
            valuesConflicts = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_CONFLICTS,
                    valuesConflicts, conflicts, false);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS) {
            valuesOnYesConflicts = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS,
                    valuesOnYesConflicts, onYesConflicts, true);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_IS_THERE) {
            valuesIsThere = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_IS_THERE,
                    valuesIsThere, isThere, false);

        } else if (tableKey == MyConstants.DL_GOVERNANCE_ONYES_IS_THERE) {
            valuesOnYesIsThere = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_IS_THERE,
                    valuesOnYesIsThere, onYesIsThere, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesFairLand = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_FAIR_LAND,
                    valuesFairLand, fairLand, true);

            valuesInclusive = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_INCLUSIVE,
                    valuesInclusive, inclusive, true);

            valuesTrans = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_TRANS,
                    valuesTrans, trans, true);

            valuesConflicts = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_CONFLICTS,
                    valuesConflicts, conflicts, false);

            valuesOnYesConflicts = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_CONFLICTS,
                    valuesOnYesConflicts, onYesConflicts, true);

            valuesIsThere = methods.setSpinnerThings(context, MyConstants.DL_GOVERNANCE_IS_THERE,
                    valuesIsThere, isThere, false);

            valuesOnYesIsThere = methods.setMultipleSelectorView(context, MyConstants.DL_GOVERNANCE_ONYES_IS_THERE,
                    valuesOnYesIsThere, onYesIsThere, true);


        }
    }

}