package com.debugsire.wsp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;

import com.debugsire.wsp.Algos.Adapters.HomeAdapter;
import com.debugsire.wsp.Algos.CustomDrawable;
import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.POJOs.HomePojos;
import com.debugsire.wsp.EndUserAssessment.EndUserAssessment;
import com.debugsire.wsp.WaterSafetyAndClimate.WaterSafetyAndClimate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    Context context;
    ListView listView;
    FloatingActionButton floatingActionButton;
    ArrayList<HomePojos> arrayList;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getExtras().getString("cboName"));

        context = this;
        methods = new Methods();
        initCompos();
        loadListView();
        addEventListeners();


    }

    private void loadListView() {
        arrayList = new ArrayList<>();
        arrayList.add(new HomePojos(getString(R.string.title_cbo_basic_details), ""));
        arrayList.add(new HomePojos(getString(R.string.title_cbo_connection_details), ""));
        arrayList.add(new HomePojos(getString(R.string.title_coverage_by_scheme), ""));
        arrayList.add(new HomePojos(getString(R.string.title_population_served), ""));

        listView.setAdapter(new HomeAdapter(context, arrayList));
    }

    private void addEventListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomePojos homePojos = arrayList.get(i);
                if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_cbo_basic_details))) {
                    startActivity(new Intent(getApplicationContext(), CboBasicDetails.class));

                } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_cbo_connection_details))) {
                    startActivity(new Intent(getApplicationContext(), CboConnectionDetails.class));

                } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_coverage_by_scheme))) {
                    Cursor data = methods.getCursorBySelectedCBONum(context, "coverageInfo");
                    if (data.getCount() == 1) {
                        while (data.moveToNext()) {
                            Intent intent = new Intent(getApplicationContext(), CoverageByTheScheme.class);
                            intent.putExtra("idGnd", data.getString(data.getColumnIndex("idGnd")));
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(context, data.getCount() + "", Toast.LENGTH_SHORT).show();
                    }

                } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_population_served))) {
                    startActivity(new Intent(getApplicationContext(), PopulationServed.class));

                }
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog alertDialog = new android.app.AlertDialog.Builder(Home.this).create();

                View v = ((LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.dialog_on_home_continue, null);
                Button ws = v.findViewById(R.id.btn_ws);
                Button endU = v.findViewById(R.id.btn_endUser);

                ws.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), WaterSafetyAndClimate.class));
                        alertDialog.dismiss();
                    }
                });

                endU.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), EndUserAssessment.class));
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(v);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
    }

    private void initCompos() {
        listView = findViewById(R.id.lv_Home);
        //
        floatingActionButton = findViewById(R.id.fab);
    }

}
