package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class CookClass {
    /*分类id*/
    private Integer cookClassId;
    public Integer getCookClassId(){
        return cookClassId;
    }
    public void setCookClassId(Integer cookClassId){
        this.cookClassId = cookClassId;
    }

    /*分类名称*/
    @NotEmpty(message="分类名称不能为空")
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    /*烹饪分类介绍*/
    @NotEmpty(message="烹饪分类介绍不能为空")
    private String cookClassDesc;
    public String getCookClassDesc() {
        return cookClassDesc;
    }
    public void setCookClassDesc(String cookClassDesc) {
        this.cookClassDesc = cookClassDesc;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonCookClass=new JSONObject(); 
		jsonCookClass.accumulate("cookClassId", this.getCookClassId());
		jsonCookClass.accumulate("className", this.getClassName());
		jsonCookClass.accumulate("cookClassDesc", this.getCookClassDesc());
		return jsonCookClass;
    }}