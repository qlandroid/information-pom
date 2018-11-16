package com.information.pojo;

import java.util.Date;

public class BProjectAuditRecord {
    private Integer id;

    private Integer bProjectId;

    private Integer adminUserId;

    private Date createDate;

    private String auditDetails;

    private String auditStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getbProjectId() {
        return bProjectId;
    }

    public void setbProjectId(Integer bProjectId) {
        this.bProjectId = bProjectId;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAuditDetails() {
        return auditDetails;
    }

    public void setAuditDetails(String auditDetails) {
        this.auditDetails = auditDetails == null ? null : auditDetails.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }
}