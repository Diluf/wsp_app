package com.debugsire.wsp.WaterSafetyAndClimate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.POJOs.SubHomePojos;
import com.debugsire.wsp.CoverageByTheScheme;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class WaterSafetyAndClimate extends AppCompatActivity {

    Context context;
    ArrayList<SubHomePojos> homePojosArrayList;
    Methods methods;
    LayoutInflater inflater;

    LinearLayout wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_safety_and_climate);

        context = this;
        methods = new Methods();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        initCompos();
        initArrayList();
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
        if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.ExistingQA))) {
            intent = new Intent(context, ExistingQa.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.Catchment))) {
            intent = new Intent(context, Catchment.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.Treatment))) {
            intent = new Intent(context, Treatment.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.Distribution))) {
            intent = new Intent(context, Distribution.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.Climate))) {
            intent = new Intent(context, ClimateAndDdr.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.Govern))) {
            intent = new Intent(context, Governance.class);

        } else if (homePojos.getTitle().equalsIgnoreCase(getString(R.string.Observation))) {
            intent = new Intent(context, Observation.class);

        }
        methods.configIntent(context, inflater, homePojos, intent);
    }

    private void initArrayList() {
        homePojosArrayList = new ArrayList<>();
        homePojosArrayList.add(new SubHomePojos(getString(R.string.ExistingQA), "existingQA", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Catchment), "catchment", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Treatment), "treatment", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Distribution), "dist", true));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Climate), "clim", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Govern), "gov", false));
        homePojosArrayList.add(new SubHomePojos(getString(R.string.Observation), "obsWS", false));

    }

    private void initCompos() {
        wrapper = findViewById(R.id.ll_wrapperWaterSafetyAndClimate);
    }
}
