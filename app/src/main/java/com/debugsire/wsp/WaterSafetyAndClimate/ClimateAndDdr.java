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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class ClimateAndDdr extends AppCompatActivity {


    private static final String TAG = "ClimateAndDdr----- ";
    Context context;
    Integer[] valuesIsTheWater, valuesWaterIsAvailable, valuesWhatAreSources,
            valuesOnYesHowIt, valuesOnYesWhatAreThey, valuesOnYesWhatAreTheFlood, valuesOnYesWhatAreTheDrought,
            valuesOnNo, valuesWhatAreTheRecharge, valuesOptionsToReduce, valuesOptionsDrought, valuesOptionsFlood;
    Methods methods;

    Spinner isTheWater, waterIsAvailable, whatAreSources;
    LinearLayout onYesHowIt, onYesWhatAreThey, onYesWhatAreTheEffectsFlood, onYesWhatAreTheEffectsDrought,
            onNo, whatAreTheRecharge, optionsToReduce, optionsDrought, optionsFlood;
    Button isTheWaterR, waterIsAvailableR, whatAreSourcesR,
            onYesHowItR, onYesWhatAreTheyR, onYesWhatAreTheEffectsFloodR, onYesWhatAreTheEffectsDroughtR,
            onNoR, whatAreTheRechargeR, optionsToReduceR, optionsDroughtR, optionsFloodR, save;
    LinearLayout optionalOnYes, optionalOnEffectFlood, optionalOnEffectDrought, optionalOnNo;
    CheckBox drought, flood;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_climate_and_ddr);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
        setSpinnerValues(MyConstants.ALL);
        setEvents();
        loadFields();


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
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("isTheW")), valuesIsTheWater, isTheWater);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("how")), valuesOnYesHowIt, onYesHowIt);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("they")), valuesOnYesWhatAreThey, onYesWhatAreThey);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("effeF")), valuesOnYesWhatAreTheFlood, onYesWhatAreTheEffectsFlood);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("effeD")), valuesOnYesWhatAreTheDrought, onYesWhatAreTheEffectsDrought);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("waterIsA")), valuesWaterIsAvailable, waterIsAvailable);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("reas")), valuesOnNo, onNo);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("whatAreS")), valuesWhatAreSources, whatAreSources);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("whatAreT")), valuesWhatAreTheRecharge, whatAreTheRecharge);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("water")), valuesOptionsToReduce, optionsToReduce);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("drought")), valuesOptionsDrought, optionsDrought);
            methods.setSelectedItemsForMultiSelection(cursor.getString(cursor.getColumnIndex("flood")), valuesOptionsFlood, optionsFlood);
        }


    }


    private void setEvents() {
        isTheWaterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_IS_THE_WATER);
            }
        });

        waterIsAvailableR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE);
            }
        });

        whatAreSourcesR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES);
            }
        });

        onYesHowItR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS);
            }
        });

        onYesWhatAreTheyR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY);
            }
        });

        onYesWhatAreTheEffectsFloodR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_FLOOD);
            }
        });

        onYesWhatAreTheEffectsDroughtR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_DROUGHT);
            }
        });

        onNoR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS);
            }
        });

        whatAreTheRechargeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER);
            }
        });

        optionsToReduceR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_OPTIONS_TO_RED);
            }
        });

        optionsDroughtR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT);
            }
        });

        optionsFloodR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD);
            }
        });

        isTheWater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesIsTheWater[i] == 1) {
                    optionalOnYes.setVisibility(View.VISIBLE);

                } else {
                    optionalOnYes.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        waterIsAvailable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (valuesIsTheWater[i] == 2) {
                    optionalOnNo.setVisibility(View.VISIBLE);

                } else {
                    optionalOnNo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        for (int i = 0; i < onYesWhatAreThey.getChildCount(); i++) {
            if (onYesWhatAreThey.getChildAt(i) instanceof CheckBox) {
                final CheckBox checkBox = (CheckBox) onYesWhatAreThey.getChildAt(i);
                if (valuesOnYesWhatAreThey[i] == 1) {
                    drought = checkBox;
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                optionalOnEffectDrought.setVisibility(View.VISIBLE);
                            } else {
                                optionalOnEffectDrought.setVisibility(View.GONE);
                            }
                        }
                    });
                } else if (valuesOnYesWhatAreThey[i] == 2) {
                    flood = checkBox;
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {
                                optionalOnEffectFlood.setVisibility(View.VISIBLE);
                            } else {
                                optionalOnEffectFlood.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, isTheWater, waterIsAvailable);
                boolean multiCheckNull = methods.isMultiSelectorNull(context, whatAreTheRecharge, optionsToReduce, optionsDrought, optionsFlood);
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
                                    valuesIsTheWater[isTheWater.getSelectedItemPosition()] + "",
                                    isTheWater.getSelectedItemPosition() == 1 ? methods.getCheckedValues(valuesOnYesHowIt, onYesHowIt) : "",
                                    isTheWater.getSelectedItemPosition() == 1 ? methods.getCheckedValues(valuesOnYesWhatAreThey, onYesWhatAreThey) : "",
                                    optionalOnEffectDrought.getVisibility() == View.VISIBLE ? methods.getCheckedValues(valuesOnYesWhatAreTheDrought, onYesWhatAreTheEffectsDrought) : "",
                                    optionalOnEffectFlood.getVisibility() == View.VISIBLE ? methods.getCheckedValues(valuesOnYesWhatAreTheFlood, onYesWhatAreTheEffectsFlood) : "",
                                    valuesWaterIsAvailable[waterIsAvailable.getSelectedItemPosition()] + "",
                                    waterIsAvailable.getSelectedItemPosition() == 2 ? methods.getCheckedValues(valuesOnNo, onNo) : "",
                                    valuesWhatAreSources[whatAreSources.getSelectedItemPosition()] + "",
                                    methods.getCheckedValues(valuesWhatAreTheRecharge, whatAreTheRecharge),
                                    methods.getCheckedValues(valuesOptionsToReduce, optionsToReduce),
                                    methods.getCheckedValues(valuesOptionsDrought, optionsDrought),
                                    methods.getCheckedValues(valuesOptionsFlood, optionsFlood)
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
        isTheWater = findViewById(R.id.spinner_isTheWater_ClimateAndDrr);
        waterIsAvailable = findViewById(R.id.spinner_waterIsAvail_ClimateAndDrr);
        whatAreSources = findViewById(R.id.spinner_whatAreTheS_ClimateAndDrr);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        onYesHowIt = findViewById(R.id.ll_onYesHowItImpacts_ClimateAndDrr);
        onYesWhatAreThey = findViewById(R.id.ll_onYesWhatAreThey_ClimateAndDrr);
        onYesWhatAreTheEffectsFlood = findViewById(R.id.ll_onYesAsBWhatAreTheEffectsOnFlood_ClimateAndDrr);
        onYesWhatAreTheEffectsDrought = findViewById(R.id.ll_onYesAsBWhatAreTheEffectsOnDrought_ClimateAndDrr);
        onNo = findViewById(R.id.ll_onNoWhatAreTheReasons_ClimateAndDrr);
        whatAreTheRecharge = findViewById(R.id.ll_whatAreTheWaterRecharging_ClimateAndDrr);
        optionsToReduce = findViewById(R.id.ll_optionsToReduce_ClimateAndDrr);
        optionsDrought = findViewById(R.id.ll_optionsDrought_ClimateAndDrr);
        optionsFlood = findViewById(R.id.ll_optionsFlood_ClimateAndDrr);

        isTheWaterR = findViewById(R.id.btn_rIsTheWater_ClimateAndDrr);
        waterIsAvailableR = findViewById(R.id.btn_rWaterIsAvail_ClimateAndDrr);
        whatAreSourcesR = findViewById(R.id.btn_rWhatAreTheS_ClimateAndDrr);
        onYesHowItR = findViewById(R.id.btn_rOnYesHowItImpacts_ClimateAndDrr);
        onYesWhatAreTheyR = findViewById(R.id.btn_rOnYesWhatAreThey_ClimateAndDrr);
        onYesWhatAreTheEffectsFloodR = findViewById(R.id.btn_rOnYesAsBWhatAreTheEffectsOnFlood_ClimateAndDrr);
        onYesWhatAreTheEffectsDroughtR = findViewById(R.id.btn_rOnYesAsBWhatAreTheEffectsOnDrought_ClimateAndDrr);
        onNoR = findViewById(R.id.btn_rOnNoWhatAreTheReasons_ClimateAndDrr);
        whatAreTheRechargeR = findViewById(R.id.btn_rWhatAreTheWaterRecharging_ClimateAndDrr);
        optionsToReduceR = findViewById(R.id.btn_rOptionstoReduce_ClimateAndDrr);
        optionsDroughtR = findViewById(R.id.btn_rOptionsDrought_ClimateAndDrr);
        optionsFloodR = findViewById(R.id.btn_rOptionsFlood_ClimateAndDrr);
        save = findViewById(R.id.btn_saveFlood_ClimateAndDrr);

        optionalOnYes = findViewById(R.id.ll_optionalOnYes_ClimateAndDrr);
        optionalOnEffectFlood = findViewById(R.id.ll_optionalOnEffectOnFlood_ClimateAndDrr);
        optionalOnEffectDrought = findViewById(R.id.ll_optionalOnEffectOnDrought_ClimateAndDrr);
        optionalOnNo = findViewById(R.id.ll_optionalOnNo_ClimateAndDrr);
    }


    //
