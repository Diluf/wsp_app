package com.debugsire.wsp.Algos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.debugsire.wsp.Algos.WebService.PostTasksHandler;
import com.debugsire.wsp.R;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


public class Methods {
    private static final String TAG = "Methods+++ ";


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getNowDateTime() {
        return MyConstants.SIMPLE_DATETIME_FORMAT.format(new Date());
    }

    public static String configNull(JSONObject jsonObject, String key, String defaultValue) throws JSONException {
        if (jsonObject.isNull(key)) {
            return defaultValue;
        }
        return jsonObject.getString(key).trim();
    }

    public static String configNull(String value, String defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        return value.trim();
    }


    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
//    Activities Things
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////
    /////////////////////////////

    public Integer[] setSpinnerThings(final Context context, String tableKey, Integer[] values, Spinner spinner, final boolean addNew) {
        spinner.setAdapter(null);

        Cursor data = MyDB.getData("SELECT * FROM wsp_droplist WHERE ref_section = '" + tableKey + "'");
        String[] items;
        if (addNew) {
            items = new String[data.getCount() + 2];
            values = new Integer[data.getCount() + 2];
            items[items.length - 1] = "Add New";
            values[values.length - 1] = -2;
        } else {
            items = new String[data.getCount() + 1];
            values = new Integer[data.getCount() + 1];
        }

        items[0] = "-";
        values[0] = -1;


        while (data.moveToNext()) {
            items[data.getPosition() + 1] = data.getString(data.getColumnIndex("display_label"));
            values[data.getPosition() + 1] = data.getInt(data.getColumnIndex("value"));
        }
        final int length = items.length;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items) {
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (addNew && position == length - 1) {
                    checkedTextView.setTextColor(Color.WHITE);
                    checkedTextView.setTextSize(context.getResources().getDimension(R.dimen.universal_text_size));
                    checkedTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.btnAddNew));

                } else {
                    checkedTextView.setTextSize(context.getResources().getDimension(R.dimen.universal_text_size));
//                    checkedTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                }
                return view;
            }
        };
        spinner.setAdapter(adapter);
        return values;
    }

    public Integer[] setMultipleSelectorView(Context context, String tableKey, Integer[] values, LinearLayout linearLayout, final boolean addNew) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        linearLayout.removeAllViews();
        Cursor data = MyDB.getData("SELECT * FROM wsp_droplist WHERE ref_section = '" + tableKey + "'");
        values = new Integer[data.getCount()];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 12);

        while (data.moveToNext()) {
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.item_multiple_selection, null);
            checkBox.setText(data.getString(data.getColumnIndex("display_label")));
            if (addNew || !data.isLast()) {
                checkBox.setLayoutParams(params);
            }
            linearLayout.addView(checkBox);
            values[data.getPosition()] = data.getInt(data.getColumnIndex("value"));
        }
        if (addNew) {
            Button addNewButton = (Button) inflater.inflate(R.layout.item_add_new_button, null);
            addNewButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            linearLayout.addView(addNewButton);
        }
        return values;
    }

    public void setAlertDialogOnResynch(final Context context, final String tableKey) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure you want to resynchronize values from server?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new AsyncWebService(context, MyConstants.ACTION_RESYNCH_DROP_LIST, tableKey)
                        .execute(WebRefferences.getDLValues.methodName, tableKey);
            }
        });

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        builder.create().show();
    }

    public void setAlertDialogOnAddNew(final Context context, final String tableKey, final Spinner spinner) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_add_new_dropdown, null);

        final AlertDialog builder = new AlertDialog.Builder(context).setView(view).setCancelable(false).create();
        builder.setCancelable(false);

        final TextInputLayout name = view.findViewById(R.id.til_nameDialogAddNewDropdown);
        final TextView errorMessage = view.findViewById(R.id.tv_errorDialogAddNewDropdown);
        Button cancel = view.findViewById(R.id.btn_cancelDialogAddNewDropdown);
        Button save = view.findViewById(R.id.btn_SaveDialogAddNewDropdown);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setSelection(0);
                builder.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer negativeId = getMaxNegativeId("wsp_droplist");
                String s = name.getEditText().getText().toString().trim();

                if (!isAvailOnDB("wsp_droplist", "ref_section", tableKey, "display_label", s)) {
                    MyDB.setData("INSERT INTO wsp_droplist VALUES (" +
                            " '" + negativeId + "', " +
                            " '" + tableKey + "', " +
                            " '" + negativeId + "', " +
                            " '" + s + "', " +
                            " '1', " +
                            " '" + Methods.getNowDateTime() + "' " +
                            ")");
                    showToast("Successfully saved!", context, MyConstants.MESSAGE_SUCCESS);
                    new PostTasksHandler().checkAndWorkOnSpinner(context, tableKey);
//                    if () {
//
//                    }
                    spinner.setSelection(spinner.getCount() - 2, true);
                    builder.dismiss();
                } else {
                    errorMessage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));

                }


            }
        });

        builder.show();
    }

    public JSONObject getLoggedUserName() {
        Cursor data = MyDB.getData("SELECT * FROM user");
        JSONObject object = new JSONObject();

        while (data.moveToNext()) {
            try {
                object.put("userName", data.getString(data.getColumnIndex("userName")));
                object.put("password", data.getString(data.getColumnIndex("password")));
                return object;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return object;
    }

    public String getLoggedUserNameAsString() {
        Cursor data = MyDB.getData("SELECT * FROM user");
        while (data.moveToNext()) {
            return data.getString(data.getColumnIndex("userName"));
        }
        return "";
    }

    public boolean isAvailOnDB(String from, String where, String value) {
        return MyDB.getData("SELECT * FROM " + from + " WHERE " + where + " = '" + value + "' ").getCount() != 0;

    }

    public boolean isAvailOnDB(String tableName) {
        return MyDB.getData("SELECT * FROM " + tableName + "").getCount() != 0;

    }

    public boolean isAvailOnDB(String from, String where1, String value1, String where2, String value2) {
        return MyDB.getData("SELECT * FROM " + from + " WHERE " + where1 + " = '" + value1 + "' AND " +
                " " + where2 + " = '" + value2 + "' ").getCount() != 0;

    }

    public JSONObject getDownloadConfigs(String key) {
        JSONObject object = new JSONObject();
        try {
            if (key.equalsIgnoreCase(MyConstants.ALL)) {
                object.put(MyConstants.ACTION_GET_CBO_DETAILS_DOWNLOADS, true);
                object.put(MyConstants.ACTION_GET_CONNECTION_DOWNLOADS, true);
                object.put(MyConstants.ACTION_GET_COVERAGE_DOWNLOADS, true);
            } else {
                object.put(MyConstants.ACTION_GET_CBO_DETAILS_DOWNLOADS, false);
                object.put(MyConstants.ACTION_GET_CONNECTION_DOWNLOADS, false);
                object.put(MyConstants.ACTION_GET_COVERAGE_DOWNLOADS, false);
                object.put(key, true);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }


    public void showToast(String text, Context context, int type) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        View v = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
        int color;
        if (type == MyConstants.MESSAGE_ERROR) {
            color = ContextCompat.getColor(context, R.color.colorAccent);
        } else if (type == MyConstants.MESSAGE_INFO) {
            color = ContextCompat.getColor(context, R.color.colorPrimary);
        } else {
            color = ContextCompat.getColor(context, R.color.greenDark);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            v.getBackground().setColorFilter(
                    new BlendModeColorFilter(color, BlendMode.SRC_ATOP));
        } else {
            v.getBackground().setColorFilter(
                    color, PorterDuff.Mode.SRC_ATOP);
        }

//Gets the TextView from the Toast so it can be edited
        ((TextView) v.findViewById(android.R.id.message)).setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

        toast.show();
    }


    public Cursor getCursorBySelectedCBONum(Context context, String tableName) {
        return MyDB.getData("SELECT * FROM " + tableName + " WHERE CBONum = '" + Methods.getCBONum(context) + "'");
    }

    public Cursor getCursor(String tableName) {
        return MyDB.getData("SELECT * FROM " + tableName);
    }

    public Cursor getCursor(String from, String where, String value) {
        return MyDB.getData("SELECT * FROM " + from + " WHERE " + where + " = '" + value + "' ");
    }

    public Cursor getCursor(String from, String where1, String value1, String where2, String value2) {
        return MyDB.getData("SELECT * FROM " + from + " WHERE " + where1 + " = '" + value1 + "' AND " +
                " " + where2 + " = '" + value2 + "' ");
    }

    public Integer getMaxNegativeId(String tableName) {
        Cursor data = MyDB.getData("SELECT MIN(id) FROM " + tableName);
        while (data.moveToNext()) {
            int i = Integer.parseInt(data.getString(data.getColumnIndex("MIN(id)")));
            if (i == 0 || i == 1) {
                return -10;
            }
            return i - 1;
        }
        return -10;
    }


    //////
    //////
    //////
    //////
    //////
    //////
    //////
    //////
    //////


    public static String getCBONum(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MyConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(MyConstants.SHARED_CBO_NUM, "").trim();
    }

    public static String getSharedPref(Context context, String sharedName) {
        SharedPreferences prefs = context.getSharedPreferences(MyConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(sharedName, "");
    }

    public void setSharedPref(Context context, String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyConstants.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }


    public void loadAnim(Button button, ImageView animation, Context context, boolean isShowAnim) {
        if (isShowAnim) {
            button.setEnabled(false);
            button.setAlpha(.5f);
            animation.setVisibility(View.VISIBLE);
            animation.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_infinite));

        } else {
            button.setEnabled(true);
            button.setAlpha(1f);
            if (animation.getAnimation() != null) {
                animation.getAnimation().cancel();
                animation.getAnimation().reset();
            }
//            animation.startAnimation();
            animation.setVisibility(View.GONE);
        }
    }

    public boolean isTILFieldsNull(Context context, TextInputLayout... textInputLayouts) {
        boolean b = false;
        for (TextInputLayout textInputLayout :
                textInputLayouts) {
            if (textInputLayout.getEditText().getText() == null
                    || textInputLayout.getEditText().getText().toString().trim().isEmpty()) {
                if (!b) {
                    b = true;
                }
                textInputLayout.setErrorTextAppearance(R.style.error_appearance);
                textInputLayout.setError(context.getString(R.string.error_cannot_be_empty));
            }
        }

        return b;
    }

    public boolean isSpinnerNull(Context context, Spinner... spinners) {
        boolean b = false;
        for (Spinner spinner :
                spinners) {
            if (spinner.getSelectedItem().toString().trim().equalsIgnoreCase("-")) {
                if (!b) {
                    b = true;
                }
                TextView errorText = (TextView) spinner.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(context.getColor(R.color.colorAccent));//just to highlight that this is an error
                errorText.setText(context.getString(R.string.error_cannot_be_empty));
            }

        }
        return b;
    }

    public void setSelctedItemForSpinner(int value, Integer[] values, Spinner spinner) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public AlertDialog getSaveConfirmationDialog(Context context, boolean isUpdate) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (isUpdate) {
            builder.setMessage("Data will be update. Wish to continue?");

        } else {
            builder.setMessage("Data will be save. Wish to continue?");

        }
        return builder.create();

    }
}
