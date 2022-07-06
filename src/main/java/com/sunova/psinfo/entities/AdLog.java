package com.sunova.psinfo.entities;

import java.util.Date;

public class AdLog {
    private Integer id;
    private String userid;
    private String password;
    private Date modifyTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public AdLog(String userid,String password){
        this.userid = userid;
        this.password = password;
    }
}
