package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.MyStringRandomGen;
import com.debugsire.wsp.Algos.POJOs.MaterialPojos;
import com.debugsire.wsp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Distribution extends AppCompatActivity {
    Context context;
    Integer[] valuesMeter, valuesExpan, valuesInterm, valuesService,
            valuesIden, valuesRisk, valuesOverall, valuesMat, valuesUnit;
    Methods methods;
    LayoutInflater inflater;

    TextInputLayout distName, num;
    Spinner meter, expan, interm, service, mat, unit;
    LinearLayout iden, risk, overall, materialHSV;
    Button meterR, expanR, intermR, serviceR,
            idenR, riskR, overallR, addNewMat, save;
    TextView matCount;

    RelativeLayout headerWrapper;
    String dateTime_, tableName, uniqueString;

    ArrayList<MaterialPojos> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);

        context = this;
        methods = new Methods();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arrayList = new ArrayList<>();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");
        uniqueString = new MyStringRandomGen().generateRandomString();

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
            methods.removeEntry(context, tableName, dateTime_, false);
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_, false);
        while (cursor.moveToNext()) {
            distName.getEditText().setText(cursor.getString(cursor.getColumnIndex("distName")));
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("meter")), valuesMeter, meter);
            num.getEditText().setText(cursor.getString(cursor.getColumnIndex("numCon")));
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("exp")), valuesExpan, expan);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("inter")), valuesInterm, interm);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("serv")), valuesService, service);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("ident")), valuesIden, iden);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("riskMit")), valuesRisk, risk);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("overAll")), valuesOverall, overall);
            uniqueString = cursor.getString(cursor.getColumnIndex("uniqueId"));

            Cursor typeCursor = methods.getCursor("distTypes", "uniqueId", uniqueString, "dateTime_", dateTime_);
            while (typeCursor.moveToNext()) {
                arrayList.add(new MaterialPojos(
                        typeCursor.getInt(typeCursor.getColumnIndex("mat")),
                        typeCursor.getString(typeCursor.getColumnIndex("diam")),
                        typeCursor.getInt(typeCursor.getColumnIndex("un")),
                        typeCursor.getString(typeCursor.getColumnIndex("len"))
                ));
            }
            loadMaterialHorizontal(-1);
        }


    }

    private void setEvents() {
        meterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_METERING);
            }
        });

        expanR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_EXPANDABILITY);
            }
        });


        intermR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_INTER);
            }
        });


        serviceR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_SERVICE);
            }
        });


        idenR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_IDENTIFIED);
            }
        });


        riskR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_RISK_MITIGATION);
            }
        });

        overallR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_OVERALL);
            }
        });

        addNewMat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMaterialDialog(-1);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, meter, interm, service);
                boolean multiCheckNull = methods.isMultiSelectorNull(context, iden, risk, overall);
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
                                    distName.getEditText().getText().toString().trim(),
                                    valuesMeter[meter.getSelectedItemPosition()] + "",
                                    num.getEditText().getText().toString().trim(),
                                    valuesExpan[expan.getSelectedItemPosition()] + "",
                                    valuesInterm[interm.getSelectedItemPosition()] + "",
                                    valuesService[service.getSelectedItemPosition()] + "",
                                    methods.getCheckedValues(valuesIden, iden),
                                    methods.getCheckedValues(valuesRisk, risk),
                                    methods.getCheckedValues(valuesOverall, overall),
                                    uniqueString

                            );

                            String s = methods.insertData(context, tableName, dateTime_, strings, false);
                            MyDB.setData("DELETE FROM distTypes WHERE uniqueId = '" + uniqueString + "' AND dateTime_ = '" + s + "'");
                            for (MaterialPojos materialPojos :
                                    arrayList) {
                                MyDB.setData("INSERT INTO distTypes VALUES(" +
                                        "'" + uniqueString + "', " +
                                        "'" + s + "', " +
                                        "'" + materialPojos.getMat() + "', " +
                                        "'" + materialPojos.getDia() + "', " +
                                        "'" + materialPojos.getUnit() + "', " +
                                        "'" + materialPojos.getLen() + "'" +
                                        ")");
                            }

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

    private void loadMaterialDialog(final int position) {
        final View view = inflater.inflate(R.layout.dialog_materials, null);
        mat = view.findViewById(R.id.spinner_matDialog_Distribution);
        final Button matR = view.findViewById(R.id.btn_resynchMatDialog_Distribution);
        final TextInputLayout dia = view.findViewById(R.id.til_diaDialogMaterials);
        unit = view.findViewById(R.id.spinner_unitDialog_Distribution);
        final Button unitR = view.findViewById(R.id.btn_resynchnUnitDialog_Distribution);
        final TextInputLayout len = view.findViewById(R.id.til_lenDialogMaterials);
        final AlertDialog builder = new AlertDialog.Builder(context).setView(view).setCancelable(false).create();

        valuesMat = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_MATERIAL, valuesMat, mat, false);
        valuesUnit = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_UNIT, valuesUnit, unit, false);

        if (position != -1) {
            ((TextView) view.findViewById(R.id.tv_titleDialogMaterials)).setText("+ Update Existing");
            MaterialPojos materialPojos = arrayList.get(position);
            methods.setSelectedItemForSpinner(materialPojos.getMat(), valuesMat, mat);
            methods.setSelectedItemForSpinner(materialPojos.getUnit(), valuesUnit, unit);
            dia.getEditText().setText(materialPojos.getDia());
            len.getEditText().setText(materialPojos.getLen());
        }

        matR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_MATERIAL);

            }
        });

        unitR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_DISTRIBUTION_UNIT);

            }
        });

        view.findViewById(R.id.btn_cancelDialogAddNewDropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mat = null;
                unit = null;
                builder.dismiss();
            }
        });

        view.findViewById(R.id.btn_SaveDialogAddNewDropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, unit);
                if (!spinnerNull) {
                    MaterialPojos materialPojos = new MaterialPojos(
                            valuesMat[mat.getSelectedItemPosition()],
                            dia.getEditText().getText().toString().trim().isEmpty() ? "0" : dia.getEditText().getText().toString().trim(),
                            valuesUnit[unit.getSelectedItemPosition()],
                            len.getEditText().getText().toString().trim().isEmpty() ? "0" : len.getEditText().getText().toString().trim()
                    );

                    if (position != -1) {
                        arrayList.set(position, materialPojos);
                        loadMaterialHorizontal(position);
                    } else {
                        arrayList.add(0, materialPojos);
                        loadMaterialHorizontal(0);
                    }

                    builder.dismiss();

                } else {
                    methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
                }

            }
        });

        builder.show();
    }

    private void loadMaterialHorizontal(int selected) {
        materialHSV.removeAllViews();
        int size = arrayList.size();
        matCount.setText(size == 0 ? "" : size == 1 ? "1 Entry" : size + " Entries");
        for (int i = 0; i < size; i++) {
            final MaterialPojos materialPojos = arrayList.get(i);
            final View view = inflater.inflate(R.layout.nested_table_type_of_mat, null);
            ((TextView) view.findViewById(R.id.tv_matItemNested_table_type_of_mat)).setText(
                    methods.getSingleStringFromDB(
                            "display_label",
                            "wsp_droplist",
                            "ref_section",
                            MyConstants.DL_DISTRIBUTION_MATERIAL,
                            "value", materialPojos.getMat() + "")
            );
            ((TextView) view.findViewById(R.id.tv_unitItemNested_table_type_of_mat)).setText(
                    methods.getSingleStringFromDB(
                            "display_label",
                            "wsp_droplist",
                            "ref_section",
                            MyConstants.DL_DISTRIBUTION_UNIT,
                            "value", materialPojos.getUnit() + "")
            );
            ((TextView) view.findViewById(R.id.tv_diaItemNested_table_type_of_mat)).setText(materialPojos.getDia());
            ((TextView) view.findViewById(R.id.tv_lenItemNested_table_type_of_mat)).setText(materialPojos.getLen());


            final int finalI = i;
            view.findViewById(R.id.imgb_editItemNested_table_type_of_mat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadMaterialDialog(finalI);
                }
            });
            view.findViewById(R.id.imgb_closeItemNested_table_type_of_mat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog builder = methods.getRequiredAlertDialog(context, MyConstants.REMOVE_DIALOG);
                    builder.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            arrayList.remove(finalI);
                            loadMaterialHorizontal(-1);
                            methods.showToast(getString(R.string.removed), context, MyConstants.MESSAGE_SUCCESS);
                        }
                    });
                    builder.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.dismiss();
                        }
                    });
                    builder.show();
                }
            });
            if (size - 1 == i) {
                view.findViewById(R.id.rl_spacerNestedableTypeMat).setVisibility(View.GONE);
            }
            materialHSV.addView(view);
            if (selected == i) {
                final RelativeLayout placeHolder = view.findViewById(R.id.rl_placeHolderItemNested_table_type_of_mat);
                placeHolder.setVisibility(View.VISIBLE);
                placeHolder.startAnimation(AnimationUtils.loadAnimation(context, R.anim.collapse_out));
                placeHolder.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }
    }

    private void initCompos() {
        distName = findViewById(R.id.til_distActivityDistribution);
        num = findViewById(R.id.til_numActivityDistribution);
        expan = findViewById(R.id.spinner_exp_Distribution);

        meter = findViewById(R.id.spinner_meter_Distribution);
        interm = findViewById(R.id.spinner_inter_Distribution);
        service = findViewById(R.id.spinner_service_Distribution);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        iden = findViewById(R.id.ll_iden_Distribution);
        risk = findViewById(R.id.ll_riskMiti_Distribution);
        overall = findViewById(R.id.ll_overall_Distribution);
        materialHSV = findViewById(R.id.ll_wrapperHorizaontalSVAcrivityDistribution);

        meterR = findViewById(R.id.btn_resynchMeter_Distribution);
        expanR = findViewById(R.id.btn_resynchExpan_Distribution);
        intermR = findViewById(R.id.btn_resynchInter_Distribution);
        serviceR = findViewById(R.id.btn_resynchService_Distribution);
        idenR = findViewById(R.id.btn_resynchIden_Distribution);
        riskR = findViewById(R.id.btn_resynchRiskMitigation_Distribution);
        overallR = findViewById(R.id.btn_resynchOverall_Distribution);
        addNewMat = findViewById(R.id.btn_addNewActivityDistribution);
        save = findViewById(R.id.btn_saveActivityDistribution);

        matCount = findViewById(R.id.tv_matCountNewActivityDistribution);
    }


    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_DISTRIBUTION_METERING) {
            valuesMeter = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_METERING,
                    valuesMeter, meter, false);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_EXPANDABILITY) {
            valuesExpan = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_EXPANDABILITY,
                    valuesExpan, expan, false);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_MATERIAL) {
            if (mat != null) {
                valuesMat = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_MATERIAL, valuesMat, mat, false);
            }

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_UNIT) {
            if (unit != null) {
                valuesUnit = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_UNIT, valuesUnit, unit, false);
            }
