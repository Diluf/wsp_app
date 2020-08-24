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
        Log.d("-=-=-=--", "setData: " + query);
        database.execSQL(query);
    }

    public static Cursor getData(String query) {
        Log.d("-=-=-=--", "getData: " + query);
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


        //From server
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
                " id VARCHAR," +
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
                " id VARCHAR," +
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
                " uploadStarted VARCHAR," +
                " userName VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS connectionD (" +
                " id VARCHAR," +
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
                " id VARCHAR," +
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
                " id VARCHAR," +
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
                " id VARCHAR," +
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
                " id VARCHAR," +
                " CBONum VARCHAR, " +
                " village VARCHAR, " +
                " idGnd VARCHAR, " +
                " noOfHHold VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");
//
//
//
        database.execSQL("CREATE TABLE IF NOT EXISTS pop (" +
                " CBONum VARCHAR, " +
                " male VARCHAR, " +
                " female VARCHAR, " +
                " less VARCHAR, " +
                " eighteen VARCHAR, " +
                " elder VARCHAR, " +
                " noOfDis VARCHAR, " +
                " dateTime_ DATETIME " +
                " )");


//
////-------------------------------------------
////-------------------------------------------
////        WaterSafetyAndClimate Data
////-------------------------------------------
////-------------------------------------------
////-------------------------------------------
//
//
//
        database.execSQL("CREATE TABLE IF NOT EXISTS existingQA (" +
                " CBONum VARCHAR, " +
                " wsp VARCHAR, " +
                " wqp VARCHAR, " +
                " wqt VARCHAR, " +
                " aow VARCHAR, " +
                " moc VARCHAR," +
                " foa VARCHAR," +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS catchment (" +
                " CBONum VARCHAR, " +
                " catchName VARCHAR, " +
                " area VARCHAR, " +
                " loca VARCHAR, " +
                " nature VARCHAR, " +
                " riskOf VARCHAR, " +
                " riskFor VARCHAR, " +
                " issues VARCHAR, " +
                " riskMit VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");


        database.execSQL("CREATE TABLE IF NOT EXISTS treatment (" +
                " CBONum VARCHAR, " +
                " sType VARCHAR, " +
                " sProt VARCHAR, " +
                " intake VARCHAR, " +
                " avail VARCHAR, " +
                " indi VARCHAR, " +
                " spec VARCHAR, " +
                " other VARCHAR, " +
                " wq VARCHAR, " +
                " currentR VARCHAR, " +
                " riskMit VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

//        dist start
        database.execSQL("CREATE TABLE IF NOT EXISTS dist (" +
                " CBONum VARCHAR, " +
                " distName VARCHAR, " +
                " meter VARCHAR, " +
                " numCon VARCHAR, " +
                " exp VARCHAR, " +
                " inter VARCHAR," +
                " serv VARCHAR," +
                " ident VARCHAR," +
                " riskMit VARCHAR," +
                " overAll VARCHAR," +
                " uniqueId VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");
        database.execSQL("CREATE TABLE IF NOT EXISTS distTypes (" +
                " uniqueId VARCHAR, " +
                " dateTime_ DATETIME, " +
                " mat VARCHAR, " +
                " diam VARCHAR, " +
                " un VARCHAR, " +
                " len VARCHAR " +
                " )");
//        dist end

        database.execSQL("CREATE TABLE IF NOT EXISTS clim (" +
                " CBONum VARCHAR, " +
                " isTheW VARCHAR, " +
                " how VARCHAR, " +
                " they VARCHAR, " +
                " effeD VARCHAR, " +
                " effeF VARCHAR, " +
                " waterIsA VARCHAR, " +
                " reas VARCHAR, " +
                " whatAreS VARCHAR, " +
                " whatAreT VARCHAR, " +
                " water VARCHAR, " +
                " drought VARCHAR, " +
                " flood VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS gov (" +
                " CBONum VARCHAR, " +
                " fair VARCHAR, " +
                " inc VARCHAR, " +
                " tra VARCHAR, " +
                " con VARCHAR, " +
                " theReas VARCHAR, " +
                " isThere VARCHAR, " +
                " pot VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS obsWS (" +
                " CBONum VARCHAR, " +
                " obs TEXT, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");


        //
        //
        //
        // EnduaweAss
        //
        //
        //

        database.execSQL("CREATE TABLE IF NOT EXISTS basicInfo (" +
                " CBONum VARCHAR, " +
                " name VARCHAR, " +
                " com VARCHAR, " +
                " desi VARCHAR, " +
                " mob VARCHAR, " +
                " gen VARCHAR, " +
                " pref VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS waterAd (" +
                " CBONum VARCHAR, " +
                " doYouG VARCHAR, " +
                " ifNo VARCHAR, " +
                " whatIs VARCHAR, " +
                " doYouUse VARCHAR, " +
                " ifYes VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");


        database.execSQL("CREATE TABLE IF NOT EXISTS waterQ (" +
                " CBONum VARCHAR, " +
                " forW VARCHAR, " +
                " doYouS VARCHAR, " +
                " pleaseP VARCHAR, " +
                " doYouT VARCHAR, " +
                " whatAre VARCHAR, " +
                " ifWater VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS houseHold (" +
                " CBONum VARCHAR, " +
                " howDoYouS VARCHAR, " +
                " isThe VARCHAR, " +
                " howOften VARCHAR, " +
                " howDoYouC VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS WaterH (" +
                " CBONum VARCHAR, " +
                " didA VARCHAR, " +
                " didY VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS waterS (" +
                " CBONum VARCHAR, " +
                " doM VARCHAR, " +
                " ifYes VARCHAR, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

        database.execSQL("CREATE TABLE IF NOT EXISTS obsEU (" +
                " CBONum VARCHAR, " +
                " obs TEXT, " +
                " dateTime_ DATETIME, " +
                " userName VARCHAR, " +
                " application VARCHAR " +
                " )");

    }

}