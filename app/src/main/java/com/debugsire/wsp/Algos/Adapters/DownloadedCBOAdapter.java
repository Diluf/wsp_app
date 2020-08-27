package com.debugsire.wsp.Algos.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.debugsire.wsp.Algos.Methods;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.POJOs.DownloadedCBOPOJO;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.debugsire.wsp.AvailableCBO;
import com.debugsire.wsp.R;

import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONArray;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DownloadedCBOAdapter extends ArrayAdapter {

    ArrayList<DownloadedCBOPOJO> offlineCbopojoArrayList;
    Context context;
    Methods methods;
    LayoutInflater layoutInflater;

    public DownloadedCBOAdapter(@NonNull Context context, ArrayList<DownloadedCBOPOJO> offlineCbopojoArrayList, Methods methods) {
        super(context, R.layout.item_downloaded_cbo, offlineCbopojoArrayList);
        this.context = context;
        this.offlineCbopojoArrayList = offlineCbopojoArrayList;
        this.methods = methods;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.item_downloaded_cbo, null);

        final TextView cboName = convertView.findViewById(R.id.tv_cboNameDownloadedCBOAdapter);
        final TextView details = convertView.findViewById(R.id.tv_detailsDownloadedCBOAdapter);
        TextView dateTime = convertView.findViewById(R.id.tv_dateTimeDownloadedCBOAdapter);
        Button upload = convertView.findViewById(R.id.btn_uploadDownloadedCBOAdapter);

        final DownloadedCBOPOJO downloadedCBOPOJO = offlineCbopojoArrayList.get(position);

        cboName.setText(downloadedCBOPOJO.getCboName());
        details.setText((downloadedCBOPOJO.getRoad().trim().isEmpty() ? "-" : downloadedCBOPOJO.getRoad()) + "\n"
                + (downloadedCBOPOJO.getStreet().trim().isEmpty() ? "-" : downloadedCBOPOJO.getStreet().trim()) + "\n"
                + (downloadedCBOPOJO.getVillage().trim().isEmpty() ? "-" : downloadedCBOPOJO.getVillage()) + "\n"
                + (downloadedCBOPOJO.getTown().trim().isEmpty() ? "-" : downloadedCBOPOJO.getTown()));
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

        if (methods.getCursor("cboBasicDetailsFilled", "CBONum", downloadedCBOPOJO.getCboNum()).getCount() != 0
                &&
                methods.getCursor("connectionDFilled", "CBONum", downloadedCBOPOJO.getCboNum()).getCount() != 0
                &&
                methods.getCursor("coverageInfoFilled", "CBONum", downloadedCBOPOJO.getCboNum()).getCount() != 0
                &&
                methods.getCursor("pop", "CBONum", downloadedCBOPOJO.getCboNum()).getCount() != 0
        ) {
            upload.setVisibility(View.VISIBLE);
            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = methods.getRequiredAlertDialog(context, MyConstants.UPLOAD_DIALOG);
                    alertDialog.setMessage("\nAre you sure you need to upload following?\n\n\n" + cboName.getText().toString().trim() + "");
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            methods.setSharedPref(context, MyConstants.SHARED_CBO_NUM, downloadedCBOPOJO.getCboNum());
                            ((AvailableCBO) context).startUpload();

                        }
                    });

                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertDialog.show();
                }
            });
        } else {
            upload.setVisibility(View.GONE);
        }


        return convertView;
    }
}
