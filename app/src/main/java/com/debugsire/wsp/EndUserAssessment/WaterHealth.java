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
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class WaterHealth extends AppCompatActivity {

    Context context;
    Integer[] valuesDidAnyone, valuesDidYou;
    Methods methods;

    Spinner didAnyone, didYou;
    Button didAnyoneR, didYouR, save;

    RelativeLayout headerWrapper;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_health);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

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
            methods.removeEntry(context, tableName, dateTime_);
        }
        return true;
    }


    private void loadFields() {
        methods.configHeaderBar(context, dateTime_, headerWrapper);
        Cursor cursor = methods.getCursorFromDateTime(context, tableName, dateTime_);
        while (cursor.moveToNext()) {
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("didA")), valuesDidAnyone, didAnyone);
            methods.setSelectedItemForSpinner(cursor.getInt(cursor.getColumnIndex("didY")), valuesDidYou, didYou);
        }


    }

    private void setEvents() {
        didAnyoneR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_HEALTH_DID_ANY);
            }
        });

        didYouR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                methods.setAlertDialogOnResynch(context, MyConstants.DL_WATER_HEALTH_DID_YOU);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean spinnerNull = methods.isSpinnerNull(context, didAnyone, didYou);
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
                                    valuesDidAnyone[didAnyone.getSelectedItemPosition()] + "",
                                    valuesDidYou[didYou.getSelectedItemPosition()] + ""

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
        didAnyone = findViewById(R.id.spinner_didAnyone_WaterHealth);
        didYou = findViewById(R.id.spinner_didYou_WaterHealth);

        headerWrapper = findViewById(R.id.rl_wrapperTop);

        didAnyoneR = findViewById(R.id.btn_rDidAnyone_WaterHealth);
        didYouR = findViewById(R.id.btn_rDidYou_WaterHealth);
        save = findViewById(R.id.btn_saveActivityWaterHealth);
    }

    public void setSpinnerValues(String tableKey) {
        if (tableKey == MyConstants.DL_WATER_HEALTH_DID_ANY) {
            valuesDidAnyone = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_ANY,
                    valuesDidAnyone, didAnyone, false);

        } else if (tableKey == MyConstants.DL_WATER_HEALTH_DID_YOU) {
            valuesDidYou = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_YOU,
                    valuesDidYou, didYou, false);

        } else if (tableKey == MyConstants.ALL) {
            valuesDidAnyone = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_ANY,
                    valuesDidAnyone, didAnyone, false);

            valuesDidYou = methods.setSpinnerThings(context, MyConstants.DL_WATER_HEALTH_DID_YOU,
                    valuesDidYou, didYou, false);


        }
    }
}