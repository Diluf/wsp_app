package com.debugsire.wsp.Algos.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.AvailableCBO;
import com.debugsire.wsp.CoverageByTheScheme;
import com.debugsire.wsp.R;

import java.util.ArrayList;

public class DropdownAdapterForGND extends BaseAdapter {

    private static final String TAG = "DropdownAdapter--- ";
    ArrayList arrayList;
    Context context;
    LayoutInflater layoutInflater;
    String idGnd;
    Methods methods;
    ArrayList<String> filledGndsStrings;

    public DropdownAdapterForGND(Context context, ArrayList arrayList, String idGnd, ArrayList<String> filledGndsStrings, Methods methods) {
        this.arrayList = arrayList;
        this.context = context;
        this.idGnd = idGnd;
        this.filledGndsStrings = filledGndsStrings;
        this.methods = methods;
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

        Log.d(TAG, "getView: " + (methods != null));
        if (methods != null) {
            if (filledGndsStrings.contains(arrayList.get(i).toString())) {
                view.findViewById(R.id.image_rightItemDropDownItem).setVisibility(View.VISIBLE);
            }
        }


        if (i == arrayList.size() - 1) {
            view.findViewById(R.id.rl_seperator_ItemDropdown).setVisibility(View.GONE);
        }

        return view;
    }
}
