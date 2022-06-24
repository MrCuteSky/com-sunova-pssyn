package com.sunova.psinfo.entities;

public class PositionRel {
    private String person_number;
    private String position_id;
    private String user_id;

    public void setPerson_number(String person_number) {
        this.person_number = person_number;
    }

    public void setPosition_id(String position_id) {
        this.position_id = position_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPerson_number() {
        return person_number;
    }

    public String getPosition_id() {
        return position_id;
    }

    public String getUser_id() {
        return user_id;
    }
}
