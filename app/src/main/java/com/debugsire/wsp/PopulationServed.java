package com.debugsire.wsp;

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

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.google.android.material.textfield.TextInputLayout;

public class PopulationServed extends AppCompatActivity {

    Context context;
    Methods methods;

    TextInputLayout male, female, less, mid, elder, dis;
    Button save;
    String dateTime_, tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_population_served);

        context = this;
        methods = new Methods();
        dateTime_ = getIntent().getExtras().getString("dateTime_");
        tableName = getIntent().getExtras().getString("tableName");

        initCompos();
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
        Cursor data = methods.getCursorBySelectedCBONum(context, "pop");

        while (data.moveToNext()) {
            male.getEditText().setText(data.getString(data.getColumnIndex("male")));
            female.getEditText().setText(data.getString(data.getColumnIndex("female")));
            less.getEditText().setText(data.getString(data.getColumnIndex("less")));
            mid.getEditText().setText(data.getString(data.getColumnIndex("eighteen")));
            elder.getEditText().setText(data.getString(data.getColumnIndex("elder")));
            dis.getEditText().setText(data.getString(data.getColumnIndex("noOfDis")));
        }
    }

    private void setEvents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = methods.isTILFieldsNull(context, male, female, less, mid, elder, dis);
                if (!b) {
                    final AlertDialog dialog = methods.getSaveConfirmationDialog(context, false);
                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialog.dismiss();
                        }
                    });

                    dialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MyDB.setData("DELETE FROM pop");
                            MyDB.setData("INSERT INTO pop VALUES (" +
                                    " '" + Methods.getCBONum(context) + "', " +
                                    " '" + Methods.configNull(male.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(female.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(less.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(mid.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(elder.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(dis.getEditText().getText().toString(), "0") + "', " +
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
    }

    private void initCompos() {
        male = findViewById(R.id.til_maleActivityPopulationServed);
        female = findViewById(R.id.til_femaleActivityPopulationServed);
        less = findViewById(R.id.til_lessActivityPopulationServed);
        mid = findViewById(R.id.til_midActivityPopulationServed);
        elder = findViewById(R.id.til_elderActivityPopulationServed);
        dis = findViewById(R.id.til_noOfActivityPopulationServed);
        save = findViewById(R.id.btn_saveActivityPopulationServed);

    }
}
