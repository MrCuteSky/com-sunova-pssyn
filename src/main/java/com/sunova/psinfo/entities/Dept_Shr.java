package com.sunova.psinfo.entities;

import java.util.Date;

public class Dept_Shr {
    private String superior;
    private Date fcreateTime;
    private String supFnumber;
    private String name;
    private String supname;
    private String easdept_id;
    private String fEffectDate;
    private String fnumber;
    private String endflag;
    private String sortCode;
    private Date flastUpdateTime;
    private String status;

    public String getSuperior() {
        return superior;
    }

    public Date getFcreateTime() {
        return fcreateTime;
    }

    public String getSupFnumber() {
        return supFnumber;
    }

    public String getName() {
        return name;
    }

    public String getSupname() {
        return supname;
    }

    public String getEasdept_id() {
        return easdept_id;
    }

    public String getfEffectDate() {
        return fEffectDate;
    }

    public String getFnumber() {
        return fnumber;
    }

    public String getEndflag() {
        return endflag;
    }

    public String getSortCode() {
        return sortCode;
    }

    public Date getFlastUpdateTime() {
        return flastUpdateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    public void setFcreateTime(Date fcreateTime) {
        this.fcreateTime = fcreateTime;
    }

    public void setSupFnumber(String supFnumber) {
        this.supFnumber = supFnumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupname(String supname) {
        this.supname = supname;
    }

    public void setEasdept_id(String easdept_id) {
        this.easdept_id = easdept_id;
    }

    public void setfEffectDate(String fEffectDate) {
        this.fEffectDate = fEffectDate;
    }

    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    public void setEndflag(String endflag) {
        this.endflag = endflag;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public void setFlastUpdateTime(Date flastUpdateTime) {
        this.flastUpdateTime = flastUpdateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
