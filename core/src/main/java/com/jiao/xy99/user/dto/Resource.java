package com.jiao.xy99.user.dto;

import org.apache.commons.lang3.StringUtils;


/**
 * 资源DTO.
 *
 * @author wuyichu
 * @author njq.niu@hand-china.com
 */

public class Resource  {


    private Long resourceId;


    private String accessCheck;


    private String description;


    private String loginRequire;


    private String name;


    private String type;


    private String url;

    public String getAccessCheck() {
        return accessCheck;
    }

    public String getDescription() {
        return description;
    }

    public String getLoginRequire() {
        return loginRequire;
    }

    public String getName() {
        return name;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setAccessCheck(String accessCheck) {
        this.accessCheck = accessCheck;
    }

    public void setDescription(String description) {
        this.description = StringUtils.trim(description);
    }

    public void setLoginRequire(String loginRequire) {
        this.loginRequire = StringUtils.trim(loginRequire);
    }

    public void setName(String name) {
        this.name = StringUtils.trim(name);
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setType(String type) {
        this.type = StringUtils.trim(type);
    }

    public void setUrl(String url) {
        this.url = StringUtils.trim(url);
    }


}