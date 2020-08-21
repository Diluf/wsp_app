package com.debugsire.wsp.Algos.WebService.Model;

/**
 * Created by User on 11/09/2017.
 */

public class WebRefferences {
//    private static final String IP = "http://192.168.8.109:8080/WSP_Service";
//    private static final String IP = "http://192.168.8.181:8080/WSP_Service";
//    private static final String IP = "http://172.20.10.14:8080/WSP_Service";
    private static final String IP = "http://rwss.lk:8080/WSP_Service";
    public static final String IMAGE_UPLOAD_URL = IP + "/FileUploadServlet";
    public static String url = IP + "/MyWebService";
    public static int timeOut = 30000;
    public static String nameSpace = "http://MyWebService/";



    public static class hello {
        public static String methodName = "hello";
    }

    public static class getDLValues {
        public static String methodName = "getDLValues";
    }

    public static class login {
        public static String methodName = "login";
    }

    public static class getDSDandCBO {
        public static String methodName = "getDSDandCBO";
    }

    public static class downloadByCbo {
        public static String methodName = "downloadByCbo";
    }

    public static class getLocation {
        public static String methodName = "getLocation";
    }




}
