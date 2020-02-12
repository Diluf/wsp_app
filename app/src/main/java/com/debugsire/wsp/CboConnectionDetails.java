package com.debugsire.wsp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;

public class CboConnectionDetails extends AppCompatActivity {


    Context context;
    Methods methods;

    TextInputLayout dom, rel, com, schools, health, gov, other;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cbo_connection_details);

        context = this;
        methods = new Methods();

        initCompos();
        setEvents();
        loadFields();
    }

    private void loadFields() {
        Cursor data;
        if (methods.isAvailOnDB("connectionDFilled")) {
            data = methods.getCursor("connectionDFilled");

        } else {
            data = methods.getCursorBySelectedCBONum(context, "connectionD");

        }
        while (data.moveToNext()) {
            dom.getEditText().setText(data.getString(data.getColumnIndex("dom")));
            rel.getEditText().setText(data.getString(data.getColumnIndex("rel")));
            com.getEditText().setText(data.getString(data.getColumnIndex("com")));
            schools.getEditText().setText(data.getString(data.getColumnIndex("sch")));
            health.getEditText().setText(data.getString(data.getColumnIndex("health")));
            gov.getEditText().setText(data.getString(data.getColumnIndex("gov")));
            other.getEditText().setText(data.getString(data.getColumnIndex("other")));
        }
    }

    private void setEvents() {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        if (!methods.isTILFieldsNull(context, dom)) {
                            MyDB.setData("DELETE FROM connectionDFilled");
                            MyDB.setData("INSERT INTO connectionDFilled VALUES (" +
                                    " '" + Methods.getCBONum(context) + "', " +
                                    " '" + Methods.configNull(dom.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(rel.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(com.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(schools.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(health.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(gov.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.configNull(other.getEditText().getText().toString(), "0") + "', " +
                                    " '" + Methods.getNowDateTime() + "' " +
                                    ")");
                            methods.showToast(getString(R.string.saved), context, MyConstants.MESSAGE_SUCCESS);
                            onBackPressed();

                        } else {
                            methods.showToast(getString(R.string.compulsory_cant_empty), context, MyConstants.MESSAGE_ERROR);
                        }
                    }
                });
                dialog.show();

            }
        });
    }

    private void initCompos() {
        dom = findViewById(R.id.til_domCboConnectionDetails);
        rel = findViewById(R.id.til_relCboConnectionDetails);
        com = findViewById(R.id.til_commCboConnectionDetails);
        schools = findViewById(R.id.til_schoolsCboConnectionDetails);
        health = findViewById(R.id.til_healthCboConnectionDetails);
        gov = findViewById(R.id.til_govCboConnectionDetails);
        other = findViewById(R.id.til_otherCboConnectionDetails);
        save = findViewById(R.id.btn_saveCboConnectionDetails);

    }
}
