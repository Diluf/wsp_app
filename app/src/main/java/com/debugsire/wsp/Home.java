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
    FloatingActionButton floatingActionButton;
    ArrayList<HomePojos> arrayList;
    Methods methods;
    LayoutInflater inflater;

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


//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final HomePojos homePojos = arrayList.get(position);
//                final Cursor cursor = methods.getCursorBySelectedCBONum(context, homePojos.getDbName());
//                if (cursor.getCount() == 0) {
//                    return true;
//                }
//
//                final boolean isCoverage = homePojos.getTitle().equalsIgnoreCase(getString(R.string.title_coverage_by_scheme));
//
//                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
//                View v = inflater.inflate(R.layout.dialog_bottom_options, null);
//                ((TextView) v.findViewById(R.id.tv_headerDialogBottomOptions)).setText(homePojos.getTitle());
//                final LinearLayout wrapper = v.findViewById(R.id.ll_moreThanOneDialogBottomOptions);
//                while (cursor.moveToNext()) {
//                    String idGnd = null;
//                    final View itemView = inflater.inflate(R.layout.item_bottom_dialog, null);
//                    ((TextView) itemView.findViewById(R.id.tv_dateTimeItemBottomDialog)).setText(cursor.getString(cursor.getColumnIndex("dateTime_")));
//                    if (isCoverage) {
//                        idGnd = cursor.getString(cursor.getColumnIndex("idGnd"));
//                        String dsdName = methods.getSingleStringFromDB("dsd", "locations", "idGnd", idGnd);
//                        String gndName = methods.getSingleStringFromDB("gnd", "locations", "idGnd", idGnd);
//                        TextView sub = itemView.findViewById(R.id.tv_subItemBottomDialog);
//                        TextView lastSub = itemView.findViewById(R.id.tv_lastSubItemBottomDialog);
//                        sub.setText(dsdName);
//                        lastSub.setText(gndName);
//                        sub.setVisibility(View.VISIBLE);
//                        lastSub.setVisibility(View.VISIBLE);
//                    }
//                    final String finalIdGnd = idGnd;
//                    itemView.findViewById(R.id.imgBDelItemBottomDialog).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            final AlertDialog builder = methods.getRequiredAlertDialog(context, MyConstants.REMOVE_DIALOG);
//                            builder.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (isCoverage) {
//                                        MyDB.setData("DELETE FROM " + homePojos.getDbName() + " WHERE CBONum = '" + Methods.getCBONum(context) + "' AND " +
//                                                " idGnd = '" + finalIdGnd + "'");
//                                        Log.d("000000000000 ", "onClick: " + finalIdGnd);
//                                    } else {
//                                        MyDB.setData("DELETE FROM " + homePojos.getDbName() + "  WHERE CBONum = '" + Methods.getCBONum(context) + "'");
//                                    }
//                                    builder.dismiss();
//                                    methods.showToast(getString(R.string.removed), context, MyConstants.MESSAGE_SUCCESS);
//                                    loadListView();
//                                    wrapper.removeView(itemView);
//                                    if (wrapper.getChildCount() == 0) {
//                                        bottomSheetDialog.dismiss();
//                                    }
//                                }
//                            });
//                            builder.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            });
//                            builder.show();
//                        }
//                    });
//
//                    wrapper.addView(itemView);
//                }
//                bottomSheetDialog.setContentView(v);
//                bottomSheetDialog.show();
//
//                return true;
//            }
//        });

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
        floatingActionButton = findViewById(R.id.fab);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadListView();
    }
}