//
    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_CLIMATE_IS_THE_WATER) {
            valuesIsTheWater = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_IS_THE_WATER,
                    valuesIsTheWater, isTheWater, false);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS) {
            valuesOnYesHowIt = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS,
                    valuesOnYesHowIt, onYesHowIt, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY) {
            valuesOnYesWhatAreThey = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY,
                    valuesOnYesWhatAreThey, onYesWhatAreThey, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_FLOOD) {
            valuesOnYesWhatAreTheFlood = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_FLOOD,
                    valuesOnYesWhatAreTheFlood, onYesWhatAreTheEffectsFlood, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_DROUGHT) {
            valuesOnYesWhatAreTheDrought = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_DROUGHT,
                    valuesOnYesWhatAreTheDrought, onYesWhatAreTheEffectsDrought, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE) {
            valuesWaterIsAvailable = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE,
                    valuesWaterIsAvailable, waterIsAvailable, false);

        } else if (tableKey == MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS) {
            valuesOnNo = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS,
                    valuesOnNo, onNo, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES) {
            valuesWhatAreSources = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES,
                    valuesWhatAreSources, whatAreSources, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER) {
            valuesWhatAreTheRecharge = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER,
                    valuesWhatAreTheRecharge, whatAreTheRecharge, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_OPTIONS_TO_RED) {
            valuesOptionsToReduce = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_TO_RED,
                    valuesOptionsToReduce, optionsToReduce, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT) {
            valuesOptionsDrought = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT,
                    valuesOptionsDrought, optionsDrought, true);

        } else if (tableKey == MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD) {
            valuesOptionsFlood = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD,
                    valuesOptionsFlood, optionsFlood, false);

        } else if (tableKey == MyConstants.ALL) {
            valuesIsTheWater = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_IS_THE_WATER,
                    valuesIsTheWater, isTheWater, false);

            valuesOnYesHowIt = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_HOW_IT_IMPACTS,
                    valuesOnYesHowIt, onYesHowIt, true);

            valuesOnYesWhatAreThey = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THEY,
                    valuesOnYesWhatAreThey, onYesWhatAreThey, true);

            valuesOnYesWhatAreTheFlood = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_FLOOD,
                    valuesOnYesWhatAreTheFlood, onYesWhatAreTheEffectsFlood, true);

            valuesOnYesWhatAreTheDrought = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_DROUGHT,
                    valuesOnYesWhatAreTheDrought, onYesWhatAreTheEffectsDrought, true);


            valuesWaterIsAvailable = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WATER_IS_AVAILABLE,
                    valuesWaterIsAvailable, waterIsAvailable, false);

            valuesOnNo = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS,
                    valuesOnNo, onNo, true);

            valuesWhatAreSources = methods.setSpinnerThings(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_SOURCES,
                    valuesWhatAreSources, whatAreSources, true);

            valuesWhatAreTheRecharge = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_WHAT_ARE_THE_WATER,
                    valuesWhatAreTheRecharge, whatAreTheRecharge, true);

            valuesOptionsToReduce = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_TO_RED,
                    valuesOptionsToReduce, optionsToReduce, true);

            valuesOptionsDrought = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT,
                    valuesOptionsDrought, optionsDrought, true);

            valuesOptionsFlood = methods.setMultipleSelectorView(context, MyConstants.DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD,
                    valuesOptionsFlood, optionsFlood, false);

        }
    }

}