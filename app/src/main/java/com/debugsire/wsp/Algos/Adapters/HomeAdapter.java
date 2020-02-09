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

import com.debugsire.wsp.Algos.POJOs.HomePojos;
import com.debugsire.wsp.R;

import java.util.ArrayList;

/**
 * Created by $anju on 2018-09-29.
 */

public class HomeAdapter extends ArrayAdapter {
    Context context;
    ArrayList<HomePojos> arrayList;
    LayoutInflater inflater;

    public HomeAdapter(@NonNull Context context, ArrayList<HomePojos> arrayList) {
        super(context, R.layout.item_my_header, arrayList);
        this.context = context;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_my_header, null);

        TextView title = convertView.findViewById(R.id.tv_titleItemHeader);
        ImageView edit = convertView.findViewById(R.id.image_optionsItemHeader);

        title.setText(arrayList.get(position).getTitle());

        addEventListeners(arrayList.get(position), edit);

        return convertView;
    }

    private void addEventListeners(final HomePojos homePojos, ImageView edit) {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, homePojos.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
