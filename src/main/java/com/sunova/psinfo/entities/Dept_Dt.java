package com.sunova.psinfo.entities;

public class Dept_Dt {
    private String dept_id;
    private String name;
    private String parent_id;

    public String getDept_id() {
        return dept_id;
    }

    public String getName() {
        return name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }
}
