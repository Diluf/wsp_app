package com.debugsire.wsp.Algos;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class MyConstants {
    public static final String APP_ID = "2000";


    public static final SimpleDateFormat SIMPLE_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String ALL = "All";

    public static final int SIGN_IN = 1;

    public static final int ACTION_GET_DROP_LIST = 100;
    public static final int ACTION_RESYNCH_DROP_LIST = 101;
    public static final int ACTION_GET_DSD_AND_CBO = 102;
    public static final int ACTION_GET_LOCATION_DSD_GND = 103;
    public static final int ACTION_UPLOAD = 104;

    public static final String ACTION_GET_CBO_DETAILS_DOWNLOADS = "GET_CBO_DETAILS";
    public static final String ACTION_GET_CONNECTION_DOWNLOADS = "GET_CONNECTION";
    public static final String ACTION_GET_COVERAGE_DOWNLOADS = "GET_COVERAGE";
    public static final int ACTION_GET_BY_CBO_NAME_DOWNLOADS = 200;
//    public static final int ACTION_GET_CBO_DETAILS_DOWNLOADS_KEY = 201;
//    public static final int ACTION_GET_CONNECTION_DOWNLOADS_KEY = 202;
//    public static final int ACTION_GET_COVERAGE_DOWNLOADS_KEY = 203;


    ////////Spinner IDs
    ////////Spinner IDs
    ////////Spinner IDs
    ////////Spinner IDs
    ////////Spinner IDs
    ////////Spinner IDs

    //    *****Water Safety And Climate
    //CBOBasicDetails
    public static final String DL_CBOBASICDETAILS_MANAGEMENT_OF_WSS = "BS_1_3";
    //Existing QA
    public static final String DL_EXISTINGQA_WATER_SAFETY = "BS_5_1";
    public static final String DL_EXISTINGQA_WATER_QUAL_PARAM = "BS_5_2";
    public static final String DL_EXISTINGQA_WATER_QUAL_TAP = "BS_5_3";
    public static final String DL_EXISTINGQA_AWARENESS = "BS_5_4";
    public static final String DL_EXISTINGQA_MODE = "BS_5_5";
    public static final String DL_EXISTINGQA_FREQ = "BS_5_6";
    //Catchment
    public static final String DL_CATCHMENT_AREA = "BS_6_2";
    public static final String DL_CATCHMENT_NATURE = "BS_6_3";
    public static final String DL_CATCHMENT_RISKS_OF_WATER = "BS_6_4";
    public static final String DL_CATCHMENT_RISKS_FOR_SOURCE = "BS_6_5";
    public static final String DL_CATCHMENT_ISSUES = "BS_6_6";
    public static final String DL_CATCHMENT_RISK_MITIGATION = "BS_6_7";
    //TreatmentSystem
    public static final String DL_TREATMENTSYSTEM_SOURCE_TYPE = "BS_7_1";
    public static final String DL_TREATMENTSYSTEM_SOURCE_IS_PRO = "BS_7_2";
    public static final String DL_TREATMENTSYSTEM_INTAKE = "BS_7_3";
    public static final String DL_TREATMENTSYSTEM_AVAIL = "BS_7_4";
    public static final String DL_TREATMENTSYSTEM_INDICATE = "BS_4_7_1";
    public static final String DL_TREATMENTSYSTEM_SPECIAL = "BS_4_7_2";
    public static final String DL_TREATMENTSYSTEM_OTHER = "BS_4_7_3";
    public static final String DL_TREATMENTSYSTEM_WATER_Q = "BS_7_5";
    public static final String DL_TREATMENTSYSTEM_CURRENT = "BS_7_6";
    public static final String DL_TREATMENTSYSTEM_RISK_MITIGATION = "BS_7_7";
    //Distribution
    public static final String DL_DISTRIBUTION_METERING = "BS_8_1";
    public static final String DL_DISTRIBUTION_EXPANDABILITY = "BS_8_4";
    public static final String DL_DISTRIBUTION_MATERIAL = "BS_8_2_1";
    public static final String DL_DISTRIBUTION_UNIT = "BS_8_2_2";
    public static final String DL_DISTRIBUTION_INTER = "BS_8_5";
    public static final String DL_DISTRIBUTION_SERVICE = "BS_8_6";
    public static final String DL_DISTRIBUTION_IDENTIFIED = "BS_8_7";
    public static final String DL_DISTRIBUTION_RISK_MITIGATION = "BS_8_8";
    public static final String DL_DISTRIBUTION_OVERALL = "BS_8_9";
    //Climate and DRR
    public static final String DL_CLIMATE_IS_THE_WATER = "BS_9_1";
    public static final String DL_CLIMATE_ON_YES_HOW_IT_IMPACTS = "BS_9_1_1";
    public static final String DL_CLIMATE_ON_YES_WHAT_ARE_THEY = "BS_9_1_2";
    public static final String DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_FLOOD = "BS_9_1_2a";
    public static final String DL_CLIMATE_ON_YES_WHAT_ARE_THE_EFFECTS_DROUGHT = "BS_9_1_2b";
    public static final String DL_CLIMATE_WATER_IS_AVAILABLE = "BS_9_2";
    public static final String DL_CLIMATE_ON_NO_WHAT_ARE_THE_REASONS = "BS_9_2_1";
    public static final String DL_CLIMATE_WHAT_ARE_THE_SOURCES = "BS_9_3";
    public static final String DL_CLIMATE_WHAT_ARE_THE_WATER = "BS_9_4";
    public static final String DL_CLIMATE_OPTIONS_TO_RED = "BS_9_5";
    public static final String DL_CLIMATE_OPTIONS_FOR_MIT_DROUGHT = "BS_9_6";
    public static final String DL_CLIMATE_OPTIONS_FOR_MIT_FLOOD = "BS_9_7";
    //Governance
    public static final String DL_GOVERNANCE_FAIR_LAND = "BS_10_1";
    public static final String DL_GOVERNANCE_INCLUSIVE = "BS_10_2";
    public static final String DL_GOVERNANCE_TRANS = "BS_10_3";
    public static final String DL_GOVERNANCE_CONFLICTS = "BS_10_4";
    public static final String DL_GOVERNANCE_ONYES_CONFLICTS = "BS_10_4_1";
    public static final String DL_GOVERNANCE_IS_THERE = "BS_10_5";
    public static final String DL_GOVERNANCE_ONYES_IS_THERE = "BS_10_5_1";

    //   *****End User Assessment
    //BasicInfo
    public static final String DL_BASIC_INFO_DESIG = "HS_1_3";
    public static final String DL_BASIC_INFO_GENDER = "HS_1_5";
    public static final String DL_BASIC_INFO_PRE_LAN = "HS_1_6";
    //WaterAdequacy
    public static final String DL_WATER_AD_DO_YOU_GET = "HS_2_1";
    public static final String DL_WATER_AD_WHAT_ARE = "HS_2_1_1";
    public static final String DL_WATER_AD_DO_YOU_USE = "HS_2_3";
    public static final String DL_WATER_AD_PLEASE_MENTION = "HS_2_3_1";
    //WaterQuality
    public static final String DL_WATER_QU_FOR_WHAT = "HS_3_1";
    public static final String DL_WATER_QU_DO_YOU_SAT = "HS_3_2";
    public static final String DL_WATER_QU_PLEASE_PRO = "HS_3_2_1";
    public static final String DL_WATER_QU_DO_YOU_TREAT = "HS_3_3";
    public static final String DL_WATER_QU_WHAT_ARE = "HS_3_3_1";
    public static final String DL_WATER_QU_IF_WATER = "HS_3_4";
    //HouseHold
    public static final String DL_HOUSE_HOLD_HOW_DO_YOU_STORE = "HS_4_1";
    public static final String DL_HOUSE_HOLD_IS_THE_D = "HS_4_2";
    public static final String DL_HOUSE_HOLD_HOW_OFTEN = "HS_4_3";
    public static final String DL_HOUSE_HOLD_HOW_DO_YOU_CLEAN = "HS_4_4";
    //WaterHealth
    public static final String DL_WATER_HEALTH_DID_ANY = "HS_5_1";
    public static final String DL_WATER_HEALTH_DID_YOU = "HS_5_2";
    //WaterSaving
    public static final String DL_WATER_SAVING_DO_MEM = "HS_6_1";
    public static final String DL_WATER_SAVING_ON_YES = "HS_6_1_1";

    //Alert Dialog
    public static final int REMOVE_DIALOG = 300;
    public static final int UPLOAD_DIALOG = 301;
    public static final int UPDATE_CONTACT_DIALOG = 302;

    /////////
    /////////
    /////////
    /////////
    /////////
    /////////
    /////////
    /////////
    /////////
    /////////
    /////////
    /////////

    public static final String MY_APPLICATION_ACCESS_COLUMN = "baseline_app";

    public static final int VALID_USER = 600;
    public static final int ERROR_INVALID_USER = 601;
    public static final int ERROR_DENIED_PERMISSION_USER = 602;
    public static final int EXPANDED_USER = 603;

    public static final int MESSAGE_INFO = 700;
    public static final int MESSAGE_ERROR = 701;
    public static final int MESSAGE_SUCCESS = 702;

    public static final String MY_PREFS_NAME = "com.debugsire.wsp";
    public static final String SHARED_CBO_NUM = "SHARED_CBO_NUM";
}
