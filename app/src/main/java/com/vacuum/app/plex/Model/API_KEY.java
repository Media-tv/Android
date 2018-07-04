package com.vacuum.app.plex.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class API_KEY {


    @SerializedName("TMBDB_API_KEY")
    @Expose
    private String tMBDBAPIKEY;
    @SerializedName("OPENLOAD_API_Login")
    @Expose
    private String oPENLOADAPILogin;
    @SerializedName("OPENLOAD_API_KEY")
    @Expose
    private String oPENLOADAPIKEY;
    @SerializedName("ADMOB_PLEX_ID")
    @Expose
    private String aDMOBPLEXID;
    @SerializedName("ADMOB_PLEX_INTERSTITIAL_1")
    @Expose
    private String aDMOBPLEXINTERSTITIAL1;
    @SerializedName("ADMOB_PLEX_BANNER_1")
    @Expose
    private String aDMOBPLEXBANNER1;
    @SerializedName("ADMOB_PLEX_BANNER_2")
    @Expose
    private String aDMOBPLEXBANNER2;
    @SerializedName("ADMOB_PLEX_REWARDED_1")
    @Expose
    private String aDMOBPLEXREWARDED1;

    public String getTMBDBAPIKEY() {
        return tMBDBAPIKEY;
    }

    public void setTMBDBAPIKEY(String tMBDBAPIKEY) {
        this.tMBDBAPIKEY = tMBDBAPIKEY;
    }

    public String getOPENLOADAPILogin() {
        return oPENLOADAPILogin;
    }

    public void setOPENLOADAPILogin(String oPENLOADAPILogin) {
        this.oPENLOADAPILogin = oPENLOADAPILogin;
    }

    public String getOPENLOADAPIKEY() {
        return oPENLOADAPIKEY;
    }

    public void setOPENLOADAPIKEY(String oPENLOADAPIKEY) {
        this.oPENLOADAPIKEY = oPENLOADAPIKEY;
    }

    public String getADMOBPLEXID() {
        return aDMOBPLEXID;
    }

    public void setADMOBPLEXID(String aDMOBPLEXID) {
        this.aDMOBPLEXID = aDMOBPLEXID;
    }

    public String getADMOBPLEXINTERSTITIAL1() {
        return aDMOBPLEXINTERSTITIAL1;
    }

    public void setADMOBPLEXINTERSTITIAL1(String aDMOBPLEXINTERSTITIAL1) {
        this.aDMOBPLEXINTERSTITIAL1 = aDMOBPLEXINTERSTITIAL1;
    }

    public String getADMOBPLEXBANNER1() {
        return aDMOBPLEXBANNER1;
    }

    public void setADMOBPLEXBANNER1(String aDMOBPLEXBANNER1) {
        this.aDMOBPLEXBANNER1 = aDMOBPLEXBANNER1;
    }

    public String getADMOBPLEXBANNER2() {
        return aDMOBPLEXBANNER2;
    }

    public void setADMOBPLEXBANNER2(String aDMOBPLEXBANNER2) {
        this.aDMOBPLEXBANNER2 = aDMOBPLEXBANNER2;
    }

    public String getADMOBPLEXREWARDED1() {
        return aDMOBPLEXREWARDED1;
    }

    public void setADMOBPLEXREWARDED1(String aDMOBPLEXREWARDED1) {
        this.aDMOBPLEXREWARDED1 = aDMOBPLEXREWARDED1;
    }

}