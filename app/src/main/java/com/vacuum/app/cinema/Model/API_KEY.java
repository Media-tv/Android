package com.vacuum.app.cinema.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class API_KEY {



    @SerializedName("TMBDB_API_KEY")
    @Expose
    private String tMBDBAPIKEY;
    @SerializedName("Alluc_API_KEY")
    @Expose
    private String allucAPIKEY;
    @SerializedName("OPENLOAD_API_Login")
    @Expose
    private String oPENLOADAPILogin;
    @SerializedName("OPENLOAD_API_KEY")
    @Expose
    private String oPENLOADAPIKEY;

    public String getTMBDBAPIKEY() {
        return tMBDBAPIKEY;
    }

    public void setTMBDBAPIKEY(String tMBDBAPIKEY) {
        this.tMBDBAPIKEY = tMBDBAPIKEY;
    }

    public String getAllucAPIKEY() {
        return allucAPIKEY;
    }

    public void setAllucAPIKEY(String allucAPIKEY) {
        this.allucAPIKEY = allucAPIKEY;
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


}
