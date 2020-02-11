package com.debugsire.wsp.Algos.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by User on 11/09/2017.
 */

public class MyDB {

    private static SQLiteDatabase database;

    public static void initDatabase(Context context) {
        createTables(context);
    }

    private static void createDB(Context context) {
        if (database == null) {
            database = context.openOrCreateDatabase("_WSP_", Context.MODE_PRIVATE, null);
        }
    }


    public static void setData(String query) {
        Log.d("-=-=-=--", "getData: " + query);
        database.execSQL(query);
    }

    public static Cursor getData(String query) {
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }

    private static void createTables(Context context) {
        createDB(context);
//-------------------------------------------
//-------------------------------------------
//-------------------------------------------
//-------------------------------------------
//-------------------------------------------
//        database.execSQL("CREATE TABLE IF NOT EXISTS user (" +
//                " userName VARCHAR, " +
//                " password VARCHAR, " +
//                " name_ VARCHAR, " +
//                " desig VARCHAR, " +
//                " status VARCHAR," +
//                " dateTime_ DATETIME" +
//                " )");


        database.execSQL("CREATE TABLE IF NOT EXISTS wsp_droplist (" +
                " id INTEGER, " +
                " ref_section VARCHAR, " +
                " value INTEGER, " +
                " display_label VARCHAR, " +
                " userOwned VARCHAR, " +
                " dateTime_ DATETIME" +
                " )");


        database.execSQL("CREATE TABLE IF NOT EXISTS user (" +
                " userName VARCHAR, " +
                " password VARCHAR, " +
                " name_ VARCHAR, " +
                " desig VARCHAR, " +
                " status VARCHAR," +
                " dateTime_ DATETIME" +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS locations (" +
                " idGnd VARCHAR, " +
                " idDsd VARCHAR, " +
                " dsd VARCHAR, " +
                " gnd VARCHAR, " +
                " dateTime_ DATETIME" +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS cboB (" +
                " CBONum VARCHAR," +
                " name VARCHAR, " +
                " street VARCHAR, " +
                " road VARCHAR," +
                " village VARCHAR," +
                " town VARCHAR," +
                " lon VARCHAR," +
                " lat VARCHAR," +
                " height VARCHAR," +
                " acc VARCHAR," +
                " userName VARCHAR, " +
                " idDSD VARCHAR, " +
                " downloaded INTEGER, " +
                " dateTime_ DATETIME " +
                " )");


        database.execSQL("CREATE TABLE IF NOT EXISTS cboBasicDetails (" +
                " CBONum VARCHAR," +
                " name VARCHAR, " +
                " street VARCHAR, " +
                " road VARCHAR," +
                " village VARCHAR," +
                " town VARCHAR," +
                " lon VARCHAR," +
                " lat VARCHAR," +
                " height VARCHAR," +
                " acc VARCHAR," +
                " userName VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS connectionD (" +
                " CBONum VARCHAR, " +
                " dom VARCHAR, " +
                " rel VARCHAR, " +
                " com VARCHAR, " +
                " sch VARCHAR, " +
                " health VARCHAR, " +
                " gov VARCHAR, " +
                " other VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS coverageInfo (" +
                " CBONum VARCHAR, " +
                " village VARCHAR, " +
                " idGnd VARCHAR, " +
                " noOfHHold VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");


        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////
        ///////////////////


        database.execSQL("CREATE TABLE IF NOT EXISTS cboBasicDetailsFilled (" +
                " CBONum VARCHAR," +
                " name VARCHAR, " +
                " assNum VARCHAR, " +
                " road VARCHAR," +
                " village VARCHAR," +
                " town VARCHAR," +
                " manWss INTEGER," +
                " lon VARCHAR," +
                " lat VARCHAR," +
                " height VARCHAR," +
                " acc VARCHAR," +
                " userName VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS connectionDFilled (" +
                " CBONum VARCHAR, " +
                " dom VARCHAR, " +
                " rel VARCHAR, " +
                " com VARCHAR, " +
                " sch VARCHAR, " +
                " health VARCHAR, " +
                " gov VARCHAR, " +
                " other VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS coverageInfoFilled (" +
                " CBONum VARCHAR, " +
                " village VARCHAR, " +
                " idGnd VARCHAR, " +
                " noOfHHold VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");


//        database.execSQL("CREATE TABLE IF NOT EXISTS braPump (" +
//                " name VARCHAR, " +
//                " status VARCHAR, " +
//                " dateTime_ DATETIME" +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS remPump (" +
//                " name VARCHAR, " +
//                " status VARCHAR, " +
//                " dateTime_ DATETIME" +
//                " )");
//
////-------------------------------------------
////-------------------------------------------
////        Management Data
////-------------------------------------------
////-------------------------------------------
////-------------------------------------------
//
//
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS regDetailsOfCBO (" +
//                " CBONum VARCHAR, " +
//                " date_ VARCHAR, " +
//                " inst VARCHAR, " +
//                " num VARCHAR, " +
//                " type VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS coverageInfo (" +
//                " CBONum VARCHAR, " +
//                " village VARCHAR, " +
//                " idGND VARCHAR, " +
//                " noOfHHold VARCHAR, " +
//                " noOfHHoldCovered VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS pointSource (" +
//                " CBONum VARCHAR, " +
//                " pDugWell DOUBLE, " +
//                " pDugWellUsers DOUBLE, " +
//                " uDugWell DOUBLE, " +
//                " uDugWellUsers DOUBLE, " +
//                " hPump DOUBLE, " +
//                " hPumpUsers DOUBLE, " +
//                " sHPump DOUBLE, " +
//                " sHPumpUsers DOUBLE, " +
//                " harv DOUBLE, " +
//                " harvUsers DOUBLE, " +
//                " wScheme DOUBLE, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS cboStaffDetails (" +
//                " CBONum VARCHAR, " +
//                " desig VARCHAR, " +
//                " name VARCHAR, " +
//                " status VARCHAR, " +
//                " mobile VARCHAR, " +
//                " resident VARCHAR," +
//                " prefLang VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS str (" +
//                " CBONum VARCHAR, " +
//                " noOf VARCHAR, " +
//                " lastAGM VARCHAR, " +
//                " theTime VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS contactDetails (" +
//                " CBONum VARCHAR, " +
//                " name VARCHAR, " +
//                " calling VARCHAR, " +
//                " desig VARCHAR, " +
//                " mob1 VARCHAR, " +
//                " mob2 VARCHAR, " +
//                " gender VARCHAR, " +
//                " lang VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS meetingRecord (" +
//                " CBONum VARCHAR, " +
//                " date_ VARCHAR, " +
//                " type VARCHAR, " +
//                " noOfPart VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS commWelfare (" +
//                " CBONum VARCHAR, " +
//                " death DOUBLE, " +
//                " medical DOUBLE, " +
//                " sharing DOUBLE, " +
//                " rel DOUBLE, " +
//                " cul DOUBLE, " +
//                " other VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS bankDetails (" +
//                " CBONum VARCHAR, " +
//                " nameOfBank VARCHAR, " +
//                " nameOfAcc VARCHAR, " +
//                " accNum VARCHAR, " +
//                " accType VARCHAR, " +
//                " accBal VARCHAR, " +
//                " asAt VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS auditingR (" +
//                " CBONum VARCHAR, " +
//                " cboHas VARCHAR, " +
//                " name VARCHAR, " +
//                " phone VARCHAR, " +
//                " lastDate VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS assetMan (" +
//                " CBONum VARCHAR, " +
//                " cboHaving VARCHAR, " +
//                " lastDay VARCHAR, " +
//                " remarks VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS datColl (" +
//                " CBONum VARCHAR, " +
//                " gis VARCHAR, " +
//                " gisPre VARCHAR, " +
//                " mNe VARCHAR, " +
//                " mNePre VARCHAR, " +
//                " DN VARCHAR, " +
//                " DNPre VARCHAR, " +
//                " NW VARCHAR, " +
//                " NWPre VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//
////        -------------------------------------------------------------------------
////        -------------------------------------------------------------------------
////        ------------------------------------Scheme Data Collection------------------------
////        -------------------------------------------------------------------------
////        -------------------------------------------------------------------------
//
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS instr (" +
//                " CBONum VARCHAR," +
//                " device VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_AddNewSo (" +
//                " CBONum VARCHAR," +
//                " sourceNo VARCHAR," +
//                " typeOfSo VARCHAR," +
//                " wet VARCHAR," +
//                " dry VARCHAR," +
//                " safe VARCHAR," +
//                " min VARCHAR," +
//                " qual TEXT," +
//                " treatP TEXT," +
//                " adeq VARCHAR," +
//                " typeOfC VARCHAR," +
//                " owner VARCHAR," +
//                " cond VARCHAR," +
//                " condAns VARCHAR," +
//                " year_ VARCHAR," +
//                " preOfV VARCHAR, " +
//                " pos VARCHAR, " +
//                " adeqEnv VARCHAR, " +
//                " preOfC VARCHAR, " +
//                " preOrE VARCHAR, " +
//                " treatId VARCHAR," +
//                " lon VARCHAR," +
//                " lat VARCHAR," +
//                " height VARCHAR," +
//                " acc VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_Pump (" +
//                " CBONum VARCHAR," +
//                " sourceNo VARCHAR," +
//                " typeOfSo VARCHAR," +
//                " brandOfSo VARCHAR," +
//                " model VARCHAR," +
//                " rated VARCHAR," +
//                " capac VARCHAR," +
//                " install VARCHAR," +
//                " description VARCHAR," +
//                " heightDiff VARCHAR," +
//                " cond VARCHAR," +
//                " condAns VARCHAR," +
//                " addF VARCHAR," +
//                " remark VARCHAR," +
//                " lon VARCHAR," +
//                " lat VARCHAR," +
//                " height VARCHAR," +
//                " acc VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_Reser (" +
//                " CBONum VARCHAR," +
//                " sourceNo VARCHAR," +
//                " type VARCHAR," +
//                " owner VARCHAR," +
//                " water VARCHAR," +
//                " mat VARCHAR," +
//                " noOf VARCHAR," +
//                " year_ VARCHAR," +
//                " capac VARCHAR," +
//                " heightDiff VARCHAR," +
//                " cond VARCHAR," +
//                " condAns VARCHAR," +
//                " lon VARCHAR," +
//                " lat VARCHAR," +
//                " height VARCHAR," +
//                " acc VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_OtherAss (" +
//                " CBONum VARCHAR," +
//                " code VARCHAR," +
//                " type VARCHAR," +
//                " cond VARCHAR," +
//                " condAns VARCHAR," +
//                " owner VARCHAR," +
//                " year_ VARCHAR," +
//                " addF1 VARCHAR," +
//                " addF2 VARCHAR," +
//                " lon VARCHAR," +
//                " lat VARCHAR," +
//                " height VARCHAR," +
//                " acc VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_Leak (" +
//                " CBONum VARCHAR," +
//                " comp VARCHAR," +
//                " why VARCHAR," +
//                " remarks TEXT," +
//                " lon VARCHAR," +
//                " lat VARCHAR," +
//                " height VARCHAR," +
//                " acc VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_Trans (" +
//                " CBONum VARCHAR," +
//                " code VARCHAR," +
//                " len VARCHAR," +
//                " max VARCHAR," +
//                " min VARCHAR," +
//                " year_ VARCHAR," +
//                " cond VARCHAR," +
//                " condAns VARCHAR," +
//                " material VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_Distr (" +
//                " CBONum VARCHAR," +
//                " code VARCHAR," +
//                " len VARCHAR," +
//                " max VARCHAR," +
//                " min VARCHAR," +
//                " year_ VARCHAR," +
//                " cond VARCHAR," +
//                " condAns VARCHAR," +
//                " mat VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_GenPhoto (" +
//                " CBONum VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_DMap (" +
//                " CBONum VARCHAR," +
//                " id VARCHAR," +
//                " type_ VARCHAR," +
//                " desc_ VARCHAR," +
//                " lon VARCHAR," +
//                " lat VARCHAR," +
//                " height VARCHAR," +
//                " acc VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS S_TMap (" +
//                " CBONum VARCHAR," +
//                " id VARCHAR," +
//                " type_ VARCHAR," +
//                " desc_ VARCHAR," +
//                " lon VARCHAR," +
//                " lat VARCHAR," +
//                " height VARCHAR," +
//                " acc VARCHAR," +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//
////        ===========================================
////        ===========================================
////        ===========================================
////        ===========================================
////        ===========================================
////        Operation Maintenance
////        ===========================================
////        ===========================================
////        ===========================================
////        ===========================================
////        ===========================================
//
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS averagePro (" +
//                " CBONum DOUBLE, " +
//                " average DOUBLE, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS issuesRel (" +
//                " CBONum VARCHAR, " +
//                " land VARCHAR, " +
//                " social VARCHAR, " +
//                " fin VARCHAR, " +
//                " man VARCHAR, " +
//                " none VARCHAR, " +
//                " remarks VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS comRel (" +
//                " CBONum VARCHAR, " +
//                " isThereA VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS connectionD (" +
//                " CBONum VARCHAR, " +
//                " dom VARCHAR, " +
//                " rel VARCHAR, " +
//                " com VARCHAR, " +
//                " sch VARCHAR, " +
//                " health VARCHAR, " +
//                " gov VARCHAR, " +
//                " stand VARCHAR, " +
//                " other VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS waterMeter (" +
//                " CBONum VARCHAR, " +
//                " avail VARCHAR, " +
//                " brand VARCHAR, " +
//                " noOfWater VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS addIns (" +
//                " CBONum VARCHAR, " +
//                " air VARCHAR, " +
//                " dist VARCHAR, " +
//                " wash VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS serCharges (" +
//                " CBONum VARCHAR, " +
//                " nDom VARCHAR, " +
//                " nRel VARCHAR, " +
//                " nCom VARCHAR, " +
//                " sDom VARCHAR, " +
//                " sRel VARCHAR, " +
//                " sCom VARCHAR, " +
//                " thereAny VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS waterTar (" +
//                " CBONum VARCHAR, " +
//                " unit VARCHAR, " +
//                " rate VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS opeEx (" +
//                " CBONum VARCHAR, " +
//                " ele VARCHAR, " +
//                " che VARCHAR, " +
//                " lab VARCHAR, " +
//                " pipe VARCHAR, " +
//                " others VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS financialStrength (" +
//                " CBONum VARCHAR, " +
//                " ann DOUBLE, " +
//                " tot DOUBLE, " +
//                " amount DOUBLE, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS detailsOfDivers (" +
//                " CBONum VARCHAR, " +
//                " micro DOUBLE, " +
//                " inter DOUBLE, " +
//                " small DOUBLE, " +
//                " dona DOUBLE, " +
//                " other DOUBLE, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS complainMan (" +
//                " CBONum VARCHAR, " +
//                " isTheCBO VARCHAR, " +
//                " water DOUBLE, " +
//                " waterSolved DOUBLE, " +
//                " acc DOUBLE, " +
//                " accSolved DOUBLE, " +
//                " ser DOUBLE, " +
//                " serSolved DOUBLE, " +
//                " man DOUBLE, " +
//                " manSolved DOUBLE, " +
//                " dry DOUBLE, " +
//                " wet DOUBLE, " +
//                " breakDs DOUBLE, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS cboCap (" +
//                " CBONum VARCHAR, " +
//                " doesTheP VARCHAR, " +
//                " doesTheCBO VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS waterSPlan (" +
//                " CBONum VARCHAR, " +
//                " wSafety VARCHAR, " +
//                " wSharing VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS WQTest (" +
//                " CBONum VARCHAR, " +
//                " carriedOn VARCHAR, " +
//                " type VARCHAR, " +
//                " location VARCHAR, " +
//                " result VARCHAR, " +
//                " resultAns VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS availToi (" +
//                " CBONum VARCHAR, " +
//                " waterS VARCHAR, " +
//                " pitLat VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS sanCover (" +
//                " CBONum VARCHAR, " +
//                " tOfS VARCHAR, " +
//                " wash VARCHAR, " +
//                " tOfC VARCHAR, " +
//                " avail VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS safeAndAd (" +
//                " CBONum VARCHAR, " +
//                " inst VARCHAR, " +
//                " san VARCHAR, " +
//                " drink VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS pdfValues (" +
//                " CBONum VARCHAR, " +
//                " com VARCHAR, " +
//                " built VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS obFrom (" +
//                " CBONum VARCHAR, " +
//                " entText VARCHAR, " +
//                " dateTime_ DATETIME, " +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//
////        ======================================
////        ======================================
////        ======================================
////        ======================================
////        ======================================
////        ======================================
////        ======================================
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS images (" +
//                " CBONum VARCHAR," +
//                " path TEXT," +
//                " dateTime_ VARCHAR," +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//        database.execSQL("CREATE TABLE IF NOT EXISTS pdfs (" +
//                " CBONum VARCHAR, " +
//                " path TEXT, " +
//                " type VARCHAR, " +
//                " dateTime_ DATETIME," +
//                " userName VARCHAR, " +
//                " application VARCHAR " +
//                " )");
//
//
    }

}