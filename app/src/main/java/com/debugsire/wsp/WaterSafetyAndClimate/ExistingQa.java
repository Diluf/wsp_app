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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class ExistingQa extends AppCompatActivity {

    private static final String TAG = "ExistingQa ++++++ ";
    Context context;
    Integer[] valuesWaterSafety, valuesWaterQualityParam, valuesWaterQualityTap, valuesAwareness, valuesMode, valuesFreq;
    Methods methods;

    Spinner waterSafety, waterQualityParam, freq;
    Button waterSafetyR, waterQualityParamR, waterQualityTapR, awarenessR, modeR, freqR, save;
    LinearLayout waterQualityTap, awareness, mode;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_existing_qa);

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
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("wsp")), valuesWaterSafety, waterSafety);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("wqp")), valuesWaterQualityParam, waterQualityParam);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("wqt")), valuesWaterQualityTap, waterQualityTap);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("aow")), valuesAwareness, awareness);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("moc")), valuesMode, mode);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("foa")), valuesFreq, freq);
        }


    }

    private void setEvents() {
        waterSafetyR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_WATER_SAFETY);
            }
        });


        waterQualityParamR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM);
            }
        });


        waterQualityTapR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP);
            }
        });


        awarenessR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_AWARENESS);
            }
        });


        modeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_MODE);
            }
        });


        freqR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_EXISTINGQA_FREQ);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, waterSafety, waterQualityParam, freq);
                boolean multiCheckNull = methods.isMultiSelectorNull(context, waterQualityTap, awareness);
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
                                    valuesWaterSafety[waterSafety.getSelectedItemPosition()] + "",
                                    valuesWaterQualityParam[waterQualityParam.getSelectedItemPosition()] + "",
                                    methods.getCheckedValues(valuesWaterQualityTap, waterQualityTap),
                                    methods.getCheckedValues(valuesAwareness, awareness),
                                    methods.getCheckedValues(valuesMode, mode),
                                    valuesFreq[freq.getSelectedItemPosition()] + ""

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
        waterSafety = findViewById(R.id.spinner_waterSafety_ExistingQA);
        waterQualityParam = findViewById(R.id.spinner_waterQualityParam_ExistingQA);
        freq = findViewById(R.id.spinner_freq_ExistingQA);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        waterQualityTap = findViewById(R.id.ll_wateerQualityTap_ExistingQA);
        awareness = findViewById(R.id.ll_awareness_ExistingQA);
        mode = findViewById(R.id.ll_mode_ExistingQA);

        waterSafetyR = findViewById(R.id.btn_resynchWaterSafe_ExistingQA);
        waterQualityParamR = findViewById(R.id.btn_resynchWaterQualityParam_ExistingQA);
        waterQualityTapR = findViewById(R.id.btn_resynchWaterQualityTap_ExistingQA);
        awarenessR = findViewById(R.id.btn_resynchAwareness_ExistingQA);
        modeR = findViewById(R.id.btn_resynchMode_ExistingQA);
        freqR = findViewById(R.id.btn_resynchFreq_ExistingQA);
        save = findViewById(R.id.btn_saveActivityExistingQA);
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_EXISTINGQA_WATER_SAFETY) {
            valuesWaterSafety = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_SAFETY,
                    valuesWaterSafety, waterSafety, false);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM) {
            valuesWaterQualityParam = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM,
                    valuesWaterQualityParam, waterQualityParam, false);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP) {
            valuesWaterQualityTap = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP,
                    valuesWaterQualityTap, waterQualityTap, false);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_AWARENESS) {
            valuesAwareness = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_AWARENESS,
                    valuesAwareness, awareness, true);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_MODE) {
            valuesMode = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_MODE,
                    valuesMode, mode, true);
        } else if (tableKey == MyConstants.DL_EXISTINGQA_FREQ) {
            valuesFreq = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_FREQ,
                    valuesFreq, freq, false);
        } else if (tableKey == MyConstants.ALL) {
            valuesWaterSafety = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_SAFETY,
                    valuesWaterSafety, waterSafety, false);

            valuesWaterQualityParam = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_PARAM,
                    valuesWaterQualityParam, waterQualityParam, false);

            valuesWaterQualityTap = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_WATER_QUAL_TAP,
                    valuesWaterQualityTap, waterQualityTap, false);

            valuesAwareness = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_AWARENESS,
                    valuesAwareness, awareness, true);

            valuesMode = methods.setMultipleSelectorView(context, MyConstants.DL_EXISTINGQA_MODE,
                    valuesMode, mode, true);

            valuesFreq = methods.setSpinnerThings(context, MyConstants.DL_EXISTINGQA_FREQ,
                    valuesFreq, freq, false);
        }
    }
}
