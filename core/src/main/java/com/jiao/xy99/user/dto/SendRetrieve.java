package com.jiao.xy99.user.dto;

import java.util.Date;

/**
 * 发送次数限制DTO
 * @author xieshuai
 * @date 2018/4/7.
 */
public class SendRetrieve {
    private Long userId;
    //类型:NAME/PWD
    private String retrieveType;
    //时间
    private Date retrieveDate;
    
    private String email;
    
    private String phoneNo;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRetrieveType() {
        return retrieveType;
    }

    public void setRetrieveType(String retrieveType) {
        this.retrieveType = retrieveType == null ? null : retrieveType.trim();
    }

    public Date getRetrieveDate() {
        return retrieveDate;
    }

    public void setRetrieveDate(Date retrieveDate) {
        this.retrieveDate = retrieveDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
}