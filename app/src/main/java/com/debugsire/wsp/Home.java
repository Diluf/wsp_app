package com.debugsire.wsp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.debugsire.wsp.Algos.Adapters.HomeAdapter;
import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.POJOs.HomePojos;
import com.debugsire.wsp.Algos.POJOs.SubHomePojos;
import com.debugsire.wsp.EndUserAssessment.EndUserAssessment;
import com.debugsire.wsp.WaterSafetyAndClimate.WaterSafetyAndClimate;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.leinardi.android.speeddial.SpeedDialActionItem;
//import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class Home extends AppCompatActivity {

    Context context;
    ListView listView;
    ArrayList<HomePojos> arrayList, bottomArrayList;
    Methods methods;
    LayoutInflater inflater;
    LinearLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getIntent().getExtras().getString("cboName"));

        context = this;
        methods = new Methods();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initCompos();
        loadListView();
        addEventListeners();

    }

    private void loadListView() {
        arrayList = new ArrayList<>();
        arrayList.add(new HomePojos(getString(R.string.title_cbo_basic_details), "cboBasicDetailsFilled"));
        arrayList.add(new HomePojos(getString(R.string.title_cbo_connection_details), "connectionDFilled"));
        arrayList.add(new HomePojos(getString(R.string.title_coverage_by_scheme), "coverageInfoFilled"));
        arrayList.add(new HomePojos(getString(R.string.title_population_served), "pop"));

        listView.setAdapter(new HomeAdapter(context, arrayList, methods));

        //
        //
        //
        //
        //

        bottomArrayList = new ArrayList<>();
        bottomArrayList.add(new HomePojos("Water Safety and climate resilience", ""));
        bottomArrayList.add(new HomePojos("End-user assessment at household level", ""));
        loadBottomButtons();

    }

    private void addEventListeners() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomePojos homePojos = arrayList.get(i);
                if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_cbo_basic_details))) {
                    Intent intent = new Intent(getApplicationContext(), CboBasicDetails.class);
                    intent.putExtra("dateTime_", methods.getSingleStringFromDB("dateTime_", "cboBasicDetailsFilled", "CBONum", Methods.getCBONum(context)));
                    intent.putExtra("tableName", "cboBasicDetailsFilled");
                    startActivity(intent);

                } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_cbo_connection_details))) {
                    Intent intent = new Intent(getApplicationContext(), CboConnectionDetails.class);
                    intent.putExtra("dateTime_", methods.getSingleStringFromDB("dateTime_", "connectionDFilled", "CBONum", Methods.getCBONum(context)));
                    intent.putExtra("tableName", "connectionDFilled");
                    startActivity(intent);

                } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_coverage_by_scheme))) {
                    View v = inflater.inflate(R.layout.dialog_which_one, null);
                    LinearLayout itemsLinearLayout = v.findViewById(R.id.ll_itemsWrapperrDialogWhichOne);

                    final AlertDialog builder = new AlertDialog.Builder(context)
                            .setView(v)
                            .create();

                    ArrayList<String> filledStrings = new ArrayList<>();
                    Cursor data = methods.getCursorBySelectedCBONum(context, "coverageInfo");
                    while (data.moveToNext()) {
                        final String idGnd = data.getString(data.getColumnIndex("idGnd"));
                        loadView(idGnd, inflater, builder, itemsLinearLayout, true);
                        filledStrings.add(idGnd);
                    }

                    data = methods.getCursorBySelectedCBONum(context, "coverageInfoFilled");
                    while (data.moveToNext()) {
                        final String idGnd = data.getString(data.getColumnIndex("idGnd"));
                        if (!filledStrings.contains(idGnd)) {
                            loadView(idGnd, inflater, builder, itemsLinearLayout, false);
                        }
                    }

                    v.findViewById(R.id.btn_addNewDialogWhichOne).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                            Intent intent = new Intent(getApplicationContext(), CoverageByTheScheme.class);
                            intent.putExtra("idGnd", "-");
                            startActivity(intent);
                        }
                    });

                    builder.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.dismiss();
                        }
                    });

                    builder.show();

                } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_population_served))) {
                    Intent intent = new Intent(getApplicationContext(), PopulationServed.class);
                    intent.putExtra("dateTime_", methods.getSingleStringFromDB("dateTime_", "pop", "CBONum", Methods.getCBONum(context)));
                    intent.putExtra("tableName", "pop");
                    startActivity(intent);


                }
            }
        });


    }

    private void loadView(final String idGnd, LayoutInflater inflater, final AlertDialog builder, LinearLayout itemsLinearLayout, boolean downloadedEntry) {
        String dsdName = methods.getSingleStringFromDB("dsd", "locations", "idGnd", idGnd);
        String gndName = methods.getSingleStringFromDB("gnd", "locations", "idGnd", idGnd);
//                        DSDGND View
        View dsdGndView = inflater.inflate(R.layout.item_gnd_dsd_button, null);
        ((TextView) dsdGndView.findViewById(R.id.tv_dsdItemGndDsdButton)).setText(dsdName == null ? "?" : dsdName);
        ((TextView) dsdGndView.findViewById(R.id.tv_gndItemGndDsdButton)).setText(gndName);
        CardView cardView = dsdGndView.findViewById(R.id.card_dsd_gndItemGndDsdButton);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CoverageByTheScheme.class);
                intent.putExtra("idGnd", idGnd);
                startActivity(intent);
                builder.dismiss();
            }
        });

        if (downloadedEntry) {
            if (methods.getSingleStringFromDB("village", "coverageInfoFilled", "idGnd", idGnd) != null) {
                ((ImageView) dsdGndView.findViewById(R.id.image_rightItemGndDsdButton)).setImageResource(R.drawable.ic_done_black_24dp);
            }
            dsdGndView.findViewById(R.id.tv_downloadedItemGndDsdButton).setVisibility(View.VISIBLE);
        }

        itemsLinearLayout.addView(dsdGndView);
    }

    private void initCompos() {
        listView = findViewById(R.id.lv_Home);
        //
        wrapper = findViewById(R.id.ll_wrapperHome);
    }

    private void loadBottomButtons() {
        wrapper.removeAllViews();
        for (int i = 0; i < bottomArrayList.size(); i++) {
            final HomePojos homePojos = bottomArrayList.get(i);

            View view = inflater.inflate(R.layout.item_sub_button, null);
            ((TextView) view.findViewById(R.id.tv_titleItemSubButton)).setText(homePojos.getTitle());

            if (i == 0) {
                view.findViewById(R.id.card_itemSubButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), WaterSafetyAndClimate.class));
                    }
                });


            } else {
                final Cursor cursor = MyDB.getData("SELECT * FROM basicInfo WHERE CBONum = '" + Methods.getCBONum(context) + "' GROUP BY generatedId");
                int count = cursor.getCount();
                if (count > 0) {
                    TextView countTextView = view.findViewById(R.id.tv_badgeItemSubButton);
                    countTextView.setText(count + "");
                    countTextView.setVisibility(View.VISIBLE);
                }

                view.findViewById(R.id.card_itemSubButton).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<String[]> strings = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            strings.add(new String[]{
                                    cursor.getString(cursor.getColumnIndex("name")),
                                    cursor.getString(cursor.getColumnIndex("com")),
                                    cursor.getString(cursor.getColumnIndex("dateTime_")),
                                    cursor.getString(cursor.getColumnIndex("generatedId"))
                            });
                        }

                        final Intent intent = new Intent(getApplicationContext(), EndUserAssessment.class);
                        if (strings.size() != 0) {
                            View v = inflater.inflate(R.layout.dialog_which_one, null);
                            LinearLayout itemsLinearLayout = v.findViewById(R.id.ll_itemsWrapperrDialogWhichOne);

                            final AlertDialog builder = new AlertDialog.Builder(context)
                                    .setView(v)
                                    .create();

                            v.findViewById(R.id.btn_addNewDialogWhichOne).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    methods.setSharedPref(context, MyConstants.ACTION_SELECTED_GENERATED_ID, null);
                                    startActivity(intent);
                                    builder.dismiss();
                                }
                            });

                            for (final String[] array :
                                    strings) {
                                View dsdGndView = inflater.inflate(R.layout.item_gnd_dsd_button, null);
                                ((TextView) dsdGndView.findViewById(R.id.tv_dsdItemGndDsdButton)).setText(array[1]);
                                ((TextView) dsdGndView.findViewById(R.id.tv_gndItemGndDsdButton)).setText(array[0]);
                                CardView cardView = dsdGndView.findViewById(R.id.card_dsd_gndItemGndDsdButton);
                                cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        methods.setSharedPref(context, MyConstants.ACTION_SELECTED_GENERATED_ID, array[3]);
                                        startActivity(intent);
                                        builder.dismiss();
                                    }
                                });

                                ((TextView) dsdGndView.findViewById(R.id.tv_downloadedItemGndDsdButton)).setText(array[2]);
                                dsdGndView.findViewById(R.id.tv_downloadedItemGndDsdButton).setVisibility(View.VISIBLE);

                                itemsLinearLayout.addView(dsdGndView);
                            }

                            builder.show();
                        } else {
                            methods.setSharedPref(context, MyConstants.ACTION_SELECTED_GENERATED_ID, null);
                            startActivity(intent);
                        }

                    }
                });

            }

            wrapper.addView(view);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }
}
