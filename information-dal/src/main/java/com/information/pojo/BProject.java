package com.information.pojo;

import com.information.base.BPage;

import java.util.Date;

public class BProject extends BPage{
    public static final String AUDIT_OK = "2";
    public static final String SEND_UP = "1";

    private Integer id;

    private Integer userId;

    private String title;

    private String province;

    private String city;

    private String district;

    private String auditStatus;

    private String sendStatus;

    private Integer bProjectTypeId;

    private String contactsUser;

    private String contactsMobile;

    private Date createDate;

    private Date changeDate;

    private String details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus == null ? null : sendStatus.trim();
    }

    public Integer getbProjectTypeId() {
        return bProjectTypeId;
    }

    public void setbProjectTypeId(Integer bProjectTypeId) {
        this.bProjectTypeId = bProjectTypeId;
    }

    public String getContactsUser() {
        return contactsUser;
    }

    public void setContactsUser(String contactsUser) {
        this.contactsUser = contactsUser == null ? null : contactsUser.trim();
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile == null ? null : contactsMobile.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }
}