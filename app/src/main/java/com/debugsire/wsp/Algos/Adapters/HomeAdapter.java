package com.debugsire.wsp.Algos.Adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.POJOs.HomePojos;
import com.debugsire.wsp.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

/**
 * Created by $anju on 2018-09-29.
 */

public class HomeAdapter extends ArrayAdapter {
    Context context;
    ArrayList<HomePojos> arrayList;
    LayoutInflater inflater;
    Methods methods;

    public HomeAdapter(@NonNull Context context, ArrayList<HomePojos> arrayList, Methods methods) {
        super(context, R.layout.item_my_header, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        this.methods = methods;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_my_header, null);

        HomePojos homePojos = arrayList.get(position);
        TextView titleTextView = convertView.findViewById(R.id.tv_titleItemHeader);
        TextView countTextView = convertView.findViewById(R.id.tv_badgeItemHeader);

        titleTextView.setText(homePojos.getTitle());

        int count = methods.getCursorBySelectedCBONum(context, homePojos.getDbName()).getCount();
        if (count == 0) {
            countTextView.setVisibility(View.GONE);
        } else {
            countTextView.setText(count + "");
            countTextView.setVisibility(View.VISIBLE);
        }


        return convertView;
    }


}
