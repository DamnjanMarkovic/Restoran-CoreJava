package com.code.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class DTOUserLogging implements Serializable {
    private int idLoging;
    private String userFirstName;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public DTOUserLogging(int idLoging, String userFirstName, Timestamp loginTime, Timestamp logoutTime) {
        this.idLoging = idLoging;
        this.userFirstName = userFirstName;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public int getIdLoging() {
        return idLoging;
    }

    public void setIdLoging(int idLoging) {
        this.idLoging = idLoging;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }
}
