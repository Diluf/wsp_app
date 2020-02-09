package com.debugsire.wsp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.debugsire.wsp.Algos.DB.MyDB;
import com.debugsire.wsp.Algos.MyConstants;
import com.debugsire.wsp.Algos.WebService.AsyncWebService;
import com.debugsire.wsp.Algos.WebService.Model.WebRefferences;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class SignIn extends AppCompatActivity {

    private static final String TAG = "********";
    Button signIn;
    TextInputLayout userName, pass;
    ImageView loader;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        MyDB.initDatabase(this);
        getSupportActionBar().hide();

        context = this;

        userName = findViewById(R.id.til_userName_SignIn);
        pass = findViewById(R.id.til_pass_SignIn);
        signIn = findViewById(R.id.btn_signIn_SignIn);
        loader = findViewById(R.id.imgV_loader_SignIn);

        Cursor cursor = MyDB.getData("SELECT * FROM user");
        if (cursor.getCount() != 0) {
            startActivity(new Intent(SignIn.this, AvailableCBO.class));
            finish();
            return;
        }


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userName", userName.getEditText().getText().toString().trim());
                    jsonObject.put("password", pass.getEditText().getText().toString().trim());
                    jsonObject.put("columnName", MyConstants.MY_APPLICATION_ACCESS_COLUMN);
                    Log.d(TAG, "onClick: " + jsonObject);

                    new AsyncWebService(SignIn.this, MyConstants.SIGN_IN)
                            .execute(WebRefferences.login.methodName, jsonObject.toString());
//                    new AsyncWebService(SignIn.this, 15000)
//                            .execute(WebRefferences.hello.methodName, jsonObject.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }


//                startActivity(new Intent(SignIn.this, AvailableCBO.class));

            }
        });
    }

    public void showProgress(boolean isToShow) {
        if (isToShow) {
//            loader.setVisibility(View.VISIBLE);
            if (loader.getAnimation() == null) {
                loader.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_infinite));
            }
            signIn.setVisibility(View.GONE);
        } else {
            signIn.setVisibility(View.VISIBLE);
//            loader.setVisibility(View.GONE);
//            loader.getAnimation().cancel();
//            loader.getAnimation().reset();
        }
    }


}
