package com.sunova.psinfo.entities;

public class Dept_Wc {
    private String id;
    private String name;
    private String parentid;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getParentid() {
        return parentid;
    }
}
