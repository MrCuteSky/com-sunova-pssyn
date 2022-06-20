package com.sunova.psinfo.entities;

public class Attendance {
    private String address;
    private String checkTime;
    private String corpId;
    private String groupId;
    private String latitude;
    private String bizId;
    private String locationMethod;
    private String userId;
    private String deviceSN;
    private String longitude;
    private String locationResult;

    public String getAddress() {
        return address;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public String getCorpId() {
        return corpId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getBizId() {
        return bizId;
    }

    public String getLocationMethod() {
        return locationMethod;
    }

    public String getUserId() {
        return userId;
    }

    public String getDeviceSN() {
        return deviceSN;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocationResult() {
        return locationResult;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public void setLocationMethod(String locationMethod) {
        this.locationMethod = locationMethod;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDeviceSN(String deviceSN) {
        this.deviceSN = deviceSN;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLocationResult(String locationResult) {
        this.locationResult = locationResult;
    }
}
