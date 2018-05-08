package com.jiao.xy99.user.dto;

import org.apache.commons.lang3.StringUtils;
import java.util.List;

/**
 * 功能DTO.
 *
 * @author njq.niu@hand-china.com
 */

public class Function  {

    private Long functionId;

    private String functionCode;

    private String functionDescription;


    private String functionIcon;

    private Long functionSequence;


    private String functionName;


    private String lang;


    private String moduleCode;


    private Long parentFunctionId;


    private String parentFunctionName;


    private String resourceName;


    private Long resourceId;


    private List<Resource> resources;


    private String type = "PAGE";

    public String getFunctionCode() {
        return functionCode;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public String getFunctionIcon() {
        return functionIcon;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getLang() {
        return lang;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public Long getParentFunctionId() {
        return parentFunctionId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public String getType() {
        return type;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = StringUtils.trim(functionCode);
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    public void setFunctionIcon(String functionIcon) {
        this.functionIcon = functionIcon;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setLang(String sourceLang) {
        this.lang = sourceLang;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = StringUtils.trim(moduleCode);
    }

    public void setParentFunctionId(Long parentFunctionId) {
        this.parentFunctionId = parentFunctionId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFunctionSequence() {
        return functionSequence;
    }

    public void setFunctionSequence(Long functionSequence) {
        this.functionSequence = functionSequence;
    }

    public String getParentFunctionName() {
        return parentFunctionName;
    }

    public void setParentFunctionName(String parentFunctionName) {
        this.parentFunctionName = parentFunctionName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}