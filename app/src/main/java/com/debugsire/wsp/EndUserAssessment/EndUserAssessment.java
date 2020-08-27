package com.debugsire.wsp.EndUserAssessment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.POJOs.SubHomePojos;
import com.debugsire.wsp.R;
import com.debugsire.wsp.WaterSafetyAndClimate.Catchment;
import com.debugsire.wsp.WaterSafetyAndClimate.ClimateAndDdr;
import com.debugsire.wsp.WaterSafetyAndClimate.Distribution;
import com.debugsire.wsp.WaterSafetyAndClimate.ExistingQa;
import com.debugsire.wsp.WaterSafetyAndClimate.Governance;
import com.debugsire.wsp.WaterSafetyAndClimate.Observation;
import com.debugsire.wsp.WaterSafetyAndClimate.Treatment;

import java.util.ArrayList;

public class EndUserAssessment extends AppCompatActivity {


    private static final String TAG = "EndUserAssessment0-----";
    Context context;
    ArrayList<SubHomePojos> homePojosArrayList;
    Methods methods;
    LayoutInflater inflater;

    LinearLayout wrapper;
    TextView fullName, commonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_user_assessment);

        context = this;
        methods = new Methods();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initCompos();
        initArrayList();

    }


    @Override
    protected void onResume() {
        super.onResume();
        String selectedGenId = Methods.getSelectedGenId(context);
        Log.d(TAG, "onResume: " + selectedGenId);
        setTopBar(selectedGenId);
        loadButtons(selectedGenId);
    }

    private void setTopBar(String selectedGenId) {
        if (selectedGenId == null) {
            commonName.setVisibility(View.GONE);
            fullName.setText("New Entry");

        } else {
            Cursor cursor = methods.getCursor("basicInfo", "generatedId", selectedGenId);
            while (cursor.moveToNext()) {
                fullName.setText(cursor.getString(cursor.getColumnIndex("name")));
                commonName.setText(cursor.getString(cursor.getColumnIndex("com")));
            }
            commonName.setVisibility(View.VISIBLE);
        }
    }

    private void loadButtons(String generatedId) {
        wrapper.removeAllViews();
        for (int i = 0; i < homePojosArrayList.size(); i++) {
            final SubHomePojos homePojos = this.homePojosArrayList.get(i);
            int count = methods.getCursor(
                    homePojos.getTableName(),
                    "CBONum",
                    Methods.getCBONum(context),
                    "generatedId",
                    generatedId).getCount();

            View view = inflater.inflate(R.layout.item_sub_button, null);
            CardView cardView = view.findViewById(R.id.card_itemSubButton);
            ((TextView) view.findViewById(R.id.tv_titleItemSubButton)).setText(homePojos.getTitle());
            if (count > 0) {
                TextView countTextView = view.findViewById(R.id.tv_badgeItemSubButton);
                countTextView.setText(count + "");
                countTextView.setVisibility(View.VISIBLE);
                if (!homePojos.isRepeat()) {
                    ((ImageView) view.findViewById(R.id.image_rightItemSubButton)).setImageResource(R.drawable.ic_done_black_24dp);
                }
            }

            if (i != 0 && generatedId == null) {
                cardView.setEnabled(false);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.colorDarkGray, getApplication().getTheme()));
            } else {
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createCardOnClick(homePojos);
                    }
                });

            }
            wrapper.addView(view);

        }
    }

    private void createCardOnClick(SubHomePojos homePojos) {
        Intent intent = null;
        if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.basic_information_of_the_householder_owner))) {
            intent = new Intent(context, BasicInfoOfHousehold.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.water_adequacy))) {
            intent = new Intent(context, WaterAdequacy.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.water_quality))) {
            intent = new Intent(context, WaterQuality.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.household_storage))) {
            intent = new Intent(context, HouseholdStorage.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.water_health))) {
            intent = new Intent(context, WaterHealth.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.water_saving))) {
            intent = new Intent(context, WaterSaving.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.Observation))) {
            intent = new Intent(context, Observation.class);

        }
        methods.configIntent(context, inflater, homePojos, intent, true);
    }

    private void initArrayList() {
        homePojosArrayList = new ArrayList<>();
        homePojosArrayList.add(new SubHomePojos(getString(R.string.basic_information_of_the_householder_owner), "basicInfo", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_adequacy), "waterAd", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_quality), "waterQ", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.household_storage), "houseHold", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_health), "waterH", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_saving), "waterS", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Observation), "obsEU", false));

    }

    private void initCompos() {
        fullName = findViewById(R.id.tv_fullEndUserAss);
        commonName = findViewById(R.id.tv_comEndUserAss);
        wrapper = findViewById(R.id.ll_wrapperWaterSafetyAndClimate);

    }


}
