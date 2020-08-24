package com.debugsire.wsp.EndUserAssessment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debugsire.wsp.Algos.Methods;
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

    Context context;
    ArrayList<SubHomePojos> homePojosArrayList;
    Methods methods;
    LayoutInflater inflater;

    LinearLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_user_assessment);

        context = this;
        methods = new Methods();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initCompos();
        initArrayList();

//        context = this;
//
//
//        ((Button) findViewById(R.id.btn_basicInfo)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, BasicInfoOfHousehold.class));
//            }
//        });
//
//        ((Button) findViewById(R.id.btn_waterAd)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, WaterAdequacy.class));
//            }
//        });
//
//        ((Button) findViewById(R.id.btn_waterQ)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, WaterQuality.class));
//            }
//        });
//
//        ((Button) findViewById(R.id.btn_householdStorage)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, HouseholdStorage.class));
//            }
//        });
//
//        ((Button) findViewById(R.id.btn_waterHealth)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, WaterHealth.class));
//            }
//        });
//
//        ((Button) findViewById(R.id.btn_waterSaving)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, WaterSaving.class));
//            }
//        });
//
//        ((Button) findViewById(R.id.btn_obs)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(context, Observation.class));
//            }
//        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        loadButtons();
    }

    private void loadButtons() {
        wrapper.removeAllViews();
        for (int i = 0; i < homePojosArrayList.size(); i++) {
            final SubHomePojos homePojos = this.homePojosArrayList.get(i);
            int count = methods.getCursorBySelectedCBONum(context, homePojos.getTableName()).getCount();

            View view = inflater.inflate(R.layout.item_sub_button, null);
            ((TextView) view.findViewById(R.id.tv_titleItemSubButton)).setText(homePojos.getTitle());
            if (count > 0) {
                TextView countTextView = view.findViewById(R.id.tv_badgeItemSubButton);
                countTextView.setText(count + "");
                countTextView.setVisibility(View.VISIBLE);
                if (!homePojos.isRepeat()) {
                    ((ImageView) view.findViewById(R.id.image_rightItemSubButton)).setImageResource(R.drawable.ic_done_black_24dp);
                }
            }

            view.findViewById(R.id.card_itemSubButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createCardOnClick(homePojos);
                }
            });

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
        methods.configIntent(context, inflater, homePojos, intent);
    }

    private void initArrayList() {
        homePojosArrayList = new ArrayList<>();
        homePojosArrayList.add(new SubHomePojos(getString(R.string.basic_information_of_the_householder_owner), "basicInfo", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_adequacy), "waterAd", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_quality), "waterQ", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.household_storage), "houseHold", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_health), "waterH", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.water_saving), "waterS", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Observation), "obsEU", true));

    }

    private void initCompos() {
        wrapper = findViewById(R.id.ll_wrapperWaterSafetyAndClimate);
    }


}