//

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_INTER) {
            valuesInterm = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_INTER,
                    valuesInterm, interm, false);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_SERVICE) {
            valuesService = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_SERVICE,
                    valuesService, service, false);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_IDENTIFIED) {
            valuesIden = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_IDENTIFIED,
                    valuesIden, iden, true);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_RISK_MITIGATION) {

            valuesRisk = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_RISK_MITIGATION,
                    valuesRisk, risk, true);

        } else if (tableKey == MyConstants.DL_DISTRIBUTION_OVERALL) {
            valuesOverall = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_OVERALL,
                    valuesIden, overall, true);

        } else if (tableKey == MyConstants.ALL) {
            valuesMeter = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_METERING,
                    valuesMeter, meter, false);

            valuesExpan = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_EXPANDABILITY,
                    valuesExpan, expan, false);

            valuesInterm = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_INTER,
                    valuesInterm, interm, false);

//            valuesIntake = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_INTAKE,
//                    valuesIntake, intake, false);
//
//            valuesAvail = methods.setSpinnerThings(context, MyConstants.DL_TREATMENTSYSTEM_AVAIL,
//                    valuesAvail, avail, false);

            valuesService = methods.setSpinnerThings(context, MyConstants.DL_DISTRIBUTION_SERVICE,
                    valuesService, service, false);

            valuesIden = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_IDENTIFIED,
                    valuesIden, iden, true);

            valuesRisk = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_RISK_MITIGATION,
                    valuesRisk, risk, true);

            valuesOverall = methods.setMultipleSelectorView(context, MyConstants.DL_DISTRIBUTION_OVERALL,
                    valuesIden, overall, true);


        }
    }

    public void removeMatTypes() {
        MyDB.setData("DELETE FROM distTypes WHERE uniqueId = '" + uniqueString + "' AND dateTime_ = '" + dateTime_ + "'");
    }
}
