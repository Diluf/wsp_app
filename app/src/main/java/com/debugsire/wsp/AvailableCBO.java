package com.debugsire.wsp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.debugsire.wsp.Algos.Adapters.DownloadedCBOAdapter;
import com.debugsire.wsp.Algos.Adapters.DropdownAdapter;
import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.POJOs.DownloadedCBOPOJO;
import com.debugsire.wsp.Algos.WebService.*;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AvailableCBO extends AppCompatActivity {

    Button makeOff, resynch;
    ListView offlineList;
    Context context;
    private String TAG = "----------- ";
    Spinner dsd, cbo;
    ArrayList<String> alDSDs, alCBOs, alCBOsId;
    ArrayList<DownloadedCBOPOJO> offlineCbopojoArrayList;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_cbo);

        context = AvailableCBO.this;
        methods = new Methods();

        makeOff = findViewById(R.id.btn_MakeOffline_AvailableCBO);
        resynch = findViewById(R.id.btn_rAvailCbo_AvailableCBO);
        dsd = findViewById(R.id.sp_DSD_AvailableCBO);
        cbo = findViewById(R.id.sp_CBOName_AvailableCBO);
        offlineList = findViewById(R.id.lv_availCBO_AvailableCBO);

        loadOfflineCBOs();
        addEventListeners();

    }

    private void addEventListeners() {
        offlineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DownloadedCBOPOJO downloadedCBOPOJO = offlineCbopojoArrayList.get(i);
                Intent intent = new Intent(context, Home.class);
                intent.putExtra("cboName", downloadedCBOPOJO.getCboName());
                methods.setSharedPref(context, MyConstants.SHARED_CBO_NUM, downloadedCBOPOJO.getCboNum());
                startActivity(intent);
            }
        });


        makeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (cbo.getSelectedItemPosition() != 0) {
                        JSONObject object = methods.getDownloadConfigs(MyConstants.ALL);
                        object.put("cboId", alCBOsId.get(cbo.getSelectedItemPosition()));
                        JSONObject userThings = methods.getLoggedUserName();
                        object.put("userName", userThings.getString("userName"));
                        object.put("password", userThings.getString("password"));
                        object.put("columnName", MyConstants.MY_APPLICATION_ACCESS_COLUMN);
                        new AsyncWebService(context, MyConstants.ACTION_GET_BY_CBO_NAME_DOWNLOADS)
                                .execute(WebRefferences.downloadByCbo.methodName, object.toString());
                    } else {
                        methods.showToast("Select a valid CBO Name to continue", context, MyConstants.MESSAGE_ERROR);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                startActivity(new Intent(context, Home.class));

            }
        });

        resynch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncWebService(context, MyConstants.ACTION_GET_DSD_AND_CBO)
                        .execute(WebRefferences.getDSDandCBO.methodName, methods.getLoggedUserName().toString());

            }
        });

        loadDSDSpinner();

        dsd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                loadCbo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void loadCbo() {
        String selectedDsd = alDSDs.get(dsd.getSelectedItemPosition());
        alCBOs = new ArrayList<>();
        alCBOsId = new ArrayList<>();
        alCBOs.add("-");
        alCBOsId.add("-");
        Cursor data = MyDB.getData("SELECT * FROM cboB WHERE idDSD = " +
                " (SELECT idDsd FROM locations WHERE dsd = '" + selectedDsd + "') ");
        while (data.moveToNext()) {
            alCBOs.add(data.getString(data.getColumnIndex("name")));
            alCBOsId.add(data.getString(data.getColumnIndex("CBONum")));
        }

        cbo.setAdapter(null);
        DropdownAdapter adapter = new DropdownAdapter(context, alCBOs);
        cbo.setAdapter(adapter);
    }

    public void loadOfflineCBOs() {
        offlineList.setAdapter(null);
        offlineCbopojoArrayList = new ArrayList<>();

        Cursor data = MyDB.getData("SELECT * FROM cboBasicDetails");
        while (data.moveToNext()) {
            offlineCbopojoArrayList.add(new DownloadedCBOPOJO(
                    data.getString(data.getColumnIndex("CBONum")),
                    data.getString(data.getColumnIndex("name")),
                    data.getString(data.getColumnIndex("street")),
                    data.getString(data.getColumnIndex("road")),
                    data.getString(data.getColumnIndex("village")),
                    data.getString(data.getColumnIndex("town")),
                    data.getString(data.getColumnIndex("dateTime_"))
            ));

        }


        offlineList.setAdapter(new DownloadedCBOAdapter(context, offlineCbopojoArrayList));
    }

    public void loadDSDSpinner() {
        dsd.setAdapter(null);
        Cursor data = MyDB.getData("SELECT * FROM locations GROUP BY dsd ORDER BY dsd ASC");
        alDSDs = new ArrayList<>();
        alDSDs.add("-");
        while (data.moveToNext()) {
            alDSDs.add(data.getString(data.getColumnIndex("dsd")));
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, alDSDs); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dsd.setAdapter(spinnerArrayAdapter);
        dsd.setSelection(0);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (MyDB.getData("SELECT * FROM wsp_droplist").getCount() == 0) {
            new AsyncWebService(context, MyConstants.ACTION_GET_DROP_LIST)
                    .execute(WebRefferences.getDLValues.methodName, MyConstants.ALL);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.on_available, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signOut_OnAvailable:
                MyDB.setData("DROP DATABASE _WSP_");
                onBackPressed();
                return true;
            case R.id.menu_about_OnAvailable:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
