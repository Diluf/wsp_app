package com.debugsire.wsp.Algos.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.POJOs.DownloadedCBOPOJO;
import com.debugsire.wsp.R;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DownloadedCBOAdapter extends ArrayAdapter {

    ArrayList<DownloadedCBOPOJO> offlineCbopojoArrayList;
    Context context;
    LayoutInflater layoutInflater;

    public DownloadedCBOAdapter(@NonNull Context context, ArrayList<DownloadedCBOPOJO> offlineCbopojoArrayList) {
        super(context, R.layout.item_downloaded_cbo, offlineCbopojoArrayList);
        this.context = context;
        this.offlineCbopojoArrayList = offlineCbopojoArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_downloaded_cbo, null);

        TextView cboName = convertView.findViewById(R.id.tv_cboNameDownloadedCBOAdapter);
        TextView details = convertView.findViewById(R.id.tv_detailsDownloadedCBOAdapter);
        TextView dateTime = convertView.findViewById(R.id.tv_dateTimeDownloadedCBOAdapter);

        DownloadedCBOPOJO downloadedCBOPOJO = offlineCbopojoArrayList.get(position);

        cboName.setText(downloadedCBOPOJO.getCboName());
        details.setText(downloadedCBOPOJO.getRoad() + "\n"
                + downloadedCBOPOJO.getStreet() + "\n"
                + downloadedCBOPOJO.getVillage() + "\n"
                + downloadedCBOPOJO.getTown());
        try {
            Date downloaded = MyConstants.SIMPLE_DATETIME_FORMAT.parse(downloadedCBOPOJO.getDateTime());
            Date expire = DateUtils.addHours(downloaded, 48);
            Date now = new Date();
            String color;
            if (now.after(expire)) {
                color = "#b21f66";
            } else if (now.before(DateUtils.addHours(downloaded, 12))) {
                color = "#0b8457";
            } else {
                color = "#0960bd";
            }

            String text = downloadedCBOPOJO.getDateTime() + "<font color=" + color + "> to <b>"
                    + MyConstants.SIMPLE_DATETIME_FORMAT.format(expire) + "</b></font>";
            dateTime.setText(Html.fromHtml(text));

        } catch (ParseException e) {
            e.printStackTrace();
        }



        return convertView;
    }
}
