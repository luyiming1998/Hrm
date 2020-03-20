package com.lym.entity;

import org.apache.ibatis.type.Alias;

import java.util.Date;

@Alias("user")
public class User {
    private int id;
    private String loginName;
    private String password;
    private int status;
    private Date createDate;
    private String username;
    private String faceUrl;
    private String facePath;

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public void setLoginName(final String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(final int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getFaceUrl() {
        return this.faceUrl;
    }

    public void setFaceUrl(final String faceUrl) {
        this.faceUrl = faceUrl;
    }

    public String getFacePath() {
        return this.facePath;
    }

    public void setFacePath(final String facePath) {
        this.facePath = facePath;
    }
}
