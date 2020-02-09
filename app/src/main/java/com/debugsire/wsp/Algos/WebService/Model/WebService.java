package com.debugsire.wsp.Algos.WebService.Model;

import android.os.StrictMode;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by User on 11/09/2017.
 */

public class WebService {

    private static void makeThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    public static String sendRequest(String methodName, PropertyInfo propertyInfo) throws Exception {
        makeThreadPolicy();
        String res = "--";

        SoapObject req = new SoapObject(WebRefferences.nameSpace, methodName);
        req.addProperty(propertyInfo);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.setOutputSoapObject(req);

        assignWebPath();
        HttpTransportSE transportSE = new HttpTransportSE(WebRefferences.url, WebRefferences.timeOut);
        transportSE.call(methodName, envelope);

        res = envelope.getResponse().toString();

        return res;
    }

    private static void assignWebPath() {

    }

}
