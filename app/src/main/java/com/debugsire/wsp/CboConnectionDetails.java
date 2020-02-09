package com.debugsire.wsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.google.android.material.textfield.TextInputLayout;

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
        Cursor data = methods.getCursorBySelectedCBONum(context, "connectionD");
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
    }

    private void initCompos() {
        dom = findViewById(R.id.til_domCboConnectionDetails);
        rel = findViewById(R.id.til_relCboConnectionDetails);
        com = findViewById(R.id.til_commCboConnectionDetails);
        schools = findViewById(R.id.til_schoolsCboConnectionDetails);
        health = findViewById(R.id.til_healthCboConnectionDetails);
        gov = findViewById(R.id.til_govCboConnectionDetails);
        other = findViewById(R.id.til_otherCboConnectionDetails);
    }
}
