package com.debugsire.wsp.Algos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.AvailableCBO;
import com.debugsire.wsp.CoverageByTheScheme;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class DropdownAdapter extends BaseAdapter {

    ArrayList arrayList;
    Context context;
    LayoutInflater layoutInflater;
    String idGnd;

    public DropdownAdapter(Context context, ArrayList arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public DropdownAdapter(Context context, ArrayList arrayList, String idGnd) {
        this.arrayList = arrayList;
        this.context = context;
        this.idGnd = idGnd;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.item_dropdown_item, null);
        TextView textView = view.findViewById(R.id.tv_item_ItemDropdownItem);
        textView.setText(arrayList.get(i).toString());

        if (context instanceof AvailableCBO) {
            if (MyDB.getData("SELECT * FROM cboBasicDetails WHERE name = '" + arrayList.get(i).toString() + "'").getCount() != 0) {
                ((ImageView) view.findViewById(R.id.image_rightItemDropDownItem)).setVisibility(View.VISIBLE);

            }
        } else if (context instanceof CoverageByTheScheme) {
            if (MyDB.getData("SELECT * FROM coverageInfoFilled WHERE idGnd = '" + idGnd + "'").getCount() != 0) {
                ((ImageView) view.findViewById(R.id.image_rightItemDropDownItem)).setVisibility(View.VISIBLE);

            }
        }

        if (i == arrayList.size() - 1) {
            view.findViewById(R.id.rl_seperator_ItemDropdown).setVisibility(View.GONE);
        }

        return view;
    }
}
