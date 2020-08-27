package com.debugsire.wsp.Algos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.POJOs.SubHomePojos;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.debugsire.wsp.Algos.WebService.PostTasksHandler;
import com.debugsire.wsp.R;
import com.debugsire.wsp.WaterSafetyAndClimate.Distribution;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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

    public Integer[] setSpinnerThings(final Context context, final String tableKey, Integer[] values, final Spinner spinner, final boolean addNew) {
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
                    checkedTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_add, 0, 0, 0);
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.btnAddNew));

                } else {
                    checkedTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    view.setBackgroundColor(ContextCompat.getColor(context, R.color.colorWhite));
                    checkedTextView.setTextColor(Color.BLACK);

                }
                return view;
            }
        };
        spinner.setAdapter(adapter);
        //
        //
        //
        //
        //
        //
        final Integer[] finalValues = values;
        if (addNew) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (finalValues[position] == -2) {
                        setAlertDialogOnAddNew(context, tableKey, spinner);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        return values;
    }

    public Integer[] setMultipleSelectorView(final Context context, final String tableKey, Integer[] values, final LinearLayout linearLayout, final boolean addNew) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        linearLayout.removeAllViews();
        Cursor data = MyDB.getData("SELECT * FROM wsp_droplist WHERE ref_section = '" + tableKey + "'");
        values = new Integer[data.getCount()];

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 12);

        JSONArray jsonArray = null;
        if (!getSharedPref(context, tableKey).trim().isEmpty()) {
            try {
                jsonArray = new JSONArray(getSharedPref(context, tableKey).trim());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        while (data.moveToNext()) {
            int value = data.getInt(data.getColumnIndex("value"));
            CheckBox checkBox = (CheckBox) inflater.inflate(R.layout.item_multiple_selection, null);
            checkBox.setText(data.getString(data.getColumnIndex("display_label")));
            if (addNew || !data.isLast()) {
                checkBox.setLayoutParams(params);
            }
            if (jsonArray != null && jsonArray.length() != 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        if ((value + "").equalsIgnoreCase(jsonArray.getString(i).trim())) {
                            checkBox.setChecked(true);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            linearLayout.addView(checkBox);
            values[data.getPosition()] = value;
        }
        if (addNew) {
            Button addNewButton = (Button) inflater.inflate(R.layout.item_add_new_button, null);
            addNewButton.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            Log.d(TAG, "setMultipleSelectorView: " + Arrays.toString(values));
            final Integer[] finalValues = values;
            addNewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAlertDialogOnAddNew(context, inflater, tableKey, linearLayout, finalValues);
                }
            });
            linearLayout.addView(addNewButton);
        }

        View cantView = inflater.inflate(R.layout.item_cannot_be_empty, null);
        cantView.setVisibility(View.INVISIBLE);
        linearLayout.addView(cantView);

        if (jsonArray != null && jsonArray.length() != 0) {
            removeSharedPref(context, tableKey);
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
                doResynchDropDownValues(context, tableKey, MyConstants.ACTION_RESYNCH_DROP_LIST);
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

    public void doResynchDropDownValues(Context context, String tableKey, int face) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ref_section", tableKey);
            jsonObject.put("newEntries", getNewEntriesFromDropList(tableKey));

//            if (tableKey.trim().equalsIgnoreCase(MyConstants.ALL)) {
//                new AsyncWebService(context, MyConstants.ACTION_GET_DROP_LIST)
//                        .execute(WebRefferences.getDLValues.methodName, jsonObject.toString());
//            } else {
            new AsyncWebService(context, face, tableKey)
                    .execute(WebRefferences.getDLValues.methodName, jsonObject.toString());
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONArray getNewEntriesFromDropList(String tableKey) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        Cursor cursor;
        if (tableKey.trim().equalsIgnoreCase(MyConstants.ALL)) {
            cursor = MyDB.getData("SELECT * FROM wsp_droplist WHERE id <= -10");
        } else {
            cursor = MyDB.getData("SELECT * FROM wsp_droplist WHERE id <= -10 AND ref_section = '" + tableKey + "'");

        }
        while (cursor.moveToNext()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ref_section", cursor.getString(cursor.getColumnIndex("ref_section")));
            jsonObject.put("display_label", cursor.getString(cursor.getColumnIndex("display_label")));
            jsonObject.put("id", cursor.getString(cursor.getColumnIndex("id")));
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public void setAlertDialogOnAddNew(final Context context, final String tableKey, final Spinner spinner) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_add_new_dropdown, null);

        final AlertDialog builder = new AlertDialog.Builder(context).setView(view).setCancelable(false).create();

        final TextInputLayout name = view.findViewById(R.id.til_nameDialogAddNewDropdown);

        view.findViewById(R.id.btn_cancelDialogAddNewDropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setSelection(0);
                builder.dismiss();
            }
        });

        view.findViewById(R.id.btn_SaveDialogAddNewDropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer negativeId = getMaxNegativeId("wsp_droplist");
                if (saveIfNoError(context, tableKey, view, negativeId, name.getEditText().getText().toString().trim())) {
                    new PostTasksHandler().checkAndWorkOnSpinner(context, tableKey);
                    spinner.setSelection(spinner.getCount() - 2, true);
                    builder.dismiss();
                }
            }
        });

        builder.show();
    }

    public void setAlertDialogOnAddNew(final Context context, final LayoutInflater inflater, final String tableKey, final LinearLayout linearLayout, final Integer[] integers) {
        final View view = inflater.inflate(R.layout.dialog_add_new_dropdown, null);
        final AlertDialog builder = new AlertDialog.Builder(context).setView(view).setCancelable(false).create();
        final TextInputLayout name = view.findViewById(R.id.til_nameDialogAddNewDropdown);

        view.findViewById(R.id.btn_cancelDialogAddNewDropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        view.findViewById(R.id.btn_SaveDialogAddNewDropdown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer negativeId = getMaxNegativeId("wsp_droplist");
                if (saveIfNoError(context, tableKey, view, negativeId, name.getEditText().getText().toString().trim())) {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < linearLayout.getChildCount(); i++) {
                        if (linearLayout.getChildAt(i) instanceof CheckBox) {
                            CheckBox checkBox = (CheckBox) linearLayout.getChildAt(i);
                            if (checkBox.isChecked()) {
                                jsonArray.put(integers[i]);
                            }
                        }
                    }
                    jsonArray.put(negativeId);
                    setSharedPref(context, tableKey, jsonArray.toString());

                    new PostTasksHandler().checkAndWorkOnSpinner(context, tableKey);
                    builder.dismiss();
                }
            }
        });

        builder.show();
        Log.d(TAG, "setAlertDialogOnAddNew: " + Arrays.toString(integers));
    }

    private boolean saveIfNoError(Context context, String tableKey, View view, int negativeId, String s) {
        final TextView errorMessage = view.findViewById(R.id.tv_errorDialogAddNewDropdown);

        if (isAvailOnDB("wsp_droplist", "ref_section", tableKey, "display_label", s)) {
            errorMessage.setText("Already exist in the list!");
            errorMessage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));

        } else if (s.isEmpty()) {
            errorMessage.setText("Cannot be empty!");
            errorMessage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));

        } else {
            MyDB.setData("INSERT INTO wsp_droplist VALUES (" +
                    " '" + negativeId + "', " +
                    " '" + tableKey + "', " +
                    " '" + negativeId + "', " +
                    " '" + s + "', " +
                    " '1', " +
                    " '" + Methods.getNowDateTime() + "' " +
                    ")");
            showToast("Saved", context, MyConstants.MESSAGE_SUCCESS);
            return true;
        }
        return false;
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
        return MyDB.getData("SELECT * FROM " + from + " WHERE " + where + " = '" + value + "'  COLLATE NOCASE").getCount() != 0;

    }

    public boolean isAvailOnDB(String tableName) {
        return MyDB.getData("SELECT * FROM " + tableName + "").getCount() != 0;

    }

    public boolean isAvailOnDB(String from, String where1, String value1, String where2, String value2) {
        return MyDB.getData("SELECT * FROM " + from + " WHERE " + where1 + " = '" + value1 + "' AND " +
                " " + where2 + " = '" + value2 + "' COLLATE NOCASE").getCount() != 0;

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

    public Cursor getCursorFromDateTime(Context context, String tableName, String dateTime_, boolean hasGeneratedId) {
        if (hasGeneratedId) {
            return MyDB.getData("SELECT * FROM " + tableName + " WHERE dateTime_ = '" + dateTime_ + "' AND  CBONum = '" + Methods.getCBONum(context) + "' " +
                    " AND generatedId = '" + getSelectedGenId(context) + "'");

        } else {
            return MyDB.getData("SELECT * FROM " + tableName + " WHERE dateTime_ = '" + dateTime_ + "' AND  CBONum = '" + Methods.getCBONum(context) + "'");

        }
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

    public String getSingleStringFromDB(String selectedColumn, String tableName, String where, String value) {
        Cursor data = MyDB.getData("SELECT " + selectedColumn + " FROM " + tableName + " WHERE " + where + " = '" + value + "' ");
        while (data.moveToNext()) {
            return data.getString(data.getColumnIndex(selectedColumn));
        }
        return null;
    }

    public String getSingleStringFromDBByCBONum(Context context, String selectedColumn, String tableName) {
        Cursor data = MyDB.getData("SELECT " + selectedColumn + " FROM " + tableName + " WHERE CBONum = '" + Methods.getCBONum(context) + "'");
        while (data.moveToNext()) {
            return data.getString(data.getColumnIndex(selectedColumn));
        }
        return null;

    }

    public String getSingleStringFromDBByCBONum(Context context, String selectedColumn, String tableName, String where, String value) {
        Cursor data = MyDB.getData("SELECT " + selectedColumn + " FROM " + tableName + " WHERE " + where + " = '" + value + "' AND CBONum = '" + Methods.getCBONum(context) + "'");
        while (data.moveToNext()) {
            return data.getString(data.getColumnIndex(selectedColumn));
        }
        return null;

    }

    public void deleteFromDBByCBONum(Context context, String tableName, String where, String value, boolean hasGeneratedId) {
        if (hasGeneratedId) {
            MyDB.setData("DELETE FROM " + tableName + " WHERE " + where + " = '" + value + "' AND CBONum = '" + Methods.getCBONum(context) + "' AND generatedId = '" + getSelectedGenId(context) + "'");
        } else {
            MyDB.setData("DELETE FROM " + tableName + " WHERE " + where + " = '" + value + "' AND CBONum = '" + Methods.getCBONum(context) + "'");
        }
    }

    public String insertData(Context context, String tableName, String dateTime_, ArrayList<String> strings, boolean hasGeneratedId) {
        if (dateTime_ == null) {
            dateTime_ = getNowDateTime();
        } else {
            if (hasGeneratedId) {
                MyDB.setData("DELETE FROM " + tableName + " WHERE CBONum = '" + Methods.getCBONum(context) + "' AND dateTime_ = '" + dateTime_ + "' AND generatedId = '" + getSelectedGenId(context) + "'");

            } else {
                MyDB.setData("DELETE FROM " + tableName + " WHERE CBONum = '" + Methods.getCBONum(context) + "' AND dateTime_ = '" + dateTime_ + "'");

            }
        }

        String[] stringArray = strings.toArray(new String[0]);
        String substring = Arrays.toString(stringArray).substring(1, Arrays.toString(stringArray).length() - 1);
        substring = "'" + getCBONum(context) + "', " + substring + ", '" + dateTime_ + "', '" + getLoggedUserNameAsString() + "', '" + MyConstants.APP_ID + "'";
        Log.d(TAG, "insertData: " + Arrays.toString(stringArray));
        Log.d(TAG, "insertData: " + substring);


        MyDB.setData("INSERT INTO " + tableName + " VALUES (" + substring + ")");

        return dateTime_;
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

    public static String getSelectedGenId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MyConstants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.getString(MyConstants.ACTION_SELECTED_GENERATED_ID, null) == null) {
            return prefs.getString(MyConstants.ACTION_SELECTED_GENERATED_ID, null);
        } else {
            return prefs.getString(MyConstants.ACTION_SELECTED_GENERATED_ID, null).trim();
        }
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

    public void removeSharedPref(Context context, String key) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MyConstants.MY_PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.remove(key);
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

    public String getDashIfNull(String value) {
        if (value == null) {
            return "-";
        }
        return value;
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


    public boolean isTILMobileFieldsHasError(Context context, TextInputLayout... textInputLayouts) {
        boolean b = false;
        if (!isTILFieldsNull(context, textInputLayouts)) {
            for (TextInputLayout textInputLayout :
                    textInputLayouts) {
                if (textInputLayout.getEditText().getText().toString().trim().length() != 10) {
                    if (!b) {
                        b = true;
                    }
                    textInputLayout.setErrorTextAppearance(R.style.error_appearance);
                    textInputLayout.setError(context.getString(R.string.error_max_length_ten));
                }
            }
        }
        return b;
    }

    public boolean isSpinnerNull(Context context, Spinner... spinners) {
        boolean b = false;
        for (Spinner spinner :
                spinners) {
            if (spinner.getSelectedItemPosition() >= 0) {
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

        }
        return b;
    }

    public boolean isSpinnerNull(Context context, Spinner spinner, ArrayList<String> strings) {
        int selectedItemPosition = spinner.getSelectedItemPosition();
        if (selectedItemPosition >= 0) {
            if (strings.size() > selectedItemPosition) {
                if (strings.get(selectedItemPosition) == null || strings.get(selectedItemPosition).trim().isEmpty()) {
                    TextView errorText = (TextView) spinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(context.getColor(R.color.colorAccent));//just to highlight that this is an error
                    errorText.setText(context.getString(R.string.error_cannot_be_empty));

                } else {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isMultiSelectorNull(Context context, LinearLayout... linearLayouts) {
        boolean notNull = true;
        for (LinearLayout linearLayout :
                linearLayouts) {
            boolean checked = false;
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                if (linearLayout.getChildAt(i) instanceof CheckBox) {
                    if (((CheckBox) linearLayout.getChildAt(i)).isChecked()) {
                        checked = true;
                        break;
                    }
                }
            }
            if (!checked) {
                linearLayout.setBackground(context.getResources().getDrawable(R.drawable.spinner_back_red, null));
                linearLayout.findViewById(R.id.ll_wrapperCannotBeEmpty).setVisibility(View.VISIBLE);
                if (notNull) {
                    notNull = false;
                }
            } else {
                linearLayout.setBackground(context.getResources().getDrawable(R.drawable.spinner_back, null));
                linearLayout.findViewById(R.id.ll_wrapperCannotBeEmpty).setVisibility(View.INVISIBLE);
            }
        }

        return !notNull;
    }


    public void setSelectedItemForSpinner(int value, Integer[] values, Spinner spinner) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == value) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public void setSelectedItemsForMultiSelection(String array, Integer[] values, LinearLayout linearLayout) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(array);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        //
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < jsonArray.length(); j++) {
                try {
                    if (values[i] == jsonArray.getInt(j)) {
                        if (linearLayout.getChildAt(i) instanceof CheckBox) {
                            ((CheckBox) linearLayout.getChildAt(i)).setChecked(true);
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void setOptionsMenuRemove(Menu menu, String dateTime_) {
        if (dateTime_ != null) {
            MenuItem uploadMenuItem = menu.add(0, 0, 0, "Remove").setShortcut('3', 'c').setIcon(R.drawable.ic_delete_black_24dp);
            uploadMenuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    public void configIntent(final Context context, LayoutInflater inflater, final SubHomePojos homePojos, final Intent intent, boolean hasGeneratedId) {
        if (homePojos.isRepeat()) {
            ArrayList<String> strings = new ArrayList<>();
            Cursor cursor;
            if (hasGeneratedId) {
                cursor = getCursor(homePojos.getTableName(), "CBONum", getCBONum(context), "generatedId", getSelectedGenId(context));

            } else {
                cursor = getCursorBySelectedCBONum(context, homePojos.getTableName());

            }
            while (cursor.moveToNext()) {
                strings.add(cursor.getString(cursor.getColumnIndex("dateTime_")));
            }
            if (strings.size() != 0) {
                View v = inflater.inflate(R.layout.dialog_which_one, null);
                LinearLayout itemsLinearLayout = v.findViewById(R.id.ll_itemsWrapperrDialogWhichOne);
                final AlertDialog builder = new AlertDialog.Builder(context)
                        .setView(v)
                        .create();

                for (String dateTime_ :
                        strings) {
                    loadView(context, dateTime_, homePojos.getTableName(), inflater, builder, itemsLinearLayout, intent);
                }

                v.findViewById(R.id.btn_addNewDialogWhichOne).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s = null;
                        intent.putExtra("dateTime_", s);
                        intent.putExtra("tableName", homePojos.getTableName());
                        context.startActivity(intent);
                        builder.dismiss();
                    }
                });

                builder.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        builder.dismiss();
                    }
                });

                builder.show();


            } else {
                intent.putExtra("tableName", homePojos.getTableName());
                intent.putExtra("dateTime_", getRequiredDateTimeByGenId(context, homePojos, hasGeneratedId));
                context.startActivity(intent);
            }

        } else {
            intent.putExtra("tableName", homePojos.getTableName());
            intent.putExtra("dateTime_", getRequiredDateTimeByGenId(context, homePojos, hasGeneratedId));
            context.startActivity(intent);
        }
    }

    private String getRequiredDateTimeByGenId(Context context, SubHomePojos homePojos, boolean hasGeneratedId) {
        if (hasGeneratedId) {
            return getSingleStringFromDBByCBONum(context, "dateTime_", homePojos.getTableName(), "generatedId", getSelectedGenId(context));

        }
        return getSingleStringFromDBByCBONum(context, "dateTime_", homePojos.getTableName());

    }

    private void loadView(final Context context, final String dateTime_, final String tableName, LayoutInflater inflater, final AlertDialog builder, LinearLayout itemsLinearLayout, final Intent intent) {
        View view = inflater.inflate(R.layout.item_sub_button, null);
        view.findViewById(R.id.tv_badgeItemSubButton).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tv_titleItemSubButton)).setText(dateTime_);
        CardView cardView = view.findViewById(R.id.card_itemSubButton);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("dateTime_", dateTime_);
                intent.putExtra("tableName", tableName);
                context.startActivity(intent);
                builder.dismiss();
            }
        });

        itemsLinearLayout.addView(view);
    }

    public AlertDialog getRequiredAlertDialog(Context context, int id) {
        AlertDialog builder = builder = new AlertDialog.Builder(context)
                .create();
        if (id == MyConstants.REMOVE_DIALOG) {
            builder.setTitle("Remove?");
            builder.setMessage("Are you sure you need to remove this entry?");

        } else if (id == MyConstants.UPLOAD_DIALOG) {
            builder.setIcon(R.drawable.ic_info_outline);
            builder.setTitle("Seems you've done collecting data");

        } else if (id == MyConstants.SIGNOUT_DIALOG) {
            builder.setIcon(R.drawable.ic_warning_black_24dp);
            builder.setTitle("Are you sure you need to Sign Out?");

        }
        return builder;
    }

    public void removeEntry(final Context context, final String tableName, final String dateTime_, final boolean hasGeneratedId) {
        final AlertDialog builder = getRequiredAlertDialog(context, MyConstants.REMOVE_DIALOG);
        builder.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (tableName.equalsIgnoreCase("basicInfo")) {
                    checkForDependencies(context, tableName, dateTime_, hasGeneratedId);
                    builder.dismiss();
                    return;
                } else {
                    deleteFromDBByCBONum(context, tableName, "dateTime_", dateTime_, hasGeneratedId);

                }
                //
                if (tableName.equalsIgnoreCase("dist")) {
                    ((Distribution) context).removeMatTypes();
                }
                //

                showToast("Removed", context, MyConstants.MESSAGE_SUCCESS);
                ((Activity) context).onBackPressed();
                builder.dismiss();
            }
        });
        builder.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.dismiss();
            }
        });
        builder.show();
    }

    private void checkForDependencies(Context context, String tableName, String dateTime_, boolean hasGeneratedId) {
        String[] strings = new String[]{"waterAd", "waterQ", "houseHold", "waterH", "waterS", "obsEU"};
        boolean canRemove = true;
        for (String s :
                strings) {
            if (getCursor(s, "generatedId", getSelectedGenId(context)).getCount() != 0) {
                canRemove = false;
                break;
            }
        }
        if (canRemove) {
            deleteFromDBByCBONum(context, tableName, "dateTime_", dateTime_, hasGeneratedId);
            setSharedPref(context, MyConstants.ACTION_SELECTED_GENERATED_ID, null);
            showToast("Removed", context, MyConstants.MESSAGE_SUCCESS);
            ((Activity) context).onBackPressed();
        } else {
            showToast("Cannot remove when have dependencies", context, MyConstants.MESSAGE_ERROR);
        }
    }

    public void configHeaderBar(Context context, String dateTime_, RelativeLayout headerBar) {
        TextView title = headerBar.findViewById(R.id.tv_titleTop);
        if (dateTime_ == null) {
            title.setText("New Entry");
        } else {
            title.setText(dateTime_);
            headerBar.setBackgroundColor(context.getResources().getColor(R.color.colorDarkGray, null));
        }

    }


    public ArrayList<String> getConfiguredStringForInsert(String... strings) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String s :
                strings) {
            arrayList.add("'" + s + "'");
        }

        return arrayList;
    }

    public String getCheckedValues(Integer[] integers, LinearLayout linearLayout) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) linearLayout.getChildAt(i);
                if (checkBox.isChecked()) {
                    jsonArray.put(integers[i]);
                }
            }
        }
        return jsonArray.toString();

    }

    public ArrayList<String> getAllTableNames(boolean isToFinishUpload) {
        ArrayList<String> tableArrayList = new ArrayList<>();
        if (!isToFinishUpload) {
            tableArrayList.add("wsp_droplist");
            tableArrayList.add("user");
            tableArrayList.add("locations");
            tableArrayList.add("cboB");
        }
//
        tableArrayList.add("cboBasicDetails");
        tableArrayList.add("connectionD");
        tableArrayList.add("coverageInfo");
        tableArrayList.add("cboBasicDetailsFilled");
        tableArrayList.add("connectionDFilled");
        tableArrayList.add("coverageInfoFilled");
        tableArrayList.add("pop");
        tableArrayList.add("existingQA");
        tableArrayList.add("catchment");
        tableArrayList.add("treatment");
        tableArrayList.add("dist");
        tableArrayList.add("distTypes");
        tableArrayList.add("clim");
        tableArrayList.add("gov");
        tableArrayList.add("obsWS");
        tableArrayList.add("basicInfo");
        tableArrayList.add("waterAd");
        tableArrayList.add("waterQ");
        tableArrayList.add("houseHold");
        tableArrayList.add("WaterH");
        tableArrayList.add("waterS");
        tableArrayList.add("obsEU");
        return tableArrayList;
    }
}
